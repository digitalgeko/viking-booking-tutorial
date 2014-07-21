<script type="text/javascript">
	var getAvailabilityAction = ${jsRoute("ScheduleAppointmentPortlet.getUserAvailability", ['userId'] )}
	var saveAppointmentAction = ${jsRoute("ScheduleAppointmentPortlet.saveAppointment", ['userId'] )}
</script>

<div ng-controller="ScheduleAppointmentController" ng-init="init(${userId?c});">
	<h3>${h.messages.get("select-the-date-for-your-appointment")}</h3>
	<div id="calendar"></div>

	<div ng-show="currentWeekDay != undefined">
		<h3>${h.messages.get("select-the-time")}</h3>
		<button 
			ng-repeat="hour in availableHours[currentWeekDay]" 
			class="btn" 
			ng-class="{'btn-primary':(hour==calendarDate.hour)}" 
			ng-click="calendarDate.hour = hour;">{{formatHour(hour)}}</button>
	</div>

	<div ng-show="calendarDate.hour >= 0">
		<h3>${h.messages.get("provide-your-contact-information")}</h3>

		<form ng-submit="saveAppointment()">
			
			<div class="control-group" ng-class="{error:errors['event.name']}">
				<label class="control-label">${h.messages.get("name")}</label>
				<div class="controls">
					<input ng-model="newAppointment.name" type="text" placeholder="${h.messages.get("name")}">
					<span class="help-inline" ng-if="errors['event.name']">{{errors['event.name']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.email']}">
				<label class="control-label">${h.messages.get("email")}</label>
				<div class="controls">
					<input ng-model="newAppointment.email" type="text" placeholder="${h.messages.get("email")}">
					<span class="help-inline" ng-if="errors['event.email']">{{errors['event.email']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.phone']}">
				<label class="control-label">${h.messages.get("phone")}</label>
				<div class="controls">
					<input ng-model="newAppointment.phone" type="text" placeholder="${h.messages.get("phone")}">
					<span class="help-inline" ng-if="errors['event.phone']">{{errors['event.phone']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.note']}">
				<label class="control-label">${h.messages.get("note")}</label>
				<div class="controls">
					<textarea ng-model="newAppointment.note" rows="3"></textarea>
					<span class="help-inline" ng-if="errors['event.note']">{{errors['event.note']}}</span>
				</div>
			</div>

			<input type="submit" value="${h.messages.get("save-appointment")}">
		</form>
	</div>

	<div ng-show="showSuccessMessage" class="alert alert-success">
		${h.messages.get("appointment-successfully-saved")}
		<button type="button" class="close" ng-click="showSuccessMessage=false">×</button>
	</div>

</div>
