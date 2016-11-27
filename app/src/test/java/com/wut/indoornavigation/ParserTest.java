package com.wut.indoornavigation;

import com.wut.indoornavigation.data.model.Building;
import com.wut.indoornavigation.data.parser.Parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void simpleParse() throws Exception {

        Parser parser = new Parser("C:\\Users\\sa\\Desktop\\IndoorNavigation\\app\\src\\test\\resources\\test.xml");
        parser.Parse();
    }
}