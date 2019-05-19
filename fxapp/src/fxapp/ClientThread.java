package fxapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

@SuppressWarnings("unused")
public class ClientThread extends Thread {
    private Socket socket;

    private OnEventListener onEventListener;

    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket);
        Object line;
        while (true) {
            try {
                line = objectInputStream.readObject();
                if (line != null) {
                    if (line instanceof HashMap) {
                        SensorEventDTO event = SensorEventDTO.fromHashMap((HashMap<String, Float>) line);
                        onEventListener.onEventReceived(event);
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

    public OnEventListener getOnEventListener() {
        return onEventListener;
    }

    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    public interface OnEventListener{
        void onEventReceived(SensorEventDTO eventDTO);
    }
}