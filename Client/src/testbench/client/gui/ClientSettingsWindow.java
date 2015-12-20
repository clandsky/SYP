package testbench.client.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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
    private JButton applyButton;
    private JPanel portPanel;
    private JTextField changePortTextField;
    private JButton debugModeButton;
    private JPanel debugModePanel;
    private JLabel debugModeLabel;

    private JFrame frame = new JFrame();
    private CardLayout cl;
    private ClientConfig clientConfig = ClientConfig.getExemplar();
    private boolean isConnectWindow;

    private final String ONLY_NUMBERS_ERROR = "Bitte nur numerische Werte als Port eintragen!";
    private final String RESTART_PROGRAM_INFO = "Um die Änderungen zu übernehmen,\nbitte das Programm neu starten";

    public ClientSettingsWindow(boolean isConnectWindow) {
        cl = (CardLayout) cardPanel.getLayout();
        this.isConnectWindow = isConnectWindow;
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        initGuiProperties(400, 500);
        cl.show(cardPanel, "portCard");

        initSettings();
        initListener();
    }

    /**
     * Diese Methode holt sich die aktuellen Einstellungen vom ClientConfig-Singleton
     * und schreibt diese in die entsprechenden JLabels/JButtons.
     */
    private void initSettings() {
        changePortTextField.setText(clientConfig.getPort());
        debugModeLabel.setText(String.valueOf(clientConfig.getDebugMode()));
        if (clientConfig.getDebugMode()) debugModeButton.setText("Set 'False'");
        else debugModeButton.setText("Set 'True'");
    }

    /**
     * Hier werden einige allgemeine GUI Einstellungen festgelegt.
     *
     * @param guiSizeX Breite des GUI-Fensters.
     * @param guiSizeY Hoehe des GUI-Fensters.
     */
    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setTitle("Protobuf Testbench Client Settings");
        Dimension d = new Dimension();
        d.setSize(guiSizeX, guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Diese Methode oeffnet die gewünschte Karte aus dem CardLayout.
     *
     * @param row Nummer des geklickten Elementes in der Einstellungen-Liste.
     */
    private void listSelectSwitch(int row) {
        switch (row) {
            case 0:
                cl.show(cardPanel, "portCard");
                break;

            case 1:
                cl.show(cardPanel, "debugModeCard");
        }
    }

    /**
     * Hier werden alle ActionListener sowie MouseListener der GUI erstellt.
     */
    private void initListener() {
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = changePortTextField.getText();
                if (isNumeric(port)) {
                    clientConfig.setPort(port);
                    clientConfig.setDebugMode(Boolean.valueOf(debugModeLabel.getText()));
                    clientConfig.writeCurrentConfig();
                    dispose();
                    if (!isConnectWindow) JOptionPane.showMessageDialog(frame, RESTART_PROGRAM_INFO);
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
                listSelectSwitch(row);
            }
        });
        debugModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debugModeLabel.setText(String.valueOf(!Boolean.valueOf(debugModeLabel.getText())));
                if (Boolean.valueOf(debugModeLabel.getText())) debugModeButton.setText("Set 'False'");
                else debugModeButton.setText("Set 'True'");
            }
        });
    }

    /**
     * Diese Methode prueft, ob der gegebene String eine valide Nummer enthaelt.
     *
     * @param input String, der ueberprueft wird.
     * @return Wenn der String eine valide Nummer enthaelt: True. Sonst False.
     */
    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        formPanel.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(400, 500), 0, false));
        final JSplitPane splitPane1 = new JSplitPane();
        mainPanel.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), new Dimension(400, 500), 0, false));
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(leftPanel);
        settingsList = new JList();
        settingsList.setEnabled(true);
        settingsList.setFont(new Font(settingsList.getFont().getName(), settingsList.getFont().getStyle(), 14));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("- Port");
        defaultListModel1.addElement("- Debug Modus");
        settingsList.setModel(defaultListModel1);
        settingsList.setSelectionMode(0);
        leftPanel.add(settingsList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(5, 5));
        splitPane1.setRightComponent(cardPanel);
        portPanel = new JPanel();
        portPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        cardPanel.add(portPanel, "portCard");
        final Spacer spacer1 = new Spacer();
        portPanel.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(273, 14), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        portPanel.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(273, 28), null, 0, false));
        changePortTextField = new JTextField();
        panel1.add(changePortTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 14));
        label1.setText("Port: ");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        portPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(273, 64), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font(label2.getFont().getName(), label2.getFont().getStyle(), 14));
        label2.setText("<html><body>Mit dieser Einstellung lässt sich<br>der Port ändern, über den zum<br>Server verbunden wird.</body></html>");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        portPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 15), new Dimension(273, 15), new Dimension(-1, 15), 0, false));
        debugModePanel = new JPanel();
        debugModePanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        cardPanel.add(debugModePanel, "debugModeCard");
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        debugModePanel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setFont(new Font(label3.getFont().getName(), label3.getFont().getStyle(), 14));
        label3.setText("Debug Modus: ");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        debugModeLabel = new JLabel();
        debugModeLabel.setFont(new Font(debugModeLabel.getFont().getName(), debugModeLabel.getFont().getStyle(), 14));
        debugModeLabel.setText("Label");
        panel3.add(debugModeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        debugModePanel.add(spacer3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        debugModeButton = new JButton();
        debugModeButton.setFont(new Font(debugModeButton.getFont().getName(), debugModeButton.getFont().getStyle(), 14));
        debugModeButton.setText("DEBUG");
        debugModePanel.add(debugModeButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        debugModePanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font(label4.getFont().getName(), label4.getFont().getStyle(), 14));
        label4.setText("<html><body>Wenn der Debug-Modus aktiviert<br>wird, werden genauere<br> Fehlerausgaben auf die Konsole<br>geschrieben.\nUm die Konsole<br>auszulesen, das Programm bitte<br> über eine Kommandozeile mit<br>\"java -jar programm\" starten.</body></html>");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        debugModePanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 15), new Dimension(-1, 15), new Dimension(-1, 15), 0, false));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(buttonsPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        abbrechenButton = new JButton();
        abbrechenButton.setText("Abbrechen");
        buttonsPanel.add(abbrechenButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        applyButton = new JButton();
        applyButton.setText("Übernehmen");
        buttonsPanel.add(applyButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return formPanel;
    }
}
