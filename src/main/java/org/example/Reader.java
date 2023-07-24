package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private static List<String> records = new ArrayList<>();

    public static List<String> readFileContent(String filePath) {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
