package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.GeneratedUUID;

/**
 * Classe représentant l'entitée Specialite.
 * 
 * @author SGT Mora Leo
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "specialite")
public class Specialite implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identifiant de la spécialité.
     */
    @Id
    @GeneratedUUID
    @Column(columnDefinition = "VARCHAR(36)", name = "id")
    String id;

    /**
     * Libelle de la spécialité.
     */
    @Column(name = "libelle")
    String libelle;

    /**
     * Numéro de la specialité.
     */
    @Column(name = "numeroSpe")
    String numeroSpe;
}
