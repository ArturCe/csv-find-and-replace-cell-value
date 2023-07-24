package org.example;

import java.util.ArrayList;
import java.util.List;

public class Rebuilder {

    private Rebuilder() {}

    public static List<String> rebuildOneRow(List<String> content, String textToReplace, FileData fileData) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {

            String[] array = content.get(i).split(FileData.getDelimiter());

            if (i == fileData.getRowIndex() && array.length >= fileData.getColumnIndex()) {
                array[fileData.getColumnIndex()] = textToReplace;
            }

            result.add(buildString(array, content.get(i), FileData.getDelimiter()));
        }
        return result;
    }

    public static List<String> rebuildAllRows(List<String> content, String textToReplace, FileData fileData) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {

            String[] array = content.get(i).split(FileData.getDelimiter());

            if (i > fileData.getHeaderIndex() && array.length >= fileData.getColumnIndex()) {
                array[fileData.getColumnIndex()] = textToReplace;
            }

            result.add(buildString(array, content.get(i), FileData.getDelimiter()));
        }
        return result;
    }

    private static String buildString(String[] splittedRow, String row, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String string : splittedRow) {
            sb.append(string).append(delimiter);
        }
        String string = sb.toString();
        return (!row.endsWith(delimiter)) ? string.substring(0 ,string.length() - 1) : string;
    }

}
