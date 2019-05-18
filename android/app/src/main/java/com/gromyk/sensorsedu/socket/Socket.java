package com.gromyk.sensorsedu.socket;

import java.util.function.Function;

public interface Socket {
    public void connect();
    public void reconnect();
    public void disconnect();
    public void on(String roomName, Function<String, Void> function);
    public void off(String roomName);
    public void sendMessage(Object message);
}
