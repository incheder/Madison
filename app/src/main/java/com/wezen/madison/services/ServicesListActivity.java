package com.wezen.madison.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeService;

import java.util.ArrayList;
import java.util.List;

public class ServicesListActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_ID_BUNDLE = "category_id_bundle";

    private List<HomeService> homeServicesList;
    private HomeServicesAdapter adapter;
    private String categoryID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.servicesListToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvServiceList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        homeServicesList = new ArrayList<>();
        adapter = new HomeServicesAdapter(homeServicesList, this);
        recyclerView.setAdapter(adapter);
        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(CATEGORY_ID)){
            categoryID = getIntent().getExtras().getString(CATEGORY_ID);
        } else if(savedInstanceState != null && savedInstanceState.containsKey(CATEGORY_ID_BUNDLE)){
            categoryID = savedInstanceState.getString(CATEGORY_ID_BUNDLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        homeServicesList.clear();
        getHomeServicesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_services_list, menu);
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

    private void getHomeServicesList(){
        ParseObject poCategory = ParseObject.createWithoutData("Categories",categoryID);
       // poCategory.setObjectId(categoryID);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("HomeServices");
        query.whereEqualTo("Category",poCategory);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    for (ParseObject po : list) {

                        String id = po.getObjectId();
                        String name = po.getString("name");
                        String imageUrl = po.getParseFile("image").getUrl();
                        String description = po.getString("description");
                        int stars = po.getInt("stars");
                        int comments = po.getInt("comments");

                        HomeService homeService = new HomeService();
                        homeService.setName(name);
                        homeService.setDescription(description);
                        homeService.setId(id);
                        homeService.setUrlImage(imageUrl);
                        homeService.setStars(stars);
                        homeService.setComments(comments);

                        homeServicesList.add(homeService);

                    }

                    adapter.notifyDataSetChanged();

                } else {
                    //show error message
                   // Snackbar.make(null,"error",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


}
