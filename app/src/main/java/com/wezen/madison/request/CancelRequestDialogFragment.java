package com.wezen.madison.request;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.wezen.madison.R;

/**
 * Created by eder on 5/9/15.
 */
public class CancelRequestDialogFragment extends DialogFragment {

    private static final String ARG_HOME_SERVICE_NAME = "home_service_name";
    private static final String ARG_USER_ADDRESS = "user_address";
    private OnClickCancelDialog mListener;
    private String mParamHomeServiceName;
    private String mParamUserAddress;

    public interface OnClickCancelDialog {
        void onCancelRequestButtonClicked();
    }

    public static CancelRequestDialogFragment newInstance(String name, String address) {
        CancelRequestDialogFragment fragment = new CancelRequestDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOME_SERVICE_NAME, name);
        args.putString(ARG_USER_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamHomeServiceName = getArguments().getString(ARG_HOME_SERVICE_NAME);
            mParamUserAddress = getArguments().getString(ARG_USER_ADDRESS);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity){
            AppCompatActivity activity = (AppCompatActivity)context;
            try {
                mListener = (OnClickCancelDialog) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnClickDialog");
            }

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // String message = String.format(getResources().getString(R.string.order_dialog), mParamHomeServiceName, mParamUserAddress);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cancel_service_dialog_title)
                //.setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCancelRequestButtonClicked();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
