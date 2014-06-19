VikingBookingApp.controller('ScheduleAppointmentController', ['$scope', '$http', function($scope, $http) {

	$scope.formatHour = function(hour) {
		return moment(hour, "H").format("HH:mm")
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
				
				$scope.newAppointment.startMonth = dateMoment.month();
				$scope.newAppointment.startDay = dateMoment.date();
				$scope.newAppointment.startYear = dateMoment.year();
				$scope.newAppointment.startHour = dateMoment.hour();
				$scope.$apply();
			});
		});
	};
	
	$scope.newAppointment = {
		hour: -1
	}
	$scope.saveAppointment = function() {
		$scope.newAppointment.userId = $scope.userId;
		$http.post(saveAppointmentAction({userId: $scope.userId}), $scope.newAppointment).success(function(data) {
			$scope.newAppointment = {
				hour: -1
			};
			$scope.currentWeekDay = null;
		});
	};
}]);