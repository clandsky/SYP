package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiLaden
{
    private final String saveDirectory = "Protodaten/";
    private final String saveMassendatenDirectory = saveDirectory+"Massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory+"Struktdaten/";
    private final String fileName = "ByteArray";

    public Massendaten ladeMassendaten(int id)
    {
        Massendaten m;
        //Printer.println("Laden der PROTOBYTE Datei");
        File file = new File(saveMassendatenDirectory + id + "/" + fileName + ".protobyte");
        FileInputStream fin = null;
        try
        {
            fin = new FileInputStream(file);
            //Printer.println("Paketgroeße in KB: "+file.length()/1000);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = Massendaten.parseFrom(fileContent);
        }
        catch (FileNotFoundException e)
        {
            Printer.println("Datei nicht gefunden" + e);
            return null;
        }
        catch (IOException ioe)
        {
            Printer.println("Fehler beim einlesen der Datei " + ioe);
            return null;
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }
            }
            catch (IOException ioe)
            {
                Printer.println("Fehler beim Schließen des Dateistreams: " + ioe);
                return null;
            }
        }
        //Printer.println("Massendaten wurden gefüttert");

        return m;
    }


    public static Struktdaten ladeStruktdaten(int id)
    {
        final String saveDirectory = "Protodaten/";
        final String saveStruktdatenDirectory = saveDirectory+"Struktdaten/";
        final String fileName = "ByteArray";


        Struktdaten m;
        //Printer.println("Laden der PROTOBYTE Datei");
        File file = new File(saveStruktdatenDirectory + id + "/" + fileName + ".protobyte");
        FileInputStream fin = null;
        try
        {
            fin = new FileInputStream(file);
            //Printer.println("Paketgroeße in KB: "+file.length()/1000);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = Struktdaten.parseFrom(fileContent);
        }
        catch (FileNotFoundException e)
        {
            Printer.println("Datei nicht gefunden" + e);
            return null;
        }
        catch (IOException ioe)
        {
            Printer.println("Fehler beim einlesen der Datei " + ioe);
            return null;
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }
            }
            catch (IOException ioe)
            {
                Printer.println("Fehler beim Schließen des Dateistreams: " + ioe);
                return null;
            }
        }


        return m;
    }
}
