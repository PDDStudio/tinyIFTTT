package com.pddstudio.tinyifttt.connection;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.pddstudio.tinyifttt.models.TinyAction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class SendActionRequest extends AsyncTask<Void, Void, Void> {

    public interface Callback {
        void onActionSend(boolean success);
    }

    private final String mServerHost;
    private final int mServerPort;
    private final TinyAction mTinyAction;

    private Callback mCallback;

    public SendActionRequest(String serverHost, int serverPort, TinyAction tinyAction) {
        this.mServerHost = serverHost;
        this.mServerPort = serverPort;
        this.mTinyAction = tinyAction;
    }

    public void sendRequest(@Nullable Callback callback) {
        Log.d("SendActionRequest", "sendRequest() called. Executing job. [" + mServerHost + ":" + mServerPort +"]");
        this.mCallback = callback;
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //get the output stream and send the action we want to be executed
        try {
            Socket socket = new Socket(mServerHost, mServerPort);
            if(!socket.isConnected()) this.cancel(true);
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            Gson gson = new Gson();
            bufferedWriter.write(gson.toJson(mTinyAction));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException io) {
            io.printStackTrace();
            this.cancel(true);
        }
        Log.d("SendActionRequest", "Action Send!");
        return null;
    }

    @Override
    protected void onCancelled() {
        //notify the callback if set
        if(mCallback != null) mCallback.onActionSend(false);
    }

    @Override
    public void onPostExecute(Void v) {
        //notify the callback if set
        if(mCallback != null) mCallback.onActionSend(true);
    }

}
