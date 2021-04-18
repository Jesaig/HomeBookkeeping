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
import android.widget.ListView;

public class OperationListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "OperationListActivity";
    public final static int RC_EDIT_OPERATION = 101;

    ListView lvOperations;
    OperationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvOperations = (ListView)findViewById(R.id.lvOperation);

        adapter = OperationAdapter.getInstance(getApplicationContext());
        lvOperations.setAdapter(adapter);
        lvOperations.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(OperationListActivity.this, OperationEditActivity.class), RC_EDIT_OPERATION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult " + resultCode);

        adapter.reloadDataFromDb();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick " + position);
        Intent intent = new Intent(getApplicationContext(), OperationEditActivity.class);
        intent.putExtra("pos", position);
        startActivityForResult(intent, RC_EDIT_OPERATION);
    }
}
