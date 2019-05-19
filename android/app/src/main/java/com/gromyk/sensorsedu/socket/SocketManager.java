package com.gromyk.sensorsedu.socket;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class SocketManager implements Socket {
    private static final int DEFAULT_THREAD_POOL_SIZE = 1;
    private ExecutorService executorService;

    private String ipAddress ;
    private int port ;

    private java.net.Socket clientSocket;
    private ObjectOutputStream out;

    public SocketManager(final String ip, final int port) {
        this.ipAddress = ip;
        this.port = port;
        executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }

    @Override
    public void connect() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new java.net.Socket(ipAddress, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void reconnect() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void on(String roomName, Function<String, Void> function) {

    }

    @Override
    public void off(String roomName) {

    }

    @Override
    public void sendMessage(final Object message) {
        if (message == null) return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (!clientSocket.isConnected())
                    connect();
                Log.d(SocketManager.class.getSimpleName(), clientSocket.toString());
                Log.d(SocketManager.class.getSimpleName(), "is connected = " + clientSocket.isConnected());
                Log.d(SocketManager.class.getSimpleName(), "received event");
                try {
                    out.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
