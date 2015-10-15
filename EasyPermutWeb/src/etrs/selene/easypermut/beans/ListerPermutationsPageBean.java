package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;

@Named
@RequestScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListerPermutationsPageBean implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * 
     */
    List<DemandePermutation> lstDemandePermutation;

    @Inject
    DemandePermutationSession facadePermutations;

    @PostConstruct
    public void init() {
        // facadePermutations.
    }

}
