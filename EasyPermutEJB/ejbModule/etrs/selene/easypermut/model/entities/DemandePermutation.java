package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.uuid.GeneratedUUID;

/**
 * Classe représentant l'entitée DemandePermutation.
 *
 * @author SGT Mora Leo
 */
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "demande_permutation")
public class DemandePermutation implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de la demande de permutation.
	 */
	@Id
	@GeneratedUUID
	@Column(columnDefinition = "VARCHAR(36)", name = "id")
	String id;

	/**
	 * Utilisateur ayant créé la demande de permutation.
	 */
	@JoinColumn(name = "utilisateur_createur_id")
	@ManyToOne
	Utilisateur utilisateurCreateur;

	/**
	 * Utilisateur intéressé par la demande.
	 */
	@JoinColumn(name = "utilisateur_interesse_id")
	@OneToOne
	Utilisateur utilisateurInteresse;

	/**
	 * Listes des avis.
	 */
	@OneToMany
	@JoinColumn(name = "lstAvis_id")
	List<Avis> lstAvis;

	/**
	 * Poste occupé par le créateur de la demande.
	 */
	@ManyToOne
	@JoinColumn(name = "poste_id")
	Poste poste;

	/**
	 * Unité du créateur de la demande.
	 */
	@ManyToOne
	@JoinColumn(name = "unite_id")
	Unite unite;

	/**
	 * Ville du créateur de la demande.
	 */
	@ManyToOne
	@JoinColumn(name = "ville_id")
	Ville ville;

	/**
	 * Zone de mutation de référence du créateur de la demande.
	 */
	@ManyToOne
	@JoinColumn(name = "zmr_id")
	ZMR zmr;

}
