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
    public ServerThread serverThread;

    public CommunicationThread(Socket socket, ServerThread serverThread) {
        this.socket = socket;
        //keep server data
        this.serverThread = serverThread;
    }

    @Override
    public void run() {
        try {
            String valueCoin = null;

            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());

            // BufferedReader - read from socket (use Utilities.readLine())
            BufferedReader bufferedReader = Utilities.getReader(socket);
            // Get info from client
            // get usd/eur
            String coin = bufferedReader.readLine();

            // Do some work accordingly
            // ...
            if (coin.toUpperCase().equals("USD")) {
                valueCoin = serverThread.coinDetailsUsd.getJSONObject("bpi").getJSONObject("USD").getString("rate");
                //valueCoin = val.toString();
            } else if (coin.toUpperCase().equals("EUR")) {
                valueCoin = serverThread.coinDetailsEur.getJSONObject("bpi").getJSONObject("EUR").getString("rate");
                //valueCoin = val.toString();
            }
            // ... end do work

            // PrintWriter - write on socket (use Utilities.getWriter())
            PrintWriter printWriter = Utilities.getWriter(socket);
            // Give info to client
            // put value in usd/eur
            printWriter.println(valueCoin);

            // Close client socket
            socket.close();
            Log.v(Constants.TAG, "Connection closed");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}