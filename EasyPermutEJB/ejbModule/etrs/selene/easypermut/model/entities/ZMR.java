package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.GeneratedUUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "zmr")
@EqualsAndHashCode(of = "id")
public class ZMR implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de la ZMR.
	 */
	@GeneratedUUID
	@Id
	@Column(columnDefinition = "VARCHAR(36)", name = "id")
	String id;

	/**
	 * Libellé de la ZMR.
	 */
	@Column(name = "libelle")
	String libelle;
	
}
