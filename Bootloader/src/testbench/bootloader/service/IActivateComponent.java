package testbench.bootloader.service;

import javax.swing.*;

/**
 * @author CGrings
 * @version 1.0
 */
public interface IActivateComponent
{
    /**
     * startComponent
     *
     * Startet eine Komponente des Systems (Client/Server/Datenveraltung)
     *
     * @return True wenn der start erfolgreich war, anderenfalls false
     * @throws Exception
     */
    boolean startComponent() throws Exception;

    /**
     * getComponentGui
     *
     * Gibt das Frame der GUI der Komponente zurück
     *
     * @return Frame (GUI) der Komponente
     */
    JFrame getComponentGui();

    /**
     * getCompType
     *
     * Gibt den Namen der Komponente zurück
     *
     * @return Komponentenname
     */
    String getCompType();
}
