package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.exception.MapParseException;
import com.wut.indoornavigation.data.model.Beacon;
import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.BuildingObject;
import com.wut.indoornavigation.data.model.Elevator;
import com.wut.indoornavigation.data.model.Floor;
import com.wut.indoornavigation.data.model.FloorObject;
import com.wut.indoornavigation.data.model.Room;
import com.wut.indoornavigation.data.model.Stairs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Singleton
public final class Parser {

    private static final String ROOMS_TAG = "Rooms";
    private static final String ROOM_TAG = "Room";
    private static final String STAIRS_TAG = "Stairs";
    private static final String STAIR_TAG = "Stair";
    private static final String ELEVATORS_TAG = "Elevators";
    private static final String ELEVATOR_TAG = "Elevator";
    private static final String BEACONS_TAG = "Beacons";
    private static final String BEACON_TAG = "Beacon";
    private static final String MAP_TAG = "Map";
    private static final String FLOOR_NUMBER_ATTR_TAG = "floor";
    private static final String ID_ATTR_TAG = "id";
    private static final String END_FLOOR_ATTR_TAG = "endfloor";
    private static final String END_ID_TAG = "endid";

    private final DocumentBuilderFactory documentBuilderFactory;

    @Inject
    public Parser(DocumentBuilderFactory documentBuilderFactory) {
        this.documentBuilderFactory = documentBuilderFactory;
    }

    public Building parse(String filename) {
        List<Floor> floors = new ArrayList<>();
        FloorObject[][] groundFloor = new FloorObject[][]{
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ROOM, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.ROOM, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.WALL},
                {FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.SPACE, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.WALL, FloorObject.CORNER, FloorObject.WALL, FloorObject.WALL, FloorObject.ROOM, FloorObject.CORNER},

        };
        List<Room> doors = new ArrayList<>();
        Room firstRoom = Room.builder().id(1).number(1).build();
        doors.add(firstRoom);
        Room secondRoom = Room.builder().id(2).number(2).build();
        doors.add(secondRoom);
        Room thirdRoom = Room.builder().id(3).number(3).build();
        doors.add(thirdRoom);
        List<Stairs> stairs = new ArrayList<>();
        List<Elevator> elevators = new ArrayList<>();
        floors.add(Floor.builder().enumMap(groundFloor).number(0).rooms(doors).stairs(stairs).elevators(elevators).build());
        //floors.add(Floor.builder().enumMap(groundFloor).number(1).rooms(doors).stairs(stairs).elevators(elevators).build());
        //floors.add(Floor.builder().enumMap(groundFloor).number(2).rooms(doors).stairs(stairs).elevators(elevators).build());
        //floors.add(Floor.builder().enumMap(groundFloor).number(3).rooms(doors).stairs(stairs).elevators(elevators).build());
        Building building = Building.builder().floors(floors).build();
        return building;
//        try {
//            final File fXmlFile = new File(filename);
//            final DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();
//            final Document document = dBuilder.parse(fXmlFile);
//
//            document.getDocumentElement().normalize();
//
//            final List<Floor> floorsList = new LinkedList<>();
//
//            final NodeList roomsNodes = document.getElementsByTagName(ROOMS_TAG);
//
//            for (int i = 0; i < roomsNodes.getLength(); i++) {
//                final Node roomsNode = roomsNodes.item(i);
//                int roomId = 0;
//                if (roomsNode.getNodeType() == Node.ELEMENT_NODE) {
//                    final Element roomsElement = (Element) roomsNode;
//                    final List<Room> roomList = new LinkedList<>();
//                    final NodeList roomNodes = roomsElement.getElementsByTagName(ROOM_TAG);
//
//                    for (int j = 0; j < roomNodes.getLength(); j++) {
//                        final int roomNumber = Integer.parseInt(roomNodes.item(j).getTextContent());
//                        roomList.add(Room.builder()
//                                .id(roomId++)
//                                .number(roomNumber)
//                                .build());
//                    }
//
//                    floorsList.add(Floor.builder()
//                            .number(Integer.parseInt(roomsElement.getAttribute(FLOOR_NUMBER_ATTR_TAG)))
//                            .rooms(roomList)
//                            .build());
//                }
//            }//Floors and rooms added
//
//            addStairs(floorsList, document);
//            addElevators(floorsList, document);
//            addBeacons(floorsList, document);
//            addMap(floorsList, document);
//
//            return Building.builder()
//                    .floors(floorsList)
//                    .build();
//        } catch (Exception e) {
//            throw new MapParseException("Error while parsing map", e);
//        }
    }

    private void addStairs(List<Floor> floors, Document document) {
        final NodeList stairsNodes = document.getElementsByTagName(STAIRS_TAG);

        for (int i = 0; i < stairsNodes.getLength(); i++) {
            final int floorNumber = Integer.parseInt(((Element) (stairsNodes.item(i)))
                    .getAttribute(FLOOR_NUMBER_ATTR_TAG));
            final NodeList stairNodes = ((Element) (stairsNodes.item(i))).getElementsByTagName(STAIR_TAG);
            final List<Stairs> stairsList = new LinkedList<>();

            for (int j = 0; j < stairNodes.getLength(); j++) {
                final Element stairNode = (Element) (stairNodes.item(j));

                stairsList.add(Stairs.builder()
                        .id(Integer.parseInt(stairNode.getAttribute(ID_ATTR_TAG)))
                        .endfloor(Integer.parseInt(stairNode.getAttribute(END_FLOOR_ATTR_TAG)))
                        .endid(Integer.parseInt(stairNode.getAttribute(END_ID_TAG)))
                        .build());
            }

            final int stairsFloorIndex = findFloorIndex(floors, floorNumber);
            final Floor stairsFloor = floors.get(stairsFloorIndex);

            floors.set(stairsFloorIndex, stairsFloor.toBuilder()
                    .stairs(stairsList)
                    .build());
        }
    }

    private void addElevators(List<Floor> floors, Document document) {
        final NodeList elevatorsNodes = document.getElementsByTagName(ELEVATORS_TAG);

        for (int i = 0; i < elevatorsNodes.getLength(); i++) {
            final int floorNumber = Integer.parseInt(((Element) (elevatorsNodes.item(i)))
                    .getAttribute(FLOOR_NUMBER_ATTR_TAG));
            final NodeList elevatorNodes = ((Element) (elevatorsNodes.item(i))).getElementsByTagName((ELEVATOR_TAG));
            final List<Elevator> elevatorList = new LinkedList<>();

            for (int j = 0; j < elevatorNodes.getLength(); j++) {
                final Element elevatorNode = (Element) (elevatorNodes.item(j));

                elevatorList.add(Elevator.builder()
                        .id(Integer.parseInt(elevatorNode.getAttribute(ID_ATTR_TAG)))
                        .build());
            }
            final int elevatorsFloorIndex = findFloorIndex(floors, floorNumber);
            final Floor elevatorsFloor = floors.get(elevatorsFloorIndex);

            floors.set(elevatorsFloorIndex, elevatorsFloor.toBuilder()
                    .elevators(elevatorList)
                    .build());
        }
    }

    private void addBeacons(List<Floor> floors, Document document) {
        final NodeList beaconsNodes = document.getElementsByTagName(BEACONS_TAG);

        for (int i = 0; i < beaconsNodes.getLength(); i++) {
            final int floorNumber = Integer.parseInt(((Element) (beaconsNodes.item(i)))
                    .getAttribute(FLOOR_NUMBER_ATTR_TAG));
            final NodeList beaconNodes = ((Element) (beaconsNodes.item(i))).getElementsByTagName((BEACON_TAG));
            final List<Beacon> beaconList = new LinkedList<>();

            for (int j = 0; j < beaconNodes.getLength(); j++) {
                final Element beaconNode = (Element) (beaconNodes.item(j));

                beaconList.add(Beacon.builder()
                        .id(Integer.parseInt(beaconNode.getAttribute(ID_ATTR_TAG)))
                        .build());
            }
            final int beaconsFloorIndex = findFloorIndex(floors, floorNumber);
            final Floor beaconsFloor = floors.get(beaconsFloorIndex);

            floors.set(beaconsFloorIndex, beaconsFloor.toBuilder()
                    .beacons(beaconList)
                    .build());
        }
    }

    private void addMap(List<Floor> floors, Document document) {
        NodeList mapNodes = document.getElementsByTagName(MAP_TAG);

        for (int i = 0; i < mapNodes.getLength(); i++) {
            final Element mapNode = (Element) (mapNodes.item(i));
            final String mapString = mapNode.getTextContent();
            final String[] mapStrings = mapString.split("\n");
            int rows = mapStrings.length - 2;
            int columns = (mapStrings[1].length() + 1) / 2;

            for (int r = 1; r < mapStrings.length - 1; r++) {
                if ((mapStrings[r].length() + 1) / 2 > columns)
                    columns = (mapStrings[r].length() + 1) / 2;
            }

            final int floorNumber = Integer.parseInt(((Element) (mapNodes.item(i)))
                    .getAttribute(FLOOR_NUMBER_ATTR_TAG));
            final FloorObject[][] newMap = new FloorObject[rows][columns];
            int floorIndex = findFloorIndex(floors, floorNumber);
            Floor currentFloor = floors.get(floorIndex);
            int roomIndex = 0, beaconIndex = 0, stairIndex = 0, elevatorIndex = 0;

            for (int r = 1; r < mapStrings.length - 1; r++) {
                for (int c = 0; c < mapStrings[r].length(); c += 2) {
                    switch (mapStrings[r].charAt(c)) {
                        case '~':
                            newMap[r - 1][c / 2] = FloorObject.CORNER;
                            break;

                        case '-':
                            newMap[r - 1][c / 2] = FloorObject.WALL;
                            break;

                        case 'P':
                            newMap[r - 1][c / 2] = FloorObject.DOOR;
                            break;

                        case ' ':
                            newMap[r - 1][c / 2] = FloorObject.SPACE;
                            break;

                        case 'D':
                            newMap[r - 1][c / 2] = FloorObject.ROOM;
                            int roomInd = findBuildingObjectIndex(currentFloor.getRooms(), roomIndex++);
                            Room room = currentFloor.getRooms().get(roomInd);
                            room = room.toBuilder().x(r - 1).y(c / 2).build();
                            currentFloor.getRooms().set(roomInd, room);
                            break;

                        case 'B':
                            newMap[r - 1][c / 2] = FloorObject.BEACON;
                            int beaconInd = findBuildingObjectIndex(currentFloor.getBeacons(), beaconIndex++);
                            Beacon beacon = currentFloor.getBeacons().get(beaconInd);
                            beacon = beacon.toBuilder().x(r - 1).y(c / 2).build();
                            currentFloor.getBeacons().set(beaconInd, beacon);
                            break;

                        case 'S':
                            newMap[r - 1][c / 2] = FloorObject.STAIRS;
                            int stairInd = findBuildingObjectIndex(currentFloor.getStairs(), stairIndex++);
                            Stairs stair = currentFloor.getStairs().get(stairInd);
                            stair = stair.toBuilder().x(r - 1).y(c / 2).build();
                            currentFloor.getStairs().set(stairInd, stair);
                            break;

                        case 'E':
                            newMap[r - 1][c / 2] = FloorObject.ELEVATOR;
                            int elevatorInd = findBuildingObjectIndex(currentFloor.getElevators(), elevatorIndex++);
                            Elevator elevator = currentFloor.getElevators().get(elevatorInd);
                            elevator = elevator.toBuilder().x(r - 1).y(c / 2).build();
                            currentFloor.getElevators().set(elevatorInd, elevator);
                            break;


                    }
                }
            }
            floors.set(floorIndex, currentFloor.toBuilder().enumMap(newMap).build());
        }
    }

    private int findFloorIndex(List<Floor> floors, int number) {
        for (int i = 0; i < floors.size(); i++) {
            if (floors.get(i).getNumber() == number) {
                return i;
            }
        }
        throw new IllegalArgumentException("There is no floor with number " + number);
    }

    private int findBuildingObjectIndex(List<? extends BuildingObject> buildingObjects, int number) {
        for (int i = 0; i < buildingObjects.size(); i++) {
            if (buildingObjects.get(i).getId() == number) {
                return i;
            }
        }
        throw new IllegalArgumentException("There is no object with id " + number);
    }
}
