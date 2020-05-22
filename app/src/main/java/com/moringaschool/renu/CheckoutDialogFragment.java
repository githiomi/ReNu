package com.moringaschool.renu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class CheckoutDialogFragment extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_checkout_dialogue, container, false);
        getDialog().setTitle("Dialogue");
        return rootView;
    }
}
