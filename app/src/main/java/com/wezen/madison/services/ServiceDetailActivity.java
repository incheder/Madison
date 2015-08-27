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

import com.wezen.madison.R;
import com.wezen.madison.map.MapActivity;
import com.wezen.madison.model.BeverageType;
import com.wezen.madison.utils.Utils;

public class ServiceDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        context = this;
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        fab = (FloatingActionButton)findViewById(R.id.serviceDetailFAB);
        collapsingToolbar.setTitle("Suleiman Ali Shakir");
        ImageView header = (ImageView) findViewById(R.id.header);
        toolBarColoring();


        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new GridAdapter(Utils.fillDataSet(this, BeverageType.valueOf(1)),this,getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ServiceDetailActivity.this, MapActivity.class);
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
                R.drawable.beach);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
             int  toolbarColor = palette.getMutedColor(getResources().getColor(R.color.primary));
             int statusBarColor = palette.getDarkMutedColor(getResources().getColor(R.color.primaryDark));
             int fabColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

               collapsingToolbar.setContentScrimColor(toolbarColor);
               setStatusBarColor(context, statusBarColor);
               fab.setBackgroundTintList(createFabColors(fabColor));
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
}
