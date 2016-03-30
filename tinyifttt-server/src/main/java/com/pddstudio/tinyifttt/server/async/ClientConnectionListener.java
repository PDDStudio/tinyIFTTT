package com.pddstudio.tinyifttt.server.async;

import com.google.gson.Gson;
import com.pddstudio.tinyifttt.models.TinyAction;
import com.pddstudio.tinyifttt.models.TinyActionReceivedListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ClientConnectionListener extends Thread {

    private final Socket clientSocket;
    private final TinyActionReceivedListener tinyActionReceivedListener;

    public ClientConnectionListener(Socket socket, TinyActionReceivedListener tinyActionReceivedListener) {
        //assign the client socket we want to communicate with
        this.clientSocket = socket;
        //assign the interface for the callbacks
        this.tinyActionReceivedListener = tinyActionReceivedListener;
    }

    public void startListening() {
        //start the new Thread
        start();
    }

    @Override
    public void run() {
        //read the input from the socket's input stream
        try {
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Gson gson = new Gson();
            String response;
            while((response = bufferedReader.readLine()) != null) {
                TinyAction tinyAction = gson.fromJson(response, TinyAction.class);
                if(tinyAction != null) tinyActionReceivedListener.onTinyActionReceived(tinyAction);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
