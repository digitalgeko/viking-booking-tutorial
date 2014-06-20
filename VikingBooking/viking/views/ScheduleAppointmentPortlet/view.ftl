<script type="text/javascript">
	var getAvailabilityAction = ${jsRoute("ScheduleAppointmentPortlet.getUserAvailability", ['userId'] )}
	var saveAppointmentAction = ${jsRoute("ScheduleAppointmentPortlet.saveAppointment", ['userId'] )}
</script>

<div ng-controller="ScheduleAppointmentController" ng-init="init(${userId?c});">
	<h3>Select the date for your appointment</h3>
	<div id="calendar"></div>

	<div ng-show="currentWeekDay">
		<h3>Select the time</h3>
		<button 
			ng-repeat="hour in availableHours[currentWeekDay]" 
			class="btn" 
			ng-class="{'btn-primary':(hour==newAppointment.hour)}" 
			ng-click="newAppointment.hour = hour;">{{formatHour(hour)}}</button>
	</div>

	<div ng-show="newAppointment.hour >= 0">
		<h3>Provide your contact information</h3>
		<form ng-submit="saveAppointment()">
			
			<div class="control-group">
				<label class="control-label">${h.messages.get("name")}</label>
				<div class="controls">
					<input ng-model="newAppointment.name" type="text" placeholder="Name">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">${h.messages.get("email")}</label>
				<div class="controls">
					<input ng-model="newAppointment.email" type="text" placeholder="Email">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">${h.messages.get("phone")}</label>
				<div class="controls">
					<input ng-model="newAppointment.phone" type="text" placeholder="Phone">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">${h.messages.get("note")}</label>
				<div class="controls">
					<textarea ng-model="newAppointment.note" rows="3"></textarea>
				</div>
			</div>

			<input type="submit" value="Save appointment">
		</form>
	</div>

</div>
