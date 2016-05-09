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
import android.widget.RatingBar;
import android.widget.TextView;

import com.wezen.madison.R;

/**
 * Created by eder on 5/9/15.
 */
public class ReviewDialogFragment extends DialogFragment {

    private static final String ARG_HOME_SERVICE_POSITION = "home_service_position";
    private OnClickReviewDialog mListener;
    //private int position;

    public interface OnClickReviewDialog {
        void onReviewDialogButtonClicked(int numStars, String comment);
    }

    public static ReviewDialogFragment newInstance() {
        ReviewDialogFragment fragment = new ReviewDialogFragment();
        //Bundle args = new Bundle();
        //args.putInt(ARG_HOME_SERVICE_POSITION, position);
        //fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //position = getArguments().getInt(ARG_HOME_SERVICE_POSITION);

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
        saveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stars = (int) ratingBar.getRating();
                if (stars == 0) {
                    rateService.setError("Este campo es obligatorio");

                } else {
                    String comment = TextUtils.isEmpty(editText.getText().toString()) ? "" : editText.getText().toString();
                    mListener.onReviewDialogButtonClicked(stars, comment);
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
