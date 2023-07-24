package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String fileName;
    private static String colText;
    private static String rowText = "00:00:00";
    private static List<String> rowTextList = new ArrayList<>();
    private static String newCellValue = "ZZZZZZZZZZ";
    private static String delimiter;
    private static final String[] DELIMITER_LIST = new String[] {",", ";"};
    private static final String DIRECTORY = "user.dir";

    public static void main(String[] args) {

        new Params("nazwa 1", Params.Precision.CONTAINS);
        new Params("00:00:00.000", Params.Precision.CONTAINS);
        new Params("x", Params.Precision.CONTAINS);
//        Params.print();

        colText = "kol 4";
        rowTextList.add("nazwa 1");
        rowTextList.add("00:00:00");

        List<String> result = new ArrayList<>();

        findFiles();

        if (fileName != null) {

            List<String> inputFileContent = Reader.readFileContent(System.getProperty(DIRECTORY) + "\\" + fileName);

            FileData.findDelimiter(DELIMITER_LIST, inputFileContent);

            FileData.findRows(Params.getList(), inputFileContent);

            for (Params p : Params.getList()) {
                if (p.isFound()) {
                    System.out.println(p.getTextToFind() + ", c=" + p.getColumn() + ", r=" + p.getRow() + ", contains=" + p.isContains() + ", equal=" + p.isEqual());
                }
            }

            System.out.println("matched rows=" + FileData.getMatchedRows());

            FileData.findColumn("kol 4", inputFileContent);

            System.out.println("column=" + FileData.getColumnIndex());
            System.out.println("header=" + FileData.getHeaderIndex());


            System.out.println("*********************");
            System.out.println("*********************");
            System.out.println("*********************");

//            fileData.findColumnAndRow(colText, rowText, inputFileContent, delimiter);

            FileData fileData = new FileData();

            fileData.findColumnAndRowMultiParam(colText, rowTextList, inputFileContent);
            fileData.printCellValue(colText, rowTextList, inputFileContent);

            if (fileData.isColumnFound()) {
                if (fileData.isRowFound()) {
                    result = Rebuilder.rebuildOneRow(inputFileContent, newCellValue, fileData);
                } else {
                    result = Rebuilder.rebuildAllRows(inputFileContent, newCellValue, fileData);
                }
            } else {
                System.out.println("Nie znaleziono kolumny z tekstem " + colText);
                System.exit(0);
            }

            Writer.write(result, fileName);
            System.out.println("\nZapisano plik " + Writer.getOutputFilePath());

        } else {
            System.out.println("Brak plikow do procesowania w lokalizacji " + System.getProperty(DIRECTORY));
            System.exit(0);
        }
    }

    public static String getDirectory() {
        return DIRECTORY;
    }

    private static void findFiles() {
        File folder = new File(System.getProperty(DIRECTORY));
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(".csv")) {
                fileName = file.getName();
                break;
            }
        }
    }
}