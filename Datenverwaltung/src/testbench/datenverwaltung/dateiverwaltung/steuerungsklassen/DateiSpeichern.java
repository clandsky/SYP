package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.masseninfo.MasseninfoProtos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiSpeichern
{
    private final String saveDirectory = "protodaten/";
    private final String saveMassendatenDirectory = saveDirectory+"massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory+"struktdaten/";
    private final String fileName = "ByteArray";
    private final String infoFileName = "Info";

    public boolean speicherMassendaten(Massendaten massendaten)
    {
        FileOutputStream fos = null;
        File massenDatenFile, massenInfoFile, directory;

        Printer.println("Die Massendaten werden jetzt in eine XML Datei geschrieben");
        try
        {

            /* erzeuge eine datei, die die informationen über die massendaten enthält (masseninfo) */
            /* die daten sind zwar auch in den massendaten enthalten, aber die zweite datei wird dennoch benötigt */
            MasseninfoProtos.Masseninfo.Builder massenInfoBuilder = MasseninfoProtos.Masseninfo.newBuilder();
            MasseninfoProtos.Masseninfo.MassenDef.Builder massenDefBuilder = MasseninfoProtos.Masseninfo.MassenDef.newBuilder();
            MasseninfoProtos.Masseninfo.Frequency.Builder frequencyBuilder;

            for(Massendaten.Frequency frequency : massendaten.getInfo().getDef().getFrequencyList()) {
                frequencyBuilder = MasseninfoProtos.Masseninfo.Frequency.newBuilder();
                frequencyBuilder.setFrequency(frequencyBuilder.getFrequency());
                frequencyBuilder.setAmplitude(frequencyBuilder.getAmplitude());
                frequencyBuilder.setPhase(frequencyBuilder.getPhase());

                massenDefBuilder.addFrequency(frequencyBuilder.build());
            }

            massenDefBuilder.setAbtastrate(massendaten.getInfo().getDef().getAbtastrate());

            massenInfoBuilder.setDef(massenDefBuilder);

            massenInfoBuilder.setId(massendaten.getInfo().getId());
            massenInfoBuilder.setPaketGroesseKB(massendaten.getInfo().getPaketGroesseKB());
            massenInfoBuilder.setPath(massendaten.getInfo().getPath());

            MasseninfoProtos.Masseninfo masseninfo = massenInfoBuilder.build();
            /* ############## massinfo erzeugung abgeschlossen ####################### */

            int massendatenID = massendaten.getInfo().getId();
            String filePath = saveMassendatenDirectory+massendatenID;
            directory = new File(filePath);
            if(!directory.exists()) directory.mkdirs();

            massenDatenFile = new File(filePath+"/"+fileName+".protobyte");
            massenInfoFile = new File(filePath+"/"+infoFileName+".protobyte");

            /* Checken ob Datei existiert ansonsten erzeuge neue Datei */
            if (!massenDatenFile.exists())
            {
                massenDatenFile.createNewFile();
            }
            if (!massenDatenFile.exists())
            {
                massenDatenFile.createNewFile();
            }

            /* Strings können nicht direkt in ein File geschrieben werden.
             * Deswegen muss dies in Bytes umgewandelt werden vor dem reinladen
             */
            // schreibe massendaten datei
            fos = new FileOutputStream(massenDatenFile);
            fos.write(massendaten.toByteArray());
            fos.flush();

            //schreibe masseninfo datei
            fos = new FileOutputStream(massenInfoFile);
            fos.write(masseninfo.toByteArray());
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
