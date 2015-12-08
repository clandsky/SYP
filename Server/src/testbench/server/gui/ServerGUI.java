package testbench.server.gui;

import testbench.bootloader.Printer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

/**
 * Created by Huskey on 08.12.2015.
 */
public class ServerGUI extends JFrame{
    private JTextArea consoleOut;
    private JPanel panel;
    private JButton clearButton;
    private Printer p;

    public ServerGUI () throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        final TextAreaOutputStream tAOS= new TextAreaOutputStream(consoleOut,50);
        PrintStream con=new PrintStream(tAOS);
        System.setOut(con);
        System.setErr(con);
        setContentPane(panel);
        p=new Printer();
        // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tAOS.clear();
                p.printWelcome();

            }
        });
    }


}
