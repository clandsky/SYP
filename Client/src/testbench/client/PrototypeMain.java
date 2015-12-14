package testbench.client;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.Printer;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.provider.ByteMessage;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.gui.ClientGUI;
import testbench.client.service.ClientConfig;
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

    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        boolean abbruch = false;
        String input;
        ClientSteuer cSteuer = new ClientSteuer();
        final String ip = "http://localhost:"+ClientConfig.getExemplar().getPort()+"/";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Printer.println("||||- Protobuf Testbench Client -||||\n");

        do{
            Printer.println("################ Bitte waehlen: ################");
            Printer.println("1) GET /massendaten/1");
            Printer.println("2) POST /massendaten");
            Printer.println("3) GET /massendaten");
            Printer.println("4) Generiere Zufalls-Massendaten");
            Printer.println("6) Datenverwaltung starten");
            Printer.print("0) Programm beenden                    Eingabe:");

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
                    Printer.println("POST an Server...");
                    try {
                        cSteuer.connect(ip);
                        cSteuer.sendeMassendaten(1);
                    } catch (Exception e) {
                        System.out.println();
                        Printer.println("!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "3":
                    try {
                        cSteuer.connect(ip);
                        List<MassenInfoGrenz> mig = cSteuer.getMassenInfoGrenzList(true);
                        for(MassenInfoGrenz m : mig)
                            Printer.println(String.valueOf(m.getDef().getAbtastrate()));
                    } catch (Exception e) {
                        System.out.println();
                        Printer.println("!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "4":
                    cSteuer.generiereZufallsMassendaten(70000000);
                    break;

                case "5":
                    break;

                case "6":
                    cSteuer.starteDatenverwaltung();
                    break;

                default:
                    Printer.println("\nFalsche Eingabe!\n");
            }

            System.out.println();

        } while(!abbruch);
    }
}
