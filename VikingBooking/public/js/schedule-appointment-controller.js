VikingBookingApp.controller('ScheduleAppointmentController', ['$scope', '$http', function($scope, $http) {

	$scope.formatHour = function(hour) {
		return moment(hour, "H").format("hh:mm a")
	};

	$scope.init = function(portletId, userId) {
		$scope.portletData = VK.getPortletData(portletId);

		$scope.userId = userId;
		$http.get($scope.portletData.getAvailabilityAction({userId: $scope.userId})).success(function(data) {
			console.log(data)
			$scope.availableHours = data.availableHours || {};
		});
		$(function() {
			$('#calendar').datepicker({}).on('changeDate', function(e) {
				$scope.$apply(function() {
					if (e.date) {
						var dateMoment = moment(e.date)
						$scope.currentWeekDay = dateMoment.day();
						$scope.calendarDate.timestamp = e.date.getTime();	
					} else {
						$scope.currentWeekDay = undefined;
						$scope.calendarDate.timestamp = undefined;
					}
				});
			});
		});
	};
	
	$scope.calendarDate = {};
	$scope.newAppointment = {};

	$scope.saveAppointment = function() {
		$scope.newAppointment.userId = $scope.userId;
		var dateMoment = moment($scope.calendarDate.timestamp).hours($scope.calendarDate.hour);
		
		$scope.newAppointment.dateTimestamp = new Date(dateMoment).getTime();
		$http.post($scope.portletData.saveAppointmentAction({userId: $scope.userId}), $scope.newAppointment).success(function(data) {
			if (data.success) {
				$scope.newAppointment = {};
				$scope.calendarDate.hour = undefined;
				$scope.currentWeekDay = undefined;
				$scope.showSuccessMessage = true;
			} else {
				$scope.errors = data.errors;
				console.log(data);
			}
		});
	};
}]);