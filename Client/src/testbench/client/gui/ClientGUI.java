package testbench.client.gui;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

/**
 * Created by Sven Riedel on 26.11.2015.
 */
public class ClientGUI extends JFrame {
    private JButton verbindenButton;
    private JTextField IPAdresseDesServersTextField;
    private JButton xButton;
    private JButton settingsButton;
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JPanel mainPanel;

    /* ######################################## */
    private ClientSteuer steuer = new ClientSteuer();
    JFrame frame = new JFrame();


    /* ######################################## */

    public ClientGUI() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        IPAdresseDesServersTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                IPAdresseDesServersTextField.setText("");
            }
        });
        verbindenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(IPAdresseDesServersTextField.getText().equals("")) JOptionPane.showMessageDialog(frame, "Bitte das IP-Adresse Feld ausfüllen!");
                else if(IPAdresseDesServersTextField.getText() == null) JOptionPane.showMessageDialog(frame, "Bitte das IP-Adresse Feld ausfüllen!");
                else {
                    String text = IPAdresseDesServersTextField.getText();
                    if(!text.startsWith("http://")) text = "http://"+text;
                    if(!text.endsWith("/")) text = text+"/";

                    boolean isConnected = steuer.connect(text);
                    if (!isConnected) JOptionPane.showMessageDialog(frame, "IP-Adresse ungültig!");
                }
            }
        });
    }
}
