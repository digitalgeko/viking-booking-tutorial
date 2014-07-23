package pages

import geb.Page

/**
 * User: mardo
 * Date: 7/23/14
 * Time: 8:42 AM
 */
class VikingBookingHomePage extends Page{

	static url = "/group/guest/home"

	static at = {
		getPageUrl() == url
	}

	static content = {

		setAvailabilityButton { $("#set-availability-button") }
		saveButton { $("#save-availability-button") }
		successMessage { $("#availability-success-message") }
		appointmentsLink { $("#appointments-link") }

	}
}
