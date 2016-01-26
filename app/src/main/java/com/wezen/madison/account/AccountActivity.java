package com.wezen.madison.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.utils.DialogActivity;
import com.wezen.madison.utils.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends DialogActivity {
    public static final String USERNAME = "username";
    public static final String IMAGE_URL = "url";
    public static final String EMAIL = "email";

    private String userName;
    private String imageUrl;
    private String userEmail;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CircleImageView avatar;
    private Bitmap businessBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar)findViewById(R.id.accountToolbar);
        setSupportActionBar(toolbar);
        avatar = (CircleImageView)findViewById(R.id.account_image);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(AccountActivity.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);
            }
        });

        Button save = (Button)findViewById(R.id.accountSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();

            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_IMAGE_REQUEST:
                businessBitmap = ImagePicker.getImageFromResult(AccountActivity.this, resultCode, data);
                // TODO use bitmap
                avatar.setImageBitmap(businessBitmap);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
