package com.pddstudio.tinyifttt.connection;

/**
 * This Class was created by Patrick J
 * on 30.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ConnectionInfo {

    private static ConnectionInfo mConnectionInfo;

    private String mRemoteHost;
    private int mRemotePort;

    private ConnectionInfo(String remoteHost, int remotePort) {
        this.mRemoteHost = remoteHost;
        this.mRemotePort = remotePort;
    }

    public static ConnectionInfo setConnectionData(String remoteHost, int remotePort) {
        mConnectionInfo = new ConnectionInfo(remoteHost, remotePort);
        return mConnectionInfo;
    }

    public static ConnectionInfo getConnectionInfo() {
        return mConnectionInfo;
    }

}
