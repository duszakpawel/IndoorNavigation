package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.model.Floor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest {

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    Parser parser = new Parser(documentBuilderFactory);

    @Ignore
    @Test
    public void generalParseTest() {
        Building building = parser.parse("C:\\Users\\sa\\Desktop\\IndoorNavigation\\app\\src\\test\\resources\\test.xml");
    }

    @Ignore
    @Test
    public void objectsCountTest() {
        Building building = parser.parse("C:\\Users\\sa\\Desktop\\IndoorNavigation\\app\\src\\test\\resources\\test.xml");
        final int floorsCount =2;

        final int firstFloorRoomCount =2;
        final int firstFloorStairsCount = 2;
        final int firstFloorElevatorCount = 1;

        final int secondFloorRoomCount =3;
        final int secondFloorStairsCount = 2;
        final int secondFloorElevatorCount = 1;

        Assert.assertEquals(building.getFloors().size(), floorsCount);

        int firstFloorIndex = -1, secondFloorIndex = -1;

        for(int i=0; i < building.getFloors().size(); i++){
            if(building.getFloors().get(i).getNumber() == 0)
                firstFloorIndex = i;
        }
        for(int i=0; i < building.getFloors().size(); i++){
            if(building.getFloors().get(i).getNumber() == 1)
                secondFloorIndex = i;
        }

        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getRooms().size(), firstFloorRoomCount);
        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getElevators().size(), firstFloorElevatorCount);
        Assert.assertEquals(building.getFloors().get(firstFloorIndex).getStairs().size(), firstFloorStairsCount);

        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getRooms().size(), secondFloorRoomCount);
        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getElevators().size(), secondFloorElevatorCount);
        Assert.assertEquals(building.getFloors().get(secondFloorIndex).getStairs().size(), secondFloorStairsCount);
    }

}