package testbench.server.steuerungsklassen;

import testbench.bootloader.service.IActivateComponent;
import testbench.server.StartServer;

import javax.swing.*;
import java.net.URISyntaxException;

/**
 * Implementierung der IActivateComponent. Dient zum Starten der Komponente Server.
 */
public class IActivateComponentImpl implements IActivateComponent{
    /** Objekt der Klasse StartServer, welches das Starten der GUI und des SessionHandlers erledigt */
    StartServer s;

    /**
     * Startet die Komponente
     * @return true
     * @throws Exception
     */
    @Override
    public boolean startComponent() throws Exception {
        s = new StartServer();
        return true;
    }

    /**
     * Gibt die GUI des Servers zurück
     * @return Server-GUI
     */
    @Override
    public JFrame getComponentGui() {
        return s.getGUI();
    }

    /**
     * Gibt den CompType zurück.
     * @return "Server"
     */
    @Override
    public String getCompType() {
        return "Server";
    }
}
