package controllers

import models.EventHibernate
import models.EventMongo
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
			events = EventMongo.findAll()
		} else {
			events = EventHibernate.findAll()
		}

		renderJSON(events)
    }

}