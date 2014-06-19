package controllers

import com.liferay.portal.kernel.cal.TZSRecurrence
import com.liferay.portal.kernel.util.CalendarUtil
import com.liferay.portal.kernel.util.StringPool
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil
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
		def formParams = binder.fromJsonBody(Map.class)

		h.serviceContext.scopeGroupId = formParams.userId;
		CalendarUtil.
		CalEventLocalServiceUtil.addEvent(
				formParams.userId,
				"Appointment",
				StringPool.BLANK,
				StringPool.BLANK,
				formParams.startMonth,
				formParams.startDay,
				formParams.startYear,
				formParams.startHour,
				0,
				1,
				0,
				false,
				true,
				"appointment",
				false,
				new TZSRecurrence(),
				0,
				0,
				0,
				h.serviceContext
		)
		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}