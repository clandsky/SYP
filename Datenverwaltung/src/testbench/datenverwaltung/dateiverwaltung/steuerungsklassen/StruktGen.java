package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.*;
/**
 * Created by Chrizzle Manizzle on 15.12.2015.
 */
public class StruktGen {
    public Struktdaten erzeugeStrukt(StruktDef def){
        Struktdaten.Builder strukt = Struktdaten.newBuilder();
        for(int i=0; i<def.getItemJoinDefCount();i++)
        {
            Struktdaten.JoinDef joinDef = erzeugeJoinDef(i);
        }
        return strukt.build();
    }

    private JoinDef erzeugeJoinDef(int number) {
        JoinDef.Builder builder= Struktdaten.JoinDef.newBuilder();
        builder.setFromAID(erzeugeLongLong(10000,999999));
        builder.setToAID(erzeugeLongLong(123445,321156));
        builder.setRefName("JoinDef Refname "+number);
        builder.setJoiningType("JoinDef JoiningType "+number);


        return builder.build();
    }

    private LongLong erzeugeLongLong(long i, long i1) {
        LongLong.Builder builder = LongLong.newBuilder();
        builder.setHigh(i);
        builder.setLow(i1);
        return builder.build();
    }
}
