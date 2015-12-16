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
            JoinDef joinDef = erzeugeJoinDef(i, erzeugeLongLong(10000,99999),erzeugeLongLong(123455,321156));
            strukt.addJoinSeq(i, joinDef);
        }
        for(int i=0; i<def.getItemAIDNameCount();i++)
        {
            AIDName aidname = erzeugeAIDName("AIDName "+i,erzeugeLongLong(1234,5678));
            strukt.addGroupBy(i, aidname);
        }

        return strukt.build();
    }

    private JoinDef erzeugeJoinDef(int number, LongLong from, LongLong to) {
        JoinDef.Builder builder= Struktdaten.JoinDef.newBuilder();
        builder.setFromAID(from);
        builder.setToAID(to);
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

    private AIDName erzeugeAIDName(String aaName, LongLong aid)
    {
        AIDName.Builder builder = AIDName.newBuilder();
        builder.setAaName(aaName);
        builder.setAid(aid);
        return builder.build();
    }
}
