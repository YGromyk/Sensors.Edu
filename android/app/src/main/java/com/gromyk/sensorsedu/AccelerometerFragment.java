package com.gromyk.sensorsedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class AccelerometerFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        initSensorsComponents();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_accelerometer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initSensorsComponents() {

    }

    @NotNull
    @org.jetbrains.annotations.Contract(" -> new")
    static AccelerometerFragment newInstance() {
        return new AccelerometerFragment();
    }
}
