<script type="text/javascript">
	var saveAvailabilityAction = ${jsRoute("VikingBookingPortlet.saveAvailability")}
	var getAvailabilityAction = ${jsRoute("VikingBookingPortlet.getAvailability")}
</script>
<div ng-controller="AvailabilityController">
	
	<div class="well">
		<p>
			Share this URL: <a href="${appointmentsURL}">${appointmentsURL}</a>
		</p>
	</div>
	

	<button ng-click="status.setAvailability = !status.setAvailability">Set availability</button>
	
	<div ng-show="status.setAvailability">
		<button ng-click="saveAvailability()">Save</button>
		<table class="table">
			<thead>
				<tr>
					<th>hours</th>	
					<th ng-repeat="weekDay in weekDays">{{weekDay.label}}</th>	
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="hour in hours">
					<td>{{formatHour(hour)}} - {{formatHour(hour+1)}} hrs</td>
					<td ng-repeat="weekDay in weekDays">
						<input type="checkbox" checklist-model="availableHours[weekDay.index]" checklist-value="hour">
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
</div>