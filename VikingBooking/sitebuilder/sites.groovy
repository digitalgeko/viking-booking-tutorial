def build(b) {
	b.site("Guest") {
		layout("Home") {
			override true
			friendlyURL "/home"
			layoutTemplateId "1_column"
			column(1) {
				portlet "vikingbookingportlet_WAR_VikingBooking"
				portlet "1_WAR_calendarportlet"
			}
		}

		layout("Schedule Appointment") {
			override true
			friendlyURL "/schedule-appointment"
			layoutTemplateId "1_column"
			isPrivateLayout false
			isHidden true

			column(1) {
				portlet "scheduleappointmentportlet_WAR_VikingBooking"
			}
		}
	}
}