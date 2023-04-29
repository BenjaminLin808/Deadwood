package benlinkurgra.deadwood;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import benlinkurgra.deadwood.location.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class ParseXML {
    public static void main(String[] args) {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {

            doc = parsing.getDocFromFile("board.xml");
            parsing.readBoardData(doc);

        } catch (Exception e) {

            System.out.println("Error = " + e);

        }
    }
    public Document getDocFromFile(String filename)
            throws ParserConfigurationException{
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try{
                doc = db.parse("D:\\IdeaProjects\\Deadwood\\src\\main\\resources\\board.xml");
            } catch (Exception ex){
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling
    }

    public void readBoardData(Document d){
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        NodeList trailers = root.getElementsByTagName("trailer");
        NodeList offices = root.getElementsByTagName("office");

//        readBoardSets(sets);
//        readTrailers(trailers);
        readOffices(offices);

    }

    public Map<String, SetLocation> readBoardSets(NodeList sets){
        Map<String, SetLocation> setsData = new HashMap<>();

        for (int i = 0; i < sets.getLength(); i++){
            Element set = (Element) sets.item(i);
            NodeList neighborsElement = set.getElementsByTagName("neighbor");
            NodeList takesElement = set.getElementsByTagName("take");
            NodeList partsElement = set.getElementsByTagName("part");

            String setName = set.getAttribute("name");
            ArrayList<String> neighbors = new ArrayList<>();

            int takes = 0;
            String roleName = "";
            int roleRank = 0;
            String roleLine = "";
            for(int j = 0; j < neighborsElement.getLength(); j++) {
                Element neighbor = (Element) neighborsElement.item(j);
                String neighborName = neighbor.getAttribute("name");
                neighbors.add(neighborName);
            }
            for(int j = 0; j < takesElement.getLength(); j++){
                Element take = (Element) takesElement.item(j);
                int shotCounter = Integer.parseInt(take.getAttribute("number"));
                if(shotCounter > takes){
                    takes = shotCounter;
                }
            }

            List<RoleData> roleList = new ArrayList<>();
            for(int j = 0; j < partsElement.getLength(); j++){
                Element part = (Element) partsElement.item(j);
                String partName = part.getAttribute("name");
                String partLevel = part.getAttribute("level");
                String partLine =  part.getElementsByTagName("line").item(0).getTextContent();
                roleName = partName;
                roleRank = Integer.parseInt(partLevel);
                roleLine = partLine;
                roleList.add(new RoleData(roleRank, partName, partLine, false));
            }
            Roles setRoles = new Roles(roleList);
            SetLocation setLocation = new SetLocation(setName, takes, setRoles, neighbors);
            setsData.put(setName, setLocation);
        }
        return setsData;
    }

    public Trailers readTrailers(NodeList trailers){
        ArrayList<String> neighbors = new ArrayList<>();
        for(int i = 0; i < trailers.getLength(); i++){
            Element trailer = (Element) trailers.item(i);
            NodeList neighborsElement = trailer.getElementsByTagName("neighbor");
            for(int j = 0; j < neighborsElement.getLength(); j++) {
                Element neighbor = (Element) neighborsElement.item(j);
                String neighborName = neighbor.getAttribute("name");
                neighbors.add(neighborName);
            }
        }
        return new Trailers("trailers", neighbors);
    }

    public CastingOffice readOffices(NodeList offices){
        ArrayList<String> neighbors = new ArrayList<>();
        Map<Integer, Map<String, Integer>> upgradeCost = new HashMap<>();
        int upgradeRank = 0;
        String currencyType = "";
        int amount = 0;
        for(int i = 0; i < offices.getLength(); i++){
            Element office = (Element) offices.item(i);
            NodeList neighborsElement = office.getElementsByTagName("neighbor");
            NodeList upgradeElement = office.getElementsByTagName("upgrade");
            for(int j = 0; j < neighborsElement.getLength(); j++) {
                Element neighbor = (Element) neighborsElement.item(j);
                String neighborName = neighbor.getAttribute("name");
                neighbors.add(neighborName);
            }
            for(int j = 0; j < upgradeElement.getLength(); j++){
                Element upgrade = (Element) upgradeElement.item(j);
                String level = upgrade.getAttribute("level");
                String currency = upgrade.getAttribute("currency");
                String amt = upgrade.getAttribute("amt");
                upgradeRank = Integer.parseInt(level);
                currencyType = currency;
                amount = Integer.parseInt(amt);
                Map<String, Integer> temp = new HashMap<>();
                temp.put(currencyType, amount);
                upgradeCost.put(upgradeRank, temp);
                System.out.println(upgradeCost.get(upgradeRank).get(currencyType));
            }

        }
        return new CastingOffice("CastingOffice", upgradeCost, neighbors);
    }

}
