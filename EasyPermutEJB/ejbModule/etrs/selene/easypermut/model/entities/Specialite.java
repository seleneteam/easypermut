package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Specialite.
 * 
 * @author SGT Mora Leo
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "SPECIALITE")
public class Specialite extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Libelle de la spécialité.
     */
    @Column(name = "libelle")
    @Length(min = 1)
    @NotNull
    @Getter
    @Setter
    String libelle;

    /**
     * Numéro de la specialité.
     */
    @Column(name = "numero_spe")
    @NotNull
    @Getter
    @Setter
    String numeroSpe;
}
