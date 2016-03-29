package com.pddstudio.tinyiftt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pddstudio.tinyiftt.connection.ServerConnection;
import com.pddstudio.tinyiftt.models.TinyAction;

public class MainActivity extends AppCompatActivity implements ServerConnection.ConnectionCallback, View.OnClickListener {

    private EditText ipAddressEditText;
    private EditText portEditText;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign the views
        ipAddressEditText = (EditText) findViewById(R.id.ipAddressEditText);
        portEditText = (EditText) findViewById(R.id.portEditText);
        connectButton = (Button) findViewById(R.id.connectButton);
        if(connectButton != null) connectButton.setOnClickListener(this);
    }


    @Override
    public void onConnectingStarted() {
        Log.d("MainActivity", "onConnectingStarted()");
    }

    @Override
    public void onConnectingFailed(@Nullable Throwable throwable) {
        Log.d("MainActivity", "onConnectionFailed()");
        if(throwable != null) throwable.printStackTrace();
    }

    @Override
    public void onConnectingFinished() {
        Log.d("MainActivity", "onConnectionFinished() called");
    }

    @Override
    public void onTinyActionFound(TinyAction tinyAction) {
        Log.d("MainActivity", "TinyAction received: " + tinyAction.getActionIdentifier() + " Title: " + tinyAction.getActionTitle() + " Description: " + tinyAction.getActionDescription());
    }

    @Override
    public void onClick(View v) {
        //get the ip and port and try to connect to the server
        String serverIp = ipAddressEditText.getText().toString();
        int serverPort = Integer.parseInt(portEditText.getText().toString());
        ServerConnection serverConnection = new ServerConnection(serverIp, serverPort, this);
        serverConnection.execute();
    }

}
