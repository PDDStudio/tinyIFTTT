package com.pddstudio.tinyiftt;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pddstudio.tinyiftt.connection.ServerConnection;
import com.pddstudio.tinyiftt.models.TinyAction;

public class MainActivity extends AppCompatActivity implements ServerConnection.ConnectionCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onConnectingStarted() {

    }

    @Override
    public void onConnectingFailed(@Nullable Throwable throwable) {

    }

    @Override
    public void onConnectingFinished() {

    }

    @Override
    public void onTinyActionFound(TinyAction tinyAction) {

    }

}
