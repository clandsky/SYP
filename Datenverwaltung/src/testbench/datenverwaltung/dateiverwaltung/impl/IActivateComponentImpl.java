package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.service.IActivateComponent;
import testbench.datenverwaltung.dateiverwaltung.gui.GeneratorGUI;

import javax.swing.*;

/**
 * Created by CGrings on 07.12.2015.
 */
public class IActivateComponentImpl implements IActivateComponent
{
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
