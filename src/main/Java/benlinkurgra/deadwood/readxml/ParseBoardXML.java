package benlinkurgra.deadwood.readxml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import benlinkurgra.deadwood.location.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.*;

public class ParseBoardXML {
    public static void main(String[] args) {
        ParseBoardXML parsing = new ParseBoardXML();
        try {
            Document doc = parsing.getDocFromFile("src/main/resources/board.xml");
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
                doc = db.parse(filename);
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

    public Map<String, SetLocation> readBoardSets(NodeList sets) {
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
        Map<Integer, UpgradeCost> upgrades = new HashMap<>();

        for (int i = 0; i < offices.getLength(); i++) {
            Element office = (Element) offices.item(i);
            NodeList neighborsElement = office.getElementsByTagName("neighbor");
            NodeList upgradeElement = office.getElementsByTagName("upgrade");
            for (int j = 0; j < neighborsElement.getLength(); j++) {
                Element neighbor = (Element) neighborsElement.item(j);
                String neighborName = neighbor.getAttribute("name");
                neighbors.add(neighborName);
            }

            // Create a new Map to store the results
            Map<Integer, List<Integer>> upgradesModel = new HashMap<>();

            // Iterate over the NodeList and extract the level and amt attributes
            for (int j = 0; j < upgradeElement.getLength(); j++) {
                Element upgrade = (Element) upgradeElement.item(j);
                int level = Integer.parseInt(upgrade.getAttribute("level"));
                String currency = upgrade.getAttribute("currency");
                int amount = Integer.parseInt(upgrade.getAttribute("amt"));

                if (!upgradesModel.containsKey(level)) {
                    List<Integer> cost = new ArrayList<>();
                    cost.add(-1);
                    cost.add(-1);
                    upgradesModel.put(level, cost);
                }
                if (currency.equals("dollar")) {
                    upgradesModel.get(level).set(0, amount);
                } else if (currency.equals("credit")) {
                    upgradesModel.get(level).set(1, amount);
                }
            }

            for (int key: upgradesModel.keySet()) {
                int dollarCost = upgradesModel.get(key).get(0);
                int creditCost = upgradesModel.get(key).get(1);
                if (dollarCost == -1) {
                    //TODO this should throw an exception
                    System.out.printf("Error, dollar amount not set for rank %d.\n", key);
                } else if (creditCost == -1) {
                    //TODO this should throw an exception
                    System.out.printf("Error, credit amount not set for rank %d.\n", key);
                }
                UpgradeCost cost = new UpgradeCost(dollarCost, creditCost);
                upgrades.put(key, cost);
            }
        }

        return new CastingOffice("CastingOffice", upgrades, neighbors);
    }
}
