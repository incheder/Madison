package com.wezen.madison.categories;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wezen.madison.R;
import com.wezen.madison.account.AccountActivity;
import com.wezen.madison.help.HelpActivity;
import com.wezen.madison.history.HistoryActivity;
import com.wezen.madison.login.LoginActivity;
import com.wezen.madison.model.BeverageMenu;
import com.wezen.madison.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private CategoriesAdapter adapter;
    private FrameLayout progressIndicator;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener( navigationItemSelectedListener );
        progressIndicator = (FrameLayout)findViewById(R.id.categoriesProgressIndicator);
        RecyclerView rvHome = (RecyclerView) findViewById(R.id.rvHome);
        rvHome.setHasFixedSize(true);
        //dummyList();

        adapter = new CategoriesAdapter(dummyList(),this,getSupportFragmentManager());
        rvHome.setAdapter(adapter);

        // Initializing Drawer Layout and ActionBarToggle
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
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


    private ArrayList<Category> dummyList() {
        final ArrayList<Category> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
        //query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> beverageMenuList, ParseException e) {
                if (e == null) {
                    Log.d("beverageMenu", "Retrieved " + beverageMenuList.size() + " BeverageMenu");
                    for(ParseObject po : beverageMenuList){
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
            } else if (id == R.id.menu_history){
                toLaunch = new Intent(CategoriesActivity.this, HistoryActivity.class);
            } else if (id == R.id.menu_settings){

            } else if (id == R.id.menu_help){
                toLaunch = new Intent(CategoriesActivity.this, HelpActivity.class);
            } else if (id == R.id.menu_sign_out){
                ParseUser.logOut();
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
}
