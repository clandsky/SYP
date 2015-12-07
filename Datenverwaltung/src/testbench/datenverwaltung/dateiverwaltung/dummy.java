package testbench.datenverwaltung.dateiverwaltung;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import java.io.*;
import java.util.List;

/**
 * Created by murattasdemir on 26.11.15.
 */
public class dummy
{
    static public void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MassendatenProtos.Massendaten massendaten = null;

        boolean exit = false;
        System.out.println(".......... Datenverwaltung ..........");
        System.out.println("  Bitte geben Sie eine Nummer ein");
        while (!exit) {
            System.out.println(".....................................");
            System.out.println("[1]" + " Massendaten generien");
            System.out.println("[2]" + " Generierte Daten Massendaten in XML schreiben");
            System.out.println("[3]" + " Hole Massendaten aus XML");
            System.out.println("[4]" + " Beenden der Dateiverwaltung");


            int n = Integer.parseInt(reader.readLine());
            switch (n) {
                /*
                 * Generiere Massendaten
                 */
                case 1:
                    Generator gen = new Generator();
                    // Konfiguration für den Generator füllen
                    MassenDef config = new MassenDef( 0.1 );
                    config.addFreqeuncy( new Frequency( 0.3, 4.0, 0.0 ) );
                    config.addFreqeuncy( new Frequency( 1.0, 1.0, 0.0 ) );
                    config.addFreqeuncy( new Frequency( 3.0, 0.4, 0.0 ) );
                    config.addFreqeuncy( new Frequency( 5.0, 0.2, 0.0 ) );
                    massendaten = gen.generatorMassData( config, 50000 );

                    List<MassendatenProtos.Massendaten.Werte> list = massendaten.getValueList();
                    double pos = 0.0;
                    for( MassendatenProtos.Massendaten.Werte w : list )
                    {
                        //     System.out.println( pos + "\t" + w.getNumber() );
                        pos += config.getAbtastrate();
                    }

                    /*
                    try
                    {
                            gen.generatorDeepStructure(0, 0, 0, 0, 0);

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    */
                    continue;
                /*
                 * Speichere in XML
                 */
                case 2:

                    String xmlFormat = XmlFormat.printToString(massendaten);
                    FileOutputStream fos = null;
                    File file;

                    System.out.println("Die Massendaten werden jetzt in eine XML Datei geschrieben");
                    try {
                        //Specify the file path here
                        file = new File("massendaten.xml");
                        fos = new FileOutputStream(file);

                    /* Checken ob Datei existiert ansonsten erzeuge neue Datei */
                        if (!file.exists()) {
                            file.createNewFile();
                        }

	                /* Strings können nicht direkt in ein File geschrieben werden.
	                Deswegen muss dies in Bytes umgewandelt werden vor dem reinladen

	                */
                        byte[] bytesArray = xmlFormat.getBytes();

                        fos.write(bytesArray);
                        fos.flush();
                        System.out.println("");
                        System.out.println("Die Datei wurde angelegt und liegt im Stamm-Projektverzeichnis");
                    }
                    catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    finally {
                        try {
                            if (fos != null)
                            {
                                fos.close();
                            }
                        }
                        catch (IOException ioe) {
                            System.out.println("Error in closing the Stream");
                        }
                    }

                    continue;

                case 3:

                    MassendatenProtos.Massendaten.Builder builder = MassendatenProtos.Massendaten.newBuilder();

                    System.out.println("Laden der XML Datei");
                    File file2 = new File("massendaten.xml");
                    FileInputStream fin = null;
                    try {
                        // create FileInputStream object
                        fin = new FileInputStream(file2);
                        byte fileContent[] = new byte[(int)file2.length()];
                        fin.read(fileContent);
                        xmlFormat = new String(fileContent);
                        XmlFormat.merge(xmlFormat, builder);

                        //Testweise Ausgebendes Inhalts
                        //System.out.println("Inhalt: " + xmlFormat);
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Datei nicht gefunden" + e);
                    }
                    catch (IOException ioe) {
                        System.out.println("Fehler beim einlesen der Datei " + ioe);
                    }

                    finally {
                        try {
                            if (fin != null) {
                                fin.close();
                            }
                        }
                        catch (IOException ioe) {
                            System.out.println("Fehler beim schließen des Dateistreams: " + ioe);
                        }
                    }
                    System.out.println("");
                    System.out.println("Massendaten wurden gefüttert");
                    continue;

                case 4:
                    System.out.println("Die Datenverwaltung wurde beendet.");
                    exit = true;
                default:
                    break;
            }






        return;
        }
    }
}

