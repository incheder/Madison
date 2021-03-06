package com.wezen.madison.request;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wezen.madison.R;
import com.wezen.madison.history.ReviewDialogFragment;
import com.wezen.madison.model.HomeServiceRequestStatus;
import com.wezen.madison.utils.DialogActivity;
import com.wezen.madison.utils.Utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class RequestActivity extends DialogActivity implements /*ReviewDialogFragment.OnClickReviewDialog,*/CancelRequestDialogFragment.OnClickCancelDialog {

    //public static final String REQUEST_ID = "clientRequestId";
    public static final String REQUEST_IMAGE_URL = "imageUrl";
    public static final String REQUEST_STATUS = "status";
    public static final String REQUEST_HOME_SERVICE_NAME = "homeServiceName";
    public static final String REQUEST_PROBLEM_DESCRIPTION = "problemDescription";
    public static final String REQUEST_ATTENDED_BY = "attendedBy";
    public static final String REQUEST_ATTENDED_BY_AVATAR = "attendedByAvatar";
    public static final String REQUEST_DATE_FOR_SERVICE = "dateForService";
    public static final String REQUEST_SHOW_RATING_BUTTON = "showRatingButton";
    public static final String REQUEST_ID = "homeServiceRequestId";
    public static final String REQUEST_NUM_STARS = "homeServiceRequestNumStars";
    public static final String REQUEST_SHOW_CANCEL_BUTTON = "showCancelButton";
    public static final String REQUEST_IS_COMPLETE = "homeServiceIsComplete";
    public static final String REQUEST_AVERAGE_STARS = "averageStars";

    private ImageView imageHeader;
    private CollapsingToolbarLayout collapsingToolbar;
    private String problemDesc;
    private ReviewDialogFragment dialog;
    private String requestId;
    //private Button buttonRating;
    private RatingBar ratingBar;

    @Bind(R.id.buttonCancelRequest)
    Button buttonCancelRequest;
    @Bind(R.id.request_rating_layout)
    LinearLayout averageRatingLayout;
    @Bind( R.id.request_my_rating)
    TextView textViewMyRating;
    @Bind(R.id.requestCoordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.request_status_label)
    TextView statusLabel;
    @Bind(R.id.request_layout_status)
    LinearLayout layoutStatus;
    @Bind(R.id.request_attended_by_layout)
    RelativeLayout attendedLayout;
    @Bind(R.id.request_your_service_will_be)
    TextView yourServiceWillBe;
    @Bind(R.id.request_service_provider_rating)
    TextView averageRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRequest);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageHeader = (ImageView)findViewById(R.id.headerRequest);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_request);
        //LinearLayout layoutStatus = (LinearLayout)findViewById(R.id.request_layout_status);
        //RelativeLayout attendedLayout = (RelativeLayout)findViewById(R.id.request_attended_by_layout);
        //TextView yourServiceWillBe = (TextView)findViewById(R.id.request_your_service_will_be);
        //TextView statusLabel = (TextView)findViewById(R.id.request_status_label);
        final TextView requestProblemDescription = (TextView)findViewById(R.id.request_problem_description);
        final TextView attendedBy = (TextView)findViewById(R.id.request_service_provider_name);
        final ImageView attendedByImageView = (ImageView)findViewById(R.id.request_service_provider_avatar);
        final TextView requestDate = (TextView)findViewById(R.id.request_date);
        //buttonRating = (Button)findViewById(R.id.buttonRatingRequest);
        ratingBar = (RatingBar)findViewById(R.id.ratingBarRequest);
        CardView cardDate = (CardView)findViewById(R.id.card_request_date);

        if(getIntent().getExtras()!= null){
            requestId = getIntent().getStringExtra(REQUEST_ID);

            /*if(getIntent().getBooleanExtra(REQUEST_IS_COMPLETE, false)){

                ParseQuery<ParseObject> query = ParseQuery.getQuery("HomeServiceRequest");
                query.include("user");
                query.include("attendedBy");
                query.include("homeService");
                query.getInBackground(requestId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject po, ParseException e) {
                        if(e == null){

                            ParseFile image = po.getParseObject("homeService").getParseFile("image");
                            if (image != null) {
                                Picasso.with(RequestActivity.this).load(image.getUrl()).into(target);
                            }
                            int status = po.getInt("status");
                            statusLabel.setText(status == -1 ? "" : HomeServiceRequestStatus.valueOf(status).toString());
                            layoutStatus.setBackgroundColor(Utils.getColorByStatus(RequestActivity.this,HomeServiceRequestStatus.valueOf(status)));
                            if(status!= -1 && (status == HomeServiceRequestStatus.CONFIRMADO.getValue() || status == HomeServiceRequestStatus.COMPLETO.getValue() )){
                                attendedLayout.setVisibility(View.VISIBLE);
                                yourServiceWillBe.setVisibility(View.VISIBLE);
                            } else {
                                attendedLayout.setVisibility(View.GONE);
                                yourServiceWillBe.setVisibility(View.INVISIBLE);

                            }
                            collapsingToolbar.setTitle(po.getParseObject("homeService").getString("name"));
                            requestProblemDescription.setText(po.getString("problemDescription"));
                            attendedBy.setText(po.getParseUser("attendedBy").getUsername());
                            ParseFile attendedFile = po.getParseUser("attendedBy").getParseFile("userImage");
                            if(attendedFile!= null){
                                Picasso.with(RequestActivity.this).load(attendedFile.getUrl()).into(attendedByImageView);
                            }
                            requestDate.setText(po.getDate("dateForService").toString());


                        } else {
                            //TODO shoe empty layot with an error message
                        }

                    }
                });*/

            //} else {
                String imageUrl = getIntent().getStringExtra(REQUEST_IMAGE_URL);
                Picasso.with(this).load(imageUrl).into(target);
                int status = getIntent().getIntExtra(REQUEST_STATUS,-1);
                statusLabel.setText(status == -1 ? "" : HomeServiceRequestStatus.valueOf(status).toString());
                layoutStatus.setBackgroundColor(Utils.getColorByStatus(this,HomeServiceRequestStatus.valueOf(status)));
                if(status!= -1 && (status == HomeServiceRequestStatus.CONFIRMADO.getValue() || status == HomeServiceRequestStatus.COMPLETO.getValue() )){
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
                if(getIntent().getStringExtra(REQUEST_DATE_FOR_SERVICE)!=null){
                    requestDate.setText(getIntent().getStringExtra(REQUEST_DATE_FOR_SERVICE));
                } else {
                    cardDate.setVisibility(View.GONE);
                }

            /*if(getIntent().getBooleanExtra(REQUEST_SHOW_RATING_BUTTON,false)){
                buttonRating.setVisibility(View.VISIBLE);
                buttonRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showRatingDialog();
                    }
                });
            } else {
                buttonRating.setVisibility(View.GONE);

            }*/
                int myReview = getIntent().getIntExtra(REQUEST_NUM_STARS,0);
                if(myReview > 0){
                    ratingBar.setVisibility(View.VISIBLE);
                    ratingBar.setRating(myReview);
                    averageRatingLayout.setVisibility(View.GONE);
                    textViewMyRating.setVisibility(View.VISIBLE);
                }else {
                    ratingBar.setVisibility(View.GONE);
                    averageRatingLayout.setVisibility(View.VISIBLE);
                    textViewMyRating.setVisibility(View.GONE);
                }
                if(getIntent().getBooleanExtra(REQUEST_SHOW_CANCEL_BUTTON,false)){
                    buttonCancelRequest.setVisibility(View.VISIBLE);
                    //buttonCancelRequest.setBackgroundColor(Utils.getColorByStatus(this,HomeServiceRequestStatus.valueOf(status)));
                    buttonCancelRequest.setTextColor(Utils.getColorByStatus(this,HomeServiceRequestStatus.valueOf(status)));
                    RxView.clicks(buttonCancelRequest).subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            CancelRequestDialogFragment cancelRequestDialogFragment = CancelRequestDialogFragment.newInstance(null,null);
                            cancelRequestDialogFragment.show(getSupportFragmentManager(),null);
                        }
                    });
                } else {
                    buttonCancelRequest.setVisibility(View.GONE);
                }

                double averageStars = getIntent().getDoubleExtra(REQUEST_AVERAGE_STARS,0);
                averageRatingTextView.setText(String.valueOf(  new DecimalFormat("#.#").format(averageStars) ));

           // }




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

    public void showRatingDialog(){

        //dialog = ReviewDialogFragment.newInstance();
        //dialog.show(getSupportFragmentManager(), null);
    }

    /*@Override
    public void onReviewDialogButtonClicked(final int numStars, String comment,String idRequest) {
        dialog.dismiss();
        ParseObject review = new ParseObject("Review");
        review.put("numStars", numStars);
        review.put("comment",comment);
        review.put("fromUser", ParseUser.getCurrentUser());
        String id = requestId; //requestList.get(position).getHomeServiceRequestID();
        ParseObject homeServiceID = ParseObject.createWithoutData("HomeServiceRequest",id);
        review.put("homeServiceRequest", homeServiceID);

        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                //requestList.get(position).setWasRated(true);
                //adapter.notifyDataSetChanged();

                if (e == null) {
                    //buttonRating.setVisibility(View.GONE);
                    ratingBar.setVisibility(View.VISIBLE);
                    ratingBar.setRating(numStars);
                    Toast.makeText(RequestActivity.this, getResources().getString(R.string.review_saved), Toast.LENGTH_SHORT).show();
                    //TODO actualizar el campo wasRated en la clase de los request, quitamos el boton y mostramos el rating bar con la calificaion recien mandada


                } else { //ups
                    Toast.makeText(RequestActivity.this, getResources().getString(R.string.review_not_saved), Toast.LENGTH_SHORT).show();
                    //requestList.get(position).setWasRated(false);
                    //adapter.notifyDataSetChanged();

                }
            }
        });


    }*/

    @Override
    public void onCancelRequestButtonClicked() {
        cancelRequest();
        sendCancelPush();

    }

    private void cancelRequest(){
        ParseObject po = ParseObject.createWithoutData("HomeServiceRequest",requestId);
        po.put("status", HomeServiceRequestStatus.CANCELADO.getValue());
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Snackbar.make(coordinatorLayout,R.string.service_cancelled,Snackbar.LENGTH_SHORT).show();
                    statusLabel.setText(HomeServiceRequestStatus.CANCELADO.toString());
                    layoutStatus.setBackgroundColor(Utils.getColorByStatus(RequestActivity.this,HomeServiceRequestStatus.CANCELADO));
                    attendedLayout.setVisibility(View.GONE);
                    yourServiceWillBe.setVisibility(View.INVISIBLE);
                    buttonCancelRequest.setVisibility(View.GONE);

                    //onBackPressed();
                } else {//ups
                    Log.d("Error",getResources().getString(R.string.error_canceling_homeservice));
                }
            }
        });

    }

    private void sendCancelPush(){
        Map<String,Object> params = new HashMap<>();
        params.put("requestId", requestId);
        //params.put("employeeId", employeeId);
        ParseCloud.callFunctionInBackground("sendCancelServicePushToEmployee", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, ParseException e) {
                if (e == null) {
                    Log.d("SUCCESS: ", o.toString());
                    //finish();
                } else {
                    Log.e("ERROR: ", e.getMessage());

                }
            }
        });
    }
}
