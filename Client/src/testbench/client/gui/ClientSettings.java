package testbench.client.gui;

import testbench.client.service.ClientConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by svenm on 10.12.2015.
 */
public class ClientSettings extends JFrame {
    private JPanel formPanel;
    private JList settingsList;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel Panel;
    private JPanel buttonsPanel;
    private JButton abbrechenButton;
    private JButton OKButton;
    private JPanel portPanel;
    private JTextField changePortTextField;

    private JFrame frame = new JFrame();
    private CardLayout cl = (CardLayout) Panel.getLayout();
    private ClientConfig clientConfig = ClientConfig.getExemplar();
    private boolean isConnectWindow;

    private final String ONLY_NUMBERS_ERROR = "Bitte nur numerische Werte als Port eintragen!";
    private final String RESTART_PROGRAM_INFO = "Um die Änderungen zu übernehmen,\nbitte das Programm neu starten";

    public ClientSettings(boolean isConnectWindow) {
        this.isConnectWindow = isConnectWindow;
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        initGuiProperties(400,500);
        cl.show(Panel, "portCard");

        initSettings();
        initListener();

    }

    private void initSettings() {
        changePortTextField.setText(clientConfig.getPort());
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

    private void initListener() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = changePortTextField.getText();
                if(isNumeric(port)) {
                    clientConfig.setPort(port);
                    clientConfig.writeCurrentConfig();
                    dispose();
                    if(!isConnectWindow) JOptionPane.showMessageDialog(frame, RESTART_PROGRAM_INFO);
                } else JOptionPane.showMessageDialog(frame, ONLY_NUMBERS_ERROR);
            }
        });
        abbrechenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
