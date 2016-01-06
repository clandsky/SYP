package testbench.server.steuerungsklassen;

import sun.org.mozilla.javascript.internal.Context;


/**
 * Created by Chrizzle Manizzle on 22.12.2015.
 */

public class ServerConfiguration {
    private String contentPath;
    private int port;
    private boolean debug;

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
