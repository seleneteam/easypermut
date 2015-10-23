package etrs.selene.easypermut.init;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import lombok.extern.apachecommons.CommonsLog;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Poste;
import etrs.selene.easypermut.model.entities.Specialite;
import etrs.selene.easypermut.model.entities.Unite;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;
import etrs.selene.easypermut.model.sessions.GradeSession;
import etrs.selene.easypermut.model.sessions.PosteSession;
import etrs.selene.easypermut.model.sessions.SpecialiteSession;
import etrs.selene.easypermut.model.sessions.UniteSession;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;
import etrs.selene.easypermut.model.sessions.VilleSession;
import etrs.selene.easypermut.model.sessions.ZMRSession;

@Singleton
@Startup
@CommonsLog
/**
 * Classe d'initialisation de l'application. Pour les tests, cette classe cré aussi des utilisateurs. 
 * Lors du passage en prod, retirez la création de ces utilisateurs.
 * @author SGT Mora Leo
 *
 */
public class InitSingleton {

    /**
     * {@link GradeSession}
     */
    @Inject
    private GradeSession facadeGrade;

    /**
     * {@link PosteSession}
     */
    @Inject
    private PosteSession facadePoste;

    /**
     * {@link SpecialiteSession}
     */
    @Inject
    private SpecialiteSession facadeSpecialite;

    /**
     * {@link UniteSession}
     */
    @Inject
    private UniteSession facadeUnite;

    /**
     * {@link UtilisateurSession}
     */
    @Inject
    private UtilisateurSession facadeUtilisateur;

    /**
     * {@link VilleSession}
     */
    @Inject
    private VilleSession facadeVille;

    /**
     * {@link ZMRSession}
     */
    @Inject
    private ZMRSession facadeZMR;

    String[] tabGrade = { "Caporal", "Caporal-Chef", "Sergent", "Adjudant" };
    String[] tabPoste = { "Mecanicien", "Electrotechnichien", "Previsionniste" };
    String[] tabNomSpecialite = { "Programmeur", "Admin-Reseaux", "Communication" };
    String[] tabSpecialite = { "8300", "8200", "8100" };
    String[] tabUnite = { "EDCM", "ESTA", "DSI" };
    String[] tabUtilisateur = { "Didier", "Serge", "Cecile" };
    String[] tabVille = { "Paris", "Rennes", "Bordeaux" };
    String[] tabZMR = { "Creil", "Angers-Rennes", "Bordeaux-Cazaux" };

    @PostConstruct
    /**
     * Methode d'initialisation de l'application.
     */
    public void init() {
        log.info("Vérification de la base de donnée.....");

        /*
         * Ajout de grades.
         */
        if (this.facadeGrade.readAll().isEmpty()) {
            for (String string : this.tabGrade) {
                Grade grade = this.facadeGrade.newInstance();
                grade.setGrade(string);
                this.facadeGrade.create(grade);
                log.info("Grade " + string + " créé");
            }
        } else {
            log.info("La table grade contient déja des éléments");
        }

        /*
         * Ajout de ZMR.
         */
        if (this.facadeZMR.readAll().isEmpty()) {

            for (String string : this.tabZMR) {
                ZMR zmr = this.facadeZMR.newInstance();
                zmr.setLibelle(string);
                this.facadeZMR.create(zmr);
                log.info("ZMR " + string + " créé");
            }
        } else {
            log.info("La table ville contient déja des éléments");
        }

        /*
         * Ajout de villes.
         */
        if (this.facadeVille.readAll().isEmpty()) {
            int index = 0;

            for (String string : this.tabVille) {
                Ville ville = this.facadeVille.newInstance();
                ville.setNom(string);
                ville.setZmr(this.facadeZMR.searchFirstResult("libelle", this.tabZMR[index]));
                this.facadeVille.create(ville);
                log.info("Ville " + string + " créé");
                index++;
            }
        } else {
            log.info("La table ville contient déja des éléments");
        }

        /*
         * Ajout d'unités.
         */
        if (this.facadeUnite.readAll().isEmpty()) {
            int index = 0;

            for (String string : this.tabUnite) {
                Unite unite = this.facadeUnite.newInstance();
                unite.setLibelle(string);
                unite.setVille(this.facadeVille.searchFirstResult("nom", this.tabVille[index]));
                this.facadeUnite.create(unite);
                log.info("Unite " + string + " créé");
                index++;
            }
        } else {
            log.info("La table unite contient déja des éléments");
        }

        /*
         * Ajout de postes.
         */
        if (this.facadePoste.readAll().isEmpty()) {
            int index = 0;

            for (String string : this.tabPoste) {
                Poste poste = this.facadePoste.newInstance();
                poste.setLibelle(string);
                poste.setUnite(this.facadeUnite.searchFirstResult("libelle", this.tabUnite[index]));
                this.facadePoste.create(poste);
                log.info("Poste " + string + " créé");
                index++;
            }
        } else {
            log.info("La table poste contient déja des éléments");
        }

        /*
         * Ajout de specialite.
         */
        if (this.facadeSpecialite.readAll().isEmpty()) {
            int index = 0;

            for (String string : this.tabNomSpecialite) {
                Specialite specialite = this.facadeSpecialite.newInstance();
                specialite.setLibelle(string);
                specialite.setNumeroSpe(this.tabSpecialite[index]);
                this.facadeSpecialite.create(specialite);
                log.info("Specialite " + string + " créé");
                index++;
            }
        } else {
            log.info("La table specialite contient déja des éléments");
        }

        /*
         * Ajout d'utilisateurs.
         */
        if (this.facadeUtilisateur.readAll().isEmpty()) {
            int index = 0;

            Utilisateur utilisateur = this.facadeUtilisateur.newInstance();
            utilisateur.setEstValide(true);
            utilisateur.setEstInteresse(false);
            utilisateur.setInformationsValide(false);
            utilisateur.setGrade(this.facadeGrade.searchFirstResult("grade", this.tabGrade[index]));
            utilisateur.setIdentifiantAnudef(this.tabUtilisateur[index]);
            utilisateur.setMail("random@gouv.fr");
            utilisateur.setNia("AL42698");
            utilisateur.setNom(this.tabUtilisateur[index]);
            utilisateur.setPoste(this.facadePoste.searchFirstResult("libelle", this.tabPoste[index]));
            utilisateur.setPrenom(this.tabUtilisateur[index]);
            utilisateur.setSpecialite(this.facadeSpecialite.searchFirstResult("libelle", this.tabNomSpecialite[index]));
            utilisateur.setDateInscription(new Date());
            this.facadeUtilisateur.create(utilisateur);
            log.info("Utilisateur " + this.tabUtilisateur[index] + " créé");
            index++;

            Utilisateur utilisateur4 = this.facadeUtilisateur.newInstance();
            utilisateur4.setEstValide(false);
            utilisateur4.setEstInteresse(false);
            utilisateur4.setInformationsValide(true);
            utilisateur4.setIdentifiantAnudef("test.trynet.21539");
            utilisateur4.setDateInscription(new Date());
            this.facadeUtilisateur.create(utilisateur4);
            log.info("Utilisateur 4 créé");

            Utilisateur utilisateur5 = this.facadeUtilisateur.newInstance();
            utilisateur5.setEstValide(false);
            utilisateur5.setInformationsValide(true);
            utilisateur5.setEstInteresse(false);
            utilisateur5.setIdentifiantAnudef("wotan.yoshin.56924");
            utilisateur5.setDateInscription(new Date());
            this.facadeUtilisateur.create(utilisateur5);
            log.info("Utilisateur 5 créé");

        } else {
            log.info("La table utilisateur contient déja des éléments");
        }
    }
}
