package testbench.client.steuerungsklassen;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.client.SendPostThread;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015.
 */


public class ClientSteuer {
    Client client;
    WebTarget target;

    public ClientSteuer() {
        client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        target = client.target("http://localhost:80/");
    }

    public long[] switchCase2() {
        ArrayList<SendPostThread> sendRequestThreadArrayList = new ArrayList<>();
        SendPostThread srt;
        long[] messArray = new long[2];
        long messStart;
        long messEnde;
        int processors = Runtime.getRuntime().availableProcessors();
        int threads = processors-1;
        int threadCycle;

        Massendaten.Builder builder = Massendaten.newBuilder();
        for (int i = 0; i < 10000000; i++) {
            builder.addValue(Massendaten.Werte.newBuilder().setNumber(Math.random()));
        }
        messStart = System.currentTimeMillis();
        Massendaten massendaten = builder.build();
        messEnde = System.currentTimeMillis();
        messArray[0] = messEnde - messStart;

        Massendaten[] massendatenArray;
        massendatenArray = new ClientSteuer().splitMassendaten1MB(massendaten.getValueList());

        if(massendatenArray.length%threads == 0) threadCycle = massendatenArray.length/threads;
        else threadCycle = massendatenArray.length/(threads)-1;
        System.out.println("threadCycle: "+threadCycle);

        try {
            messStart = System.currentTimeMillis();

            for (int i = 0; i < threads; i++) {
                int endIndex = i*threadCycle+threadCycle;

                if(i == threads-1) {
                    endIndex = massendatenArray.length-1;
                }

                System.out.println("endIndex: "+endIndex);
                srt = new SendPostThread(target.getUriBuilder().toString(),massendatenArray,i*threadCycle,endIndex);
                sendRequestThreadArrayList.add(srt);
                srt.start();
               // target.path("testlauf").request().post(Entity.entity(massendatenArray[i], MediaTypeExt.APPLICATION_PROTOBUF), Massendaten.class);
            }

            for(int cnt=0 ; cnt<sendRequestThreadArrayList.size() ; cnt++)
                sendRequestThreadArrayList.get(cnt).join();

            //target.path("testlauf").request().post(Entity.entity(massendaten, MediaTypeExt.APPLICATION_PROTOBUF), Massendaten.class);
            messEnde = System.currentTimeMillis();
            messArray[1] = messEnde - messStart;

            return messArray;
        } catch (Exception e) {
            System.out.println("\n !!! Verbindung zum Server fehlgeschlagen !!!");
        }

        return null;
    }

    /* 1 Double-Wert = 11 Byte || 90909 Double-Werte = 999999 Byte*/
    public Massendaten[] splitMassendaten1MB(List<Werte> werteList) {
        System.out.println("\nSPLIT MASSENDATEN 1MB");
        int divider = 90909;

        Massendaten[] massendatenArray;
        if(werteList.size()%divider == 0) massendatenArray = new Massendaten[werteList.size()/divider];
        else massendatenArray = new Massendaten[werteList.size()/divider+1];

        System.out.println("Listengroeße: "+werteList.size());
        System.out.println("Arraygroeße: "+massendatenArray.length);

        for(int i=0 ; i<massendatenArray.length ; i++) {
            Massendaten.Builder builder = Massendaten.newBuilder();

            if(i == massendatenArray.length-1) {
                for(int j=0 ; j<werteList.size()-divider*i ; j++) {
                    builder.addValue(Massendaten.Werte.newBuilder().setNumber(werteList.get(i*divider+j).getNumber()));
                }
            } else {
                for(int j=0 ; j<divider ; j++) {
                    builder.addValue(werteList.get(i*divider+j));
                    }
            }
            massendatenArray[i] = builder.build();
        }

  /*      int werte = 0;
        for(int x=0 ; x<massendatenArray.length ; x++) {
            werte += massendatenArray[x].getValueList().size();
            System.out.println("Array["+x+"] size: "+(double)massendatenArray[x].getSerializedSize()/1000000);
        }
        System.out.println("Gesamtwerte in Array: "+werte); */

        return massendatenArray;
    }

}
