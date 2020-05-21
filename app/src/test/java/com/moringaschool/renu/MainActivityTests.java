package com.moringaschool.renu;

import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.*;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)

public class MainActivityTests {

    private MainActivity mainActivity;

    @Before
    public void setUp(){
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void checksThatMyAppMainActivityHasDesiredText() {
        TextView appName = mainActivity.findViewById(R.id.tvAppName);
        assertEquals("RE-NU", appName.getText().toString());
    }

    @Test
    public void checksToSeeIfItOpensTheNextIntent(){
        mainActivity.findViewById(R.id.btnProceed).performClick();
        Intent expected = new Intent(mainActivity, LoginActivity.class);

        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(mainActivity);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertTrue(actual.filterEquals(expected));
    }



}
