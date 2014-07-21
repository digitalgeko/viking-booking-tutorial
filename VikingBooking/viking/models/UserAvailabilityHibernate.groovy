package models

import org.codehaus.jackson.annotate.JsonIgnore
import org.hibernate.Criteria
import org.hibernate.criterion.Restrictions

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import nl.viking.model.hibernate.Model

import javax.persistence.MapKey
import javax.persistence.Transient

/**
 * User: mardo
 * Date: 6/18/14
 * Time: 11:31 AM
 */
@Entity
class UserAvailabilityHibernate extends Model {

	Long userId

	@ElementCollection @JsonIgnore
	Map<String, HoursList> availableHoursAux

	@Transient
	def getAvailableHours() {
		if (availableHoursAux) {
			return availableHoursAux.collectEntries {
				[(it.key): it.value.hours]
			}
		}
		[:]
	}

	static UserAvailabilityHibernate forUserId(long userId) {
		def storedUserAvailability = UserAvailabilityHibernate.query { Criteria criteria ->
			criteria.add(Restrictions.eq("userId", userId))
			criteria.uniqueResult()
		}

		storedUserAvailability ?: new UserAvailabilityHibernate(userId: userId)
	}
}
