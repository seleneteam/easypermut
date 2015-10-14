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

	@Id
	@GeneratedUUID
	@Column(columnDefinition = "VARCHAR(36)", name = "id")
	String id;

	@Column(name = "nom")
	String nom;
	
	@Column(name = "prenom")
	String prenom;
	
	@Column(name = "nia")
	String nia;
	
	@Column(name = "mail")
	String mail;

	@ManyToOne
	@JoinColumn(name = "grade_id")
	Grade grade;

	@ManyToOne
	@JoinColumn(name = "specialite_id")
	Specialite specialite;

	@ManyToOne
	@JoinColumn(name = "poste_id")
	Poste poste;
	
}
