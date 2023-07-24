package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    private static String outputPath;
    private static BufferedWriter bufferedWriter = null;

    public static void write(List<String> inputList, String fileName) {

        setOutputFilePath(fileName);

        try {
            FileWriter fileWriter = new FileWriter(outputPath, false);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String string : inputList) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setOutputFilePath(String fileName) {
        outputPath = System.getProperty(Main.getDirectory()) + "\\" + fileName.replace(".csv", ".txt");
    }

    public static String getOutputFilePath() {
        return outputPath;
    }
}
