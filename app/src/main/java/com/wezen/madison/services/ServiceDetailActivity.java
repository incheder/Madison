package com.wezen.madison.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.categories.ViewPagerAdapter;
import com.wezen.madison.map.MapActivity;
import com.wezen.madison.model.BeverageType;
import com.wezen.madison.utils.Utils;

public class ServiceDetailActivity extends AppCompatActivity {
    public static final String PARAM_ID = "ID";
    public static final String PARAM_COMMENTS = "COMMENTS";
    public static final String PARAM_DESCRIPTION = "DESCRIPTION";
    public static final String PARAM_NAME = "NAME";
    public static final String PARAM_STARS = "STARS";
    public static final String PARAM_URL_IMAGE = "URL_IMAGE";

    private CollapsingToolbarLayout collapsingToolbar;
    private Context context;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        fab = (FloatingActionButton)findViewById(R.id.serviceDetailFAB);
        ImageView header = (ImageView) findViewById(R.id.header);
        toolBarColoring();

        id = getIntent().getExtras().getString(PARAM_ID);
       // int comments = getIntent().getExtras().getInt(PARAM_COMMENTS);
        String description = getIntent().getExtras().getString(PARAM_DESCRIPTION);
        name = getIntent().getExtras().getString(PARAM_NAME);
       // int stars = getIntent().getExtras().getInt(PARAM_STARS);
        String urlImage = getIntent().getExtras().getString(PARAM_URL_IMAGE);

        Picasso.with(context).load(urlImage).into(header);
        collapsingToolbar.setTitle(name);

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        GridAdapter adapter = new GridAdapter(Utils.fillDataSet(this, BeverageType.valueOf(1)), this, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);*/

        ViewPager viewPager = (ViewPager) findViewById(R.id.service_viewpager);
        setupViewPager(viewPager,description,id);
        tabLayout = (TabLayout) findViewById(R.id.service_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ServiceDetailActivity.this, MapActivity.class);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_ID,id);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_NAME,name);
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_detail, menu);
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

    private void toolBarColoring(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.doctor_solucion);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
             int  toolbarColor = palette.getMutedColor(getResources().getColor(R.color.primary));
             int statusBarColor = palette.getDarkMutedColor(getResources().getColor(R.color.primaryDark));
             int fabColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

               collapsingToolbar.setContentScrimColor(toolbarColor);
               setStatusBarColor(context, statusBarColor);
               fab.setBackgroundTintList(createFabColors(fabColor));
                tabLayout.setBackgroundColor(toolbarColor);
                tabLayout.setSelectedTabIndicatorColor(fabColor);
            }
        });
    }


    private void setStatusBarColor( Context context, int resource ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity)context).getWindow();
            window.setStatusBarColor( resource  );
        }
    }

    private ColorStateList createFabColors(int color){
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
               // new int[] {-android.R.attr.state_enabled}, // disabled
               // new int[] {-android.R.attr.state_checked}, // unchecked
               // new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                color
                //Color.BLACK,
               // Color.GREEN,
               // Color.BLUE
        };

        ColorStateList myList = new ColorStateList(states, colors);
        return myList;
    }

    private void setupViewPager(ViewPager viewPager, String information, String homeServiceId) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(InformationFragment.newInstance(information,""), getResources().getString(R.string.service_info_tab));
        adapter.addFrag(ReviewsFragment.newInstance(homeServiceId,""), getResources().getString(R.string.service_rating_tab));
        viewPager.setAdapter(adapter);
    }
}
