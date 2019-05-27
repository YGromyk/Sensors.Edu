package com.gromyk.sensorsedu.socket;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketManager implements Socket {
    private static final String LOG_TAG = SocketManager.class.getSimpleName();

    private static final int DEFAULT_THREAD_POOL_SIZE = 1;
    private ExecutorService executorService;

    private boolean isReconnecting = false;

    private String ipAddress;
    private int port;

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
                    return;
                }
                try {
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                isReconnecting = false;
                Log.i(LOG_TAG, "Socket connected!");
            }
        });
    }

    @Override
    public void disconnect() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(LOG_TAG, "Socket disconnected!");
            }
        }).start();
    }

    @Override
    public void sendMessage(final Object message) {
        if (message == null) return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (isReconnecting) return;
                try {
                    out.writeObject(message);
                } catch (SocketException exception) {
                    reconnect(5 * 1000);
                } catch (IOException e) {
                    reconnect(15 * 1000);
                } catch (NullPointerException exception) {
                    reconnect(30 * 1000);
                }
            }
        });
    }

    @Override
    public void reconnect(final int reconnectWithDelay) {
        isReconnecting = true;
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(reconnectWithDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        connect();
                    }
                }
        );
    }
}
