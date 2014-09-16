# Viking Booking

This is a tutorial for the [viking framework](https://github.com/digitalgeko/viking-framework) for Liferay, we asume that you already have read the [HelloWorld section](https://github.com/digitalgeko/viking-framework#hello-world) in the documentation.

With this tutorial you'll se how to take your first steps into the framework. We're going to build a small appointment scheduler, where you set your availability and the users that have your URL, can schedule appointments to yourself.

First, let's create a new project:

Start your viking shell 
```
$ viking-shell
```

Create ScheduleAppointmentPortlet with the `new-project` viking-shell command:
```
viking> new-project
```
Follow the wizard, set "VikingBooking" as name, we'll also be using Liferay 6.2.1 for this tutorial.

You should have now the project on your filesystem at ~/viking-projects/VikingBooking-env and the viking-shell output should have changed to
```
VikingBooking>
```

This is because "VikingBooking" is the active project.

Every portlets project have a default portlet named "[ProjectName]Portlet", so in this case is "VikingBookingPortlet". 

Ok, let's create our first model!, we'll call it UserAvailability.

Create a class in *models/morphia/UserAvailability.groovy*, and add the following content:

```
package models.morphia

import com.google.code.morphia.annotations.Entity
import nl.viking.model.morphia.Model

@Entity
class UserAvailability extends Model {

	Long userId

	Map availableHours

	static UserAvailability forUserId(long userId) {
		find("userId", userId).get() ?: new UserAvailability(userId: userId)
	}
}

```

Easy, we'll save the logged user id and a map that will have which day of the week and which hours is he available per day.

There's a `forUserId(userId)` method, this is a helper that will return an existing record that contains all the user availability, or create a new one if no record exists.

## VikingBookingPortlet

Ok, now create a form to set our availability, edit *viking/views/VikingBookingPortlet/view.ftl*

First add 2 jsRoutes:
```
<script type="text/javascript">
	VK.setPortletData("${h.portletId}", {
		saveAvailabilityAction: ${jsRoute("VikingBookingPortlet.saveAvailability")},
		getAvailabilityAction: ${jsRoute("VikingBookingPortlet.getAvailability")},
		i18n: ${jsi18n(['sunday','monday','tuesday','wednesday','thursday','friday','saturday'])}
	});
</script>
```

We're using `VK.setPortletData` to share javascript variables between this view and the angular controller, we'll se how to retrieve these variables in a moment.

Next we have `jsRoute`, which creates a javascript function that will return a string URL with the **controller** and **method** specified in the first parameter (i.e. *"VikingBookingPortlet.saveAvailability"*), this function can also take parameters to send them in the URL, we'll se how to do that when we define our *ScheduleAppointmentPortlet*.

`saveAvailabilityAction` and `getAvailabilityAction` will be used to **save** and **get** the user's availability respectively.

We used `jsi18n` to create `i18n`, a javascript function to interationalize keys. Simply pass the keys you want to have in the function to inernationalize them later.

As we are using angular, we'll define angular controllers. Create a file at *public/js/availability-controller.js*, every script located in /public/js will be automatically included when you deploy your project.

**public/js/availability-controller.js**
```
VikingBookingApp.controller('AvailabilityController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {
	
	$scope.init = function(portletId) {
		$scope.portletData = VK.getPortletData(portletId);
		$http.get($scope.portletData.getAvailabilityAction()).success(function(data) {
			$scope.availableHours = data.availableHours || {};
		});
	};

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
		return moment(hour, "H").format("hh:mm a");
	};

	$scope.status = {
		setAvailability: false,
		showSuccessMessage: false
	};

	$scope.showAvailabilityForm = function() {
		$scope.status.setAvailability = !$scope.status.setAvailability;
		$scope.status.showSuccessMessage = false;
	};

	$scope.saveAvailability = function() {
		$http.post($scope.portletData.saveAvailabilityAction(), $scope.availableHours).success(function(data) {
			$scope.status.setAvailability = false;
			$scope.status.showSuccessMessage = true;
		});
	};

	$scope.messages = function(key) {
		return $scope.portletData.i18n(key);
	};

}]);
```

Here we define the days of the week we are going to set the availability, save it and retrieve it. And some helper functions that we'll use to handle our user interface.

Ok, add the following html form to the view:

**viking/views/VikingBookingPortlet/view.ftl**
```
<script type="text/javascript">
	VK.setPortletData("${h.portletId}", {
		saveAvailabilityAction: ${jsRoute("VikingBookingPortlet.saveAvailability")},
		getAvailabilityAction: ${jsRoute("VikingBookingPortlet.getAvailability")},
		i18n: ${jsi18n(['sunday','monday','tuesday','wednesday','thursday','friday','saturday'])}
	});
</script>
<div ng-controller="AvailabilityController">
	<div class="well">
		<p>
			${h.messages.get("share-this-url")} <a href="${appointmentsURL}" id="appointments-link">${appointmentsURL}</a>
		</p>
	</div>
	

	<button id="set-availability-button" ng-click="showAvailabilityForm()" ng-show="!status.setAvailability">${h.messages.get("set-availability")}</button>
	<div ng-show="status.setAvailability">
		<button id="save-availability-button" ng-click="saveAvailability()">${h.messages.get("save")}</button>
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
```

Here we'll show the link that he'll share to his users, and the form to set his availabity.

Finally, the controller.

**viking/controllers/VikingBookingPortlet.groovy**
```
package controllers

import com.liferay.portal.util.PortalUtil
import models.hibernate.HoursList
import models.morphia.UserAvailability
import nl.viking.Conf
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class VikingBookingPortlet extends Controller {

    @Render
    def view() {
     	def appointmentsURL = PortalUtil.getPortalURL(request) + "/web/guest/schedule-appointment/-/schedule-appointment-portlet/render/view/${h.user.userId}"
		[appointmentsURL: appointmentsURL]
    }

	@Resource(mode="view")
	def getAvailability() {
		def userAvailability
		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(h.user.userId)
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(h.user.userId)
		}
		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAvailability() {
		def availableHours = binder.fromJsonBody(Object.class)
		def userAvailability

		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(h.user.userId)
			userAvailability.availableHours = availableHours
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(h.user.userId)
			userAvailability.availableHoursAux = availableHours.collectEntries {
				def hoursList = userAvailability.availableHoursAux?.get(it.key)
				if (hoursList == null) {
					hoursList = new HoursList()
				}
				hoursList.hours = it.value
				hoursList.save()
				[(it.key): hoursList]
			}
		}
		userAvailability.save()
		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}
```

Here we have our methods to get and set the user's availability, as well as the share url creation.

We're done with the portlet, deploy it with the `deploy` command:
```
VikingBooking> deploy
```

Edit your *sitebuilder/sites.groovy* to:
```
def build(b) {
	b.site("Guest") {
		layout("Home") {
			override true
			layoutTemplateId "1_column"
			column(1) {
				portlet "vikingbookingportlet_WAR_VikingBooking"
			}
		}
	}
}
```

And run the site builder `build-site` command:
```
VikingBooking> build-site
```

This will connect to your local liferay (or the one specified in *conf/sitebuilder.conf*) and create (or update) the layout (page) named "Home" and will add our portlet in the first column.

Go to to [http://localhost:8080/group/vikingbooking/home](http://localhost:8080/group/vikingbooking/home) to see the portlet in action.

Ok, now that we have our availability set, we can now share the URL to our users, and start getting appointments!

Start by defining our new model "Event", that will represent our appointments, the model should look like:

**viking/models/morphia/Event.groovy**
```
package models.morphia

import com.google.code.morphia.annotations.Entity
import nl.viking.model.morphia.Model
import org.hibernate.validator.constraints.Email

import javax.validation.constraints.NotNull

/**
 * User: mardo
 * Date: 6/19/14
 * Time: 12:31 PM
 */
@Entity
class Event extends Model {

	Long userId

	Long dateTimestamp

	@NotNull
	String name

	@Email
	String email

	String phone

	String note

}
```

## ScheduleAppointmentPortlet

Let's proceed to define our controller; every viking project can have any number of portlets, every controller that ends with "Portlet" in his class name, will be treated as one.

Let's create a new portlet with the `add-portlet` command.

```
VikingBooking> add-portlet --name ScheduleAppointment
```

Notice that we didn't add "Portlet" in the name, because viking will add it for us. You should now have 2 portlets:

* VikingBooking**Portlet**.groovy
* and ScheduleAppointment**Portlet**.groovy

Open your view file and create 2 jsRoutes, one to get the availability and the other to set it:
```
<script type="text/javascript">
	VK.setPortletData("${h.portletId}", {
		getAvailabilityAction: ${jsRoute("ScheduleAppointmentPortlet.getUserAvailability", ['userId'] )},
		saveAppointmentAction: ${jsRoute("ScheduleAppointmentPortlet.saveAppointment", ['userId'] )}
	});
</script>
```

Then, create an angular controller

**public/js/set-availability-controller.js**

```
VikingBookingApp.controller('ScheduleAppointmentController', ['$scope', '$http', function($scope, $http) {

	$scope.formatHour = function(hour) {
		return moment(hour, "H").format("hh:mm a")
	};

	$scope.init = function(userId) {
		$scope.userId = userId;
		$http.get(getAvailabilityAction({userId: $scope.userId})).success(function(data) {
			console.log(data)
			$scope.availableHours = data.availableHours || {};
		});
		$(function() {
			$('#calendar').datepicker({}).on('changeDate', function(e) {
				if (e.date) {
					var dateMoment = moment(e.date)
					$scope.currentWeekDay = dateMoment.day();
					$scope.calendarDate.timestamp = e.date.getTime();	
				} else {
					$scope.currentWeekDay = undefined;
					$scope.calendarDate.timestamp = undefined;
				}
				
				$scope.$apply();
			});
		});
	};
	
	$scope.calendarDate = {};
	$scope.newAppointment = {};

	$scope.saveAppointment = function() {
		$scope.newAppointment.userId = $scope.userId;
		var dateMoment = moment($scope.calendarDate.timestamp).hours($scope.calendarDate.hour);
		
		$scope.newAppointment.dateTimestamp = new Date(dateMoment).getTime();
		$http.post(saveAppointmentAction({userId: $scope.userId}), $scope.newAppointment).success(function(data) {
			if (data.success) {
				$scope.newAppointment = {};
				$scope.calendarDate.hour = undefined;
				$scope.currentWeekDay = undefined;
				$scope.showSuccessMessage = true;
			} else {
				$scope.errors = data.errors;
				console.log(data);
			}
		});
	};
}]);
```

Here we define 2 main functions `$scope.saveAppointment` where we save our availability, and `$scope.init` where we get the availability that's saved in the database.

Now, the html form:

**viking/views/ScheduleAppointmentPortlet/view.ftl**
```
<script type="text/javascript">
	VK.setPortletData("${h.portletId}", {
		getAvailabilityAction: ${jsRoute("ScheduleAppointmentPortlet.getUserAvailability", ['userId'] )},
		saveAppointmentAction: ${jsRoute("ScheduleAppointmentPortlet.saveAppointment", ['userId'] )}
	});
</script>

<div ng-controller="ScheduleAppointmentController" ng-init="init(${userId?c});">
	<h3>${h.messages.get("select-the-date-for-your-appointment")}</h3>
	<div id="calendar"></div>

	<div ng-show="currentWeekDay != undefined">
		<h3>${h.messages.get("select-the-time")}</h3>
		<button 
			ng-repeat="hour in availableHours[currentWeekDay]" 
			class="btn available-hour" 
			ng-class="{'btn-primary':(hour==calendarDate.hour)}" 
			ng-click="calendarDate.hour = hour;">{{formatHour(hour)}}</button>
	</div>

	<div ng-show="calendarDate.hour >= 0">
		<h3>${h.messages.get("provide-your-contact-information")}</h3>

		<form ng-submit="saveAppointment()">
			
			<div class="control-group" ng-class="{error:errors['event.name']}">
				<label class="control-label">${h.messages.get("name")}</label>
				<div class="controls">
					<input id="appointment-name" ng-model="newAppointment.name" type="text" placeholder="${h.messages.get("name")}">
					<span class="help-inline" ng-if="errors['event.name']">{{errors['event.name']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.email']}">
				<label class="control-label">${h.messages.get("email")}</label>
				<div class="controls">
					<input id="appointment-email" ng-model="newAppointment.email" type="text" placeholder="${h.messages.get("email")}">
					<span class="help-inline" ng-if="errors['event.email']">{{errors['event.email']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.phone']}">
				<label class="control-label">${h.messages.get("phone")}</label>
				<div class="controls">
					<input id="appointment-phone" ng-model="newAppointment.phone" type="text" placeholder="${h.messages.get("phone")}">
					<span class="help-inline" ng-if="errors['event.phone']">{{errors['event.phone']}}</span>
				</div>
			</div>

			<div class="control-group" ng-class="{error:errors['event.note']}">
				<label class="control-label">${h.messages.get("note")}</label>
				<div class="controls">
					<textarea id="appointment-note" ng-model="newAppointment.note" rows="3"></textarea>
					<span class="help-inline" ng-if="errors['event.note']">{{errors['event.note']}}</span>
				</div>
			</div>

			<input id="save-appointment-button" type="submit" value="${h.messages.get("save-appointment")}">
		</form>
	</div>

	<div ng-show="showSuccessMessage" class="alert alert-success" id="appointment-success-message">
		${h.messages.get("appointment-successfully-saved")}
		<button type="button" class="close" ng-click="showSuccessMessage=false">×</button>
	</div>

</div>
```

And finally our controller

**viking/controllers/ScheduleAppointmentPortlet.groovy**
```
package controllers

import models.morphia.Event
import models.morphia.UserAvailability
import nl.viking.Conf
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class ScheduleAppointmentPortlet extends Controller {

    @Render
    def view() {
        def userId = bindLong('userId')
		[userId:userId]
    }

	@Resource(mode="view")
	def getUserAvailability() {
		def userId = bindLong("userId")

		def userAvailability

		if (Conf.properties.persistance.database == 'mongo'){
			userAvailability = UserAvailability.forUserId(userId)
		} else {
			userAvailability = models.hibernate.UserAvailability.forUserId(userId)
		}

		renderJSON(userAvailability)
	}

	@Resource(mode="view")
	def saveAppointment() {
		def event

		if (Conf.properties.persistance.database == 'mongo'){
			event = binder.fromJsonBody(Event.class)
		} else {
			event = binder.fromJsonBody(models.hibernate.Event.class)
		}
		validator.validate("event", event)
		if (validator.hasErrors()) {
			return renderJSON([success:false, errors: validator.errors])
		}

		event.save()
		renderJSON([success:true])
	}

    @Render
    def edit() {
        
    }

}
```

We've defined our methods to get and set your appointments.

Update your **sitebuilder/sites.groovy** to:

```
def build(b) {
	b.site("Guest") {
		layout("Home") {
			override true
			layoutTemplateId "1_column"
			column(1) {
				portlet "vikingbookingportlet_WAR_VikingBooking"
			}
		}

		layout("Schedule Appointment") {
			override true
			layoutTemplateId "1_column"
			isPrivateLayout false
			isHidden true

			column(1) {
				portlet "scheduleappointmentportlet_WAR_VikingBooking"
			}
		}
	}
}
```

This will add a new layout called "Schedule Appointment" and will add this new portlet.

Run `build-site` again:
```
VikingBooking> build-site
```

Copy the url that we set in VikingBookingPortlet view and open it in another browser (or another session, like a new chrome's incognito window), and schedule an appointment.


## CalendarPortlet

Ok, to conclude this tutorial we now need to see our scheduled appointments, let's create our last portlet:

```
VikingBooking> add-portlet --name CalendarPortlet
```

Let's define CalendarPortlet

**viking/controllers/CalendarPortlet.groovy**
```
package controllers

import models.morphia.Event
import nl.viking.Conf
import nl.viking.controllers.Controller
import nl.viking.controllers.annotation.*

class CalendarPortlet extends Controller {

    @Render
    def view() {
        
    }

    @Render
    def edit() {
        
    }

    @Resource(mode="view")
    def getEvents() {
    	def events

		if (Conf.properties.persistance.database == 'mongo'){
			events = Event.findAll()
		} else {
			events = models.hibernate.Event.findAll()
		}

		renderJSON(events)
    }

}
```

The angular controller 

**public/js/calendar-controller.js**
```
VikingBookingApp.controller('CalendarController', ['$scope', '$http', '$modal', function($scope, $http, $modal) {

	$scope.events =	[];
	$http.get(getEventsAction()).success(function(data) {
		_.each(data, function (ev) {
			var start = new Date(ev.dateTimestamp);
			var end = new Date(moment(start).add('hours', 1));
			$scope.events.push ({
				title: ev.name,
				start: start,
				end: end,
				allDay: false,
				name: ev.name,
				email: ev.email,
				phone: ev.phone,
				note: ev.note
			});
		});
	});
	
	$scope.uiConfig = {
		calendar:{
			header:{
				left: 'title',
				center: '',
				right: 'today prev,next'
			},
			eventClick: function(calendarEvent, clickEvent) {
				var modalInstance = $modal.open({
					templateUrl: 'calendar-event-details.html',
					controller: 'CalendarEventDetailsController',
					resolve: {
						calendarEvent: function () {
							return calendarEvent;
						}
					}
				});
			}
		}
	};

	$scope.eventSources = [$scope.events];

}]);


VikingBookingApp.controller('CalendarEventDetailsController', ['$scope', '$modalInstance', 'calendarEvent', function ($scope, $modalInstance, calendarEvent) {

	$scope.calendarEvent = calendarEvent;
	console.log(calendarEvent);
	
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
}]);
```

And the view:

**viking/views/CalendarPortlet/view.ftl**
```
<script type="text/javascript">
	var getEventsAction = ${jsRoute("CalendarPortlet.getEvents")}
</script>
<div ng-controller="CalendarController">
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
```


Update *sitebuilder/sites.groovy* to:
```
def build(b) {
	b.site("Guest") {
		layout("Home") {
			override true
			layoutTemplateId "1_column"
			column(1) {
				portlet "vikingbookingportlet_WAR_VikingBooking"
				portlet "calendarportlet_WAR_VikingBooking"
			}
		}

		layout("Schedule Appointment") {
			override true
			layoutTemplateId "1_column"
			isPrivateLayout false
			isHidden true

			column(1) {
				portlet "scheduleappointmentportlet_WAR_VikingBooking"
			}
		}
	}
}
```

Run `build-site` again, and check it at:
[http://localhost:8080/group/guest/home](http://localhost:8080/group/guest/home)

The calendar portlet will be located right below viking booking portlet.

Ok, now you have completed your first viking portlets project! we hope you've liked it and start building awesome things with it!

For further reading go to:
[https://github.com/digitalgeko/viking-framework#table-of-contents](https://github.com/digitalgeko/viking-framework#table-of-contents)