package com.wezen.madison.categories;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wezen.madison.R;
import com.wezen.madison.model.BeverageMenu;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        RecyclerView rvHome = (RecyclerView) findViewById(R.id.rvHome);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        //rvHome.setLayoutManager(layoutManager);
        rvHome.setHasFixedSize(true);
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

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(), getResources().getString(R.string.category_one));
        adapter.addFrag(new DummyFragment(), getResources().getString(R.string.category_two));
        adapter.addFrag(new DummyFragment(), getResources().getString(R.string.category_three));
        adapter.addFrag(new DummyFragment(), getResources().getString(R.string.category_four));
        viewPager.setAdapter(adapter);
    }*/

    private ArrayList<BeverageMenu> dummyList() {
        final ArrayList<BeverageMenu> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("BeverageMenu");
        //query.whereEqualTo("ruta", po);
        //query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> beverageMenuList, ParseException e) {
                if (e == null) {
                    Log.d("beverageMenu", "Retrieved " + beverageMenuList.size() + " BeverageMenu");
                    final int sizeArrayRetrieved = beverageMenuList.size();
                    for(ParseObject po : beverageMenuList){
                        //  list.add(new BeverageMenu());
                        final String name = po.getString("name");
                        ParseFile image = po.getParseFile("image");
                        image.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if(e == null){
                                    list.add(new BeverageMenu(bytes,name));
                                    if(list.size() == sizeArrayRetrieved){
                                        adapter.notifyDataSetChanged();
                                    }
                                }else{

                                }
                            }
                        });

                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return list;
    }
}
