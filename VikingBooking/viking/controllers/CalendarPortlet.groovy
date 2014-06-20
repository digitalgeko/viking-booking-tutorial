package controllers

import com.liferay.portal.kernel.dao.orm.QueryUtil
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil
import models.Event
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
    	def events = Event.findAll()
		renderJSON(events)
    }

}