package models.hibernate

import nl.viking.model.hibernate.Model

import javax.persistence.ElementCollection
import javax.persistence.Entity

/**
 * User: mardo
 * Date: 7/21/14
 * Time: 12:41 PM
 */
@Entity
class HoursList extends Model{

	@ElementCollection
	List<Integer> hours

}
