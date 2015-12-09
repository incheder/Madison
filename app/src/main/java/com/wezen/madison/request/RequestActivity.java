package com.wezen.madison.request;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wezen.madison.R;

public class RequestActivity extends AppCompatActivity {

    public static final String REQUEST_ID = "clientRequestId";
    public static final String REQUEST_IMAGE_URL = "imageUrl";
    private ImageView imageHeader;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRequest);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageHeader = (ImageView)findViewById(R.id.headerRequest);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_request);
        if(getIntent().getExtras()!= null){
            String imageUrl = getIntent().getStringExtra(REQUEST_IMAGE_URL);
            Picasso.with(this).load(imageUrl).into(target);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request, menu);
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

    private void toolBarColoring(Bitmap bitmap){
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.doctor_solucion);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int  toolbarColor = palette.getMutedColor(ContextCompat.getColor(RequestActivity.this, R.color.primary));
                int statusBarColor = palette.getDarkMutedColor(ContextCompat.getColor(RequestActivity.this, R.color.primaryDark));

                collapsingToolbar.setContentScrimColor(toolbarColor);
                setStatusBarColor(RequestActivity.this, statusBarColor);
            }
        });
        imageHeader.setImageBitmap(bitmap);
    }

    private void setStatusBarColor( Context context, int resource ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity)context).getWindow();
            window.setStatusBarColor( resource  );
        }
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            toolBarColoring(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    };
}
