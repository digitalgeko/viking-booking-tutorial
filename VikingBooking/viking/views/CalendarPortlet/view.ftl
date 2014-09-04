<script type="text/javascript">
    VK.setPortletData("${h.portletId}", {
        getEventsAction: ${jsRoute("CalendarPortlet.getEvents")}
    });
</script>
<div ng-controller="CalendarController" ng-init="init('${h.portletId}')">
	<div ui-calendar="uiConfig.calendar" ng-model="eventSources"></div>


	<script type="text/ng-template" id="calendar-event-details.html">
        <div class="modal-header">
            <h3 class="modal-title">${h.messages.get("event-details")}</h3>
        </div>
        <div class="modal-body">
            <div>
            	<label><strong>${h.messages.get("name")}</strong></label>
            	<p>{{calendarEvent.name}}</p>
            </div>
            <div>
            	<label><strong>${h.messages.get("email")}</strong></label>
            	<p>{{calendarEvent.email}}</p>
            </div>
            <div>
            	<label><strong>${h.messages.get("phone")}</strong></label>
            	<p>{{calendarEvent.phone}}</p>
            </div>
            <div>
            	<label><strong>${h.messages.get("note")}</strong></label>
            	<p>{{calendarEvent.note}}</p>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-warning" ng-click="cancel()">${h.messages.get("close")}</button>
        </div>
    </script>
</div>


