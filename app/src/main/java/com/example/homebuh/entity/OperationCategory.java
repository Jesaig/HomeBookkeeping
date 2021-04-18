package com.example.homebuh.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OperationCategory")
public class OperationCategory {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    // наименование
    private String name;

    // код родительской категории
    //private OperationCategory parent;

    public OperationCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // конструктор - устанавливает значения полей объекта
    public OperationCategory(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public OperationCategory getParent() {
//        return parent;
//    }
//
//    public void setParent(OperationCategory parent) {
//        this.parent = parent;
//    }
}
