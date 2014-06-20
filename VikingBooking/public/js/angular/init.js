VikingBookingApp = angular.module('VikingBookingApp', 
	[
		"checklist-model",
		"ui.bootstrap",
		"ui.calendar"
	]
);

var htmlBody = document.getElementsByTagName("body")[0]

if (htmlBody.getAttribute("ng-app") == null) {
	angular.element(document).ready(function() {
		angular.bootstrap(document, ['VikingBookingApp']);
	});
};
