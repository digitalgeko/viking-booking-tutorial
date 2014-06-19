VikingBookingApp.controller('AvailabilityController', ['$scope', '$http', function($scope, $http) {
	$scope.weekDays = [
		{index:1, label: "monday"},
		{index:2, label: "tuesday"},
		{index:3, label: "wednesday"},
		{index:4, label: "thursday"},
		{index:5, label: "friday"},
		{index:6, label: "saturday"},
		{index:0, label: "sunday"}
	];
	
	$scope.hours = []
	for (var i = 0; i < 24; i++) {
		$scope.hours.push(i)

	};

	$scope.formatHour = function(hour) {
		return moment(hour, "H").format("HH:mm")
	};

	$http.get(getAvailabilityAction()).success(function(data) {
		$scope.availableHours = data.availableHours || {};
	});
	
	$scope.status = {
		setAvailability: false
	};
	$scope.saveAvailability = function() {
		$http.post(saveAvailabilityAction(), $scope.availableHours).success(function(data) {
			$scope.status.setAvailability = false;
		});
	};
}]);