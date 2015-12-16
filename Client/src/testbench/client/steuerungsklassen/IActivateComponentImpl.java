package testbench.client.steuerungsklassen;

import testbench.bootloader.service.IActivateComponent;
import testbench.client.gui.ClientGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sven Riedel on 26.11.2015
 */
public class IActivateComponentImpl implements IActivateComponent {
    private ClientGUI clientGui;
    private int guiSizeX = 800;
    private int guiSizeY = 500;

    @Override
    public boolean startComponent() throws Exception {
        ClientGUI clientGUI = new ClientGUI(guiSizeX,guiSizeY);
        return true;
    }

    @Override
    public JFrame getComponentGui() {
        return clientGui;
    }

    @Override
    public String getCompType() {
        return "Client";
    }
}
