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
    public Map<String, Location> getLocations(String filename) throws ParserConfigurationException {
        Map<String, Location> locations = new HashMap<>();
        try {
            Document doc = getDocFromFile(filename);
            //TODO decide who we want locations structured
            locations = readBoardData(doc);
            return locations;
        } catch (Exception e) {
            System.out.println("Error = " + e);
            throw e;
        }
    }

    private Document getDocFromFile(String filename)
            throws ParserConfigurationException {
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try {
                doc = db.parse(filename);
            } catch (Exception ex) {
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling
    }

    private Map<String, Location> readBoardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        NodeList trailers = root.getElementsByTagName("trailer");
        NodeList offices = root.getElementsByTagName("office");

        //TODO may want to change this from a string to something else
        Map<String, Location> locations = readBoardSets(sets);
        locations.put("office", readOffices(offices));
        locations.put("trailer", readTrailers(trailers));
        return locations;
    }

    private Map<String, Location> readBoardSets(NodeList sets) {
        Map<String, Location> setsData = new HashMap<>();

        for (int i = 0; i < sets.getLength(); i++) {
            Element set = (Element) sets.item(i);
            NodeList neighborsElement = set.getElementsByTagName("neighbor");
            NodeList takesElement = set.getElementsByTagName("take");
            NodeList partsElement = set.getElementsByTagName("part");

            String setName = set.getAttribute("name");

            ArrayList<String> neighbors = new ArrayList<>();

            Element setAreaElement = (Element) set.getElementsByTagName("area").item(0);
            Coordinates setCoordinates = new Coordinates(
                    Integer.parseInt(setAreaElement.getAttribute("x")),
                    Integer.parseInt(setAreaElement.getAttribute("y")),
                    Integer.parseInt(setAreaElement.getAttribute("w")),
                    Integer.parseInt(setAreaElement.getAttribute("h"))
            );

            for (int j = 0; j < neighborsElement.getLength(); j++) {
                Element neighbor = (Element) neighborsElement.item(j);
                String neighborName = neighbor.getAttribute("name");
                neighbors.add(neighborName);
            }

            int takes = takesElement.getLength();
            List<Coordinates> takeCoordinates = new ArrayList<>();
            for (int j = 0; j < takesElement.getLength(); j++) {
                Element take = (Element) takesElement.item(j);
                Element takeAreaElement = (Element) take.getElementsByTagName("area").item(0);
                takeCoordinates.add(new Coordinates(
                        Integer.parseInt(takeAreaElement.getAttribute("x")),
                        Integer.parseInt(takeAreaElement.getAttribute("y")),
                        Integer.parseInt(takeAreaElement.getAttribute("w")),
                        Integer.parseInt(takeAreaElement.getAttribute("h"))
                ));

                int shotCounter = Integer.parseInt(take.getAttribute("number"));
                if (shotCounter > takes) {
                    takes = shotCounter;
                }
            }

            List<RoleData> roleList = new ArrayList<>();
            for (int j = 0; j < partsElement.getLength(); j++) {
                Element part = (Element) partsElement.item(j);
                String partName = part.getAttribute("name");
                int partLevel = Integer.parseInt(part.getAttribute("level"));
                String partLine = part.getElementsByTagName("line").item(0).getTextContent();

                Element areaElement = (Element) part.getElementsByTagName("area").item(0);
                Coordinates roleCoordinates = new Coordinates(
                        Integer.parseInt(areaElement.getAttribute("x")),
                        Integer.parseInt(areaElement.getAttribute("y")),
                        Integer.parseInt(areaElement.getAttribute("w")),
                        Integer.parseInt(areaElement.getAttribute("h")));

                roleList.add(new RoleData(partLevel, partName, partLine, roleCoordinates));
            }
            Roles setRoles = new Roles(roleList);
            SetLocation setLocation = new SetLocation(
                    setName,
                    takes,
                    setRoles,
                    neighbors,
                    setCoordinates,
                    takeCoordinates);

            setsData.put(setName, setLocation);
        }
        return setsData;
    }

    private Trailers readTrailers(NodeList trailers) {
        ArrayList<String> neighbors = new ArrayList<>();

        Element trailer = (Element) trailers.item(0);
        NodeList neighborsElement = trailer.getElementsByTagName("neighbor");
        for (int j = 0; j < neighborsElement.getLength(); j++) {
            Element neighbor = (Element) neighborsElement.item(j);
            String neighborName = neighbor.getAttribute("name");
            neighbors.add(neighborName);
        }
        Element areaElement = (Element) trailer.getElementsByTagName("area").item(0);
        Coordinates coordinates = new Coordinates(
                Integer.parseInt(areaElement.getAttribute("x")),
                Integer.parseInt(areaElement.getAttribute("y")),
                Integer.parseInt(areaElement.getAttribute("w")),
                Integer.parseInt(areaElement.getAttribute("h")));

        // trailer has no attribute name in XML but sticking with name convention
        // used throughout file
        return new Trailers("trailer", neighbors, coordinates);
    }

    private CastingOffice readOffices(NodeList offices) {
        ArrayList<String> neighbors = new ArrayList<>();
        Map<Integer, UpgradeCost> upgrades = new HashMap<>();

        Element office = (Element) offices.item(0);

        Element areaElement = (Element) office.getElementsByTagName("area").item(0);
        Coordinates coordinates = new Coordinates(
                Integer.parseInt(areaElement.getAttribute("x")),
                Integer.parseInt(areaElement.getAttribute("y")),
                Integer.parseInt(areaElement.getAttribute("w")),
                Integer.parseInt(areaElement.getAttribute("h")));

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

        for (int key : upgradesModel.keySet()) {
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

        // office has no attribute name in XML but sticking with name convention
        // used throughout file
        return new CastingOffice("office", upgrades, neighbors, coordinates);
    }
}
