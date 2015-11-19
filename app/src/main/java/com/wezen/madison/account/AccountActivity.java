package com.wezen.madison.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.utils.DialogActivity;

public class AccountActivity extends DialogActivity {
    public static final String USERNAME = "username";
    public static final String IMAGE_URL = "url";
    public static final String EMAIL = "email";

    private String userName;
    private String imageUrl;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar)findViewById(R.id.accountToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if(getIntent().getExtras()!= null){
            userName = getIntent().getStringExtra(USERNAME);
            userEmail = getIntent().getStringExtra(EMAIL);
            imageUrl = getIntent().getStringExtra(IMAGE_URL);
        }

        getUserInfo();
    }

    private void getUserInfo() {
        EditText accountName = (EditText)findViewById(R.id.accountNameEditText);
        EditText accountEmail = (EditText)findViewById(R.id.accountEmailEditText);
        EditText accountLastName = (EditText)findViewById(R.id.accountLastNameEditText);
        EditText accountPhone = (EditText)findViewById(R.id.accountPhoneEditText);
        ImageView accountImage = (ImageView)findViewById(R.id.account_image);

        accountName.setText(userName);
        accountEmail.setText(userEmail);
        if(imageUrl!= null){
            Picasso.with(this).load(imageUrl).into(accountImage);
        }

    }

}
