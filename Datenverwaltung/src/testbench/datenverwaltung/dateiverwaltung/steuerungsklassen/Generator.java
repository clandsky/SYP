package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;

import static java.lang.Math.*;

/**
 * Created by CGrings on 29.11.2015
 */
public class Generator
{
    private final boolean _DEBUG = true;

    public Massendaten generatorMassData(MassenDef config, int fileSize)
    {

        double pos = 0.0f;
        int typeSize = 8;

        int procent = 0;

        Massendaten.Builder massendatenBuilder = Massendaten.newBuilder();
        Massendaten.MassenInfo.Builder massenInfoBuilder = Massendaten.MassenInfo.newBuilder();
        Massendaten.MassenDef.Builder massenDefBuilder = Massendaten.MassenDef.newBuilder();

        for(Frequency f : config.getFrequencies()) {
            Massendaten.Frequency.Builder freqBuilder = Massendaten.Frequency.newBuilder();
            freqBuilder.setFrequency(f.getFrequency());
            freqBuilder.setAmplitude(f.getAmplitude());
            freqBuilder.setPhase(f.getPhase());
            massenDefBuilder.addFrequency(freqBuilder);
        }

        massenDefBuilder.setAbtastrate(config.getAbtastrate());
        massenInfoBuilder.setDef(massenDefBuilder);

        long longWert; //für progressbar
        double temp; //für progressbar

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
            if (_DEBUG)
            {
                longWert = (long)(i)*100;
                temp = longWert / (fileSize / typeSize);
                if (procent != temp)
                {
                    procent = (int)temp;
                    Printer.printProgressBar((int)temp, 0.5f);
                }
            }
            
            massendatenBuilder.addValue(Massendaten.Werte.newBuilder().setNumber(value));

            // erhöhen der Position der Abtastung
            pos += config.getAbtastrate();
        }

        Printer.printProgressBar(100, 0.5f);

        // 1: einmal builden, um die serializesSize zu bekommen
        Massendaten tempMdaten = massendatenBuilder.build();

        // 2: serializesSize in info eintragen
        massenInfoBuilder.setPaketGroesseKB(tempMdaten.getSerializedSize()/1000);
        massendatenBuilder.setInfo(massenInfoBuilder);

        // 3: nochmal builden
        return massendatenBuilder.build();
    }

    public Struktdaten generatorDeepStructure(StruktDef struktDef)
    {
        Struktdaten.Builder structBuilder = Struktdaten.newBuilder();
        Struktdaten.SelAIDNameUnitID.Builder selAIDNameUnitIDBuilder = Struktdaten.SelAIDNameUnitID.newBuilder();
        Struktdaten.AIDName.Builder aIDNameBuilder = Struktdaten.AIDName.newBuilder();
        Struktdaten.LongLong.Builder longLong = Struktdaten.LongLong.newBuilder();

        longLong.setLow(300);
        longLong.setHigh(300);

        aIDNameBuilder.setAaName("AName");
        aIDNameBuilder.setAid(longLong);

        selAIDNameUnitIDBuilder.setAggregate("Test");
        selAIDNameUnitIDBuilder.setAidname(aIDNameBuilder);


        longLong = Struktdaten.LongLong.newBuilder();
        longLong.setLow(300);
        longLong.setHigh(300);
        selAIDNameUnitIDBuilder.setUnitid(longLong);

        structBuilder.addAnuSeq(selAIDNameUnitIDBuilder);

        return structBuilder.build();
    }
}
