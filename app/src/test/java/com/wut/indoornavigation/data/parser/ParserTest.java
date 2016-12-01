package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.model.Building;

import org.junit.Before;
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

    @Test
    public void test() {
        Building building = parser.parse("C:\\Users\\sa\\Desktop\\IndoorNavigation\\app\\src\\test\\resources\\test.xml");
    }
}