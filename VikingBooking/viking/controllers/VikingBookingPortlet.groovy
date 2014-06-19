package controllers

import com.liferay.portal.util.PortalUtil
import models.UserAvailability
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class VikingBookingPortlet extends Controller {

    @Render
    def view() {
     	def appointmentsURL = PortalUtil.getPortalURL(request) + "/web/guest/schedule-appointment/-/schedule-appointment-portlet/render/view/${h.user.userId}"
		[appointmentsURL: appointmentsURL]
    }

	@Resource(mode="view")
	def getAvailability() {
		def userAvailability = UserAvailability.forUserId(h.user.userId)
		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAvailability() {
		def availableHours = binder.fromJsonBody(Object.class)
		def userAvailability = UserAvailability.forUserId(h.user.userId)
		userAvailability.availableHours = availableHours
		userAvailability.save()
		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}