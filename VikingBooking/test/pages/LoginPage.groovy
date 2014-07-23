package pages

import geb.Page

/**
 * User: mardo
 * Date: 7/23/14
 * Time: 9:41 AM
 */
class LoginPage extends Page {

	static at = {
		$("section.portlet", 0, id:'portlet_58')
	}

	static content = {
		emailField { $("#_58_login") }
		passwordField { $("#_58_password") }

		loginButton { $("button", 0, type:"submit") }
	}


}
