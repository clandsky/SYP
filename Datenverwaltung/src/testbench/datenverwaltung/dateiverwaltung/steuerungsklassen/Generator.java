package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;

import static java.lang.Math.*;

/**
 * Created by CGrings on 29.11.2015.
 */
public class Generator
{
    final boolean _DEBUG = true;

    public MassendatenProtos.Massendaten generatorMassData(MassenDef config, int fileSize)
    {

        double pos = 0.0f;
        int typeSize = 8;

        int procent = 0;

        MassendatenProtos.Massendaten.Builder builder = MassendatenProtos.Massendaten.newBuilder();

        // Schleife um die Daten zu generieren:
        for (int i = 0; i < fileSize / typeSize; i++)
        {
            double value = 0.0f;

            // Amplitude für diesen Schritt erzeugen
            for (Frequency f : config.getFrequencies())
            {
                value += sin(f.getPhase() + f.getFrequency() * pos) * f.getAmplitude();
            }

            // Prozentanzeige wenn im DEBUG-Modus
            if( _DEBUG )
            {
                if (procent != i * 100 / (fileSize / typeSize))
                {
                    procent = i * 100 / (fileSize / typeSize);
                    String s = "";

                    final int LEN = 40;
                    for (int j = 0; j < LEN; j++)
                    {
                        if (j * 100 / LEN < procent)
                            s += "#";
                        else
                            s += "-";
                    }

                    System.out.println("generiere " + s + "  " + (double) i * 100 / (fileSize / typeSize) + "%       \r");
                }
            }
            
            builder.addValue(MassendatenProtos.Massendaten.Werte.newBuilder().setNumber(value));

            // erhöhen der Position der Abtastung
            pos += config.getAbtastrate();
        }

        System.out.println("\nFertig!\n\n");

        MassendatenProtos.Massendaten massendaten = builder.build();

        return massendaten;
    }

    public StruktdatenProtos.Struktdaten generatorDeepStructure( StruktDef struktDef )
    {
        StruktdatenProtos.Struktdaten.Builder structBuilder = StruktdatenProtos.Struktdaten.newBuilder();
        StruktdatenProtos.Struktdaten.SelAIDNameUnitID.Builder selAIDNameUnitIDBuilder = StruktdatenProtos.Struktdaten.SelAIDNameUnitID.newBuilder();
        StruktdatenProtos.Struktdaten.AIDName.Builder aIDNameBuilder = StruktdatenProtos.Struktdaten.AIDName.newBuilder();
        StruktdatenProtos.Struktdaten.LongLong.Builder longLong = StruktdatenProtos.Struktdaten.LongLong.newBuilder();

        longLong.setLow( 300 );
        longLong.setHigh( 300 );

        aIDNameBuilder.setAaName( "AName" );
        aIDNameBuilder.setAid( longLong );

        selAIDNameUnitIDBuilder.setAggregate( "Test" );
        selAIDNameUnitIDBuilder.setAidname( aIDNameBuilder );


        longLong = StruktdatenProtos.Struktdaten.LongLong.newBuilder();
        longLong.setLow( 300 );
        longLong.setHigh( 300 );
        selAIDNameUnitIDBuilder.setUnitid( longLong );

        structBuilder.addAnuSeq( selAIDNameUnitIDBuilder );

        return structBuilder.build();
    }
}
