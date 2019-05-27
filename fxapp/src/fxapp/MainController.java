package fxapp;

import fxapp.socket.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private int countOfClients = 0;

    public List<ClientThread.OnEventListener> getEventListeners() {
        return eventListeners;
    }

    public void setEventListeners(List<ClientThread.OnEventListener> eventListeners) {
        this.eventListeners = eventListeners;
    }

    private List<ClientThread.OnEventListener> eventListeners;

    public MainController() {
        eventListeners = new ArrayList<>();
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
            client.setOnEventListener(eventListeners.get(countOfClients));
            client.start();
            countOfClients++;
        }
    }
}
