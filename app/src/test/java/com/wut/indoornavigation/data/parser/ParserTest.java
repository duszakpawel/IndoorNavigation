package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.model.Building;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.parsers.DocumentBuilderFactory;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest {

    private static final String FILE_NAME = "test.xml";

    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private final Parser parser = new Parser(documentBuilderFactory);

    @Test
    public void generalParseTest() {
        parser.parse(getClass().getClassLoader().getResource(FILE_NAME).getFile());
    }

    @Test
    public void objectsCountTest() {
        final Building building = parser.parse(getClass().getClassLoader().getResource(FILE_NAME).getFile());
        final int floorsCount = 2;

        final int firstFloorRoomCount = 2;
        final int firstFloorStairsCount = 2;
        final int firstFloorElevatorCount = 1;

        final int secondFloorRoomCount = 3;
        final int secondFloorStairsCount = 2;
        final int secondFloorElevatorCount = 1;

        int firstFloorIndex = -1, secondFloorIndex = -1;

        for (int i = 0; i < building.getFloors().size(); i++) {
            if (building.getFloors().get(i).getNumber() == 0)
                firstFloorIndex = i;
        }
        for (int i = 0; i < building.getFloors().size(); i++) {
            if (building.getFloors().get(i).getNumber() == 1)
                secondFloorIndex = i;
        }

        Assert.assertEquals(building.getFloors().size(), floorsCount);
        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getRooms().size(), firstFloorRoomCount);
        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getElevators().size(), firstFloorElevatorCount);
        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getStairs().size(), firstFloorStairsCount);

        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getRooms().size(), secondFloorRoomCount);
        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getElevators().size(), secondFloorElevatorCount);
        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getStairs().size(), secondFloorStairsCount);
    }

}