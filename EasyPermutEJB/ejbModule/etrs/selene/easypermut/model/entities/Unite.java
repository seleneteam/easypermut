package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

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
 * Classe représentant l'entitée Unite.
 *
 * @author SGT Mora Leo
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "UNITE")
public class Unite extends AbstractEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Libellé de l'unité.
	 */
	@Column(name = "libelle")
	@Length(min = 1)
	@NotNull
	@Getter
	@Setter
	String libelle;

	/**
	 * Ville où se situe l'unité.
	 */
	@ManyToOne
	@JoinColumn(name = "ville_id")
	@NotNull
	@Getter
	@Setter
	Ville ville;

	@Override
	public String toString()
	{
		return this.libelle;
	}

}
