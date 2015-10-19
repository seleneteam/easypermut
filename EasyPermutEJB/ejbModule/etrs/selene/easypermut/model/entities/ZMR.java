package etrs.selene.easypermut.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Classe représentant l'entitée ZMR.
 *
 * @author SGT Mora Leo
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "ZMR")
public class ZMR extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	/**
	 * Libellé de la ZMR.
	 */
	@Column(name = "libelle")
	@Length(min = 1)
	@NotNull
	@Getter
	@Setter
	String libelle;

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.libelle);
		return sb.toString();
	}

}
