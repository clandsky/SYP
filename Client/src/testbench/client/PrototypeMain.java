package testbench.client;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.client.gui.ClientGUI;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class PrototypeMain {
    private static boolean startGUI = false;

    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        boolean abbruch = false;
        String input;
        ClientSteuer cSteuer = new ClientSteuer();
        Printer printer = new Printer();

        if(PrototypeMain.startGUI) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ClientGUI clientGUI = new ClientGUI();
            clientGUI.setLocationRelativeTo(null);
            clientGUI.setResizable(false);
            Dimension d = new Dimension();
            d.setSize(700,500);
            clientGUI.setMinimumSize(d);
            clientGUI.setVisible(true);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        cSteuer.connect("http://localhost:8000/");

        printer.printlnWithDate("||||- Protobuf Testbench Client -||||\n");

        do{
            printer.printlnWithDate("################ Bitte waehlen: ################");
            printer.printlnWithDate("1) GET /massendaten/1");
            printer.printlnWithDate("2) POST /massendaten");
            printer.printlnWithDate("3) Generiere Zufalls-Massendaten");
            printer.printlnWithDate("4) GET /massendaten");
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

            switch(input) {
                case "0":
                    abbruch = true;
                    break;

                case "1":
                    try{
                        MassendatenGrenz response = cSteuer.empfangeMassendaten(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        printer.printlnWithDate("\n!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "2":
                    printer.printlnWithDate("\nPOST an Server...");
                    cSteuer.sendeMassendaten(1);
                    break;

                case "3":
                    cSteuer.generiereZufallsMassendaten(800000);
                    break;

                case "4":
                    cSteuer.empfangeMassenInfoGrenzList();
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
