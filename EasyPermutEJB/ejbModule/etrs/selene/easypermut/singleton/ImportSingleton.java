package etrs.selene.easypermut.singleton;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
@Startup
@DependsOn("InitSingleton")
public class ImportSingleton {

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

    /**
     * Methode d'import des données. Cette methode parse le XML. Si elle
     * rencontre des informations non présentes dans la base, elle les crée et
     * les ajoute en BDD.
     */
    /**
     * Le @PostConstruct doit etre remplacé par un @Schedule(hour = "3") lors du
     * lancement de l'application. Pour les tests, on utilisera un @PostConstruct
     * avec un @DependsOn("InitSingleton") pour que la méthode soit exécuté
     * apres l'init.
     */
    @PostConstruct
    public void recupereDonnees() {

        File fXmlFile;

        try {
            File testFile = new File("/home/codeur/Bureau/test/test.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;

            builder = factory.newDocumentBuilder();

            Document dc = builder.parse(testFile);
            dc.getDocumentElement().normalize();

            NodeList listZmr = dc.getElementsByTagName("zmr");
            for (int tempZmr = 0; tempZmr < listZmr.getLength(); tempZmr++) {

                Node nodeZmr = listZmr.item(tempZmr);
                if (nodeZmr.getNodeType() == Node.ELEMENT_NODE) {

                    Element zmr = (Element) nodeZmr;
                    System.err.println("ZMR >> " + zmr.getAttribute("nom"));

                    NodeList listVille = zmr.getElementsByTagName("ville");
                    for (int tempVille = 0; tempVille < listVille.getLength(); tempVille++) {

                        Node nodeVille = listVille.item(tempVille);
                        if (nodeVille.getNodeType() == Node.ELEMENT_NODE) {

                            Element ville = (Element) nodeVille;
                            System.err.println("VLE >> " + ville.getAttribute("nom"));

                            NodeList listUnite = ville.getElementsByTagName("unite");
                            for (int tempUnite = 0; tempUnite < listUnite.getLength(); tempUnite++) {

                                Node nodeUnite = listUnite.item(tempUnite);
                                if (nodeUnite.getNodeType() == Node.ELEMENT_NODE) {

                                    Element unite = (Element) nodeUnite;
                                    System.err.println("UNI >> " + unite.getAttribute("nom"));

                                }
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e1) {
            e1.printStackTrace();
        }

        // try {
        // if ((fXmlFile = new
        // File("/home/codeur/Bureau/test/data.xml")).exists()) {
        // DocumentBuilderFactory dbFactory =
        // DocumentBuilderFactory.newInstance();
        // DocumentBuilder dBuilder;
        //
        // dBuilder = dbFactory.newDocumentBuilder();
        // Document doc = dBuilder.parse(fXmlFile);
        // doc.getDocumentElement().normalize();
        //
        // NodeList nList = doc.getElementsByTagName("militaire");
        //
        // for (int temp = 0; temp < nList.getLength(); temp++) {
        //
        // Node nNode = nList.item(temp);
        //
        // if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        //
        // Element eMilitaire = (Element) nNode;
        // Element ePoste = (Element)
        // eMilitaire.getElementsByTagName("poste").item(0);
        // Element eSpe = (Element)
        // eMilitaire.getElementsByTagName("specialite").item(0);
        //
        // Grade grade;
        // if ((grade = this.facadeGrade.searchFirstResult("grade",
        // eMilitaire.getElementsByTagName("grade").item(0).getTextContent()))
        // == null) {
        // grade = this.facadeGrade.newInstance();
        // grade.setGrade(eMilitaire.getElementsByTagName("grade").item(0).getTextContent());
        // this.facadeGrade.create(grade);
        // }
        //
        // Specialite specialite;
        // if ((specialite =
        // this.facadeSpecialite.searchFirstResult("numeroSpe",
        // eSpe.getAttribute("numero_spe"))) == null) {
        // specialite = this.facadeSpecialite.newInstance();
        // specialite.setNumeroSpe(eSpe.getAttribute("numero_spe"));
        // specialite.setLibelle(eMilitaire.getElementsByTagName("specialite").item(0).getTextContent());
        // this.facadeSpecialite.create(specialite);
        // }
        //
        // ZMR zmr;
        // if ((zmr = this.facadeZMR.searchFirstResult("libelle",
        // ePoste.getElementsByTagName("zmr").item(0).getTextContent())) ==
        // null) {
        // zmr = this.facadeZMR.newInstance();
        // zmr.setLibelle(ePoste.getElementsByTagName("zmr").item(0).getTextContent());
        // this.facadeZMR.create(zmr);
        // }
        //
        // Ville ville;
        // if ((ville = this.facadeVille.searchFirstResult("nom",
        // ePoste.getElementsByTagName("ville").item(0).getTextContent())) ==
        // null) {
        // ville = this.facadeVille.newInstance();
        // ville.setNom(ePoste.getElementsByTagName("ville").item(0).getTextContent());
        // ville.setZmr(zmr);
        // this.facadeVille.create(ville);
        // }
        //
        // Unite unite;
        // if ((unite = this.facadeUnite.searchFirstResult("libelle",
        // ePoste.getElementsByTagName("unite").item(0).getTextContent())) ==
        // null) {
        // unite = this.facadeUnite.newInstance();
        // unite.setLibelle(ePoste.getElementsByTagName("unite").item(0).getTextContent());
        // unite.setVille(ville);
        // this.facadeUnite.create(unite);
        // }
        //
        // Poste poste;
        // if ((poste = this.facadePoste.searchFirstResult("libelle",
        // ePoste.getAttribute("nom"))) == null) {
        // poste = this.facadePoste.newInstance();
        // poste.setLibelle(ePoste.getAttribute("nom"));
        // poste.setUnite(unite);
        // this.facadePoste.create(poste);
        // }
        //
        // Utilisateur utilisateur;
        //
        // if ((utilisateur =
        // this.facadeUtilisateur.searchFirstResult("identifiantAnudef",
        // eMilitaire.getAttribute("idanudef"))) != null &&
        // utilisateur.getEstValide() == false) {
        // utilisateur.setMail(eMilitaire.getElementsByTagName("email").item(0).getTextContent());
        // utilisateur.setNia(eMilitaire.getElementsByTagName("nia").item(0).getTextContent());
        // utilisateur.setNom(eMilitaire.getElementsByTagName("nom").item(0).getTextContent());
        // utilisateur.setPrenom(eMilitaire.getElementsByTagName("prenom").item(0).getTextContent());
        // utilisateur.setGrade(grade);
        // utilisateur.setSpecialite(specialite);
        // utilisateur.setPoste(poste);
        // utilisateur.setEstValide(true);
        // utilisateur.setInformationsValide(true);
        // utilisateur.setDateInscription(new Date());
        // this.facadeUtilisateur.update(utilisateur);
        // }
        // }
        // }
        // }
        // } catch (ParserConfigurationException | SAXException | IOException e)
        // {
        // e.printStackTrace();
        // }

    }
}
