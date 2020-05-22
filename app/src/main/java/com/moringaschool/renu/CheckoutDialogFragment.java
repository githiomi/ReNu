package com.moringaschool.renu;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class CheckoutDialogFragment extends DialogFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.activity_checkout_dialogue, container, false);
        getDialog().setTitle("Dialogue");

        Button complete = (Button) mainView.findViewById(R.id.btnComplete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View main = inflater.inflate(R.layout.activity_loading_dialog, container, false);
                        dismiss();
                    }
                }, 1000);
            }
        });

        return mainView;
    }

}
