package com.example.homebuh;

import android.util.Log;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.OperationCategory;

import java.util.ArrayList;

public class AccountCollection {
    private static AccountCollection instance;
    private static ArrayList<Account> collection;

    public static AccountCollection getInstance() {
        if (instance == null) {
            instance = new AccountCollection();
            collection = new ArrayList<>();
            collection.add(new Account("Зарплатная карта", 0));
            collection.add(new Account("Кошелёк", 0));

        }
        return instance;
    }

    // возвращает коллекцию в виде массива строк
    public ArrayList<String> toStringArray() {
        //String[] array = new String[collection.size()];
        ArrayList<String> array = new ArrayList<>();
        for (Account obj : collection) {
            array.add(obj.getName());
        }
        return array;
    }

    private AccountCollection() {
    }

    public void add(Account account) {

    }
}
