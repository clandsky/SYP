package testbench.client;


import com.google.protobuf.CodedInputStream;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    public static void main(String args[]) {
        Client client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        WebTarget target = client.target("http://localhost:80/");
        ClientSteuer clientSteuer = new ClientSteuer();

        long messStart;
        long messEnde;
        boolean abbruch = false;
        String input = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println();
        System.out.println("||||-Protobuf Testbench-||||\n");

        do{
            System.out.println("Bitte waehlen:");
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
                        Massendaten response = target.path( "testlauf" ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Massendaten.class);
                        messEnde = System.currentTimeMillis();

                        int serializedSizeGet = response.getSerializedSize();
                        double groeßeMB = Math.round(serializedSizeGet/1000000);
                        System.out.println("Groesse in Byte: "+serializedSizeGet);
                        System.out.println("Groesse in Megabyte: "+groeßeMB);

                        System.out.println("Benötigte Zeit: "+String.valueOf(messEnde-messStart)+" ms");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("\n !!! Verbindung zum Server fehlgeschlagen !!!");
                    }
                    break;

                case "2":
                    long[] messArray = clientSteuer.switchCase2();
                    if(messArray != null) {
                        System.out.println("Benötigte Serialisierungszeit: "+messArray[0]+" ms");
                        System.out.println("Benötigte Zeit: "+messArray[1]+" ms");
                    } else {
                        System.out.println("Fehler in Case 2!");
                    }

                    break;

                default:
                    System.out.println();
                    System.out.println("Falsche Eingabe!");
                    System.out.println();
            }

            System.out.println();

        } while(!abbruch);



    }
}
