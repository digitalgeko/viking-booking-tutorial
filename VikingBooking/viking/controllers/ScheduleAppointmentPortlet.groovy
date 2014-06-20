package controllers

import com.liferay.portal.kernel.cal.TZSRecurrence
import com.liferay.portal.kernel.util.CalendarUtil
import com.liferay.portal.kernel.util.StringPool
import com.liferay.portal.model.User
import com.liferay.portal.service.UserLocalServiceUtil
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil
import models.Event
import models.UserAvailability
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class ScheduleAppointmentPortlet extends Controller {

    @Render
    def view() {
        def userId = bindLong('userId')
		[userId:userId]
    }

	@Resource(mode="view")
	def getUserAvailability() {
		def userAvailability = UserAvailability.forUserId(bindLong("userId"))
		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAppointment() {
		Event event = binder.fromJsonBody(Event.class)
		validator.validate("event", event)
		if (validator.hasErrors()) {
			return renderJSON([success:false, errors: validator.errors])
		}

		event.save()

		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}