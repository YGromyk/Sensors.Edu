package fxapp;

import fxapp.socket.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainController {
    private ClientThread.OnEventListener eventListener;

    public MainController() {
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void connectSocket() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        final int portNumber = 3000;
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println(serverSocket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException exception) {
            return;
        }

        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClientThread client = new ClientThread(socket);
            client.setOnEventListener(eventListener);
            client.start();
         }
    }

    public void setEventListener(ClientThread.OnEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public ClientThread.OnEventListener getEventListener() {
        return eventListener;
    }

}
