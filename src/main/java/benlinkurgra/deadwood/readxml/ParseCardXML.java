package benlinkurgra.deadwood.readxml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import benlinkurgra.deadwood.location.*;
import benlinkurgra.deadwood.model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.*;

public class ParseCardXML {

    public Queue<Scene> getScenes(URL filename) throws ParserConfigurationException {
        try {
            Document doc = getDocFromFile(filename);
            return readCardData(doc);
        } catch (Exception e) {
            System.out.println("Error = " + e);
            throw e;
        }
    }

    private Document getDocFromFile(URL filename)
            throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
            doc = db.parse(filename.openConnection().getInputStream());
        } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    } // exception handling

    private Queue<Scene> readCardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        return readCardRoles(cards);
    }
    private Queue<Scene>  readCardRoles(NodeList cards) {
        List<Scene> sceneCards = new ArrayList<>();
        for(int i = 0; i < cards.getLength(); i++){
            List<RoleData> roleList = new ArrayList<>();
            Element card = (Element) cards.item(i);
            NodeList sceneElement = card.getElementsByTagName("scene");
            String cardName = card.getAttribute("name");
            int budget = Integer.parseInt(card.getAttribute("budget"));
            int sceneNum = 0;
            String sceneLine = "";
            for(int j = 0; j < sceneElement.getLength(); j++){
                Element scene = (Element) sceneElement.item(j);
                sceneNum = Integer.parseInt(scene.getAttribute("number"));
                sceneLine = scene.getTextContent();
            }
            NodeList partsElement = card.getElementsByTagName("part");
            for(int j = 0; j < partsElement.getLength(); j++){
                Element part = (Element) partsElement.item(j);
                String partName = part.getAttribute("name");
                int partLevel = Integer.parseInt(part.getAttribute("level"));
                String partLine =  part.getElementsByTagName("line").item(0).getTextContent();

                Element areaElement = (Element) part.getElementsByTagName("area").item(0);
                int[] area = new int[4];
                area[0] = Integer.parseInt(areaElement.getAttribute("x"));
                area[1] = Integer.parseInt(areaElement.getAttribute("y"));
                area[2] = Integer.parseInt(areaElement.getAttribute("h"));
                area[3] = Integer.parseInt(areaElement.getAttribute("w"));


                roleList.add(new RoleData(partLevel, partName, partLine, area));
            }
            sceneCards.add(new Scene(cardName, budget, sceneLine, new Roles(roleList), sceneNum));
        }
        Collections.shuffle(sceneCards);
        return new LinkedList<>(sceneCards);
    }
}
