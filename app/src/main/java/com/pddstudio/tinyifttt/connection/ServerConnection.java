package com.pddstudio.tinyifttt.connection;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.pddstudio.tinyifttt.models.TinyAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ServerConnection extends AsyncTask<Void, TinyAction, Void> implements Serializable {

    public interface ConnectionCallback {
        void onConnectingStarted();
        void onConnectingFailed(@Nullable Throwable throwable);
        void onConnectingFinished();
        void onTinyActionFound(TinyAction tinyAction);
    }

    private final String mRemoteHost;
    private final int mRemotePort;
    private final ConnectionCallback mConnectionCallback;

    private Throwable mErrorThrowable;

    public ServerConnection(String hostName, int port, ConnectionCallback connectionCallback) {
        this.mRemoteHost = hostName;
        this.mRemotePort = port;
        this.mConnectionCallback = connectionCallback;
    }

    @Override
    public void onPreExecute() {
        mConnectionCallback.onConnectingStarted();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //get the input stream and receive the available commands
        try {
            Socket socket = new Socket(mRemoteHost, mRemotePort);
            if(!socket.isConnected()) return null;

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Gson gson = new Gson();
            String response;
            while ((response = bufferedReader.readLine()) != null) {
                //parse the incoming string and notify the callback if it's not null
                TinyAction tinyAction = gson.fromJson(response, TinyAction.class);
                if(tinyAction != null) publishProgress(tinyAction);
            }

        } catch (IOException io) {
            this.mErrorThrowable = io;
            this.cancel(true);
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        //notify the callback that something went wrong
        mConnectionCallback.onConnectingFailed(mErrorThrowable);
    }

    @Override
    protected void onProgressUpdate(TinyAction... values) {
        //we can be sure here that the array contains only one item
        mConnectionCallback.onTinyActionFound(values[0]);
    }

    @Override
    public void onPostExecute(Void v) {
        //notify the callback that the connection doesn't exist anymore
        mConnectionCallback.onConnectingFinished();
    }

}
