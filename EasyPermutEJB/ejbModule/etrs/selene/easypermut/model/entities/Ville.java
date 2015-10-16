package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * Classe représentant l'entitée Ville.
 * 
 * @author SGT Mora Leo
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "ville")
@EqualsAndHashCode(of = "id")
public class Ville extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nom de la ville.
     */
    @Column(name = "nom")
    @Length(min = 1)
    @NotNull
    String nom;

    /**
     * ZMR dans laquelle la ville est située.
     */
    @ManyToOne
    @JoinColumn(name = "zmr_id")
    @NotNull
    ZMR zmr;
}
