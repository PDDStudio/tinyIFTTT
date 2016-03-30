package com.pddstudio.tinyifttt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pddstudio.tinyifttt.connection.ConnectionInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mIpAddressEditText;
    private EditText mPortEditText;
    private Button mConnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mIpAddressEditText = (EditText) findViewById(R.id.ipAddressEditText);
        mPortEditText = (EditText) findViewById(R.id.portEditText);
        mConnectButton = (Button) findViewById(R.id.connectButton);
        if(mConnectButton != null) mConnectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //get the ip and port and try to connect to the server
        String serverIp = mIpAddressEditText.getText().toString();
        int serverPort = Integer.parseInt(mPortEditText.getText().toString());
        ConnectionInfo.setConnectionData(serverIp, serverPort);
        Intent data = new Intent(this, MainActivity.class);
        startActivity(data);
    }

}
