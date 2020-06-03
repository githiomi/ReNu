package com.moringaschool.renu.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.moringaschool.renu.R;
import com.moringaschool.renu.ui.HomeActivity;
import com.moringaschool.renu.ui.MainActivity;
import com.moringaschool.renu.ui.UserLogin;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutDialogFragment extends DialogFragment{

//    Local variables
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.activity_checkout_dialogue, container, false);
        getDialog().setTitle("Dialogue");

        createProgressDialog();

//        Binding button to view
        Button mAddOrderToCart = (Button) mainView.findViewById(R.id.btnComplete);

//        Over riding the on click listener
        mAddOrderToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // To show the progress dialog for the delay time frame
                mProgressDialog.show();

                //        Runs the progressbar for a while before moving to the next intent
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // To hide the progress dialog after the delay
                        mProgressDialog.dismiss();

                        // Routes back to place another order
                        Intent goToNewOrder = new Intent(getContext(), HomeActivity.class);
                        goToNewOrder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(goToNewOrder);

                    }
                }, 2500);
            }
        });
        return mainView;
    }

//    Custom method for the proress dialog
    public void createProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Authenticating payment ...");
        mProgressDialog.setCancelable(false);
    }

}
