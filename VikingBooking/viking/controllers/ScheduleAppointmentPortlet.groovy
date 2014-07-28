package controllers

import models.morphia.Event
import models.morphia.UserAvailability
import nl.viking.Conf
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
		def userId = bindLong("userId")

		def userAvailability

		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(userId)
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(userId)
		}

		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAppointment() {
		def event

		if (Conf.properties.persistance.database == 'mongo'){
			event = binder.fromJsonBody(Event.class)
		} else {
			event = binder.fromJsonBody(models.hibernate.Event.class)
		}
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