package com.moringaschool.renu;

import android.widget.TextView;

import com.moringaschool.renu.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.*;

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
}
