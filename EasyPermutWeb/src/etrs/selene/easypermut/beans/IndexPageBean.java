package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.entities.Personne;
import etrs.selene.easypermut.model.sessions.PersonneSession;
import etrs.selene.easypermut.utils.JsfUtils;

@Named
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewScoped
public class IndexPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    PersonneSession facadePersonne;

    Personne personne;

    @PostConstruct
    public void init() {
        personne = facadePersonne.newInstance();
    }

    public void ajouterPersonne() {
        facadePersonne.create(personne);
        JsfUtils.sendMessage(String.format("La personne %s a été crée", personne.getNom()));
        personne = facadePersonne.newInstance();
    }

    public List<Personne> getLstPersonnes() {
        return facadePersonne.readAll();
    }

}
