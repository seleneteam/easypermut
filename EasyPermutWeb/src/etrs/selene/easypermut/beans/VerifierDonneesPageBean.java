package etrs.selene.easypermut.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Bean de la page de verification des données.
 * 
 * @author SGT Mora Leo
 *
 */
@Named
@RequestScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifierDonneesPageBean {

}
