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

    @Override
    public boolean startComponent() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.setLocationRelativeTo(null);
        clientGUI.setResizable(false);
        clientGUI.setTitle("Protobuf Testbench");
        clientGUI.setBackground(new Color(232,232,232));
        Dimension d = new Dimension();
        d.setSize(800,500);
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
