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

import com.gromyk.sensorsedu.socket.Socket;

import org.jetbrains.annotations.NotNull;

public class AccelerometerFragment extends Fragment {
    private int sensorType;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private Socket socket;

    private TextView xValueTextView;
    private TextView yValueTextView;
    private TextView zValueTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSocket();
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


    private void initSocket() {
        socket = App.getSocket();
        try {
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            @Override
            public void onSensorChanged(SensorEvent event) {
                SensorEventDTO eventDTO = new SensorEventDTO(event.values, event.timestamp);
                socket.sendMessage(eventDTO.toHashMap());
                processSensorChanges(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            private void processSensorChanges(SensorEvent event) {
                if (AccelerometerFragment.this.getView() != null) {
                    xValueTextView.setText(NumberFormatter.format(event.values[0]));
                    yValueTextView.setText(NumberFormatter.format(event.values[1]));
                    zValueTextView.setText(NumberFormatter.format(event.values[2]));
                }
            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        NumberFormatter.setDecimals(4);
    }

    @NotNull
    @org.jetbrains.annotations.Contract(" -> new")
    static AccelerometerFragment newInstance() {
        return new AccelerometerFragment();
    }
}
