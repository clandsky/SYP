package testbench.client;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.Printer;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.gui.ClientGUI;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class PrototypeMain {
    private static boolean startGUI = true;

    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        boolean abbruch = false;
        String input;
        ClientSteuer cSteuer = new ClientSteuer();
        Printer printer = new Printer();
        final String ip = "http://localhost:8000/";

        if(PrototypeMain.startGUI) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ClientGUI clientGUI = new ClientGUI();
            clientGUI.setLocationRelativeTo(null);
            clientGUI.setResizable(false);
            clientGUI.setTitle("Protobuf Testbench");
            Dimension d = new Dimension();
            d.setSize(800,500);
            clientGUI.setMinimumSize(d);
            clientGUI.setVisible(true);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        printer.printlnWithDate("||||- Protobuf Testbench Client -||||\n");

        do{
            printer.printlnWithDate("################ Bitte waehlen: ################");
            printer.printlnWithDate("1) GET /massendaten/1");
            printer.printlnWithDate("2) POST /massendaten");
            printer.printlnWithDate("3) GET /massendaten");
            printer.printlnWithDate("4) Generiere Zufalls-Massendaten");
            printer.printlnWithDate("6) Datenverwaltung starten");
            printer.printWithDate("0) Programm beenden                    Eingabe:");

            input = ""; //wegen null-pointer warnung intellij
            try {
                do{
                    input = br.readLine();
                } while(input == null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println();

            switch(input) {
                case "0":
                    abbruch = true;
                    break;

                case "1":
                    cSteuer.connect(ip);
                    try {
                        MassendatenGrenz response = cSteuer.empfangeMassendaten(1);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }

                    break;

                case "2":
                    printer.printlnWithDate("POST an Server...");
                    try {
                        cSteuer.connect(ip);
                        cSteuer.sendeMassendaten(1);
                    } catch (Exception e) {
                        System.out.println();
                        printer.printlnWithDate("!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "3":
                    try {
                        cSteuer.connect(ip);
                        List<MassenInfoGrenz> mig = cSteuer.empfangeMassenInfoGrenzList();
                        for(MassenInfoGrenz m : mig)
                            printer.printlnWithDate(String.valueOf(m.getDef().getAbtastrate()));
                    } catch (Exception e) {
                        System.out.println();
                        printer.printlnWithDate("!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "4":
                    cSteuer.generiereZufallsMassendaten(800000);
                    break;

                case "5":
                    break;

                case "6":
                    cSteuer.starteDatenverwaltung();
                    break;

                default:
                    printer.printlnWithDate("\nFalsche Eingabe!\n");
            }

            System.out.println();

        } while(!abbruch);
    }
}
