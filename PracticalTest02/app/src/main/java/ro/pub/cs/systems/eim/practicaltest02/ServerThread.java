package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    // Server state
    private boolean isRunning;

    // Server socket info
    private ServerSocket serverSocket;
    private int sockNr;

    // Info handled by server
    private JSONObject coinDetails = null;

    // Constructor -> init needed structs
    public ServerThread(int sockNr) {
        this.sockNr = sockNr;
        //init data handled by server
    }

    // Start server
    public void startServer() {
        isRunning = true;
        start();
        Log.v(Constants.TAG, "startServer() method was invoked");
    }

    // Stop server
    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(Constants.TAG, "stopServer() method was invoked");
    }

    // Run - handle clients
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(sockNr);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    // Start new thread for each client - custom info in constructor
                    CommunicationThread communicationThread = new CommunicationThread(socket, coinDetails);
                    communicationThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}