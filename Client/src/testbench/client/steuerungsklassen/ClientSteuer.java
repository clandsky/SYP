package testbench.client.steuerungsklassen;

import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.Werkzeug;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.client.HTTPClient;
import testbench.client.PrototypDaten;
import testbench.client.grenzklassen.MassendatenListeGrenz;
import testbench.client.grenzklassen.StruktdatenListeGrenz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015.
 */

public class ClientSteuer {
    private int FORTSCHRITT_GENAUIGKEIT = 5;
    private boolean PRINT_STACKTRACE_CONSOLE = true;
    private boolean PRINT_DEBUG_INFO = true;
    HTTPClient httpClient;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
    }

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) {
        Werkzeug w = new Werkzeug();
        Massendaten m = httpClient.empfangeMassendaten(id);
        return new MassendatenGrenz(w.werteListToDoubleList(m.getValueList()));
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
        List<Massendaten> massendatenList;
        SendPostThread srt;
        int processors;
        int threads;
        int threadCycle;

        massendatenList = new Splitter().splitMassendaten(PrototypDaten.getMassendaten(id), 1000);

        System.out.println("\nSenden der Massendaten wird vorbereitet...\n");

        processors = Runtime.getRuntime().availableProcessors();
        threads = (int)(processors*0.5); // Nur die haelfte der CPU's als Threads nutzen
        if(PRINT_DEBUG_INFO) System.out.println("Verfuegbare CPUs: "+processors+"... "+threads+" der CPUs werden als Threads benutzt... ");

        if(threads > massendatenList.size()) threads = massendatenList.size();

        if(massendatenList.size()%threads == 0) threadCycle = massendatenList.size()/threads;
        else threadCycle = massendatenList.size()/(threads)-1;

        try {
            for (int i = 0; i < threads; i++) {
                int endIndex = i*threadCycle+threadCycle;

                if(i == threads-1) {
                    endIndex = massendatenList.size()-1;
                }

                srt = new SendPostThread(sendRequestThreadArrayList.size()+1,massendatenList,i*threadCycle,endIndex);
                sendRequestThreadArrayList.add(srt);
                srt.start();
            }

            System.out.println("\nMassendaten werden gesendet...");
            ProgressThread pThread = new ProgressThread(sendRequestThreadArrayList);
            pThread.start();

            for(SendPostThread s : sendRequestThreadArrayList)
                s.join();

            pThread.abbruch();
            pThread.join();

        } catch (Exception e) {
            if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
            System.out.println("\n !!! Verbindung zum Server fehlgeschlagen !!!");
        }

            return true;
    }

    public boolean sendeStruktdaten(int id) {
        return true;
    }

    public boolean connect(String adresse) {
        httpClient.connect(adresse);
        return true;
    }

    public void starteDatenverwaltung() {

    }

    public MassendatenGrenz erzeugeZufallsMassendaten() {
        return null;
    }

    /*************************************** THREADS ***************************************************/
    public class SendPostThread extends Thread {
        private int threadID;
        private List<Massendaten> massendatenList;
        private int startIndex;
        private int endIndex;
        private double progress;

        public SendPostThread(int threadID, List<Massendaten> massendatenList, int startIndex, int endIndex) {
            this.threadID = threadID;
            this.massendatenList = massendatenList;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public void run() {
            double work = endIndex-startIndex;
            double cnt = 0;

            try{
                for(int i=startIndex ; i<endIndex ; i++) {
                    cnt++;
                    progress = cnt/work*100;
                    httpClient.sendeMassendaten(massendatenList.get(i));
                }
            } catch(Exception e) {
                System.out.println("Thread "+threadID+": Fehler bei der Uebertragung!");
                if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
            }
        }

        public double getProgress() {
            return progress;
        }
    }

    public class ProgressThread extends Thread {
        private int THREAD_SLEEP_MS = 100;
        private boolean abbruch;
        private List<SendPostThread> sendPostThreadList;

        public ProgressThread(List<SendPostThread> sendPostThreadList) {
            this.sendPostThreadList = sendPostThreadList;
            this.abbruch = false;
        }

        public void run() {
            int progress = 0;
            int lastProgress = 0;
            Werkzeug w = new Werkzeug();

            while(!abbruch){
                try {
                    sleep(THREAD_SLEEP_MS);
                } catch (InterruptedException e) {
                    if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
                }

                progress = (int)Math.round(getProgress());
                if(progress != lastProgress) {
                    w.printProgressBar(progress);
                }
                lastProgress = progress;
                if(progress >= 100) abbruch = true;
            }
        }

        private double getProgress() {
            double progress = 0;

            for (SendPostThread s : sendPostThreadList)
                progress += s.getProgress();

            return progress;
        }

        public void abbruch() {
            abbruch = true;
        }
    }
}
