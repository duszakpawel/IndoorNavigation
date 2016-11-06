package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.model.Building;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by sa on 06.11.2016.
 */

public class Parser {

    private String filename;
    private Building building;

    public Parser(String _filename) {
        filename = _filename;
    }

    public void Parse() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line = br.readLine();

            while (line != null) {
                /// TODO interpretacja linii
            }

        } finally {
            br.close();
        }
    }
}
