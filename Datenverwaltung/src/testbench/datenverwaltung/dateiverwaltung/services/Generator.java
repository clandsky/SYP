package testbench.datenverwaltung.dateiverwaltung.services;

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

            builder.addValue(MassendatenProtos.Massendaten.Werte.newBuilder().setNumber(value));

            // erhöhen der Position der Abtastung
            pos += config.getAbtastrate();
        }

        MassendatenProtos.Massendaten massendaten = builder.build();

        return massendaten;
    }

    public StruktdatenProtos.Struktdaten generatorDeepStructure(int aIdNameCount, int joinDefCount, int selAidNameUnitIdCount, int selItemCount, int selOrderCount)
    {
        StruktdatenProtos.Struktdaten struct;
        return null;
    }
}
