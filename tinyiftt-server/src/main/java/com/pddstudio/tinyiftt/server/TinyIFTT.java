package com.pddstudio.tinyiftt.server;

import com.pddstudio.tinyiftt.models.TinyAction;
import com.pddstudio.tinyiftt.models.TinyActionReceivedListener;
import com.pddstudio.tinyiftt.server.async.ClientConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class TinyIFTT implements TinyActionReceivedListener {

    private final String configFile;
    private int serverPort;
    private ServerSocket serverSocket;

    public TinyIFTT(String configFilePath) {
        this.configFile = configFilePath;
        loadConfiguration();
    }

    public void start(int serverPort) throws IOException {
        this.serverPort = serverPort;
        runTinyServer();
    }

    private void loadConfiguration() {

    }

    private void runTinyServer() throws IOException {
        serverSocket = new ServerSocket(serverPort);
        while(true) {
            Socket socket = serverSocket.accept();
            onSocketConnectionFound(socket);
        }
    }

    private void onSocketConnectionFound(Socket socket) {
        //run a new asynchronous listener
        new ClientConnectionListener(socket, this).startListening();
    }

    @Override
    public void onTinyActionReceived(TinyAction tinyAction) {
        //handle the action we received from one of the listeners
        new ActionExecutor(tinyAction).execute();
    }

}
