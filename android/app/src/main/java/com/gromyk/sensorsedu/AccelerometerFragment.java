package com.gromyk.sensorsedu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AccelerometerFragment extends Fragment {
    private int sensorType;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private TextView xValueTextView;
    private TextView yValueTextView;
    private TextView zValueTextView;

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
        initView();
    }

    @Override
    public void onResume() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    private void initView() {
        xValueTextView = getView().findViewById(R.id.xValueTextView);
        yValueTextView = getView().findViewById(R.id.yValueTextView);
        zValueTextView = getView().findViewById(R.id.zValueTextView);
    }

    private void initSensorsComponents() {
        if (getActivity() == null) return;
        sensorType = Sensor.TYPE_ACCELEROMETER;
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        sensorEventListener = new SensorEventListener() {
            private String message;

            @Override
            public void onSensorChanged(SensorEvent event) {
                message = "Got a sensor event " + Arrays.toString(event.values) + "\n";
                processSensorChanges(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            private void processSensorChanges(SensorEvent event) {
                xValueTextView.setText(String.valueOf(event.values[0]));
                yValueTextView.setText(String.valueOf(event.values[1]));
                zValueTextView.setText(String.valueOf(event.values[2]));
            }
        };
    }

    @NotNull
    @org.jetbrains.annotations.Contract(" -> new")
    static AccelerometerFragment newInstance() {
        return new AccelerometerFragment();
    }
}
