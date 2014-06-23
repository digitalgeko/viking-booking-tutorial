<script type="text/javascript">
	var saveAvailabilityAction = ${jsRoute("VikingBookingPortlet.saveAvailability")}
	var getAvailabilityAction = ${jsRoute("VikingBookingPortlet.getAvailability")}
	var i18n = ${jsi18n(['sunday','monday','tuesday','wednesday','thursday','friday','saturday'])}
</script>
<div ng-controller="AvailabilityController">
	<div class="well">
		<p>
			${h.messages.get("share-this-url")} <a href="${appointmentsURL}">${appointmentsURL}</a>
		</p>
	</div>
	

	<button ng-click="status.setAvailability = !status.setAvailability" ng-show="!status.setAvailability">${h.messages.get("set-availability")}</button>
	<div ng-show="status.setAvailability">
		<button ng-click="saveAvailability()">${h.messages.get("save")}</button>
		<table class="table">
			<thead>
				<tr>
					<th>${h.messages.get("hours")}</th>	
					<th ng-repeat="weekDay in weekDays">{{messages(weekDay.label)}}</th>	
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="hour in hours">
					<td>{{formatHour(hour)}} - {{formatHour(hour+1)}}</td>
					<td ng-repeat="weekDay in weekDays">
						<input type="checkbox" checklist-model="availableHours[weekDay.index]" checklist-value="hour">
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
</div>