package testbench.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by svenm on 14.12.2015.
 */
public class DetailMassendaten extends JFrame{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JList settingsList;
    private JPanel cardPanel;
    private JPanel allgemeinPanel;
    private JTextField allgemeinIDTextField;
    private JPanel buttonsPanel;
    private JButton abbrechenButton;
    private JButton OKButton;
    private JPanel formPanel;
    private JPanel frequencyPanel;
    private JTextField frequenzTextField;
    private JTextField frequencyPhaseTextField;
    private JTextField frequencyAmplitudeTextField;
    private JPanel dataGraphPanel;

    private CardLayout cl = (CardLayout) cardPanel.getLayout();

    public DetailMassendaten() {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        initGuiProperties(400,500);
        cl.show(cardPanel, "allgemeinCard");
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setTitle("Protobuf Testbench Client Settings");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
