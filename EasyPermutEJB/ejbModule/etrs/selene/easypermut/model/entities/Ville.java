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
@Table(name="ville")
@EqualsAndHashCode(of="id")
public class Ville implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de la ville.
	 */
	@GeneratedUUID
	@Id
	@Column(length=36, name = "id")
	String id;
	
	/**
	 * Nom de la ville.
	 */
	@Column(name = "nom_ville")
	String nom;
	
	/**
	 * ZMR dans laquelle la ville est située.
	 */
	@Column(name = "zmr_ville")
	ZMR zmr;
}
