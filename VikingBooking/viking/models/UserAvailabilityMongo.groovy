package models

import com.google.code.morphia.annotations.Entity
import nl.viking.model.morphia.Model

/**
 * User: mardo
 * Date: 6/18/14
 * Time: 11:31 AM
 */
@Entity
class UserAvailabilityMongo extends Model {

	Long userId

	Map availableHours

	static UserAvailabilityMongo forUserId(long userId) {
		find("userId", userId).get() ?: new UserAvailabilityMongo(userId: userId)
	}
}
