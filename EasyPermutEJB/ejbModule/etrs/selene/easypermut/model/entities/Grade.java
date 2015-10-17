package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Grade.
 *
 * @author SGT Mora Leo
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "GRADE")
public class Grade extends AbstractEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Libelle du grade.
	 */
	@Column(name = "grade")
	@NotNull
	@Getter
	@Setter
	String grade;

	@Override
	public String toString()
	{
		return this.grade;
	}

}
