package fxapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        ObjectInputStream brinp = null;
        try {
            inp = socket.getInputStream();
            brinp = new ObjectInputStream(inp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket);
        Object line;
        while (true) {
            try {
                line = brinp.readObject();
                if (line != null) {
                    if (line instanceof HashMap) {
                        System.out.println(SensorEventDTO.fromHashMap((HashMap<String, Float>) line).toString() + "\n\r");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}