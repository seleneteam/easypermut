package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import etrs.selene.easypermut.model.commons.GeneratedUUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name="unite")
@EqualsAndHashCode(of="id")
public class Unite implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identifiant de l'unité.
	 */
	@GeneratedUUID
	@Id
	@Column(length=36, name = "id")
	String id;
	
	/**
	 * Libellé de l'unité.
	 */
	@Column(name = "libelle_unite")
	String libelle;
	
	/**
	 * Ville où se situe l'unité.
	 */
	@Column(name = "ville_unite")
	Ville ville;

}
