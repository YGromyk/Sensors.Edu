package fxapp.socket;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SensorEventDTO implements Serializable {
    public static final String X_KEY = "xKey";
    public static final String Y_KEY = "yKey";
    public static final String Z_KEY = "zKey";
    public static final String TIMESTAMP_KEY = "timestamp";

    private float x;
    private float y;
    private float z;

    private long timestamp;

    public SensorEventDTO(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SensorEventDTO(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    public SensorEventDTO() {
        this.x = .0f;
        this.y = .0f;
        this.z = .0f;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "x = " + getX() + ", y = " + getY() + ", z = " + getZ();
    }

    public HashMap<String, Float> toHashMap() {
        HashMap<String, Float> hashMap = new HashMap<>();
        hashMap.put(X_KEY, getX());
        hashMap.put(Y_KEY, getY());
        hashMap.put(Z_KEY, getZ());
        hashMap.put(TIMESTAMP_KEY, ((float) getTimestamp()));
        return hashMap;
    }

    public static SensorEventDTO fromHashMap(HashMap<String, Float> hashMap) {
        SensorEventDTO eventDTO = new SensorEventDTO();
        if (hashMap.get(X_KEY) != null) {
            eventDTO.setX(hashMap.get(X_KEY));
        }
        if (hashMap.get(Y_KEY) != null) {
            eventDTO.setY(hashMap.get(Y_KEY));
        }
        if (hashMap.get(Z_KEY) != null) {
            eventDTO.setZ(hashMap.get(Z_KEY));
        }
        if (hashMap.get(TIMESTAMP_KEY) != null) {
            eventDTO.setTimestamp(hashMap.get(TIMESTAMP_KEY).longValue());
        }
        return eventDTO;
    }
}
