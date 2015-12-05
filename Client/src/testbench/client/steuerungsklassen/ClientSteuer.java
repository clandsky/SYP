package testbench.client.steuerungsklassen;

import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.protobuf.WerteListConverter;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.client.HTTPClient;
import testbench.client.PrototypDaten;
import testbench.client.grenzklassen.MassendatenListeGrenz;
import testbench.client.grenzklassen.StruktdatenListeGrenz;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015.
 */


public class ClientSteuer {

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) {
        WerteListConverter w = new WerteListConverter();
        List<Werte> werteList = HTTPClient.getExemplar().empfangeMassendaten(id).getValueList();
        return new MassendatenGrenz(w.werteListToDoubleList(werteList));
    }

    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    public MassendatenListeGrenz empfangeMassenInfo() {
        return null;
    }

    public StruktdatenListeGrenz empfangeStruktInfo() {
        return null;
    }

    public boolean sendeMassendaten(int id) {
        ArrayList<SendPostThread> sendRequestThreadArrayList = new ArrayList<>();
        SendPostThread srt;
        int processors = Runtime.getRuntime().availableProcessors();

        int threads;
        // Wenn Server+Client auf dem localhost genutzt werden, nur die hälfte der CPU's als Threads nutzen
        if(HTTPClient.getExemplar().getServerIP().equals("http://localhost:8000/")) threads = (int)(processors*0.5);
        else threads = processors-1;
        int threadCycle;

        System.out.println("Verfuegbare CPUs: "+processors);
        System.out.println("Threads: " + threads);
        List<Massendaten> massendatenList = new Splitter().splitMassendaten(PrototypDaten.getMassendaten(id), 1000);
        System.out.println("Massendaten in MassendatenListe: " + massendatenList.size());

        if(threads > massendatenList.size()) threads = massendatenList.size();

        if(massendatenList.size()%threads == 0) threadCycle = massendatenList.size()/threads;
        else threadCycle = massendatenList.size()/(threads)-1;
        System.out.println("threadCycle: "+threadCycle);

        try {
            for (int i = 0; i < threads; i++) {
                int endIndex = i*threadCycle+threadCycle;

                if(i == threads-1) {
                    endIndex =massendatenList.size()-1;
                }

                System.out.println("endIndex: "+endIndex);
                srt = new SendPostThread(massendatenList,i*threadCycle,endIndex);
                sendRequestThreadArrayList.add(srt);
                srt.start();
            }

            for(int cnt=0 ; cnt<sendRequestThreadArrayList.size() ; cnt++) {
                sendRequestThreadArrayList.get(cnt).join();
            }

         /*   for (int i = 0; i < massendatenList.size(); i++) {
                HTTPClient.getExemplar().sendeMassendaten(massendatenList.get(i));
            } */

        } catch (Exception e) {
            System.out.println("\n !!! Verbindung zum Server fehlgeschlagen !!!");
        }

            return true;
    }

    public boolean sendeStruktdaten(int id) {
        return true;
    }

    public boolean connect(String adresse) {
        HTTPClient.getExemplar().connect(adresse);
        return true;
    }

    public void starteDatenverwaltung() {

    }

    public MassendatenGrenz erzeugeZufallsMassendaten() {
        return null;
    }


    /*****************************************************************************************************************/
    public class SendPostThread extends Thread {
        private List<Massendaten> massendatenList;
        private int startIndex;
        private int endIndex;

        public SendPostThread(List<Massendaten> massendatenList, int startIndex, int endIndex) {
            this.massendatenList = massendatenList;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public void run() {
            for(int i=startIndex ; i<endIndex ; i++) {
                Response response = HTTPClient.getExemplar().sendeMassendaten(massendatenList.get(i));
            //    System.out.println(this.getName()+" - Response Code: "+response.getStatus());
            }

        }
    }


    /* 1 Double-Wert = 11 Byte || 90909 Double-Werte = 999999 Byte*/
   /* public Massendaten[] splitMassendaten1MB(List<Werte> werteList) {
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


        return massendatenArray;
    } */

}
