package models

import nl.viking.model.hibernate.Model
import org.hibernate.validator.constraints.Email

import javax.persistence.Entity
import javax.validation.constraints.NotNull

/**
 * User: mardo
 * Date: 6/19/14
 * Time: 12:31 PM
 */
@Entity
class EventHibernate extends Model {

	Long userId

	Long dateTimestamp

	@NotNull
	String name

	@Email
	String email

	String phone

	String note

}
