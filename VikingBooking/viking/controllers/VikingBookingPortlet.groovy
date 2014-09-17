package controllers

import com.liferay.portal.util.PortalUtil
import models.hibernate.HoursList
import models.morphia.UserAvailability
import nl.viking.Conf
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class VikingBookingPortlet extends Controller {

    @Render
    def view() {
     	def appointmentsURL = PortalUtil.getPortalURL(request) + "/web/vikingbooking/schedule-appointment/-/schedule-appointment-portlet/render/view/${h.user.userId}"
		[appointmentsURL: appointmentsURL]
    }

	@Resource(mode="view")
	def getAvailability() {
		def userAvailability
		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(h.user.userId)
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(h.user.userId)
		}
		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAvailability() {
		def availableHours = binder.fromJsonBody(Object.class)
		def userAvailability

		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(h.user.userId)
			userAvailability.availableHours = availableHours
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(h.user.userId)
			userAvailability.availableHoursAux = availableHours.collectEntries {
				def hoursList = userAvailability.availableHoursAux?.get(it.key)
				if (hoursList == null) {
					hoursList = new HoursList()
				}
				hoursList.hours = it.value
				hoursList.save()
				[(it.key): hoursList]
			}
		}
		userAvailability.save()
		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}