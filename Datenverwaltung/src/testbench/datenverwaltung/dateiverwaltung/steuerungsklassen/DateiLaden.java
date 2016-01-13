package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.messdaten.MessdatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.service.StaticHolder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DateiLaden {
    /**
     * Diese Methode laedt die Massendaten aus einer spezifischen Datei auf der Festplatte,
     * speichert diese in den Datentypen Massendaten und gibt diese zurueck.
     * @return Die geladenen Massendaten.
     */
    public Massendaten ladeMassendaten(int id) {
        Massendaten m;
        //Printer.println("Laden der PROTOBYTE Datei");
        File file = new File(StaticHolder.saveMassendatenDirectory + id + "/" + StaticHolder.fileName + ".protobyte");
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

    /**
     * Diese Methode laedt die strukturierten Daten aus einer spezifischen Datei auf der Festplatte,
     * speichert diese in den Datentypen Struktdaten und gibt diese zurueck.
     * @return Die geladenen Struktdaten.
     */

    public Struktdaten ladeStruktdaten(int id) {
        Struktdaten m;

        File file = new File(StaticHolder.saveStruktdatenDirectory + id + "/" + StaticHolder.fileName + ".protobyte");
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


    /**
     * Diese Methode laedt die Messendaten als Proto File aus einer spezifischen Datei auf der Festplatte,
     * speichert diese in den Datentypen MassendatenProtos.Messdaten und gibt diese zurueck.
     * @return Die geladenen Messdaten.
     */

    public MessdatenProtos.Messdaten ladeMessdaten(int id) {
        MessdatenProtos.Messdaten m;
        //Printer.println("Laden der PROTOBYTE Datei");
        File file = new File(StaticHolder.saveMessdatenDirectory + id + "/" + StaticHolder.fileName + ".protobyte");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = MessdatenProtos.Messdaten.parseFrom(fileContent);
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

        return m;
    }

    /**
     * Diese Methode laedt die Messendaten in eine Liste,
     * speichert diese in einen String und gibt diese zurueck.
     * @return Die geladenen Messdaten.
     */

    public List<MessdatenProtos.Messdaten> ladeMessdatenListe() {
        File file = new File(StaticHolder.saveMessdatenDirectory);
        String[] alleMessdaten = file.list();
        ArrayList<MessdatenProtos.Messdaten> messdatenArrayList = new ArrayList<>();

        if (alleMessdaten != null) {
            for(String s : alleMessdaten) {
                messdatenArrayList.add(ladeMessdaten(Integer.valueOf(s)));
            }
        }

        return messdatenArrayList;
    }

    /**
     * Diese Methode laedt die Masseninfo in eine ArrayList,
     * und gibt diese als Arraylist zurueck.
     * @return Die Masseninfo Arraylist.
     */
    public ArrayList<MassenInfo> ladeMassenInfo() {
        FileInputStream fin;
        File directory = new File(StaticHolder.saveMassendatenDirectory);
        String[] fileNameArray;
        ArrayList<MassenInfo> massenInfoArrayList = new ArrayList<>();

        MassendatenProtos.Massendaten.MassenInfo protoInfo;
        MassenInfo mInfo;
        MassenDef mDef;
        ArrayList<Frequency> frequencyList;

        if (directory.exists()) {
            fileNameArray = directory.list();

            for (int i = 0; i < fileNameArray.length; i++) {
                File mInfoFile = new File(StaticHolder.saveMassendatenDirectory + fileNameArray[i] + "/" + StaticHolder.infoFileName + ".protobyte");
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

    /**
     * Diese Methode laedt die Struktinfo in eine ArrayList,
     * und gibt diese als Arraylist zurueck.
     * @return Die Struktinfo Arraylist.
     */
    public ArrayList<StruktInfo> ladeStruktInfo() {
        FileInputStream fin;
        File directory = new File(StaticHolder.saveStruktdatenDirectory);
        String[] fileNameArray;
        ArrayList<StruktInfo> struktInfoArrayList = new ArrayList<>();

        Struktdaten.StruktInfo protoInfo;
        StruktInfo struktInfo;
        StruktDef struktDef;


        if (directory.exists()) {
            fileNameArray = directory.list();

            for (int i = 0; i < fileNameArray.length; i++) {
                File sInfoFile = new File(StaticHolder.saveStruktdatenDirectory + fileNameArray[i] + "/" + StaticHolder.infoFileName + ".protobyte");


                try {
                    fin = new FileInputStream(sInfoFile);
                    byte fileContent[] = new byte[(int) sInfoFile.length()];
                    fin.read(fileContent);

                    protoInfo = Struktdaten.StruktInfo.parseFrom(fileContent);

                    Struktdaten.StruktInfo.StruktDef def = protoInfo.getDef();
                    struktDef = new StruktDef(def);
                    struktInfo = new StruktInfo(protoInfo);
                    struktInfoArrayList.add(struktInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return struktInfoArrayList;
    }

    /**
     * Diese Methode laedt die Frequenzen für die Massendaten
     * aus einer Datei und gibt diese zurueck.
     * @return Die Frequenzen als massenDef.
     */
    public MassenDef ladeConfig()
    {
        MassenDef massenDef = new MassenDef();

        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter( "Frequenz Konfiguration",  "cfg" );
        fc.setFileFilter(filter);

        //In response to a button click:
        int returnVal = fc.showOpenDialog( null );

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();

            FileReader fr = null;
            try
            {
                fr = new FileReader(file.getAbsolutePath());
                BufferedReader br = new BufferedReader(fr);

                String line;
                while( ( line = br.readLine() ) != null )
                {
                    System.out.println( line );
                    String[] sl = line.split( ";" );
                    if( sl.length == 3 )
                    {
                        massenDef.getFrequencies().add(
                                new Frequency(
                                    Double.parseDouble( sl[0] ),
                                    Double.parseDouble( sl[1] ),
                                    Double.parseDouble( sl[2] )
                                    )
                        );
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return massenDef;
    }
}
