package selenium


import geb.spock.GebSpec
import pages.LoginPage
import pages.ScheduleAppointmentsPage
import pages.VikingBookingHomePage

class SetUserAvailabilityTest extends GebSpec {

	def "first result for wikipedia search should be wikipedia"() {

		given:
		browser.baseUrl = "http://localhost:8080"
		to VikingBookingHomePage

		expect:
		at LoginPage
		emailField.value("test@liferay.com")
		passwordField.value("test")
		loginButton.click()

		at VikingBookingHomePage

		when:
		setAvailabilityButton.click()
		$("input", 0, "checklist-value": "hour", "type": "checkbox").value(true)
		saveButton.click()

		then:
		waitFor { successMessage.isDisplayed() }

		and:
		appointmentsLink.click()
		at ScheduleAppointmentsPage

		when:
		firstAvailableDay.click()
		firstAvailableHour.click()

		nameField.value("Test name")
		emailField.value("test@email.com")
		phoneField.value("5555 5555")
		noteField.value("This is a test")

		saveButton.click()

		then:
		waitFor { successMessage.isDisplayed() }

	}
}
