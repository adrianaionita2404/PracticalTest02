package ro.pub.cs.systems.eim.practicaltest02;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {

    // Server socket
    private Socket socket;

    // Custom server data
    private JSONObject coinDetails;

    public CommunicationThread(Socket socket, JSONObject coinDetails) {
        this.socket = socket;
        //keep server data
        this.coinDetails = coinDetails;
    }

    @Override
    public void run() {
        try {
            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());

            // BufferedReader - read from socket (use Utilities.readLine())
            BufferedReader bufferedReader = Utilities.getReader(socket);
            // Get info from client
            // get usd/eur

            // Do some work accordingly
            // ...
            // ... end do work

            // PrintWriter - write on socket (use Utilities.getWriter())
            PrintWriter printWriter = Utilities.getWriter(socket);
            // Give info to client
            // put value in usd/eur
            printWriter.println("test");

            // Close client socket
            socket.close();
            Log.v(Constants.TAG, "Connection closed");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}