package testbench.server.steuerungsklassen;

import sun.org.mozilla.javascript.internal.Context;


/**
 * Konfigurationsinformationen für den Server.
 */

public class ServerConfiguration {
    /**
     * Pfad zur Datenbank.
     * @deprecated
     */
    private String contentPath;
    /** Port des Servers */
    private int port;

    /**
     * Debug-Mode
     * @deprecated
     */
    private boolean debug;

    /**
     * Gibt den Pfad zur Datenbank (lokal) zurück.
     * @return Pfad zur Datenbank (lokal)
     * @deprecated
     */

    public String getContentPath() {
        return contentPath;
    }

    /**
     * Setzt den Pfad zur Datenbank (lokal).
     * @param contentPath Pfad zur Datenbank (lokal)
     * @deprecated
     */
    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gibt zurück, ob der Server im Debugmodus läuft
     * @return Debug-status
     * @deprecated
     */
    public boolean isDebug() {
        return debug;
    }


    /**
     * Setze Debug-Mode
     * @param debug Debugstatus
     * @deprecated
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
