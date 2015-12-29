import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import testbench.server.steuerungsklassen.IActivateComponentImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chrizzle Manizzle on 10.12.2015
 */
public class StartBootloader extends JFrame {
    private JButton serverButton;
    private JPanel panel1;
    private JButton clientButton;
    private JButton datenverwaltungButton;

    public StartBootloader() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Bootloader - Testbench Protobuf");
        setSize(400, 200);
        this.setContentPane(panel1);
        setVisible(true);

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IActivateComponentImpl server = new IActivateComponentImpl();
                try {
                    setVisible(false);
                    server.startComponent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testbench.client.steuerungsklassen.IActivateComponentImpl client = new testbench.client.steuerungsklassen.IActivateComponentImpl();
                try {
                    setVisible(false);
                    client.startComponent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        datenverwaltungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl generator = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
                try {
                    setVisible(false);
                    generator.startComponent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
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
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        clientButton = new JButton();
        clientButton.setText("Client");
        panel1.add(clientButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverButton = new JButton();
        serverButton.setText("Server");
        panel1.add(serverButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        datenverwaltungButton = new JButton();
        datenverwaltungButton.setText("Datenverwaltung");
        panel1.add(datenverwaltungButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}