package com.wezen.madison.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wezen.madison.R;
import com.wezen.madison.categories.ViewPagerAdapter;
import com.wezen.madison.map.MapActivity;
import com.wezen.madison.model.BeverageType;
import com.wezen.madison.utils.Application;
import com.wezen.madison.utils.DialogActivity;
import com.wezen.madison.utils.ToolbarColorSinleton;
import com.wezen.madison.utils.Utils;

import rx.functions.Action1;

public class ServiceDetailActivity extends DialogActivity {
    public static final String PARAM_ID = "ID";
    public static final String PARAM_COMMENTS = "COMMENTS";
    public static final String PARAM_DESCRIPTION = "DESCRIPTION";
    public static final String PARAM_NAME = "NAME";
    public static final String PARAM_STARS = "STARS";
    public static final String PARAM_URL_IMAGE = "URL_IMAGE";
    public static final String PARAM_SERVICE_PROVIDER = "SERVICE_PROVIDER";

    private CollapsingToolbarLayout collapsingToolbar;
    private Context context;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private String id;
    private String name;
    private String description;
    private ImageView header;
    private String serviceProvider;

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
        header = (ImageView) findViewById(R.id.header);
        //toolBarColoring();

        id = getIntent().getExtras().getString(PARAM_ID);
       // int comments = getIntent().getExtras().getInt(PARAM_COMMENTS);
        description = getIntent().getExtras().getString(PARAM_DESCRIPTION);
        name = getIntent().getExtras().getString(PARAM_NAME);
       // int stars = getIntent().getExtras().getInt(PARAM_STARS);
        String urlImage = getIntent().getExtras().getString(PARAM_URL_IMAGE);
        serviceProvider = getIntent().getExtras().getString(PARAM_SERVICE_PROVIDER);

        Picasso.with(context).load(urlImage).into(target);
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

        RxView.clicks(fab).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent mapIntent = new Intent(ServiceDetailActivity.this, MapActivity.class);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_ID,id);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_NAME,name);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_DESCRIPTION,description);
                mapIntent.putExtra(MapActivity.HOME_SERVICE_PROVIDER,serviceProvider);
                startActivity(mapIntent);
            }
        });

    }


    private void toolBarColoring(Bitmap bitmap){
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.doctor_solucion);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int toolbarColor = palette.getMutedColor(ContextCompat.getColor(ServiceDetailActivity.this, R.color.primary));
                int statusBarColor = palette.getDarkMutedColor(ContextCompat.getColor(ServiceDetailActivity.this, R.color.primaryDark));
                int fabColor = palette.getVibrantColor(ContextCompat.getColor(ServiceDetailActivity.this, R.color.accent));

                ServiceDetailActivity.this.setMyStatusBarcolor(statusBarColor);
                ServiceDetailActivity.this.setMyToolbarColor(toolbarColor);
                ServiceDetailActivity.this.setMyFabColor(fabColor);

                collapsingToolbar.setContentScrimColor(toolbarColor);
                setStatusBarColor(context, statusBarColor);
                fab.setBackgroundTintList(ColorStateList.valueOf(fabColor));
                //fab.setRippleColor(statusBarColor);
                tabLayout.setBackgroundColor(toolbarColor);
                tabLayout.setSelectedTabIndicatorColor(fabColor);
            }
        });
        header.setImageBitmap(bitmap);
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

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // do something with the Bitmap
           toolBarColoring(bitmap);
          // header.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    };

}
