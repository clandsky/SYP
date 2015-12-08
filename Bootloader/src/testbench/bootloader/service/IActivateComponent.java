package testbench.bootloader.service;

import javax.swing.*;

/**
 * Created by CGrings on 07.12.2015.
 */
public interface IActivateComponent
{
    boolean startComponent() throws Exception;
    JFrame getComponentGui();
    String getCompType();
}
