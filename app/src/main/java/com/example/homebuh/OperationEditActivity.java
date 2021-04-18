package com.example.homebuh;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.Measure;
import com.example.homebuh.entity.Operation;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationEditActivity extends AppCompatActivity implements DeleteConfirmDialogFragment.DialogListener {
    private final static String TAG = "OperationEditActivity";

    Spinner spMeasures, spCategory, spAccount, spType;
    private int pos;
    private Operation operation;
    private EditText etSum, etCount, etPrice, etNote;
    private DatePicker dpDate;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_edit);
        databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        spMeasures = (Spinner)findViewById(R.id.sp_oper_measure);
        spCategory = (Spinner)findViewById(R.id.sp_operation_category);
        spAccount = (Spinner)findViewById(R.id.sp_oper_account);
        spType = (Spinner)findViewById(R.id.sp_operation_type);

        etSum = (EditText)findViewById(R.id.et_oper_sum);
        etCount = (EditText)findViewById(R.id.et_oper_count);
        etPrice = (EditText)findViewById(R.id.et_oper_price);
        etNote = (EditText)findViewById(R.id.et_oper_note);
        dpDate = (DatePicker)findViewById(R.id.dp_oper_date);

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    etSum.setText("" + (Float.parseFloat(etPrice.getText().toString()) * Float.parseFloat(etCount.getText().toString())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ArrayAdapter<String> measuresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MeasureAdapter.getInstance(this).toStringArray());
        spMeasures.setAdapter(measuresAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategoryAdapter.getInstance(this).toStringArray());
        spCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, AccountAdapter.getInstance(this).toStringArray());
        spAccount.setAdapter(accountAdapter);

        String[] typesArray = new String [] {Operation.TYPE_INCOME, Operation.TYPE_OUTGO};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typesArray);
        spType.setAdapter(typeAdapter);

        pos = getIntent().getIntExtra("pos", -1);
        if (pos != -1) {
            operation = OperationAdapter.getInstance(this).getItemObject(pos);
            etSum.setText("" + operation.getSum());
            etCount.setText("" + operation.getCount());
            etPrice.setText("" + operation.getPrice());
            etNote.setText("" + operation.getNote());
            spAccount.setSelection(AccountAdapter.getInstance(this).getItemPositionById(operation.getAccount().getId()));
            spMeasures.setSelection( MeasureAdapter.getInstance(this).getItemPositionById(operation.getMeasure().getId()) );
            spCategory.setSelection( CategoryAdapter.getInstance(this).getItemPositionById(operation.getCategory().getId()) );
            spType.setSelection(operation.getType());
            if (operation.getDate() != null) {
                Log.d(TAG, String.format("load date %d-%d-%d", operation.getDate().getYear(), operation.getDate().getMonth(), operation.getDate().getDay()+7));
                dpDate.updateDate(operation.getDate().getYear(), operation.getDate().getMonth(), operation.getDate().getDay()+7);
            }

        } else {
            operation = new Operation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.operation_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_operation_save:
                operation.setSum(Float.parseFloat(etSum.getText().toString()));
                operation.setCount(Float.parseFloat(etCount.getText().toString()));
                operation.setPrice(Float.parseFloat(etPrice.getText().toString()));
                operation.setNote(etNote.getText().toString());
                operation.setAccount(AccountAdapter.getInstance(this).getItemByName(spAccount.getSelectedItem().toString()));
                operation.setMeasure(MeasureAdapter.getInstance(this).getItemByName(spMeasures.getSelectedItem().toString()));
                operation.setCategory(CategoryAdapter.getInstance(this).getItemByName(spCategory.getSelectedItem().toString()));
                operation.setType(spType.getSelectedItemPosition());
                Date date = new Date(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth());
                Log.d(TAG, String.format("save date %d-%d-%d", dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth()));
                operation.setDate(date);

                try {
                    if (pos != -1) {
                        databaseHelper.getOperationDao().update(operation);
                    } else {
                        databaseHelper.getOperationDao().create(operation);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.action_operation_delete:
                new DeleteConfirmDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
                break;
            case R.id.action_operation_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        try {
            databaseHelper.getOperationDao().delete(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setResult(RESULT_OK);
        finish();
    }

}
