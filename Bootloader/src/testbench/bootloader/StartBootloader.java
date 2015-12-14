package testbench.bootloader;

import testbench.server.steuerungsklassen.IActivateComponentImpl;

import javax.swing.*;
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

    public StartBootloader () throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Bootloader - Testbench Protobuf");
        setSize(400,200);
        setVisible(true);
        this.setContentPane(panel1);

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
                testbench.client.steuerungsklassen.IActivateComponentImpl client=new testbench.client.steuerungsklassen.IActivateComponentImpl();
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


}
