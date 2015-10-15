package etrs.selene.easypermut.model.commons;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.entetrs.commons.jpa.AbstractFacadeJavaEntrepriseEdition;

public class AbstractFacade<T> extends AbstractFacadeJavaEntrepriseEdition<T> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
