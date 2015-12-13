package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiLaden
{
    public MassendatenProtos.Massendaten ladeMassendaten(int id)
    {
        MassendatenProtos.Massendaten m;
        Printer.println("Laden der PROTOBYTE Datei");
        File file = new File("massendaten" + id + ".protobyte");
        FileInputStream fin = null;
        try
        {
            // create FileInputStream object
            fin = new FileInputStream(file);
            Printer.println("Paketgroeße in KB: "+file.length()/1000);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            m = MassendatenProtos.Massendaten.parseFrom(fileContent);
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
        Printer.println("Massendaten wurden gefüttert");

        return m;
    }
}
