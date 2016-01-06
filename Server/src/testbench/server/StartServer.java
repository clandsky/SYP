package testbench.server;

import testbench.server.gui.ServerGUI;
import testbench.server.steuerungsklassen.SessionHandler;

import javax.swing.*;
import java.net.URISyntaxException;

/**
 * Created by Huskey on 08.12.2015.
 */

/**
 * Erstellt die Server GUI und erzeugt den SessionHandler.
 */
public class StartServer {
    /** Server GUI */
    ServerGUI serverGUI;

    /**
     * Standardkonstruktor. StartRoutine der Server GUI.
     *
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws URISyntaxException
     */
    public StartServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.serverGUI = new ServerGUI(new SessionHandler());
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);
    }

    /**
     * Gibt die GUI zurück
     * @return ServerGUI
     */
    public JFrame getGUI(){
        return serverGUI;
    }

    /**
     * Hauptroutine des Programms. Falls der Server nicht als Objekt über die Execute.class aufgerufen wird, kann dieser auch statisch über die main () aufgerufen werden.
     * @param args
     * @throws Exception
     */
    public static void main (String args[]) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ServerGUI serverGUI = new ServerGUI(new SessionHandler());
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);
    }
}
