package com.pddstudio.tinyifttt;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.pddstudio.tinyifttt.adapter.TinyActionItem;
import com.pddstudio.tinyifttt.connection.ConnectionInfo;
import com.pddstudio.tinyifttt.connection.SendActionRequest;
import com.pddstudio.tinyifttt.connection.ServerConnection;
import com.pddstudio.tinyifttt.models.TinyAction;
import com.pddstudio.tinyifttt.utils.Dialog;

public class MainActivity extends AppCompatActivity implements ServerConnection.ConnectionCallback, FastAdapter.OnClickListener, SendActionRequest.Callback {

    private Toolbar mToolbar;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FastItemAdapter<TinyActionItem> fastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign the views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //execute the request
        ServerConnection serverConnection = new ServerConnection(ConnectionInfo.getConnectionInfo().getmRemoteHost(), ConnectionInfo.getConnectionInfo().getmRemotePort(), this);
        serverConnection.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        recyclerView = (RecyclerView) findViewById(R.id.itemRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        fastAdapter = new FastItemAdapter<>();
        fastAdapter.withOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fastAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClick(View v, IAdapter adapter, IItem item, int position) {
        String serverIp = ConnectionInfo.getConnectionInfo().getmRemoteHost();
        int serverPort = ConnectionInfo.getConnectionInfo().getmRemotePort();
        new SendActionRequest(serverIp, serverPort, fastAdapter.getAdapterItem(position).getTinyAction()).sendRequest(this);
        return true;
    }

    @Override
    public void onActionSend(boolean success) {
        Log.d("MainActivity", "Action Send : " + success);
        if(success) showToast(getString(R.string.toast_action_send_success));
        else showToast(getString(R.string.toast_action_send_fail));
    }

    @Override
    public void onConnectingStarted() {
        Log.d("MainActivity", "onConnectingStarted()");
    }

    @Override
    public void onConnectingFailed(@Nullable Throwable throwable) {
        Log.d("MainActivity", "onConnectionFailed()");
        if(throwable != null) throwable.printStackTrace();
        new Dialog().setContext(this)
                .setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .show(getSupportFragmentManager(), getString(R.string.dialog_connection_failed_title), getString(R.string.dialog_connection_failed_content, throwable != null ? throwable.getMessage() : ""));
    }

    @Override
    public void onConnectingFinished() {
        //set the result code and finish the activity
        Log.d("MainActivity", "onConnectionFinished() called");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onTinyActionFound(TinyAction tinyAction) {
        Log.d("MainActivity", "TinyAction received: " + tinyAction.getActionIdentifier() + " Title: " + tinyAction.getActionTitle() + " Description: " + tinyAction.getActionDescription());
        fastAdapter.add(new TinyActionItem(tinyAction));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
