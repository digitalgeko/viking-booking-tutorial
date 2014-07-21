package controllers

import models.EventHibernate
import models.EventMongo
import models.UserAvailabilityHibernate
import models.UserAvailabilityMongo
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

		println Conf.properties.persistance.database
		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailabilityMongo.forUserId(userId)
		} else {
			userAvailability = UserAvailabilityHibernate.forUserId(userId)
		}

		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAppointment() {
		def event

		if (Conf.properties.persistance.database == 'mongo'){
			event = binder.fromJsonBody(EventMongo.class)
		} else {
			event = binder.fromJsonBody(EventHibernate.class)
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