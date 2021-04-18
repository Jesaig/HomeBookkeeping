package com.example.homebuh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.Measure;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class MeasureAdapter {
    private final static String TAG = MeasureAdapter.class.toString();

    Context context;
    ArrayList<Measure> collection = new ArrayList<>();
    private static MeasureAdapter instance;
    private DatabaseHelper databaseHelper;

    public static MeasureAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new MeasureAdapter(context);
        }
        return instance;
    }

    public MeasureAdapter(Context context) {
        super();
        this.context = context;
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        reloadDataFromDb();
    }

    public void reloadDataFromDb() {
        collection.clear();
        try {
            collection.addAll(databaseHelper.getMeasureDao().queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, "queryForAll error");
            e.printStackTrace();
        }
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> array = new ArrayList<>();
        for (Measure obj : collection) {
            array.add(obj.getLongName());
        }
        return array;
    }

    public Measure getItemByName(String name) {
        Measure measure = null;
        for (int i = 0; i < collection.size(); i++) {
            if (name.equals(collection.get(i).getLongName())) {
                measure = collection.get(i);
                break;
            }
        }
        return measure;
    }

    public int getItemPositionById(int id) {
        int pos = -1;
        for (int i = 0; i < collection.size(); i++) {
            if (id == collection.get(i).getId()) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
