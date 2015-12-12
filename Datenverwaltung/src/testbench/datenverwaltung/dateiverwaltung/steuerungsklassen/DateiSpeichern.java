package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiSpeichern
{
    public boolean speicherMasendaten(MassendatenProtos.Massendaten massendaten)
    {
        String xmlFormat = XmlFormat.printToString(massendaten);
        FileOutputStream fos = null;
        File file;

        Printer.println("Die Massendaten werden jetzt in eine XML Datei geschrieben");
        try
        {
            //Specify the file path here
          //  file = new File("massendaten.xml");
            file = new File("massendaten1.protobyte");
            fos = new FileOutputStream(file);

                    /* Checken ob Datei existiert ansonsten erzeuge neue Datei */
            if (!file.exists())
            {
                file.createNewFile();
            }

            /* Strings können nicht direkt in ein File geschrieben werden.
             * Deswegen muss dies in Bytes umgewandelt werden vor dem reinladen
             */
          //  byte[] bytesArray = xmlFormat.getBytes();

            fos.write(massendaten.toByteArray());
            fos.flush();
            Printer.println("Die Datei wurde angelegt und liegt im Stamm-Projektverzeichnis");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch (IOException ioe)
            {
                Printer.println("Error in closing the Stream");
                return false;
            }
        }
        return true;
    }
}
