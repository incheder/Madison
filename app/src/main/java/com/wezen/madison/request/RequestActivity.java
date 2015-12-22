package com.wezen.madison.request;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeServiceRequestStatus;
import com.wezen.madison.utils.Utils;

public class RequestActivity extends AppCompatActivity {

    //public static final String REQUEST_ID = "clientRequestId";
    public static final String REQUEST_IMAGE_URL = "imageUrl";
    public static final String REQUEST_STATUS = "status";
    public static final String REQUEST_HOME_SERVICE_NAME = "homeServiceName";
    public static final String REQUEST_PROBLEM_DESCRIPTION = "problemDescription";
    public static final String REQUEST_ATTENDED_BY = "attendedBy";
    public static final String REQUEST_ATTENDED_BY_AVATAR = "attendedByAvatar";

    private ImageView imageHeader;
    private CollapsingToolbarLayout collapsingToolbar;
    private String problemDesc;

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
        LinearLayout layoutStatus = (LinearLayout)findViewById(R.id.request_layout_status);
        RelativeLayout attendedLayout = (RelativeLayout)findViewById(R.id.request_attended_by_layout);
        TextView yourServiceWillBe = (TextView)findViewById(R.id.request_your_service_will_be);
        TextView statusLabel = (TextView)findViewById(R.id.request_status_label);
        TextView requestProblemDescription = (TextView)findViewById(R.id.request_problem_description);
        TextView attendedBy = (TextView)findViewById(R.id.request_service_provider_name);
        ImageView attendedByImageView = (ImageView)findViewById(R.id.request_service_provider_avatar);

        if(getIntent().getExtras()!= null){
            String imageUrl = getIntent().getStringExtra(REQUEST_IMAGE_URL);
            Picasso.with(this).load(imageUrl).into(target);
            int status = getIntent().getIntExtra(REQUEST_STATUS,-1);
            statusLabel.setText(status == -1 ? "" : HomeServiceRequestStatus.valueOf(status).toString());
            layoutStatus.setBackgroundColor(Utils.getColorByStatus(this,HomeServiceRequestStatus.valueOf(status)));
            if(status!= -1 && status == HomeServiceRequestStatus.CONFIRMADO.getValue()){
                attendedLayout.setVisibility(View.VISIBLE);
                yourServiceWillBe.setVisibility(View.VISIBLE);
            } else {
                attendedLayout.setVisibility(View.GONE);
                yourServiceWillBe.setVisibility(View.INVISIBLE);

            }
            String title = getIntent().getStringExtra(REQUEST_HOME_SERVICE_NAME);
            collapsingToolbar.setTitle(title);
            problemDesc = getIntent().getStringExtra(REQUEST_PROBLEM_DESCRIPTION);
            requestProblemDescription.setText(problemDesc);
            attendedBy.setText(getIntent().getStringExtra(REQUEST_ATTENDED_BY));
            String attendedByAvatarUrl = getIntent().getStringExtra(REQUEST_ATTENDED_BY_AVATAR);
            if(attendedByAvatarUrl!= null){
                Picasso.with(this).load(attendedByAvatarUrl).into(attendedByImageView);
            }

        }
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
