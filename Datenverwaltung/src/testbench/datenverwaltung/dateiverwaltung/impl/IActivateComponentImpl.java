package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.service.IActivateComponent;
import testbench.datenverwaltung.dateiverwaltung.gui.GeneratorGUI;

import javax.swing.*;

/**
 * @author CGrings
 * @version 1.0
 * @see testbench.bootloader.service.IActivateComponent
 */
public class IActivateComponentImpl implements IActivateComponent
{
    /**
     * Genderator GUI die durch die Aktivierung der Komponente erzeugt wird
     */
    static GeneratorGUI generatorGUI = null;

    @Override
    public boolean startComponent()
    {
        generatorGUI = new GeneratorGUI();
        return true;
    }

    @Override
    public JFrame getComponentGui()
    {
        return generatorGUI;
    }

    @Override
    public String getCompType()
    {
        return "DATENVERWALUNG";
    }
}
