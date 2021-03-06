package etrs.selene.easypermut.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import org.hibernate.validator.constraints.Length;

import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Ville.
 *
 * @author SGT Mora Leo
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "VILLE")
public class Ville extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	/**
	 * Nom de la ville.
	 */
	@Column(name = "nom")
	@Length(min = 1)
	@NotNull
	@Getter
	@Setter
	String nom;

	/**
	 * ZMR dans laquelle la ville est située.
	 */
	@ManyToOne
	@JoinColumn(name = "zmr_id")
	@NotNull
	@Getter
	@Setter
	ZMR zmr;

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.nom);
		return sb.toString();
	}
}
