package com.example.homebuh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private final static String TAG = "CategoryAdapter";

    //CategoryCollection collection = CategoryCollection.getInstance();
    // коллекция записей из БД для хранения в памяти (кэширование)
    ArrayList<OperationCategory> collection = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    CategoryAdapter self;
    private static CategoryAdapter instance;
    private DatabaseHelper databaseHelper;

    public static CategoryAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new CategoryAdapter(context);
        }
        return instance;
    }

    public CategoryAdapter(Context context) {
        super();
        this.context = context;
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        self = this;

        reloadDataFromDb();

    }

    public void reloadDataFromDb() {
        collection.clear();
        // загрузка всех категорий из таблицы БД в нашу коллекцию
        try {
            collection.addAll(databaseHelper.getCategoryDao().queryForAll());
            for (OperationCategory c : collection) {
                Log.d(TAG, "name = " + c.getName());
            }
        } catch (SQLException e) {
            Log.e(TAG, "queryForAll error");
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.category_list_item, parent, false);
        }

        OperationCategory category = collection.get(pos);

        ((TextView)view.findViewById(R.id.tvCategoryName)).setText(category.getName());

//        TextView label = (TextView)view;
//
//        if (view == null) {
//            view = new TextView(context);
//            label = (TextView)view;
//        }
//        label.setText(collection.get(pos).getName());
//        label.setTextColor(android.R.color.black);

        return view;
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public String getItem(int pos) {
        return collection.get(pos).getName();
    }

    public OperationCategory getItemObject(int pos) {
        return collection.get(pos);
    }

    // возвращает список категорий в виде массива строк
    public ArrayList<String> toStringArray() {
        ArrayList<String> array = new ArrayList<>();
        for (OperationCategory cat : collection) {
            array.add(cat.getName());
        }
        return array;
    }

    public OperationCategory getItemByName(String name) {
        OperationCategory category = null;
        for (int i = 0; i < collection.size(); i++) {
            if (name.equals(collection.get(i).getName())) {
                category = collection.get(i);
                break;
            }
        }
        return category;
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

    public String getItemNameById(int id) {
        String name = "<не указана>";
        for (int i = 0; i < collection.size(); i++) {
            if (id == collection.get(i).getId()) {
                name = collection.get(i).getName();
                break;
            }
        }
        return name;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
