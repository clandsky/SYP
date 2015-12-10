package testbench.client.steuerungsklassen;

import testbench.bootloader.service.IActivateComponent;
import testbench.client.gui.ClientGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sven Riedel on 26.11.2015
 */
public class IActivateComponentImpl implements IActivateComponent {
    ClientGUI clientGui;
    private int guiSizeX = 800;
    private int guiSizeY = 500;

    @Override
    public boolean startComponent() throws Exception {
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.setLocationRelativeTo(null);
        clientGUI.setResizable(false);
        clientGUI.setTitle("Protobuf Testbench Client");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        clientGUI.setMinimumSize(d);
        clientGUI.setVisible(true);
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
