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
    private int FORTSCHRITT_GENAUIGKEIT = 5;
    private boolean PRINT_STACKTRACE_CONSOLE = true;
    private boolean PRINT_DEBUG_INFO = true;


    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) {
        WerteListConverter w = new WerteListConverter();
        Massendaten m = HTTPClient.getExemplar().empfangeMassendaten(id);
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
        List<Double> progressList = new ArrayList<>();
        List<Massendaten> massendatenList;

        SendPostThread srt;
        int processors;
        int threads;
        int threadCycle;

        massendatenList = new Splitter().splitMassendaten(PrototypDaten.getMassendaten(id), 1000);

        processors = Runtime.getRuntime().availableProcessors();
        if(PRINT_DEBUG_INFO) System.out.println("\nVerfuegbare CPUs: "+processors);

        threads = (int)(processors*0.5); // Nur die haelfte der CPU's als Threads nutzen
        if(PRINT_DEBUG_INFO) System.out.println("Threads: " + threads);

        if(threads > massendatenList.size()) threads = massendatenList.size();

        if(massendatenList.size()%threads == 0) threadCycle = massendatenList.size()/threads;
        else threadCycle = massendatenList.size()/(threads)-1;

        try {
            for (int i = 0; i < threads; i++) {
                int endIndex = i*threadCycle+threadCycle;

                if(i == threads-1) {
                    endIndex = massendatenList.size()-1;
                }

                srt = new SendPostThread(sendRequestThreadArrayList.size()+1,massendatenList,i*threadCycle,endIndex,progressList);
                sendRequestThreadArrayList.add(srt);
                srt.start();
                progressList.add(0.0);
            }

            System.out.println("\nMassendaten werden gesendet...");
            ProgressThread pThread = new ProgressThread(sendRequestThreadArrayList,FORTSCHRITT_GENAUIGKEIT);
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
        HTTPClient.getExemplar().connect(adresse);
        return true;
    }

    public void starteDatenverwaltung() {

    }

    public MassendatenGrenz erzeugeZufallsMassendaten() {
        return null;
    }

    public void printProgressBar(int progress) {
        StringBuffer progressBuffer = new StringBuffer();
        progressBuffer.append('|');

        for(int i=1 ; i<99 ; i++) {
            if(i < progress) progressBuffer.append('=');
            else progressBuffer.append(' ');
        }
        progressBuffer.append('|');

        if(progress >= 100) {
            System.out.print("\r"+progressBuffer+" 100%\n");
            System.out.println("Fertig!");
        }
        else System.out.print("\r"+progressBuffer+" "+progress+"%");
    }

    /*************************************** THREADS ***************************************************/
    public class SendPostThread extends Thread {
        private int threadID;
        private List<Massendaten> massendatenList;
        private int startIndex;
        private int endIndex;
        private List<Double> progressList;
        private double progress;

        public SendPostThread(int threadID, List<Massendaten> massendatenList, int startIndex, int endIndex, List<Double> progressList) {
            this.threadID = threadID;
            this.massendatenList = massendatenList;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.progressList = progressList;
        }

        public void run() {
            double work = endIndex-startIndex;
            double cnt = 0;

            try{
                for(int i=startIndex ; i<endIndex ; i++) {
                    cnt++;
                    progressList.set(threadID-1, cnt/work*100);
                    progress = progressList.get(threadID-1);
                    HTTPClient.getExemplar().sendeMassendaten(massendatenList.get(i));
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
        private List<Integer> intList;
        private List<SendPostThread> sendPostThreadList;

        public ProgressThread(List<SendPostThread> sendPostThreadList, int precision) {
            this.sendPostThreadList = sendPostThreadList;
            this.abbruch = false;
            this.intList = new ArrayList<>();

            for(int i=1 ; i<=100/precision ; i++) intList.add(i*precision);
        }

        public void run() {
            int progress = 0;
            int lastProgress = 0;

            while(!abbruch){
                try {
                    sleep(THREAD_SLEEP_MS);
                } catch (InterruptedException e) {
                    if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
                }

                progress = (int)Math.round(getProgress());
                if(progress != lastProgress) {
                    printProgressBar(progress);
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
