package com.example.homebuh.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Measure")
public class Measure {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String shortName;
    @DatabaseField
    private String longName;

    public Measure() {
    }

    public Measure(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
