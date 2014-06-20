def build(b) {
	b.site("Guest") {
		layout("Home") {
			override true
			layoutTemplateId "1_column"
			column(1) {
				portlet "vikingbookingportlet_WAR_VikingBooking"
				portlet "calendarportlet_WAR_VikingBooking"
			}
		}

		layout("Schedule Appointment") {
			override true
			layoutTemplateId "1_column"
			isPrivateLayout false
			isHidden true

			column(1) {
				portlet "scheduleappointmentportlet_WAR_VikingBooking"
			}
		}
	}
}