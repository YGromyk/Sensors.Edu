package com.gromyk.sensorsedu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG = AccelerometerFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null) {
            Fragment accelerometerFragment = AccelerometerFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootLayout, accelerometerFragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
