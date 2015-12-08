package testbench.client;

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
        long messStart;
        long messEnde;
        boolean abbruch = false;
        String input = null;
        ClientSteuer cSteuer = new ClientSteuer();

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

        System.out.println();
        System.out.println("||||- Protobuf Testbench Client -||||\n");

        do{
            System.out.println("\nBitte waehlen:");
            System.out.println("1: GET-Request an den Server (Daten downloaden)");
            System.out.println("2: POST-Request an den Server (Daten uploaden)");
            System.out.println("3: Generiere Zufalls-Massendaten");
            System.out.println("5: Datenverwaltung starten");
            System.out.println("0: Programm beenden\n");
            System.out.print("Eingabe: ");

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
                        messStart = System.currentTimeMillis();
                        MassendatenGrenz response = cSteuer.empfangeMassendaten(1);
                        messEnde = System.currentTimeMillis();
                        System.out.println("Benötigte Empfangszeit: "+String.valueOf(messEnde-messStart)+" ms");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("\n!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "2":
                    long uebertragZeit;
                    System.out.println("\nPOST an Server...");

                    messStart = System.currentTimeMillis();
                    cSteuer.sendeMassendaten(1);
                    messEnde = System.currentTimeMillis();
                    uebertragZeit = messEnde-messStart;

                    System.out.println("\nBenötigte Übertragungszeit: "+String.valueOf(uebertragZeit)+" ms");
                    break;

                case "3":
                    cSteuer.generiereZufallsMassendaten(800000);
                    break;

                case "5":
                    cSteuer.starteDatenverwaltung();
                    break;

                default:
                    System.out.println("\nFalsche Eingabe!\n");
            }

            System.out.println();

        } while(!abbruch);
    }
}
