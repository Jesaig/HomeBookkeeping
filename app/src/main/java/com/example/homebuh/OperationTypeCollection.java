package com.example.homebuh;

import com.example.homebuh.entity.Account;

import java.util.ArrayList;

public class OperationTypeCollection {
    private static OperationTypeCollection instance;
    private static ArrayList<String> collection;

    public static OperationTypeCollection getInstance() {
        if (instance == null) {
            instance = new OperationTypeCollection();
            collection = new ArrayList<>();
            collection.add("Доход");
            collection.add("Расход");

        }
        return instance;
    }

    public ArrayList<String> toStringArray() {
        return collection;
    }

    public void add(Account account) {

    }
}
