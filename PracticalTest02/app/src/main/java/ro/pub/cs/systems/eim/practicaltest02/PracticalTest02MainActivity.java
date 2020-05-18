package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest02MainActivity extends AppCompatActivity {

    public ServerThread serverThread = null;

    public TextView serverMessageTextView;

    public EditText addressClient;
    public EditText portClient;

    public EditText reqClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverMessageTextView = (TextView) findViewById(R.id.coin_value_text_view);
        addressClient = (EditText) findViewById(R.id.client_address_edit_text);
        portClient = (EditText) findViewById(R.id.client_port_edit_text);

        reqClient = (EditText) findViewById(R.id.coin_edit_text);
    }

    public void onServerStartPress(View v) {
        Log.v(Constants.TAG, "Trying to start server..");

        EditText server_port = (EditText) findViewById(R.id.server_port_edit_text);

        if (serverThread == null && !server_port.getText().toString().isEmpty()) {
            if (server_port.getText().toString().matches("[0-9]+")) {
                int portNr = Integer.parseInt(server_port.getText().toString());
                serverThread = new ServerThread(portNr);
                serverThread.startServer();
                Log.v(Constants.TAG, "Starting server...");
            } else {
                Log.v(Constants.TAG, "Incorrect port format...");
            }
        } else {
            Log.v(Constants.TAG, "Server already running...");
        }
    }

    public void onClientStartPress(View v) {
        ClientAsyncTask clientAsyncTask = new ClientAsyncTask(serverMessageTextView);
        clientAsyncTask.execute(addressClient.getText().toString(), portClient.getText().toString(),
                reqClient.getText().toString());
    }

    @Override
    public void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}
