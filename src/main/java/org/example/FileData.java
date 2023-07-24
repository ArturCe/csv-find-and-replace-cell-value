package org.example;

import java.util.*;

public class FileData {

    private boolean isRowFound = false;
    private int rowIndex = 0;
    private static boolean isColumnFound = false;
    private static int columnIndex = 0;
    private static int headerIndex = 0;
    private static String delimiter;
    private static List<Integer> matchedRows = new ArrayList<>();

    FileData() {}

    public static boolean isColumnFound() {
        return isColumnFound;
    }

    public boolean isRowFound() {
        return isRowFound;
    }

    public static int getColumnIndex() {
        return columnIndex;
    }

    public static int getHeaderIndex() {
        return headerIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public static List<Integer> getMatchedRows() {
        return matchedRows;
    }

    public static String getDelimiter() {
        return delimiter;
    }

    public static void setDelimiter (String del) {
        delimiter = del;
    }

    public static void findDelimiter(String[] delimiterList, List<String> content) {
        int occurence = 0;
        String result = "";

        for (String s : delimiterList) {
            int count = 0;

            for (String row : content) {
                for (int i = 0; i < row.length(); i++) {
                    if (row.charAt(i) == s.charAt(0)) {
                        count++;
                    }
                }
                if (count > 0) {
                    result = s;
                }
            }

            if (count > occurence) {
                occurence = occurence + count;
            }
        }

        if (result.length() == 0) {
            System.out.println("Nie znaleziono separatora z listy " + Arrays.asList(delimiterList));
            System.exit(0);
        }
        delimiter = result;
    }

    public void findColumnAndRow(String columnTextToFind, String rowTextToFind, List<String> content, String delimiter) {

        for (int i = 0; i < content.size(); i++) {

            if (content.get(i).length() > 0) {

                if (content.get(i).contains(rowTextToFind)) {
                    rowIndex = i;
                    isRowFound = true;
                }

                String[] array = content.get(i).split(delimiter);

                for (int j = 0; j < array.length; j++) {
                    if (array[j].equalsIgnoreCase(columnTextToFind)) {
                        headerIndex = i;
                        columnIndex = j;
                        isColumnFound = true;
                        break;
                    }
                }
            }
        }

        System.out.println(rowTextToFind +  ", row " + rowIndex + ", " + isRowFound);
        System.out.println(columnTextToFind + ", col " + columnIndex + ", " + isColumnFound);
        System.out.println("header " + headerIndex);
        System.out.println("delimiter=" + delimiter);
    }

    public void findColumnAndRowMultiParam(String columnToFind, List<String> rowTextToFind, List<String> content) {

        for (int i = 0; i < content.size(); i++) {

            if (content.get(i).length() > 0) {

                String[] array = content.get(i).split(delimiter);
                List<String> rowList = Arrays.asList(array);

                if (containsPartialText(rowTextToFind, rowList)) {
                    rowIndex = i;
                    isRowFound = true;
                }

                for (int j = 0; j < rowList.size(); j++) {
                    if (rowList.get(j).equalsIgnoreCase(columnToFind)) {
                        headerIndex = i;
                        columnIndex = j;
                        isColumnFound = true;
                        break;
                    }
                }

                if (isRowFound && isColumnFound)
                    break;
            }
        }

        System.out.println(rowTextToFind +  ", r " + rowIndex + ", " + isRowFound);
        System.out.println(columnToFind + ", c " + columnIndex + ", " + isColumnFound);
        System.out.println("header " + headerIndex);
        System.out.println("delimiter=" + delimiter);
    }

    public static void findColumn(String name, List<String> content) {
        for (int i = 0; i < content.size(); i++) {

            if (content.get(i).length() > 0) {

                String[] array = content.get(i).split(delimiter);
                List<String> rowList = Arrays.asList(array);

                for (int j = 0; j < rowList.size(); j++) {
                    if (rowList.get(j).equalsIgnoreCase(name)) {
                        headerIndex = i;
                        columnIndex = j;
                        isColumnFound = true;
                        break;
                    }
                }
            }
        }
    }

    public static void findRows(List<Params> params, List<String> content) {

        for (int i = 0; i < content.size(); i++) {
            FileData.findParamsInRow(params, content.get(i), i);
        }

        FileData.setMatchedRows(params);
    }

    private static void findParamsInRow(List<Params> params, String text, int rowIndex) {

        String[] array = text.split(FileData.getDelimiter());

        for (int i = 0; i < array.length; i++) {

            for (Params p : params) {
                if ((p.isContains() && array[i].contains(p.getTextToFind())) || (p.isEqual() && array[i].equals(p.getTextToFind()))) {
                    p.setColumn(i);
                    p.setRow(rowIndex);
                    p.setFound(true);
                }
            }
        }
    }

    private static void setMatchedRows(List<Params> params) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Params p : params) {
            for (Integer i : p.getRow()) {
                if (!map.containsKey(i)) {
                    map.put(i, 1);
                } else {
                    map.replace(i, map.get(i) + 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == params.size()) {
                matchedRows.add(entry.getKey());
            }
        }
    }

    public void printCellValue(String columnTextToFind, List<String> rowTextToFind, List<String> content) {
        FileData fd = new FileData();
        fd.findColumnAndRowMultiParam(columnTextToFind, rowTextToFind, content);
        String[] array = content.get(fd.getRowIndex()).split(delimiter);
        System.out.println("cell value = " + array[fd.getColumnIndex()]);

    }


    private boolean containsPartialText(List<String> small, List<String> big) {
        Map<String, Boolean> map = new HashMap<>();

        for (String b : big) {
            for (String s : small) {
                if (b.toLowerCase().contains(s.toLowerCase())) {
                    map.put(b, true);
                }
            }
        }

        return map.size() == small.size();
    }

}
