package testbench.datenverwaltung.dateiverwaltung;

import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;
import testbench.datenverwaltung.dateiverwaltung.services.Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by murattasdemir on 26.11.15.
 */
public class dummy
{
    static public void main(String[] args) throws IOException
    {
        Generator gen = new Generator();

        // Konfiguration für den Generator füllen
        MassenDef config = new MassenDef( 0.1 );
        config.addFreqeuncy( new Frequency( 0.3, 4.0, 0.0 ) );
        config.addFreqeuncy( new Frequency( 1.0, 1.0, 0.0 ) );
        config.addFreqeuncy( new Frequency( 3.0, 0.4, 0.0 ) );
        config.addFreqeuncy( new Frequency( 5.0, 0.2, 0.0 ) );
        MassendatenProtos.Massendaten massendaten = gen.generatorMassData( config, 43000000 );

        List<MassendatenProtos.Massendaten.Werte> list = massendaten.getValueList();
        double pos = 0.0;
        for( MassendatenProtos.Massendaten.Werte w : list )
        {
       //     System.out.println( pos + "\t" + w.getNumber() );
            pos += config.getAbtastrate();
        }

        String xmlFormat = XmlFormat.printToString(massendaten);
        FileOutputStream fos = null;
        File file;

        System.out.println("Bis hier");
        try {
            //Specify the file path here
            file = new File("massendaten.xml");
            fos = new FileOutputStream(file);

          /* This logic will check whether the file
	   * exists or not. If the file is not found
	   * at the specified location it would create
	   * a new file*/
            if (!file.exists()) {
                file.createNewFile();
            }

	  /*String content cannot be directly written into
	   * a file. It needs to be converted into bytes
	   */
            byte[] bytesArray = xmlFormat.getBytes();

            fos.write(bytesArray);
            fos.flush();
            System.out.println("File Written Successfully");
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


        try
        {
            gen.generatorDeepStructure(0, 0, 0, 0, 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }





        return;
    }
}
