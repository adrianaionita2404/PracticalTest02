package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PracticalTest02MainActivity extends AppCompatActivity {

    public ServerThread serverThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
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

    @Override
    public void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}
