package etrs.selene.easypermut.model.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée DemandePermutation.
 *
 * @author SGT Mora Leo
 */
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "DEMANDE_PERMUTATION")
@NamedQueries({ @NamedQuery(name = "DemandesPermut.listeSepcifique", query = "SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.grade = :grade AND dp.utilisateurCreateur.specialite = :specialite AND dp.utilisateurInteresse IS NULL AND dp.utilisateurCreateur != :createur"), @NamedQuery(name = "DemandesPermut.listeMesDemandes", query = "SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.id = :id"), @NamedQuery(name = "DemandesPermut.qtParZMR", query = "SELECT COUNT(dp) FROM DemandePermutation dp WHERE dp.zmr = :zmr"), @NamedQuery(name = "DemandesPermut.qtParSpe", query = "SELECT COUNT(dp) FROM DemandePermutation dp WHERE dp.utilisateurCreateur.specialite = :spe") })
public class DemandePermutation extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Utilisateur ayant créé la demande de permutation.
     */
    @JoinColumn(name = "utilisateur_createur_id")
    @ManyToOne
    @NotNull
    @Getter
    @Setter
    Utilisateur utilisateurCreateur;

    /**
     * Utilisateur intéressé par la demande.
     */
    @JoinColumn(name = "utilisateur_interesse_id")
    @OneToOne
    @Getter
    @Setter
    Utilisateur utilisateurInteresse;

    /**
     * Poste souhaité par le créateur de la demande.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poste_id")
    @Getter
    @Setter
    Poste poste;

    /**
     * Unité souhaité par le créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "unite_id")
    @NotNull
    @Getter
    @Setter
    Unite unite;

    /**
     * Ville souhaité par le créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "ville_id")
    @NotNull
    @Getter
    @Setter
    Ville ville;

    /**
     * Zone de mutation de référence souhaité par le créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "zmr_id")
    @NotNull
    @Getter
    @Setter
    ZMR zmr;

    /**
     * Date de la création de la demande.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    @NotNull
    @Getter
    @Setter
    Date dateCreation;

}
