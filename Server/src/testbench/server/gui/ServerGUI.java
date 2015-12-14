package testbench.server.gui;

import testbench.bootloader.Printer;
import testbench.server.SessionHandler;

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
    private JButton startServerButton;
    private JButton konfigurierenButton;
    private boolean started=false;
    private SessionHandler sh;
    private JFrame frame;

    public ServerGUI () throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        final KonsoleOutputStream tAOS= new KonsoleOutputStream(consoleOut,34);
        PrintStream con=new PrintStream(tAOS);
        System.setOut(con);
        System.setErr(con);
        sh=new SessionHandler();
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        frame=this;
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tAOS.clear();
                Printer.printWelcome();

            }
        });
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!started) {
                    if (!sh.isRunning()) {
                        sh.startServer();
                        startServerButton.setText("Stop Server");
                        started=true;
                    }
                }else{
                    if (sh.isRunning()){
                        sh.stopServer();
                        startServerButton.setText("Start Server");
                        started = false;
                    }
                }
            }
        });
        konfigurierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port=0;
                boolean ok=false;

                do {
                    String s = (String)JOptionPane.showInputDialog(frame,
                            "Bitten geben Sie den gewünschten Port ein",
                            "Port-Konfiguration",
                            JOptionPane.PLAIN_MESSAGE,
                            null,null,null);
                    if (s==null) return;
                    try {
                        port = Integer.parseInt(s);
                    }catch (Exception exc) {
                        Printer.println("Bitte geben Sie eine Zahl ein...");
                    }

                    if (port > 0) {
                        sh.setPort(port);
                        ok=true;
                        if (sh.isRunning())Printer.println("Port wurde auf "+port+" gesetzt. \nZum Übernehmen der Änderung muss der Server neugestartet werden");
                        else Printer.println("Port wurde auf "+port+" gesetzt. \nSie können den Server jetzt mit der neuen Konfiguration starten");
                        return;
                    }

                }while (ok==false);
            }
        });
    }


}
