package com.example.homebuh;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.homebuh.R;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryEditActivity extends AppCompatActivity implements DeleteConfirmDialogFragment.DialogListener {

    private EditText etName;
    private int pos;
    private OperationCategory category;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);
        databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        etName = (EditText) findViewById(R.id.etCategoryName);
        pos = getIntent().getIntExtra("pos", -1);
        // если pos = -1, то создаётся новая запись
        if (pos != -1) {
            //category = CategoryCollection.getInstance().get(pos);
            category = CategoryAdapter.getInstance(this).getItemObject(pos);
            etName.setText(category.getName());
        } else {
            category = new OperationCategory("");
        }
    }

    public void onSave(View v) {
        category.setName(etName.getText().toString());
        try {
            if (pos != -1) {
                databaseHelper.getCategoryDao().update(category);
            } else {
                databaseHelper.getCategoryDao().create(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setResult(RESULT_OK);
        finish();
    }

    public void onCancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onDelete(View v) {
        // открываем диалоговое окно
        new DeleteConfirmDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
    }

    // метод, вызываемый при нажатии на кнопку Да в диалоге подтверждения удаления
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        try {
            databaseHelper.getCategoryDao().delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //CategoryCollection.getInstance().remove(pos);
        setResult(RESULT_OK);
        finish();
    }
}
