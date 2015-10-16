package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
public class DemandePermutation extends AbstractEntity implements Serializable {

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
     * Poste occupé par le créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "poste_id")
    @NotNull
    @Getter
    @Setter
    Poste poste;

    /**
     * Unité du créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "unite_id")
    @NotNull
    @Getter
    @Setter
    Unite unite;

    /**
     * Ville du créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "ville_id")
    @NotNull
    @Getter
    @Setter
    Ville ville;

    /**
     * Zone de mutation de référence du créateur de la demande.
     */
    @ManyToOne
    @JoinColumn(name = "zmr_id")
    @NotNull
    @Getter
    @Setter
    ZMR zmr;

}
