package org.example;

import java.util.ArrayList;
import java.util.List;

public class Params {

    enum Precision {EQUAL, CONTAINS}
    private boolean isEqual;
    private boolean isContains;
    private boolean isFound;
    private List<Integer> column = new ArrayList<>();
    private List<Integer> row = new ArrayList<>();
    private String textToFind;


    public Params(String textToFind, Precision precision) {
        this.textToFind = textToFind;

        switch (precision) {
            case EQUAL:
                isEqual = true;
                break;
            case CONTAINS:
                isContains = true;
                break;
        }
        list.add(this);
    }

    private static List<Params> list = new ArrayList<>();

    public boolean isEqual() {
        return isEqual;
    }

    public boolean isContains() {
        return isContains;
    }

    public boolean isFound() {
        return isFound;
    }

    public int getColumn() {
        return column.get(0);
    }

    public List<Integer> getRow() {
        return row;
    }

    public String getTextToFind() {
        return textToFind;
    }

    public static List<Params> getList() {
        return list;
    }

    public void setColumn(int column) {
        this.column.add(column);
    }

    public void setRow(int row) {
        this.row.add(row);
    }

    public void setFound(boolean bool) {
        this.isFound = bool;
    }

    public static void print() {
        for (Params p : Params.getList()) {
            System.out.println(p.getTextToFind());
            System.out.println("equal=" + p.isEqual());
            System.out.println("contain=" + p.isContains() + "\n***");
        }
    }
}
