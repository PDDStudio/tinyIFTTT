package com.pddstudio.tinyifttt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.pddstudio.tinyifttt.connection.ConnectionInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SAVED_HOST_IP = "remoteHost";
    private static final String SAVED_HOST_PORT = "remotePort";

    private TextInputLayout mIpInputLayout;
    private TextInputLayout mPortInputLayout;
    private EditText mIpAddressEditText;
    private EditText mPortEditText;
    private Button mConnectButton;
    private CheckBox mSaveLoginsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //assign the views
        mIpInputLayout = (TextInputLayout) findViewById(R.id.ipInputLayout);
        if(mIpInputLayout != null) mIpInputLayout.setErrorEnabled(true);

        mPortInputLayout = (TextInputLayout) findViewById(R.id.portInputLayout);
        if(mPortInputLayout != null) mPortInputLayout.setErrorEnabled(true);

        mIpAddressEditText = (EditText) findViewById(R.id.ipAddressEditText);
        mPortEditText = (EditText) findViewById(R.id.portEditText);
        mSaveLoginsCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckbox);

        mConnectButton = (Button) findViewById(R.id.connectButton);
        if(mConnectButton != null) mConnectButton.setOnClickListener(this);

        //load the configuration - if saved
        loadConnectionInfo();
    }

    @Override
    public void onClick(View v) {
        //get the ip and port and set the connection info
        if(mIpAddressEditText.getText().toString().isEmpty()) mIpInputLayout.setError(getString(R.string.error_ip_missing));
        else if(mPortEditText.getText().toString().isEmpty()) mPortInputLayout.setError(getString(R.string.error_port_missing));
        else {
            try {
                int serverPort = Integer.parseInt(mPortEditText.getText().toString());
                String serverIp = mIpAddressEditText.getText().toString();

                if(mSaveLoginsCheckBox.isChecked()) saveConnectionInfo(serverIp, serverPort);
                ConnectionInfo.setConnectionData(serverIp, serverPort);
                Intent data = new Intent(this, MainActivity.class);
                startActivity(data);
            } catch (NumberFormatException nnf) {
                mPortInputLayout.setError(getString(R.string.error_port_missing));
            }
        }
    }

    private void loadConnectionInfo() {
        //load the saved date (if any)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String hostIp = sharedPreferences.getString(SAVED_HOST_IP, null);
        if(hostIp != null) mIpAddressEditText.setText(hostIp);
        int hostPort = sharedPreferences.getInt(SAVED_HOST_PORT, -1);
        if(hostPort != -1) mPortEditText.setText(hostPort);
    }

    private void saveConnectionInfo(String hostName, int hostPort) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(SAVED_HOST_IP, hostName).putInt(SAVED_HOST_PORT, hostPort).apply();
    }

}
