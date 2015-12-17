package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.MassenInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Frequency;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.MassenDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CGrings on 07.12.2015
 */
public class DateiSpeichern
{
    private final String saveDirectory = "Protodaten/";
    private final String saveMassendatenDirectory = saveDirectory+"Massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory+"Struktdaten/";
    private final String fileName = "ByteArray";
    private final String infoFileName = "Info";

    public boolean speicherMassendaten(Massendaten massendaten)
    {
        FileOutputStream fos = null;
        File massenDatenFile, massenInfoFile, directory;

        //Printer.println("Die Massendaten werden jetzt in eine XML Datei geschrieben");
        try
        {
            /* ############## massinfo erzeugung beginnt ####################### */
            /* erzeuge eine datei, die die informationen über die massendaten enthält (masseninfo) */
            /* die daten sind zwar auch in den massendaten enthalten, aber die zweite datei wird dennoch benötigt */
            MassenInfo.Builder massenInfoBuilder = MassenInfo.newBuilder();
            MassenDef.Builder massenDefBuilder = MassenDef.newBuilder();
            Frequency.Builder frequencyBuilder;

            for(Massendaten.Frequency frequency : massendaten.getInfo().getDef().getFrequencyList()) {
                frequencyBuilder = Frequency.newBuilder();
                frequencyBuilder.setFrequency(frequencyBuilder.getFrequency());
                frequencyBuilder.setAmplitude(frequencyBuilder.getAmplitude());
                frequencyBuilder.setPhase(frequencyBuilder.getPhase());

                massenDefBuilder.addFrequency(frequencyBuilder.build());
            }

            massenDefBuilder.setAbtastrate(massendaten.getInfo().getDef().getAbtastrate());

            massenInfoBuilder.setDef(massenDefBuilder);

            massenInfoBuilder.setId(massendaten.getInfo().getId());
            massenInfoBuilder.setPaketGroesseKB(massendaten.getInfo().getPaketGroesseKB());

            MassenInfo masseninfo = massenInfoBuilder.build();
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
            fos.close();

            //schreibe masseninfo datei
            fos = new FileOutputStream(massenInfoFile);
            fos.write(masseninfo.toByteArray());
            fos.flush();
            fos.close();
            //Printer.println("Die Datei wurde angelegt und liegt im Stamm-Projektverzeichnis");
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


    public boolean speicherStruktdaten (Struktdaten strukt)
    {
        FileOutputStream fos = null;
        File struktFile, directory;

        try {
            //int struktid = strukt.hashCode();
            int struktid=12345;
            String filePath = saveStruktdatenDirectory+struktid;

            directory = new File(filePath);
            if(!directory.exists()) directory.mkdirs();

            struktFile = new File(filePath+"/"+fileName+".protobyte");


            // Checken ob Datei existiert ansonsten erzeuge neue Datei
            if (!struktFile.exists())
            {
                struktFile.createNewFile();
            }


            // Strings können nicht direkt in ein File geschrieben werden.
            // Deswegen muss dies in Bytes umgewandelt werden vor dem reinladen



            // schreibe Struktdaten datei
            fos = new FileOutputStream(struktFile);
            fos.write(strukt.toByteArray());
            fos.flush();
            fos.close();

            //Printer.println("Die Datei wurde angelegt und liegt im Stamm-Projektverzeichnis");
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

