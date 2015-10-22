package etrs.selene.easypermut.singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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

/**
 * Singleton d'imporation des données depuis un XML (potentiellement Orchestra).
 * Elle serra lancé tous les jours a 3h du matin.
 *
 * @author SGT Mora Leo
 */
@Singleton
@NoArgsConstructor
public class ImportSingleton {

    static ImportSingleton instance;

    public static ImportSingleton getInstance() {
        if (instance == null) {
            instance = new ImportSingleton();
        }
        return instance;
    }

    @Inject
    GradeSession facadeGrade;

    @Inject
    PosteSession facadePoste;

    @Inject
    SpecialiteSession facadeSpecialite;

    @Inject
    UniteSession facadeUnite;

    @Inject
    UtilisateurSession facadeUtilisateur;

    @Inject
    VilleSession facadeVille;

    @Inject
    ZMRSession facadeZMR;

    /**
     * Methode d'import des données. Cette methode parse le XML. Si elle
     * rencontre des informations non présentes dans la base, elle les crée et
     * les ajoute en BDD.
     */
    // @Schedule(hour = "3")
    public void recupereDonnees() throws FileNotFoundException {

        File fXmlFile;

        try {
            if ((fXmlFile = new File("/home/codeur/Bureau/test/data.xml")) != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;

                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("militaire");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eMilitaire = (Element) nNode;
                        Element ePoste = (Element) eMilitaire.getElementsByTagName("poste").item(0);
                        Element eSpe = (Element) eMilitaire.getElementsByTagName("specialite").item(0);

                        Grade grade;
                        if ((grade = this.facadeGrade.searchFirstResult("grade", eMilitaire.getElementsByTagName("grade").item(0).getTextContent())) == null) {
                            grade = this.facadeGrade.newInstance();
                            grade.setGrade(eMilitaire.getElementsByTagName("grade").item(0).getTextContent());
                            this.facadeGrade.create(grade);
                        }

                        Specialite specialite;
                        if ((specialite = this.facadeSpecialite.searchFirstResult("numeroSpe", eSpe.getAttribute("numero_spe"))) == null) {
                            specialite = this.facadeSpecialite.newInstance();
                            specialite.setNumeroSpe(eSpe.getAttribute("numero_spe"));
                            specialite.setLibelle(eMilitaire.getElementsByTagName("specialite").item(0).getTextContent());
                            this.facadeSpecialite.create(specialite);
                        }

                        ZMR zmr;
                        if ((zmr = this.facadeZMR.searchFirstResult("libelle", ePoste.getElementsByTagName("zmr").item(0).getTextContent())) == null) {
                            zmr = this.facadeZMR.newInstance();
                            zmr.setLibelle(ePoste.getElementsByTagName("zmr").item(0).getTextContent());
                            this.facadeZMR.create(zmr);
                        }

                        Ville ville;
                        if ((ville = this.facadeVille.searchFirstResult("nom", ePoste.getElementsByTagName("ville").item(0).getTextContent())) == null) {
                            ville = this.facadeVille.newInstance();
                            ville.setNom(ePoste.getElementsByTagName("ville").item(0).getTextContent());
                            ville.setZmr(zmr);
                            this.facadeVille.create(ville);
                        }

                        Unite unite;
                        if ((unite = this.facadeUnite.searchFirstResult("libelle", ePoste.getElementsByTagName("unite").item(0).getTextContent())) == null) {
                            unite = this.facadeUnite.newInstance();
                            unite.setLibelle(ePoste.getElementsByTagName("unite").item(0).getTextContent());
                            unite.setVille(ville);
                            this.facadeUnite.create(unite);
                        }

                        Poste poste;
                        if ((poste = this.facadePoste.searchFirstResult("libelle", ePoste.getAttribute("nom"))) == null) {
                            poste = this.facadePoste.newInstance();
                            poste.setLibelle(ePoste.getAttribute("nom"));
                            poste.setUnite(unite);
                            this.facadePoste.create(poste);
                        }

                        Utilisateur utilisateur;

                        if ((utilisateur = this.facadeUtilisateur.searchFirstResult("identifiantAnudef", eMilitaire.getAttribute("idanudef"))) != null && utilisateur.getEstValide() == false) {
                            utilisateur.setMail(eMilitaire.getElementsByTagName("email").item(0).getTextContent());
                            utilisateur.setNia(eMilitaire.getElementsByTagName("nia").item(0).getTextContent());
                            utilisateur.setNom(eMilitaire.getElementsByTagName("nom").item(0).getTextContent());
                            utilisateur.setPrenom(eMilitaire.getElementsByTagName("prenom").item(0).getTextContent());
                            utilisateur.setGrade(grade);
                            utilisateur.setSpecialite(specialite);
                            utilisateur.setPoste(poste);
                            utilisateur.setEstValide(true);
                            // TODO Put False !
                            utilisateur.setInformationsValide(true);
                            this.facadeUtilisateur.update(utilisateur);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
