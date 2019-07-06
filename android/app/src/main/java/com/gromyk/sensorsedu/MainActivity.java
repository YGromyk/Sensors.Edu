package com.gromyk.sensorsedu;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


public class MainActivity extends Activity {
    private static final String FRAGMENT_TAG = AccelerometerFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        if (getFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null) {
            Fragment accelerometerFragment = AccelerometerFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.rootLayout, accelerometerFragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
