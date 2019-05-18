package com.gromyk.sensorsedu;

import android.app.Application;

import com.gromyk.sensorsedu.socket.Socket;
import com.gromyk.sensorsedu.socket.SocketManager;

public class App extends Application {
    private static Socket socket;
    public static Socket getSocket() {
        if (socket == null) {
            socket = new SocketManager();
            try {
                socket.connect("10.55.2.155", 81);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return socket;
    }
}
