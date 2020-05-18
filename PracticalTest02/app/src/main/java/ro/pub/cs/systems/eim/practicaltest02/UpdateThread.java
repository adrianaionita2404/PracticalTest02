package ro.pub.cs.systems.eim.practicaltest02;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class UpdateThread extends Thread {

    private boolean isRunning;

    // Custom server data
    private ServerThread serverThread;

    public UpdateThread(ServerThread serverThreads) {
        isRunning = true;
        this.serverThread = serverThreads;
    }

    public void stopUpdate() {
        isRunning = false;
        Log.v(Constants.TAG, "stopUpdate() method was invoked");
    }

    @Override
    public void run() {
        try {
            while (isRunning) {

                //update info
                //http
                HttpClient httpClient = new DefaultHttpClient();
                // - create the URL to the web service, appending the bounding box coordinates and the username to the base Internet address
                String urlUsd = "https://api.coindesk.com/v1/bpi/currentprice/USD.json";
                String urlEur = "https://api.coindesk.com/v1/bpi/currentprice/EUR.json";

                //Log.d(Constants.TAG, url);
                // - create an instance of a HttGet object
                HttpGet httpGetUsd = new HttpGet(urlUsd);
                HttpGet httpGetEur = new HttpGet(urlEur);
                // - create an instance of a ReponseHandler object
                ResponseHandler<String> responseHandlerUsd = new BasicResponseHandler();
                ResponseHandler<String> responseHandlerEur = new BasicResponseHandler();
                try {
                    // - execute the request, thus obtaining the response
                    String contentUsd = httpClient.execute(httpGetUsd, responseHandlerUsd);
                    String contentEur = httpClient.execute(httpGetEur, responseHandlerEur);
                    // - get the JSON object representing the response
                    JSONObject resultUsd = new JSONObject(contentUsd);
                    JSONObject resultEur = new JSONObject(contentEur);

                    serverThread.coinDetailsUsd = resultUsd;
                    serverThread.coinDetailsEur = resultEur;

                    Log.v(Constants.TAG, "Updating info... ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //sleep 1 min
                sleep(1000);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}