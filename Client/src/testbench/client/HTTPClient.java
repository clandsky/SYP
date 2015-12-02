package testbench.client;


import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    public static void main(String args[]) {
        Client client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        WebTarget target = client.target("http://localhost:80/");

        long messStart;
        long messEnde;
        boolean abbruch = false;
        String input = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("||||-Protobuf Testbench-||||");
        System.out.println();

        do{
            System.out.println("Bitte waehlen:");
            System.out.println("1: GET-Request an den Server senden (Daten downloaden)");
            System.out.println("2: POST-Request an den Server senden (Daten uploaden)");
            System.out.println("0: Programm beenden");

            try {
                do{
                    input = br.readLine();
                } while(input == null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch(input) {
                case "1":
                    messStart = System.currentTimeMillis();
                    Massendaten response = target.path( "testlauf" ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Massendaten.class);
                    messEnde = System.currentTimeMillis();

                    int serializedSize = response.getSerializedSize();
                    System.out.println("Groesse: "+serializedSize+" Byte");
                    System.out.println("Groesse: "+serializedSize/1000000+" MB");

                    System.out.println(messEnde-messStart+" ms");

                    break;

                case "2":
                    Massendaten.Builder builder = Massendaten.newBuilder();

                    for(int i=0 ; i<1000000 ; i++) {
                        builder.addValue(Massendaten.Werte.newBuilder().setNumber(Math.random()));
                    }

                    Massendaten massendaten = builder.build();

                    messStart = System.currentTimeMillis();
                    target.path("testlauf").request().post(Entity.entity(massendaten, MediaTypeExt.APPLICATION_PROTOBUF), Massendaten.class);
                    messEnde = System.currentTimeMillis();

                    int serializedSize2 = massendaten.getSerializedSize();
                    System.out.println("Groesse: "+serializedSize2+" Byte");
                    System.out.println("Groesse: "+serializedSize2/1000000+" MB");

                    System.out.println(messEnde-messStart+" ms");
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
