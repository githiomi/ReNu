package com.moringaschool.renu.models;

import com.moringaschool.renu.BuildConfig;

public class ApiConstants {

    public static final String YELP_TOKEN = BuildConfig.YELP_API_KEY;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v3/";
    public static final String YELP_LOCATION_QUERY_PARAMETER = "location";

    //    Firebase keys for key value pairs
    public static final String NAME_KEY = "name";
    public static final String FIREBASE_CHILD_RESTAURANTS = "restaurants";

    //    Firebase
    public static final String FIREBASE_USERNAME_KEY = "Username";
    public static final String FIREBASE_LOCATION_KEY = "Location";

}
