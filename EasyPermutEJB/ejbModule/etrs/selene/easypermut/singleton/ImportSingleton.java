package etrs.selene.easypermut.singleton;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.apachecommons.CommonsLog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Singleton
@Startup
@CommonsLog
public class ImportSingleton {

    @PostConstruct
    public void recupereDonnees() {

        try {
            File fXmlFile = new File("/home/codeur/Bureau/test/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("militaire");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Id Anudef : " + eElement.getAttribute("idanudef"));
                    System.out.println("Grade : " + eElement.getElementsByTagName("grade").item(0).getTextContent());
                    System.out.println("Nom : " + eElement.getElementsByTagName("nom").item(0).getTextContent());
                    System.out.println("Prenom : " + eElement.getElementsByTagName("prenom").item(0).getTextContent());
                    System.out.println("NIA : " + eElement.getElementsByTagName("specialite").item(0).getTextContent());
                    System.out.println("Specialité : " + eElement.getElementsByTagName("nia").item(0).getTextContent());

                    Element ePoste = (Element) eElement.getElementsByTagName("poste").item(0);

                    System.out.println("Poste : " + ePoste.getAttribute("nom"));
                    System.out.println("Unite : " + ePoste.getElementsByTagName("unite").item(0).getTextContent());
                    System.out.println("Ville : " + ePoste.getElementsByTagName("ville").item(0).getTextContent());
                    System.out.println("ZMR : " + ePoste.getElementsByTagName("zmr").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
