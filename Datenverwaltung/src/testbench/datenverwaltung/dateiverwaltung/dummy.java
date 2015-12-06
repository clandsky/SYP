package testbench.datenverwaltung.dateiverwaltung;

import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.datenverwaltung.dateiverwaltung.services.Generator;

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
        MassenDef config = new MassenDef(0.1);
        config.addFreqeuncy(new Frequency(0.3, 4.0, 0.0));
        config.addFreqeuncy(new Frequency(1.0, 1.0, 0.0));
        config.addFreqeuncy(new Frequency(3.0, 0.4, 0.0));
        config.addFreqeuncy(new Frequency(5.0, 0.2, 0.0));
        MassendatenProtos.Massendaten massendaten = gen.generatorMassData(config, 50000000);

        List<MassendatenProtos.Massendaten.Werte> list = massendaten.getValueList();

        FileOutputStream output = new FileOutputStream("E:\\Test.dat");
        massendaten.writeTo(output);

        try
        {
            StruktDef struktDef = new StruktDef();
            StruktdatenProtos.Struktdaten stp = gen.generatorDeepStructure(struktDef);
            FileOutputStream output2 = new FileOutputStream("E:\\Test2.dat");
            stp.writeTo(output2);


        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return;
    }
}
