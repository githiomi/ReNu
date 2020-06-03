package com.moringaschool.renu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.renu.Network.YelpApi;
import com.moringaschool.renu.Network.YelpClient;
import com.moringaschool.renu.R;
import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.models.YelpBusinessesSearchResponse;
import com.moringaschool.renu.adapters.RestaurantListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealViewActivity extends AppCompatActivity {

//    TAG
    private static final String TAG = MealViewActivity.class.getSimpleName();

//    Binding views from the layout to the class
    @BindView(R.id.rvRestaurants) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

//    Array list to hold all the restaurants
    public List<Business> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

//        Butter Knife binding
        ButterKnife.bind(this);

//        Received data from the home activity
        Intent receivedIntent = getIntent();
        String tableNumber = receivedIntent.getStringExtra("tableNumber");
        //  set received data to the app bar
        getSupportActionBar().setTitle("Order For Table: " + tableNumber);

//        Method that calls the get restaurants
        getRestaurants();
    }

//    Main method to retrieve the restaurants
    public void getRestaurants() {

        //        Local Variables
        String location = "New York";
        String restaurants = "restaurants";


        YelpApi yelpClient = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = yelpClient.getRestaurants(location, restaurants);

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {

                if (response.isSuccessful()) {
                    mRestaurants = response.body().getBusinesses();

                    for (Business restaurant : mRestaurants) {
                        Log.v(TAG, restaurant.getName());
                    }

                    RestaurantListAdapter mAdapter;

                    mAdapter = new RestaurantListAdapter(MealViewActivity.this, mRestaurants);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(MealViewActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    hideProgressBar();
                    showRestaurants();
                } else {
                    hideProgressBar();
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

//    UI improvement methods to show loading while getting restaurants
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}