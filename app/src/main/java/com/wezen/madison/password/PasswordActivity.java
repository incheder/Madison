package com.wezen.madison.password;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.wezen.madison.R;

public class PasswordActivity extends AppCompatActivity {

    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private EditText editTextRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editTextOldPassword = (EditText)findViewById(R.id.oldPassword);
        editTextNewPassword = (EditText)findViewById(R.id.newPassword);
        editTextRepeatPassword = (EditText)findViewById(R.id.repeatPassword);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabPassword);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateLocalFields()){
                    validateInBackground();
                }
            }
        });
    }


    private boolean validateLocalFields(){
        if(TextUtils.isEmpty(editTextOldPassword.getText().toString())){
            editTextOldPassword.requestFocus();
            editTextOldPassword.setError(getResources().getString(R.string.error_field_required));
            return false;
        }
        if(TextUtils.isEmpty(editTextNewPassword.getText().toString())){
            editTextNewPassword.requestFocus();
            editTextNewPassword.setError(getResources().getString(R.string.error_field_required));
            return false;
        }
        if(TextUtils.isEmpty(editTextRepeatPassword.getText().toString())){
            editTextRepeatPassword.requestFocus();
            editTextRepeatPassword.setError(getResources().getString(R.string.error_field_required));
            return false;
        }
        if(!editTextNewPassword.getText().toString().equals(editTextRepeatPassword.getText().toString())){
            editTextRepeatPassword.requestFocus();
            editTextRepeatPassword.setError(getResources().getString(R.string.passwords_must_been_the_same));
            return false;
        }
        if(editTextNewPassword.getText().toString().equals(editTextOldPassword.getText().toString())){
            editTextNewPassword.requestFocus();
            editTextNewPassword.setError(getResources().getString(R.string.you_can_not_use_the_same_password));
        }



        return true;
    }


    private void validateInBackground(){

        boolean isValid = false;
        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), editTextOldPassword.getText().toString(), (parseUser, e) -> {
            if(e== null){
                //old password is correct
                parseUser.setPassword(editTextNewPassword.getText().toString());
                parseUser.saveInBackground(e1 -> {
                    if(e1 == null){
                        ParseUser.getCurrentUser().fetchInBackground();
                    } else {//ups

                    }

                });


            } else{//ups

            }
        });

        //return  isValid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
