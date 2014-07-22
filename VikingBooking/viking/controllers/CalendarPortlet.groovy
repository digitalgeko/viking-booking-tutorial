package controllers

import models.morphia.Event
import nl.viking.Conf
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class CalendarPortlet extends Controller {

    @Render
    def view() {
        
    }

    @Render
    def edit() {
        
    }

    @Resource(mode="view")
    def getEvents() {
    	def events

		if (Conf.properties.persistance.database == 'mongo'){
			events = Event.findAll()
		} else {
			events = models.hibernate.Event.findAll()
		}

		renderJSON(events)
    }

}