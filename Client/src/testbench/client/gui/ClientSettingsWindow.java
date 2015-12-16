package testbench.client.gui;

import testbench.client.service.ClientConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by svenm on 10.12.2015.
 */
public class ClientSettingsWindow extends JFrame {
    private JPanel formPanel;
    private JList settingsList;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel cardPanel;
    private JPanel buttonsPanel;
    private JButton abbrechenButton;
    private JButton OKButton;
    private JPanel portPanel;
    private JTextField changePortTextField;
    private JButton debugModeButton;
    private JPanel debugModePanel;
    private JLabel debugModeLabel;

    private JFrame frame = new JFrame();
    private CardLayout cl = (CardLayout) cardPanel.getLayout();
    private ClientConfig clientConfig = ClientConfig.getExemplar();
    private boolean isConnectWindow;

    private final String ONLY_NUMBERS_ERROR = "Bitte nur numerische Werte als Port eintragen!";
    private final String RESTART_PROGRAM_INFO = "Um die Änderungen zu übernehmen,\nbitte das Programm neu starten";

    public ClientSettingsWindow(boolean isConnectWindow) {
        this.isConnectWindow = isConnectWindow;
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        initGuiProperties(400,500);
        cl.show(cardPanel, "portCard");

        initSettings();
        initListener();



    }

    private void initSettings() {
        changePortTextField.setText(clientConfig.getPort());
        debugModeLabel.setText(String.valueOf(clientConfig.getDebugMode()));
        debugModeButton.setText("Setze auf "+!clientConfig.getDebugMode());
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
        settingsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = settingsList.getSelectedIndex();
                switch(row) {
                    case 0:
                        cl.show(cardPanel,"portCard");
                        break;

                    case 1:
                        cl.show(cardPanel,"debugModeCard");
                }
            }
        });
        debugModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // clientConfig.setDebugMode(!clientConfig.getDebugMode());
           //     if(Boolean.valueOf(debugModeLabel.gett))
           //     debugModeButton.setText(String.valueOf(!clientConfig.getDebugMode()));
           //     debugModeLabel.setText(String.valueOf(clientConfig.getDebugMode()));
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
