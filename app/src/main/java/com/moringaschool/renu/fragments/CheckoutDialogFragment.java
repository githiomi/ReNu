package com.moringaschool.renu.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.moringaschool.renu.R;
import com.moringaschool.renu.ui.HomeActivity;

import org.w3c.dom.Text;

public class CheckoutDialogFragment extends DialogFragment{

//    Local variables
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.activity_checkout_dialogue, container, false);
        getDialog().setTitle("Dialogue");

//        Custom methods to initialize the progress dialogs
        createProgressDialog();
        errorAlertVisible();

//        Binding button to view
        Button mAddOrderToCart = (Button) mainView.findViewById(R.id.btnComplete);
        RadioGroup mRadioPaymentGroup = (RadioGroup) mainView.findViewById(R.id.rgPayment);
        TextView mErrorMessage = (TextView) mainView.findViewById(R.id.paymentError);

//        Over riding the on click listener
        mAddOrderToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRadioPaymentGroup != null) {
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
                            String url = "https://www.safaricom.co.ke/personal/m-pesa/lipa-na-m-pesa";
                            Intent goToNewOrder = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(goToNewOrder);
                        }
                    }, 2000);
                }else {

                    // To display the error message
                    mErrorMessage.setVisibility(View.VISIBLE);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Hide the error message
                            mErrorMessage.setVisibility(View.GONE);
                        }
                    }, 1000);

                }
            }
        });
        return mainView;
    }

//    Custom method for the progress dialog
    public void createProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Authenticating payment ...");
        mProgressDialog.setCancelable(false);
    }

//    To show when a radio item is not checked
    public void errorAlertVisible(){

    }


}
