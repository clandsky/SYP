package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;
import testbench.datenverwaltung.dateiverwaltung.gui.GeneratorGuiProgress;

import javax.swing.*;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.*;

/**
 * @author CGrings
 * @version 1.0
 */
public class Generator
{
    /**
     * Schaltet die Debugausgaben ein
     */
    private final boolean _DEBUG = true;

    /**
     * generatorMassData
     *
     * Generiert Massendaten die durch "config" und "fileSize" definiert werden.
     *
     * @param config Massendaten Konfigurationsdatei
     * @param fileSize Größe der Nutzdaten in Byte
     * @return Protobuf Builder für Massendaten
     */
    public Massendaten generatorMassData(final MassenDef config, final int fileSize)
    {
        Massendaten mDaten;

        Massendaten.Builder massendatenBuilder = Massendaten.newBuilder();

        ProgressThread thread = new ProgressThread();
        thread.setPorgress( new AtomicInteger( 0 ) );
        thread.start();


        double pos = 0.0f;
        int typeSize = 11;

        int procent = 0;

        Massendaten.MassenInfo.Builder massenInfoBuilder = Massendaten.MassenInfo.newBuilder();
        Massendaten.MassenDef.Builder massenDefBuilder = Massendaten.MassenDef.newBuilder();

        for (Frequency f : config.getFrequencies())
        {
            Massendaten.Frequency.Builder freqBuilder = Massendaten.Frequency.newBuilder();
            freqBuilder.setFrequency(f.getFrequency());
            freqBuilder.setAmplitude(f.getAmplitude());
            freqBuilder.setPhase(f.getPhase());
            massenDefBuilder.addFrequency(freqBuilder);
        }

        massenDefBuilder.setAbtastrate(config.getAbtastrate());
        massenInfoBuilder.setDef(massenDefBuilder);

        long longWert; //für progressbar
        double temp;

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
            longWert = (long) (i) * 100;
            temp = longWert / (fileSize / typeSize);
            if (procent != temp)
            {
                thread.setPorgress( new AtomicInteger( (int)temp ) );
                procent = (int) temp;
            }

            massendatenBuilder.addValue(Massendaten.Werte.newBuilder().setNumber(value));

            // erhöhen der Position der Abtastung
            pos += config.getAbtastrate();
        }

        // 1: einmal builden, um die serializedSize zu bekommen
        Massendaten tempMdaten = massendatenBuilder.build();

        // 2: serializedSize und id in info eintragen
        int hashID = System.identityHashCode(tempMdaten.getValueList());
        massenInfoBuilder.setId(hashID);
        massenInfoBuilder.setPaketGroesseKB(tempMdaten.getSerializedSize());
        Printer.println("size: " + tempMdaten.getSerializedSize());
        massendatenBuilder.setInfo(massenInfoBuilder);

        thread.setPorgress( new AtomicInteger( 100 ) );
        thread.killProgress();

        mDaten = massendatenBuilder.build();

        return mDaten;
    }

    /**
     * generatorDeepStructure
     *
     * Generiert Struktdaten die durch "struktDef" definiert werden.
     *
     * @param struktDef Definition welche Anzahl an Datensätzen generiert werden soll
     * @return Protobuf Builder für Struktdaten
     */
    public Struktdaten generatorDeepStructure(StruktDef struktDef)
    {
        ProgressThread thread = new ProgressThread();
        thread.setPorgress( new AtomicInteger( 0 ) );
        thread.start();

        Struktdaten.Builder structBuilder = Struktdaten.newBuilder();

        Struktdaten.SelAIDNameUnitID.Builder selAIDNameUnitIDBuilder;
        Struktdaten.AIDName.Builder aIDNameBuilder;
        Struktdaten.LongLong.Builder longLong;
        Struktdaten.JoinDef.Builder joinDef;
        Struktdaten.SelOrder.Builder selOrder;
        Struktdaten.SelItem.Builder selItem;
        Struktdaten.SelValueExt.Builder selValueExt;
        Struktdaten.TS_Value.Builder ts_Value;
        Struktdaten.AIDNameUnitID.Builder aIDNameUnitID;

        // Erzeugen der ItemAIDName Elementen
        for (int i = 0; i < struktDef.getItemAIDNameCount(); i++)
        {
            thread.setPorgress( new AtomicInteger( i * 20 / struktDef.getItemAIDNameCount() ) );

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(523300 + i);
            longLong.setHigh(315100 + i);

            aIDNameBuilder = Struktdaten.AIDName.newBuilder();
            aIDNameBuilder.setAaName("AName");
            aIDNameBuilder.setAid(longLong);

            selAIDNameUnitIDBuilder = Struktdaten.SelAIDNameUnitID.newBuilder();
            selAIDNameUnitIDBuilder.setAggregate("Test");
            selAIDNameUnitIDBuilder.setAidname(aIDNameBuilder);

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(302350+i);
            longLong.setHigh(6235300 + i);
            selAIDNameUnitIDBuilder.setUnitid(longLong);

            structBuilder.addAnuSeq(selAIDNameUnitIDBuilder);
        }

        // Erzeugen der JoinDef Elementen
        for (int i = 0; i < struktDef.getItemJoinDefCount(); i++)
        {
            thread.setPorgress( new AtomicInteger( 20 + i * 20 / struktDef.getItemAIDNameCount() ) );

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(200);
            longLong.setHigh(200);

            joinDef = Struktdaten.JoinDef.newBuilder();
            joinDef.setFromAID(longLong);

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(654200 + i);
            longLong.setHigh(203440 + i);

            joinDef.setToAID(longLong);

            joinDef.setJoiningType("JoiningType");
            joinDef.setRefName("RefName");

            structBuilder.addJoinSeq(joinDef);
        }

        // Erzeugen der SelOrder Elemente
        for (int i = 0; i < struktDef.getItemSelOrderCount(); i++)
        {
            thread.setPorgress( new AtomicInteger( 40 + i * 20 / struktDef.getItemAIDNameCount() ) );

            selOrder = Struktdaten.SelOrder.newBuilder();

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(203470 + i);
            longLong.setHigh(2078760 + i);

            aIDNameBuilder = Struktdaten.AIDName.newBuilder();
            aIDNameBuilder.setAaName("AName");
            aIDNameBuilder.setAid(longLong);

            selOrder.setAttr(aIDNameBuilder);
            selOrder.setAscending(true);

            structBuilder.addOrderBy(selOrder);
        }

        // Erzeugen der SelUID Elemente
        for (int i = 0; i < struktDef.getItemSelUIDCount(); i++)
        {
            thread.setPorgress( new AtomicInteger( 60 + i * 20 / struktDef.getItemAIDNameCount() ) );

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(2062440 + i);
            longLong.setHigh(25600 + i);

            aIDNameBuilder = Struktdaten.AIDName.newBuilder();
            aIDNameBuilder.setAaName("AName");
            aIDNameBuilder.setAid(longLong);

            structBuilder.addGroupBy(aIDNameBuilder);
        }

        // Erzeugen der SelItem Elemente
        for (int i = 0; i < struktDef.getItemSelItemCount(); i++)
        {
            thread.setPorgress( new AtomicInteger( 80 + i * 20 / struktDef.getItemAIDNameCount() ) );

            selItem = Struktdaten.SelItem.newBuilder();

            ts_Value = Struktdaten.TS_Value.newBuilder();
            ts_Value.setU("Unit");
            ts_Value.setFlag(0x8001);

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(30034 + i);
            longLong.setHigh(300526 + i);

            aIDNameBuilder = Struktdaten.AIDName.newBuilder();
            aIDNameBuilder.setAaName("AName");
            aIDNameBuilder.setAid(longLong);

            aIDNameUnitID = Struktdaten.AIDNameUnitID.newBuilder();
            aIDNameUnitID.setAttr(aIDNameBuilder);

            longLong = Struktdaten.LongLong.newBuilder();
            longLong.setLow(20);
            longLong.setHigh(200);
            aIDNameUnitID.setUnitID(longLong);

            selValueExt = Struktdaten.SelValueExt.newBuilder();
            selValueExt.setValue(ts_Value);
            selValueExt.setAttr(aIDNameUnitID);

            selItem.setValue(selValueExt);
            selItem.setOperator("Oper");
        }

        // Erzeugen der Informationen zu dieser Struktur
        StruktdatenProtos.Struktdaten.StruktInfo.Builder info = StruktdatenProtos.Struktdaten.StruktInfo.newBuilder();

        StruktdatenProtos.Struktdaten.StruktInfo.StruktDef.Builder def = StruktdatenProtos.Struktdaten.StruktInfo.StruktDef.newBuilder();
        def.setItemAIDNameCount(struktDef.getItemAIDNameCount());
        def.setItemSelUIDCount(struktDef.getItemSelUIDCount());
        def.setItemSelOrderCount(struktDef.getItemSelOrderCount());
        def.setItemSelItemCount(struktDef.getItemSelItemCount());
        def.setItemJoinDefCount(struktDef.getItemJoinDefCount());
        info.setDef(def);

        info.setSize(structBuilder.build().getSerializedSize());
        info.setId((int) (Calendar.getInstance().getTime().getTime() / 2000));
        structBuilder.setInfo(info);

        thread.setPorgress( new AtomicInteger( 100 ) );
        thread.killProgress();

        // Rückgabe der kompletten Struktur
        return structBuilder.build();
    }
}
