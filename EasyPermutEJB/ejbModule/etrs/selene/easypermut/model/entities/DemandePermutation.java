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
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.GeneratedUUID;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "demande_permutation")
@EqualsAndHashCode(of = "id")
public class DemandePermutation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedUUID
    @Column(name = "id")
    String id;

    @JoinColumn(name = "utilisateur_createur_id")
    @ManyToOne
    Utilisateur utilisateurCreateur;

    @Column(name = "utilisateur_interesse_id")
    @OneToOne
    Utilisateur utilisateurInteresse;

    @OneToMany
    @JoinColumn(name = "lstAvis_id")
    List<Avis> lstAvis;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    Poste poste;

    @ManyToOne
    @JoinColumn(name = "unite_id")
    Unite unite;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    Ville ville;

    @ManyToOne
    @JoinColumn(name = "zmr_id")
    ZMR zmr;

}
