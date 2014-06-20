VikingBookingApp.controller('ScheduleAppointmentController', ['$scope', '$http', function($scope, $http) {

	$scope.formatHour = function(hour) {
		return moment(hour, "H").format("hh:mm a")
	};

	$scope.init = function(userId) {
		$scope.userId = userId;
		$http.get(getAvailabilityAction({userId: $scope.userId})).success(function(data) {
			console.log(data)
			$scope.availableHours = data.availableHours || {};
		});
		$(function() {
			$('#calendar').datepicker({

			}).on('changeDate', function(e) {
				var dateMoment = moment(e.date)
				$scope.currentWeekDay = dateMoment.day();
				$scope.calendarDateTimestamp = e.date.getTime();
				$scope.$apply();
			});
		});
	};
	
	$scope.newAppointment = {
		hour: -1
	};

	$scope.saveAppointment = function() {
		$scope.newAppointment.userId = $scope.userId;
		var dateMoment = moment($scope.calendarDateTimestamp).hours($scope.newAppointment.hour);
		
		$scope.newAppointment.dateTimestamp = new Date(dateMoment).getTime();
		$http.post(saveAppointmentAction({userId: $scope.userId}), $scope.newAppointment).success(function(data) {
			console.log(data);
			// $scope.newAppointment = {
			// 	hour: -1
			// };
			// $scope.currentWeekDay = null;
		});
	};
}]);