<script type="text/javascript">
	var getAvailabilityAction = ${jsRoute("ScheduleAppointmentPortlet.getUserAvailability", ['userId'] )}
	var saveAppointmentAction = ${jsRoute("ScheduleAppointmentPortlet.saveAppointment", ['userId'] )}
</script>

<div ng-controller="ScheduleAppointmentController" ng-init="init(${userId?c});">
	<div id="calendar"></div>

	<div ng-show="currentWeekDay">
		<button 
			ng-repeat="hour in availableHours[currentWeekDay]" 
			class="btn" 
			ng-class="{'btn-primary':(hour==newAppointment.hour)}" 
			ng-click="newAppointment.hour = hour;">{{formatHour(hour)}}</button>
	</div>

	<div ng-show="newAppointment.hour >= 0">
		<form ng-submit="saveAppointment()">
			
			<div class="control-group">
				<label class="control-label" for="inputEmail">Name</label>
				<div class="controls">
					<input ng-model="newAppointment.name" type="text" placeholder="Name">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="inputEmail">Email</label>
				<div class="controls">
					<input ng-model="newAppointment.email" type="text" placeholder="Email">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="inputEmail">Phone</label>
				<div class="controls">
					<input ng-model="newAppointment.phone" type="text" placeholder="Phone">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="inputEmail">Note</label>
				<div class="controls">
					<textarea ng-model="newAppointment.note" rows="3"></textarea>
				</div>
			</div>

			<input type="submit" value="Save appointment">
		</form>
	</div>

</div>
