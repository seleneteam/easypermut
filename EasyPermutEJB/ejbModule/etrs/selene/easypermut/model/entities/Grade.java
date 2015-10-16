package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Grade.
 * 
 * @author SGT Mora Leo
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "GRADE")
public class Grade extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Libelle du grade.
     */
    @Column(name = "grade")
    @Length(min = 3, max = 3)
    @Getter
    @Setter
    String grade;

}
