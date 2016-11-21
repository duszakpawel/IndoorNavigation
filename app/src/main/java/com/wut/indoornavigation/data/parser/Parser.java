package com.wut.indoornavigation.data.parser;

import com.wut.indoornavigation.data.model.Building;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private final String filename;
    private Building building;

    public Parser(String filename) {
        this.filename = filename;
    }

    public void parse() throws IOException {

        final BufferedReader br = new BufferedReader(new FileReader(filename));
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
