package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.GeneratedUUID;

/**
 * Classe représentant l'entitée Avis.
 * 
 * @author SGT Mora Leo
 *
 */
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "avis")
@EqualsAndHashCode(of = "id")
public class Avis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant de l'avis.
     */
    @Id
    @GeneratedUUID
    @Column(columnDefinition = "VARCHAR(36)", name = "id")
    String id;

    /**
     * Contenu de l'avis.
     */
    @Column(name = "contenu")
    String contenu;

    /**
     * Utilisateur concerné par l'avis.
     */
    @JoinColumn(name = "utilisateur_id")
    @ManyToOne
    Utilisateur utilisateur;

}
