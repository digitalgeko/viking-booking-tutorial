VikingBookingApp.controller('AvailabilityController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {
	$scope.weekDays = [
		{index:0, label: "sunday"},
		{index:1, label: "monday"},
		{index:2, label: "tuesday"},
		{index:3, label: "wednesday"},
		{index:4, label: "thursday"},
		{index:5, label: "friday"},
		{index:6, label: "saturday"}
	];
	
	$scope.hours = []
	for (var i = 0; i < 24; i++) {
		$scope.hours.push(i)

	};

	$scope.formatHour = function(hour) {
		if (hour == 24) hour = 0;
		return moment(hour, "H").format("hh:mm a")
	};

	$http.get(getAvailabilityAction()).success(function(data) {
		$scope.availableHours = data.availableHours || {};
	});
	
	$scope.status = {
		setAvailability: false,
		showSuccessMessage: false
	};

	$scope.showAvailabilityForm = function() {
		$scope.status.setAvailability = !status.setAvailability;
		$scope.status.showSuccessMessage = false;
	};

	$scope.saveAvailability = function() {
		$http.post(saveAvailabilityAction(), $scope.availableHours).success(function(data) {
			$scope.status.setAvailability = false;
			$scope.status.showSuccessMessage = true;
		});
	};

	$scope.messages = function(key) {
		return i18n(key)
	};

}]);