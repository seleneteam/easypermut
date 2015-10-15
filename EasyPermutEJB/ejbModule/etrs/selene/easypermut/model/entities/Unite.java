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
import net.entetrs.commons.uuid.GeneratedUUID;

/**
 * Classe représentant l'entitée Unite.
 * 
 * @author SGT Mora Leo
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "unite")
@EqualsAndHashCode(of = "id")
public class Unite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant de l'unité.
     */
    @GeneratedUUID
    @Id
    @Column(columnDefinition = "VARCHAR(36)", name = "id")
    String id;

    /**
     * Libellé de l'unité.
     */
    @Column(name = "libelle")
    String libelle;

    /**
     * Ville où se situe l'unité.
     */
    @ManyToOne
    @JoinColumn(name = "ville_id")
    Ville ville;

}
