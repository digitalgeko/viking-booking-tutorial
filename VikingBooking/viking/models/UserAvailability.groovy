package models

import com.google.code.morphia.annotations.Entity
import com.liferay.portal.model.User
import nl.viking.model.morphia.Model

/**
 * User: mardo
 * Date: 6/18/14
 * Time: 11:31 AM
 */
@Entity
class UserAvailability extends Model {

	Long userId

	Map availableHours

	static UserAvailability forUserId(long userId) {
		find("userId", userId).get() ?: new UserAvailability(userId: userId)
	}
}
