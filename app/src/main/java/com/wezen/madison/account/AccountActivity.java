package com.wezen.madison.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.ParseUser;
import com.wezen.madison.R;
import com.wezen.madison.utils.DialogActivity;

public class AccountActivity extends DialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar)findViewById(R.id.accountToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getUserInfo();
    }

    private void getUserInfo() {
        EditText accountName = (EditText)findViewById(R.id.accountNameEditText);
        EditText accountEmail = (EditText)findViewById(R.id.accountEmailEditText);
        EditText accountLastName = (EditText)findViewById(R.id.accountLastNameEditText);
        EditText accountPhone = (EditText)findViewById(R.id.accountPhoneEditText);

        ParseUser user = ParseUser.getCurrentUser();
        accountEmail.setText(user.getEmail());
    }

}
