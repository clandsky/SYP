package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015.
 */
public class DateiLaden
{

    public MassendatenProtos.Massendaten ladeMassendaten(int id)
    {
        MassendatenProtos.Massendaten.Builder m = MassendatenProtos.Massendaten.newBuilder();
        System.out.println("Laden der XML Datei");
        File file2 = new File("massendaten" + id + ".xml");
        FileInputStream fin = null;
        try
        {
            // create FileInputStream object
            fin = new FileInputStream(file2);
            byte fileContent[] = new byte[(int) file2.length()];
            fin.read(fileContent);
            String xmlFormat = new String(fileContent);
            XmlFormat.merge(xmlFormat, m);

            //Testweise Ausgebendes Inhalts
            System.out.println("Inhalt: " + xmlFormat.substring(0, 200));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Datei nicht gefunden" + e);
            return null;
        }
        catch (IOException ioe)
        {
            System.out.println("Fehler beim einlesen der Datei " + ioe);
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
                System.out.println("Fehler beim schließen des Dateistreams: " + ioe);
                return null;
            }
        }
        System.out.println("");
        System.out.println("Massendaten wurden gefüttert");

        return m.build();
    }
}
