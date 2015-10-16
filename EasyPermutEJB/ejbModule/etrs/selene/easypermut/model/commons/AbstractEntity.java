package etrs.selene.easypermut.model.commons;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.uuid.GeneratedUUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Id
    @GeneratedUUID
    @Column(columnDefinition = "VARCHAR(36)", name = "id")
    String id;
}
