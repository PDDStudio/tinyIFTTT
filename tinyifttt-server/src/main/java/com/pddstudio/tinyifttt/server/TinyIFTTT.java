package com.pddstudio.tinyifttt.server;

import com.google.gson.Gson;
import com.pddstudio.tinyifttt.models.TinyAction;
import com.pddstudio.tinyifttt.models.TinyActionReceivedListener;
import com.pddstudio.tinyifttt.server.async.ClientConnectionListener;
import com.pddstudio.tinyifttt.server.utils.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class TinyIFTTT implements TinyActionReceivedListener {

    private final String configFile;
    private int serverPort;
    private ServerSocket serverSocket;
    private TinyAction[] tinyActions;

    public TinyIFTTT(String configFilePath) throws IOException {
        this.configFile = configFilePath;
        loadConfiguration();
        Logger.log(this, "tinyIFTT Server configuration loaded.");
    }

    public void start(int serverPort) throws IOException {
        this.serverPort = serverPort;
        Logger.log(this, "Starting tinyIFTT Server...");
        runTinyServer();
    }

    private void loadConfiguration() throws IOException {
        //parse all available actions
        File actionFile = new File(configFile);
        FileReader fileReader = new FileReader(actionFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Gson gson = new Gson();
        this.tinyActions = gson.fromJson(bufferedReader, TinyAction[].class);
    }

    private void runTinyServer() throws IOException {
        //start the server on the given port and listen for incoming connections
        serverSocket = new ServerSocket(serverPort);
        while(true) {
            Socket socket = serverSocket.accept();
            onSocketConnectionFound(socket);
        }
    }

    private void onSocketConnectionFound(Socket socket) {
        Logger.log(this, "SocketConnection found: " + socket.getInetAddress().getHostAddress());
        //run a new asynchronous listener
        new ClientConnectionListener(socket, this).startListening();
        //send the list of available commands to the client
        sendAvailableCommands(socket);
    }

    private void sendAvailableCommands(Socket socket) {
        try {
            //open the output stream
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            Gson gson = new Gson();
            //send all available commands
            for(TinyAction tinyAction : tinyActions) {
                bufferedWriter.write(gson.toJson(tinyAction));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void onTinyActionReceived(TinyAction tinyAction) {
        Logger.log(this, "ActionReceived: ID [" + tinyAction.getActionIdentifier() + "] Exec: " + tinyAction.getActionExec()[0]);
        //handle the action we received from one of the listeners
        for(TinyAction mAction : tinyActions) {
            //only execute the action if it's known
            if(mAction.getActionIdentifier() == tinyAction.getActionIdentifier()) new ActionExecutor(tinyAction).execute();
        }
    }

}
