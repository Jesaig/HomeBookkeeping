package com.example.homebuh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "CategoryListActivity";
    public final static int RC_EDIT_CATEGORY = 101;

    ListView lvCategories;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        lvCategories = (ListView)findViewById(R.id.lvCategories);

//        ArrayAdapter<String> adapter  =  new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, categories);
        adapter = CategoryAdapter.getInstance(getApplicationContext()); //new CategoryAdapter(getApplicationContext());
        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(this);
//        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryEditActivity.class);
                startActivityForResult(intent, RC_EDIT_CATEGORY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult " + requestCode);

        adapter.reloadDataFromDb();
        adapter.notifyDataSetChanged();

//        if (requestCode == RESULT_OK) {
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick " + position);
        Intent intent = new Intent(getApplicationContext(), CategoryEditActivity.class);
        intent.putExtra("pos", position);
        //intent.putExtra("adapter", (Object)adapter);
        startActivityForResult(intent, RC_EDIT_CATEGORY);
    }
}
