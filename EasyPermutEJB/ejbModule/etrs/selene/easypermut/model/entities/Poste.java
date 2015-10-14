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
@Table(name="poste")
@EqualsAndHashCode(of="id")
public class Poste implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant du poste.
	 */
	@GeneratedUUID
	@Id
	@Column(length=36, name = "id")
	String id;
	
	/**
	 * Libellé du poste.
	 */
	@Column(name = "libelle_poste")
	String libelle;
	
	/**
	 * Unité dans laquelle est le poste.
	 */
	@Column(name = "unite_poste")
	Unite unite;
}
