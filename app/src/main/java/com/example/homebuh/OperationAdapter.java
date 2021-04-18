package com.example.homebuh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homebuh.entity.Operation;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OperationAdapter extends BaseAdapter {
    private final static String TAG = "OperationAdapter";

    ArrayList<Operation> collection = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    OperationAdapter self;

    private static OperationAdapter instance;
    private DatabaseHelper databaseHelper;

    public static OperationAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new OperationAdapter(context);
        }
        return instance;
    }

    public OperationAdapter(Context context) {
        super();
        this.context = context;
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        self = this;

        reloadDataFromDb();
    }

    public void reloadDataFromDb() {
        collection.clear();
        try {
            collection.addAll(databaseHelper.getOperationDao().queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, "queryForAll error");
            e.printStackTrace();
        }
    }

    // метод отрисовки элемента списка
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.operation_list_item, parent, false);
        }

        Operation operation = collection.get(pos);

        if (operation.getType() == 0) {
            ((ImageView)view.findViewById(R.id.iv_operation_type)).setImageResource(R.drawable.income64);
        } else {
            ((ImageView)view.findViewById(R.id.iv_operation_type)).setImageResource(R.drawable.outgo64);
        }

        int catId = operation.getCategory() != null ? operation.getCategory().getId() : -1;
        String category = catId != -1 ? CategoryAdapter.getInstance(this.context).getItemNameById(catId) : "<без категории>";
        ((TextView)view.findViewById(R.id.tvOperationCategory)).setText(category);
        if (operation.getDate() != null) {
            Date date = operation.getDate();
            String dateStr = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
            ((TextView) view.findViewById(R.id.tvOperationDate)).setText(dateStr);
        }
        ((TextView)view.findViewById(R.id.tvOperationSum)).setText("" + operation.getSum());

        return view;
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public String getItem(int pos) {
        return collection.get(pos).getCategory().getName();
    }

    public Operation getItemObject(int pos) {
        return collection.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
