package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiLaden {
    private final String saveDirectory = "Protodaten/";
    private final String saveMassendatenDirectory = saveDirectory + "Massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory + "Struktdaten/";
    private final String fileName = "ByteArray";
    private final String infoFileName = "Info";

    public Massendaten ladeMassendaten(int id) {
        Massendaten m;
        //Printer.println("Laden der PROTOBYTE Datei");
        File file = new File(saveMassendatenDirectory + id + "/" + fileName + ".protobyte");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            //Printer.println("Paketgroeße in KB: "+file.length()/1000);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = Massendaten.parseFrom(fileContent);
        } catch (FileNotFoundException e) {
            Printer.println("Datei nicht gefunden" + e);
            return null;
        } catch (IOException ioe) {
            Printer.println("Fehler beim einlesen der Datei " + ioe);
            return null;
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ioe) {
                Printer.println("Fehler beim Schließen des Dateistreams: " + ioe);
                return null;
            }
        }
        //Printer.println("Massendaten wurden gefüttert");

        return m;
    }


    public Struktdaten ladeStruktdaten(int id) {
        Struktdaten m;

        File file = new File(saveStruktdatenDirectory + id + "/" + fileName + ".protobyte");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);

            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = Struktdaten.parseFrom(fileContent);
        } catch (FileNotFoundException e) {
            Printer.println("Datei nicht gefunden! " + e);
            return null;
        } catch (IOException ioe) {
            Printer.println("Fehler beim einlesen der Datei " + ioe);
            return null;
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ioe) {
                Printer.println("Fehler beim Schließen des Dateistreams: " + ioe);
                return null;
            }
        }


        return m;
    }

    public ArrayList<MassenInfo> ladeMassenInfo() {
        FileInputStream fin;
        File directory = new File(saveMassendatenDirectory);
        String[] fileNameArray;
        ArrayList<MassenInfo> massenInfoArrayList = new ArrayList<>();

        MassendatenProtos.Massendaten.MassenInfo protoInfo;
        MassenInfo mInfo;
        MassenDef mDef;
        ArrayList<Frequency> frequencyList;

        if (directory.exists()) {
            fileNameArray = directory.list();

            for (int i = 0; i < fileNameArray.length; i++) {
                File mInfoFile = new File(saveMassendatenDirectory + fileNameArray[i] + "/" + infoFileName + ".protobyte");
                frequencyList = new ArrayList<>();

                try {
                    fin = new FileInputStream(mInfoFile);
                    byte fileContent[] = new byte[(int) mInfoFile.length()];
                    fin.read(fileContent);
                    protoInfo = MassendatenProtos.Massendaten.MassenInfo.parseFrom(fileContent);

                    for (MassendatenProtos.Massendaten.Frequency frequency : protoInfo.getDef().getFrequencyList()) {
                        frequencyList.add(new Frequency(frequency.getFrequency(), frequency.getAmplitude(), frequency.getPhase()));
                    }
                    mDef = new MassenDef(protoInfo.getDef().getAbtastrate(), frequencyList);
                    mInfo = new MassenInfo(protoInfo.getId(), protoInfo.getPaketGroesseKB(), mDef);
                    massenInfoArrayList.add(mInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return massenInfoArrayList;
    }
    public ArrayList<StruktInfo> ladeStruktInfo() {
        FileInputStream fin;
        File directory = new File(saveStruktdatenDirectory);
        String[] fileNameArray;
        ArrayList<StruktInfo> struktInfoArrayList = new ArrayList<>();

        Struktdaten.StruktInfo protoInfo;
        StruktInfo struktInfo;
        StruktDef struktDef;


        if (directory.exists()) {
            fileNameArray = directory.list();

            for (int i = 0; i < fileNameArray.length; i++) {
                File sInfoFile = new File(saveStruktdatenDirectory + fileNameArray[i] + "/" + infoFileName + ".protobyte");


                try {
                    fin = new FileInputStream(sInfoFile);
                    byte fileContent[] = new byte[(int) sInfoFile.length()];
                    fin.read(fileContent);

                    protoInfo = Struktdaten.StruktInfo.parseFrom(fileContent);

                    Struktdaten.StruktInfo.StruktDef def = protoInfo.getDef();
                    struktDef = new StruktDef(def.getItemJoinDefCount(),def.getItemAIDNameCount(),def.getItemSelItemCount(),def.getItemSelOrderCount(),def.getItemSelUIDCount());
                    struktInfo = new StruktInfo(protoInfo.getId(),protoInfo.getSize(), struktDef);
                    struktInfoArrayList.add(struktInfo);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }

        return struktInfoArrayList;
    }
}
