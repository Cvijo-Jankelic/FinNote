package com.project.finnote.entity;

public class ReportItem {
    private final String name;
    private final String value;

    public ReportItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
