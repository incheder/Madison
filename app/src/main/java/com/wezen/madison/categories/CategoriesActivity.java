package com.wezen.madison.categories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import com.parse.SaveCallback;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.account.AccountActivity;
import com.wezen.madison.help.HelpActivity;
import com.wezen.madison.history.HistoryActivity;
import com.wezen.madison.login.LoginActivity;
import com.wezen.madison.model.Category;
import com.wezen.madison.password.PasswordActivity;
import com.wezen.madison.utils.DialogActivity;
import com.wezen.madison.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends DialogActivity {

    private static final int INSTALLATION_DATA_SAVED = 1;
    private static final int INSTALLATION_DATA_NOT_SAVED = 0;
    private static final int USER_ADDED_TO_ROLE = 1;
    private static final int USER_NOT_ADDED_TO_ROLE = 0;
    private CategoriesAdapter adapter;
    private FrameLayout progressIndicator;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userName;
    private String userEmail;
    private String imageUrl;
    private SharedPreferences sharedPref;
    private String userLastName;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener( navigationItemSelectedListener );
        progressIndicator = (FrameLayout)findViewById(R.id.categoriesProgressIndicator);
        RecyclerView rvHome = (RecyclerView) findViewById(R.id.rvHome);
        rvHome.setHasFixedSize(true);

        adapter = new CategoriesAdapter(dummyList(),this,getSupportFragmentManager());
        rvHome.setAdapter(adapter);

        // Initializing Drawer Layout and ActionBarToggle
        //DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        saveSharedPrefData();

        fillNavigationViewHeader();


    }


    private ArrayList<Category> dummyList() {
        final ArrayList<Category> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
        //query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> beverageMenuList, ParseException e) {
                if (e == null) {
                    Log.d("beverageMenu", "Retrieved " + beverageMenuList.size() + " BeverageMenu");
                    for (ParseObject po : beverageMenuList) {
                        //  list.add(new BeverageMenu());
                        String name = po.getString("name");
                        String image = po.getParseFile("image").getUrl();
                        String mainColor = po.getString("mainColor");
                        String secondaryColor = po.getString("secondaryColor");
                        String id = po.getObjectId();

                        Category category = new Category();
                        category.setImage(image);
                        category.setName(name);
                        category.setMainColor(mainColor);
                        category.setSecondaryColor(secondaryColor);
                        category.setId(id);
                        list.add(category);
                    }
                    adapter.notifyDataSetChanged();
                    progressIndicator.setVisibility(View.GONE);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return list;
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            Intent toLaunch = null;

            int id = menuItem.getItemId();

            if(id == R.id.menu_account){
                toLaunch = new Intent(CategoriesActivity.this, AccountActivity.class);
                toLaunch.putExtra(AccountActivity.USERNAME,userName);
                toLaunch.putExtra(AccountActivity.EMAIL,userEmail);
                toLaunch.putExtra(AccountActivity.IMAGE_URL,imageUrl);
                toLaunch.putExtra(AccountActivity.LASTNAME,userLastName);
                toLaunch.putExtra(AccountActivity.PHONE,phone);
            } else if (id == R.id.menu_history){
                toLaunch = new Intent(CategoriesActivity.this, HistoryActivity.class);
            } else if (id == R.id.menu_password){
                toLaunch = new Intent(CategoriesActivity.this, PasswordActivity.class);
            } /*else if (id == R.id.menu_help){
                toLaunch = new Intent(CategoriesActivity.this, HelpActivity.class);
            }*/ else if (id == R.id.menu_sign_out){
                ParseUser.logOut();
                updateSharedPref(R.string.installation_already_saved, INSTALLATION_DATA_NOT_SAVED);
                ParseInstallation.getCurrentInstallation().remove("channels");
                ParseInstallation.getCurrentInstallation().saveInBackground();
                goToLogin();
            }
            if(toLaunch != null){
                menuItem.setChecked(true);
                startActivity(toLaunch);
                drawerLayout.closeDrawers();
            }
            return true;
        }
    };

    private void goToLogin(){
        Intent loginIntent = new Intent(CategoriesActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }


    private void saveSharedPrefData(){
        //sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref = getSharedPreferences(getString(R.string.my_pref),Context.MODE_PRIVATE);
        int isSaved = sharedPref.getInt(getString(R.string.installation_already_saved), INSTALLATION_DATA_NOT_SAVED);
        if(isSaved == INSTALLATION_DATA_NOT_SAVED){
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("user", ParseUser.getCurrentUser());
            installation.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Log.d("SUCCESS", "installation saved");
                        updateSharedPref(R.string.installation_already_saved,INSTALLATION_DATA_SAVED);
                    } else {
                        Log.e("ERROR", "installation not saved: " + e.getMessage());
                        updateSharedPref(R.string.installation_already_saved, INSTALLATION_DATA_NOT_SAVED);
                    }
                }
            });
        }

        /*isSaved = sharedPref.getInt(getString(R.string.role_already_added),USER_NOT_ADDED_TO_ROLE);
        if(isSaved == USER_NOT_ADDED_TO_ROLE){
            ParseQuery<ParseRole> query = ParseRole.getQuery();
            query.whereEqualTo("name","Client");
            query.getFirstInBackground(new GetCallback<ParseRole>() {
                @Override
                public void done(ParseRole parseRole, ParseException e) {
                    if (e == null) {
                        addUserToRole(parseRole);
                    } else {
                        //ups
                        Log.e("ERROR", "Ocurrio un prooblema al obtener el rol "+ e.getMessage());
                    }
                }
            });
        }*/


    }


    private void fillNavigationViewHeader(){
        final ImageView imageAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        final TextView textViewUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        final TextView textViewEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        //ParseUser user = ParseUser.getCurrentUser();
        ParseUser.getCurrentUser().fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){
                    ParseUser user = (ParseUser) parseObject;
                    userName = user.getUsername();
                    userEmail = user.getEmail();
                    userLastName = user.getString("lastName");
                    phone = user.getString("phone");
                    textViewUsername.setText(userName);
                    textViewEmail.setText(userEmail);
                    if (user.getParseFile("userImage") != null) {
                        imageUrl = user.getParseFile("userImage").getUrl();
                        Picasso.with(CategoriesActivity.this).load(imageUrl).into(imageAvatar);

                    }
                }
            }
        });


    }

    private void updateSharedPref(int key, int value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(key), value);
        editor.apply();
    }

    private void addUserToRole(ParseRole role){
        role.getUsers().add(ParseUser.getCurrentUser());
        role.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.d("SUCCESS", "user added to role");
                    updateSharedPref(R.string.role_already_added,USER_ADDED_TO_ROLE);
                } else {
                    //ups
                    Log.e("ERROR", "user not added to role" + e.getMessage());
                    updateSharedPref(R.string.role_already_added, USER_NOT_ADDED_TO_ROLE);
                }
            }
        });
    }

}
