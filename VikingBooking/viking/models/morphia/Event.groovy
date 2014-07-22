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
