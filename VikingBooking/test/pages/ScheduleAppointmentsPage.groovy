package pages

import geb.Page

/**
 * User: mardo
 * Date: 7/23/14
 * Time: 8:42 AM
 */
class ScheduleAppointmentsPage extends Page{

	static url = "/group/guest/home"

	static at = {
		getPageUrl() == url
	}
	static content = {

		firstAvailableDay { $("td.day", 0) }
		firstAvailableHour { $("button.available-hour", 0) }

		nameField { $("#appointment-name") }
		emailField { $("#appointment-email") }
		phoneField { $("#appointment-phone") }
		noteField { $("#appointment-note") }

		saveButton { $("#save-appointment-button") }

		successMessage { $("#appointment-success-message") }
	}
}
