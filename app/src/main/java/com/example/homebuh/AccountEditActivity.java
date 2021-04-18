package com.example.homebuh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.homebuh.entity.Account;

import java.util.ArrayList;

public class AccountEditActivity extends AppCompatActivity {

    ArrayList<Account> accounts = new ArrayList<>();
    EditText etName, etBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        etName = (EditText)findViewById(R.id.etName);
        etBalance = (EditText)findViewById(R.id.etBalance);
    }
    public void onCancel(View v){
        this.finish();
    }

    public void onSave(View v) {
        Account acc = new Account("", 0);
        acc.setName(etName.getText().toString());

        acc.setBalance(Float.parseFloat(etBalance.getText().toString()));

        accounts.add(acc);

        etName.setText("");
        etBalance.setText("");
    }
}
