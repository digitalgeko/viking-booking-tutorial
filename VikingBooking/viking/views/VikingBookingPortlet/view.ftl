<script type="text/javascript">
	VK.setPortletData("${h.portletId}", {
		saveAvailabilityAction: ${jsRoute("VikingBookingPortlet.saveAvailability")},
		getAvailabilityAction: ${jsRoute("VikingBookingPortlet.getAvailability")},
		i18n: ${jsi18n(['sunday','monday','tuesday','wednesday','thursday','friday','saturday'])}
	});
</script>
<div ng-controller="AvailabilityController" ng-init="init('${h.portletId}')">
	<div class="well">
		<p>
			${h.messages.get("share-this-url")} <a href="${appointmentsURL}" id="appointments-link">${appointmentsURL}</a>
		</p>
	</div>
	

	<button class="btn" id="set-availability-button" ng-click="showAvailabilityForm()" ng-show="!status.setAvailability">${h.messages.get("set-availability")}</button>
	<div ng-show="status.setAvailability">
		<button class="btn" id="save-availability-button" ng-click="saveAvailability()">${h.messages.get("save")}</button>
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
	
	<div ng-show="status.showSuccessMessage">
		<p class="alert alert-success" id="availability-success-message">Availability successfully saved!</p>
	</div>
</div>