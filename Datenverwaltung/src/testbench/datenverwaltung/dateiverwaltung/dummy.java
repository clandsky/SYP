package testbench.datenverwaltung.dateiverwaltung;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.datenverwaltung.dateiverwaltung.grenz.MassenDef;
import testbench.datenverwaltung.dateiverwaltung.grenz.Frequency;
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
        MassenDef config = new MassenDef( 0.1 );
        config.addFreqeuncy( new Frequency( 0.3, 4.0, 0.0 ) );
        config.addFreqeuncy( new Frequency( 1.0, 1.0, 0.0 ) );
        config.addFreqeuncy( new Frequency( 3.0, 0.4, 0.0 ) );
        config.addFreqeuncy( new Frequency( 5.0, 0.2, 0.0 ) );
        MassendatenProtos.Massendaten massendaten = gen.generatorMassData( config, 50000 );

        List<MassendatenProtos.Massendaten.Werte> list = massendaten.getValueList();
        double pos = 0.0;
        for( MassendatenProtos.Massendaten.Werte w : list )
        {
            System.out.println( pos + "\t" + w.getNumber() );
            pos += config.getAbtastrate();
        }

        //QueryStuctureExtGrenz qse = gen.generatorDeepStructure( 1, 2, 3, 4, 5 );
        FileOutputStream output = new FileOutputStream("E:\\Test.dat");
        massendaten.writeTo(output);

        return;
    }
}
