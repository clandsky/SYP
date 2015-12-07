package testbench.client;

import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.Werkzeug;
import testbench.client.steuerungsklassen.ClientSteuer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class PrototypeMain {
    public static void main(String args[]) {
        long messStart;
        long messEnde;
        boolean abbruch = false;
        String input = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        new ClientSteuer().connect("http://localhost:8000/");

        System.out.println();
        System.out.println("||||- Protobuf Testbench Client -||||\n");

        do{
            System.out.println("\nBitte waehlen:");
            System.out.println("1: GET-Request an den Server (Daten downloaden)");
            System.out.println("2: POST-Request an den Server (Daten uploaden)");
            System.out.println("0: Programm beenden\n");
            System.out.print("Eingabe: ");

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
                        MassendatenGrenz response = new ClientSteuer().empfangeMassendaten(0);
                        messEnde = System.currentTimeMillis();
                        System.out.println("Benötigte Empfangszeit: "+String.valueOf(messEnde-messStart)+" ms");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("\n!!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "2":
                    long uebertragZeit;
                    int doubleAnzahl = 40000000;
                    int newProgress, oldProgress=-1;
                    System.out.println("\nPOST an Server...");
                    Werkzeug w = new Werkzeug();

                    if(PrototypDaten.mList.isEmpty()) {
                        Massendaten.Builder builder = Massendaten.newBuilder();
                        System.out.println("\nTest-Massendaten ("+doubleAnzahl+" Double-Werte) werden generiert...");
                        for (int i=0; i < doubleAnzahl; i++) {
                            builder.addValue(Massendaten.Werte.newBuilder().setNumber(Math.random()));
                            newProgress = (i+1)*100/doubleAnzahl;
                            if(newProgress != oldProgress) w.printProgressBar(newProgress);
                            oldProgress = newProgress;
                        }
                        PrototypDaten.mList.add( builder.build() );
                    }

                    messStart = System.currentTimeMillis();
                    new ClientSteuer().sendeMassendaten(0);
                    messEnde = System.currentTimeMillis();
                    uebertragZeit = messEnde-messStart;

                    System.out.println("\nBenötigte Übertragungszeit: "+String.valueOf(uebertragZeit)+" ms");
                    break;

                default:
                    System.out.println("\nFalsche Eingabe!\n");
            }

            System.out.println();

        } while(!abbruch);
    }
}
