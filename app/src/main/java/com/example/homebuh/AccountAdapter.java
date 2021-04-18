package com.example.homebuh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountAdapter extends BaseAdapter {
    private final static String TAG = AccountAdapter.class.toString();

    Context context;
    LayoutInflater layoutInflater;
    AccountAdapter self;
    ArrayList<Account> collection = new ArrayList<>();
    private static AccountAdapter instance;
    private DatabaseHelper databaseHelper;

    public static AccountAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new AccountAdapter(context);
        }
        return instance;
    }

    public AccountAdapter(Context context) {
        super();
        this.context = context;
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        self = this;

        reloadDataFromDb();
    }

    public void reloadDataFromDb() {
        collection.clear();
        // загрузка всех категорий из таблицы БД в нашу коллекцию
        try {
            collection.addAll(databaseHelper.getAccountDao().queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, "queryForAll error");
            e.printStackTrace();
        }
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> array = new ArrayList<>();
        for (Account obj : collection) {
            array.add(obj.getName());
        }
        return array;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public Account getItemByName(String name) {
        Account account = null;
        for (int i = 0; i < collection.size(); i++) {
            if (name.equals(collection.get(i).getName())) {
                account = collection.get(i);
                break;
            }
        }
        return account;
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

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int pos, View view, ViewGroup parent) {
//        if (view == null) {
//            view = layoutInflater.inflate(R.layout., parent, false);
//        }
//
//        OperationCategory category = collection.get(pos);
        return null;
    }
}
