package com.wezen.madison.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;

/**
 * Created by eder on 5/9/15.
 */
public class ReviewDialogFragment extends DialogFragment {

    private static final String ARG_HOME_SERVICE_NAME = "home_service_name";
    private static final String ARG_HOME_SERVICE_AVATAR = "home_service_avatar";
    private static final String ARG_ID_REQUEST = "id_request";
    private OnClickReviewDialog mListener;
    private String name;
    private String avatarUrl;
    private String idRequest;

    public interface OnClickReviewDialog {
        void onReviewDialogButtonClicked(int numStars, String comment,String idRequest);
    }

    public static ReviewDialogFragment newInstance(String name, String avatarUrl,String idRequest) {
        ReviewDialogFragment fragment = new ReviewDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOME_SERVICE_NAME, name);
        args.putString(ARG_HOME_SERVICE_AVATAR, avatarUrl);
        args.putString(ARG_ID_REQUEST, idRequest);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_HOME_SERVICE_NAME);
            avatarUrl = getArguments().getString(ARG_HOME_SERVICE_AVATAR);
            idRequest = getArguments().getString(ARG_ID_REQUEST);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity){
            AppCompatActivity activity = (AppCompatActivity)context;
            try {
                mListener = (OnClickReviewDialog) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnClickReviewDialog");
            }

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //String message = String.format(getResources().getString(R.string.order_dialog), mParamHomeServiceName, mParamUserAddress);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.bottom_sheet_rating, null);
        final RatingBar ratingBar = (RatingBar)view.findViewById(R.id.historyItemRating);
        final EditText editText = (EditText)view.findViewById(R.id.edit_text_review);
        final TextView rateService = (TextView)view.findViewById(R.id.rateService);
        final Button saveReview = (Button)view.findViewById(R.id.save_review_button);
        TextView homeServiceName = (TextView)view.findViewById(R.id.home_service_name_rating);
        ImageView homeServiceAvatar = (ImageView)view.findViewById(R.id.home_service_avatar_rating);
        if(!name.equals("")){
            homeServiceName.setText(name);
        }
        if(!avatarUrl.equals("")){
            Picasso.with(getActivity()).load(avatarUrl).into(homeServiceAvatar);
        }


        saveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stars = (int) ratingBar.getRating();
                if (stars == 0) {
                    rateService.setError("Este campo es obligatorio");

                } else {
                    String comment = TextUtils.isEmpty(editText.getText().toString()) ? "" : editText.getText().toString();
                    mListener.onReviewDialogButtonClicked(stars, comment,idRequest);
                }
            }
        });
        builder.setView(view);
        /*.setPositiveButton(R.string.save_review, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });*/

        return builder.create();
    }
}
