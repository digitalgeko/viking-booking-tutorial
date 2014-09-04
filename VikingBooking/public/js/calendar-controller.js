VikingBookingApp.controller('CalendarController', ['$scope', '$http', '$modal', function($scope, $http, $modal) {

	$scope.init = function(portletId) {
		$scope.portletData = VK.getPortletData(portletId);
	};

	$scope.events =	[];
	$http.get($scope.portletData.getEventsAction()).success(function(data) {
		_.each(data, function (ev) {
			var start = new Date(ev.dateTimestamp);
			var end = new Date(moment(start).add('hours', 1));
			$scope.events.push ({
				title: ev.name,
				start: start,
				end: end,
				allDay: false,
				name: ev.name,
				email: ev.email,
				phone: ev.phone,
				note: ev.note
			});
		});
	});
	
	$scope.uiConfig = {
		calendar:{
			header:{
				left: 'title',
				center: '',
				right: 'today prev,next'
			},
			eventClick: function(calendarEvent, clickEvent) {
				var modalInstance = $modal.open({
					templateUrl: 'calendar-event-details.html',
					controller: 'CalendarEventDetailsController',
					resolve: {
						calendarEvent: function () {
							return calendarEvent;
						}
					}
				});
			}
		}
	};

	$scope.eventSources = [$scope.events];

}]);


VikingBookingApp.controller('CalendarEventDetailsController', ['$scope', '$modalInstance', 'calendarEvent', function ($scope, $modalInstance, calendarEvent) {

	$scope.calendarEvent = calendarEvent;
	console.log(calendarEvent);
	
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
}]);