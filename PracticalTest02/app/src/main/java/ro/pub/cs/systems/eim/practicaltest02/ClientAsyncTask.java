package ro.pub.cs.systems.eim.practicaltest02;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    // Attributes to hold & display results from server
    private TextView serverMessageTextView;
    private String resultServer;

    public ClientAsyncTask(TextView serverMessageTextView) {
        this.serverMessageTextView = serverMessageTextView;
    }

    @Override
    protected Void doInBackground(String... params) {
        Socket socket = null;
        try {

            // Get connection parameters
            // serverAddress - pos = 0
            String serverAddress = params[0];
            // serverPort - on pos = 1)
            int serverPort = Integer.parseInt(params[1]);

            // Open a socket to the server
            socket = new Socket(serverAddress, serverPort);
            if (socket == null) {
                return null;
            }
            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());

            // PrintWriter to send data to server
            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(params[2]);

            // BufferReader to get data from server
            BufferedReader bufferedReader = Utilities.getReader(socket);

            // Do something with server results - resultServer = bufferedReader.readLine(); ... while etc
            resultServer = bufferedReader.readLine();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            // Close the socket to the server
            try {
                if (socket != null) {
                    socket.close();
                }
                Log.v(Constants.TAG, "Connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Do sth while processing
    private void publishProgress(String currentLine) {
    }

    // Do sth before processing
    @Override
    protected void onPreExecute() {
        serverMessageTextView.setText("");
    }

    // Do sth after processing
    @Override
    protected void onPostExecute(Void result) {
        serverMessageTextView.append(resultServer);
    }
}