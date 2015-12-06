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
        SendPostThread srt;
        int processors;
        int threads;
        int threadCycle;
        List<Double> progressList = new ArrayList<>();

        Massendaten m = PrototypDaten.getMassendaten(id);
        List<Massendaten> massendatenList = new Splitter().splitMassendaten(m, 1000);

        processors = Runtime.getRuntime().availableProcessors();
        if(PRINT_DEBUG_INFO) System.out.println("\nVerfuegbare CPUs: "+processors);

        threads = (int)(processors*0.5); // Nur die hälfte der CPU's als Threads nutzen
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

            ProgressThread pThread = new ProgressThread(progressList,FORTSCHRITT_GENAUIGKEIT);
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


    /*************************************** THREADS ***************************************************/
    public class SendPostThread extends Thread {
        private int threadID;
        private List<Massendaten> massendatenList;
        private int startIndex;
        private int endIndex;
        private List<Double> progressList;

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
                    HTTPClient.getExemplar().sendeMassendaten(massendatenList.get(i));
                }
            } catch(Exception e) {
                System.out.println("Thread "+threadID+": Fehler bei der Uebertragung!");
                if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
            }
        }
    }

    public class ProgressThread extends Thread {
        private List<Double> progressList;
        private boolean abbruch;
        List<Integer> intList;

        public ProgressThread(List<Double> progressList, int precision) {
            this.progressList = progressList;
            this.abbruch = false;
            this.intList = new ArrayList<>();

            for(int i=1 ; i<=100/precision ; i++) intList.add(i*precision);
        }

        public void run() {
            double progress = 0.0;
            long rounded;
            while(!abbruch){
                for(Double d : progressList) {
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
                    }

                    progress += d;
                    rounded = Math.round(progress);
                    if(rounded >= 100) abbruch = true;
                    printProgress(rounded);
                    progress = 0.0;
                }
            }
        }

        private void printProgress(long progress) {
            if(intList.size() > 0) {
                if(progress >= intList.get(0)) {
                    System.out.println("Uebertragung: "+intList.get(0)+"%");
                    intList.remove(0);
                }
            }
        }

        public void abbruch() {
            abbruch = true;
        }
    }
}
