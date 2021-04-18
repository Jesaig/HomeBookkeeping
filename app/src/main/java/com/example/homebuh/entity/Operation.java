package com.example.homebuh.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;



// операция - доход или расход
@DatabaseTable(tableName = "Operation")
public class Operation {
    public static final String TYPE_INCOME = "Доход";
    public static final String TYPE_OUTGO = "Расход";

    //public enum OperationType {Доход, Расход};

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int type;
    @DatabaseField
    private Date date;
    @DatabaseField(foreign = true)
    private OperationCategory category;
    @DatabaseField(foreign = true)
    private OperationCategory subCategory;
    @DatabaseField(foreign = true)
    private Account account;
    @DatabaseField
    private float count;
    @DatabaseField(foreign = true)
    private Measure measure;
    @DatabaseField
    private float price;
    @DatabaseField
    private float sum;
    @DatabaseField
    private String note;

    public Operation() {}

    public Operation(int type, Date date, OperationCategory category, float sum) {
        this.type = type;
        this.date = date;
        this.category = category;
        this.sum = sum;
    }

    public int getType() {
        return type;
    }

    public String getTypeTitle() {
        String res = "";
        switch (type) {
            case 1:
                res = TYPE_INCOME;
                break;
            case 2:
                res = TYPE_OUTGO;
                break;
        }
        return res;
    }

    public void setType(int type) {
        this.type = type;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OperationCategory getCategory() {
        return category;
    }

    public void setCategory(OperationCategory category) {
        this.category = category;
    }

    public OperationCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(OperationCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
