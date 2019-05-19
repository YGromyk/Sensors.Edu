package com.gromyk.sensorsedu.socket;

import java.io.IOException;

public interface Socket {
    void connect() throws Exception;

    void reconnect(int reconnectWithDelay);

    void disconnect() throws IOException;

    void sendMessage(Object message);
}