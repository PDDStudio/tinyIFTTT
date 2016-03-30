package com.pddstudio.tinyifttt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.pddstudio.tinyifttt.adapter.TinyActionItem;
import com.pddstudio.tinyifttt.connection.SendActionRequest;
import com.pddstudio.tinyifttt.connection.ServerConnection;
import com.pddstudio.tinyifttt.models.TinyAction;

public class MainActivity extends AppCompatActivity implements ServerConnection.ConnectionCallback,
        View.OnClickListener, FastAdapter.OnClickListener, SendActionRequest.Callback {

    private EditText ipAddressEditText;
    private EditText portEditText;
    private Button connectButton;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter<TinyActionItem> fastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign the views
        ipAddressEditText = (EditText) findViewById(R.id.ipAddressEditText);
        portEditText = (EditText) findViewById(R.id.portEditText);
        connectButton = (Button) findViewById(R.id.connectButton);
        if(connectButton != null) connectButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.itemRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        fastAdapter = new FastItemAdapter<>();
        fastAdapter.withOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastAdapter);
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
        fastAdapter.add(new TinyActionItem(tinyAction));
    }

    @Override
    public void onClick(View v) {
        //get the ip and port and try to connect to the server
        String serverIp = ipAddressEditText.getText().toString();
        int serverPort = Integer.parseInt(portEditText.getText().toString());
        ServerConnection serverConnection = new ServerConnection(serverIp, serverPort, this);
        serverConnection.execute();
    }

    @Override
    public boolean onClick(View v, IAdapter adapter, IItem item, int position) {
        String serverIp = ipAddressEditText.getText().toString();
        int serverPort = Integer.parseInt(portEditText.getText().toString());
        new SendActionRequest(serverIp, serverPort, fastAdapter.getAdapterItem(position).getTinyAction()).sendRequest(this);
        return true;
    }

    @Override
    public void onActionSend(boolean success) {
        Log.d("MainActivity", "Action Send : " + success);
    }
}
