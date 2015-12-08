package testbench.server.gui;

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

    public ServerGUI () throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        PrintStream con=new PrintStream(new TextAreaOutputStream(consoleOut,50));
        System.setOut(con);
        System.setErr(con);
        setContentPane(panel);
        // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleOut.setText ("");
            }
        });
    }


}
