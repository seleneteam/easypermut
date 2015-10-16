package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée ZMR.
 * 
 * @author SGT Mora Leo
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "zmr")
@EqualsAndHashCode(of = "id")
public class ZMR extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Libellé de la ZMR.
     */
    @Column(name = "libelle")
    @Length(min = 1)
    @NotNull
    String libelle;

}
