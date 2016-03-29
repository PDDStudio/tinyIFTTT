package com.pddstudio.tinyiftt.connection;

import android.os.AsyncTask;

import com.pddstudio.tinyiftt.models.TinyAction;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ServerConnection extends AsyncTask<Void, TinyAction, Void> {

    public interface ConnectionCallback {
        void onConnectingStarted();
        void onConnectingFailed();
        void onConnectingFinished();
        void onTinyActionFound(TinyAction tinyAction);
    }

    private final String mRemoteHost;
    private final int mRemotePort;
    private final ConnectionCallback mConnectionCallback;

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
        return null;
    }

    @Override
    protected void onCancelled() {

    }

    @Override
    protected void onProgressUpdate(TinyAction... values) {

    }

    @Override
    public void onPostExecute(Void v) {

    }

}
