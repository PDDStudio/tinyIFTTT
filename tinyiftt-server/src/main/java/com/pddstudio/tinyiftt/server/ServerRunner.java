package com.pddstudio.tinyiftt.server;

import java.io.IOException;

import javax.inject.Inject;

import io.airlift.airline.Command;
import io.airlift.airline.HelpOption;
import io.airlift.airline.Option;
import io.airlift.airline.SingleCommand;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
@Command(name = "tinyIFTT ServerRunner", description = "Run a tinyIFTT-Server")
public class ServerRunner {

    @Inject
    public HelpOption helpOption;

    @Option(name = { "-c", "--config" }, description = "The location to the tinyIFTT configuration file.")
    public String configFileLocation;

    @Option(name = { "-p", "--port" }, description = "The port the tinyIFTT server should run on.")
    public int configPort = 1337;

    public static void main(String[] args) {
        ServerRunner serverRunner = SingleCommand.singleCommand(ServerRunner.class).parse(args);
        if(serverRunner.helpOption.showHelpIfRequested()) return;
        serverRunner.startService();
    }

    public void startService() {
        try {
            TinyIFTT tinyIFTT = new TinyIFTT(configFileLocation);
            tinyIFTT.start(configPort);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
