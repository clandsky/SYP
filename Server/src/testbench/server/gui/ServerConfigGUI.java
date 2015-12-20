package testbench.server.gui;

import testbench.server.SessionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Huskey on 14.12.2015.
 */
public class ServerConfigGUI extends JFrame {
    private JCheckBox verwendeManuellenPortCheckBox;
    private JTextField textField1;
    private JPanel panel;
    private SessionHandler sh;

    public ServerConfigGUI(SessionHandler sh) {
        this.sh = sh;
        setContentPane(panel);
        pack();


    }

    public void setSh(SessionHandler sh) {
        this.sh = sh;
    }

}

