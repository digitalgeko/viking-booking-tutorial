VikingBookingApp = angular.module('VikingBookingApp', 
	[
		"checklist-model"
	]
);

var htmlBody = document.getElementsByTagName("body")[0]

if (htmlBody.getAttribute("ng-app") == null) {
	angular.element(document).ready(function() {
		angular.bootstrap(document, ['VikingBookingApp']);
	});
};
