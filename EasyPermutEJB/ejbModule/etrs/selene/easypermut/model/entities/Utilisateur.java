package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de l'utilisateur.
	 */
	@Id
	@GeneratedUUID
	@Column(columnDefinition = "VARCHAR(36)", name = "id")
	String id;

	/**
	 * Nom de l'utilisateur.
	 */
	@Column(name = "nom")
	String nom;
	
	/**
	 * Prenom de l'utiisateur.
	 */
	@Column(name = "prenom")
	String prenom;
	
	/**
	 * NIA de l'utilisateur.
	 */
	@Column(name = "nia")
	String nia;
	
	/**
	 * Mail de l'utilisateur.
	 */
	@Column(name = "mail")
	String mail;

	/**
	 * Grade de l'utilisateur.
	 */
	@ManyToOne
	@JoinColumn(name = "grade_id")
	Grade grade;

	/**
	 * Specialite de l'utilisateur.
	 */
	@ManyToOne
	@JoinColumn(name = "specialite_id")
	Specialite specialite;

	/**
	 * Poste occupé par l'utilisateur.
	 */
	@ManyToOne
	@JoinColumn(name = "poste_id")
	Poste poste;
	
}