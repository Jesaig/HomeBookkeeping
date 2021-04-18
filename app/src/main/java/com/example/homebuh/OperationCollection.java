package com.example.homebuh;

import com.example.homebuh.entity.Operation;

import java.util.ArrayList;

public class OperationCollection {
    private ArrayList<Operation> collection = new ArrayList<>();
    private static OperationCollection instance;

    public static OperationCollection getInstance() {
        if (instance == null) {
            instance = new OperationCollection();
        }
        return instance;
    }

    public int size() {
        return collection.size();
    }

    public void add(Operation c) {
        collection.add(c);
    }

    public Operation get(int position) {
        return collection.get(position);
    }

    public void set(int pos, Operation c) {
        collection.set(pos, c);
    }

    public void remove(int pos) {
        collection.remove(pos);
    }

}
