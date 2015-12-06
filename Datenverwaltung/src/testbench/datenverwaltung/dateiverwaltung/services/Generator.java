package testbench.datenverwaltung.dateiverwaltung.services;

import com.sun.javafx.binding.StringFormatter;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.datenverwaltung.dateiverwaltung.grenz.MassenDef;
import testbench.datenverwaltung.dateiverwaltung.grenz.Frequency;

import static java.lang.Math.*;

/**
 * Created by CGrings on 29.11.2015.
 */
public class Generator
{
    final boolean _DEBUG = true;

    /***
     * Generiert anhand der
     *
     * @param sampleRate
     * @param frequencies
     * @param fileSize
     * @return
     */
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

    public StruktdatenProtos.Struktdaten generatorDeepStructure(int aIdNameCount, int joinDefCount, int selAidNameUnitIdCount, int selItemCount, int selOrderCount) throws Exception
    {
        StruktdatenProtos.Struktdaten.Builder structBuilder = StruktdatenProtos.Struktdaten.newBuilder();
        StruktdatenProtos.Struktdaten.SelAIDNameUnitID.Builder selAIDNameUnitIDBuilder = StruktdatenProtos.Struktdaten.SelAIDNameUnitID.newBuilder();

        selAIDNameUnitIDBuilder.setAggregate( "Test" );
        selAIDNameUnitIDBuilder.setAidname()

        structBuilder.addAnuSeq( selAIDNameUnitIDOrBuilder );

        return structBuilder.build();

        throw new Exception( "DeepStructure: NOT WORKING BY NOW!" );
    }
}
