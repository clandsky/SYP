package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.*;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/**
 * Created by Chrizzle Manizzle on 15.12.2015.
 */
@Deprecated
public class StruktGen {
    public static Struktdaten erzeugeStrukt(StruktDef def){
        Builder builder = Struktdaten.newBuilder();
        for(int i=0; i<def.getItemJoinDefCount();i++)
        {
            /*  Kann auch per StruktDef oder per Grenzklassen gefüllt werden,
                sofern diese implementiert sind. Testweise werden hier die Longs und Strings
                direkt mit irgendwelchen Werten gefüllt.
                Später dann: long fromHigh = StruktGrenz.JoiningType.getLong() oder so ähnlich ;)
                Dies gilt auch für die anderen Schleifen!
             */
            String joiningType = "JoinDef JoiningType "+i;
            String refName = "JoinDef RefName "+i;
            long fromHigh = 10000;
            long fromLow = 99999+i;
            long toHigh = 123455;
            long toLow = 321156+i;
            JoinDef joinDef = erzeugeJoinDef(fromHigh,fromLow,toHigh,toLow,refName,joiningType);
            builder.addJoinSeq(i, joinDef);
        }
        for(int i=0; i<def.getItemAIDNameCount();i++)
        {
            String aaName = "AIDName "+i;
            long high = 1234;
            long low = 5678+i;
            AIDName aidname = erzeugeAIDName(aaName,erzeugeLongLong(high,low));

            builder.addGroupBy(i, aidname);
        }
        for(int i=0; i<def.getItemSelItemCount();i++)
        {
            String operator = "SelItem operator "+i;
            String oper = "SelValueExt oper "+i;
            String u = "TS_VALUE u "+i;
            short flag = (short) (1+i);
            long unitIDHigh = 123456;
            long unitIDLow = 456789+i;
            long aidLow = 987654+i;
            long aidHigh = 1234;
            String aaName="SelItem aaName "+i;
            SelItem selItem = erzeugeSelItem(operator,oper,u,flag,unitIDHigh,unitIDLow,aidHigh,aidLow,aaName);
            builder.addCondSeq(i, selItem);
        }
        for(int i=0; i<def.getItemSelOrderCount();i++)
        {
            String aaName="SelOrder aaName "+i;
            boolean ascending = true;
            long aidHigh = 89153;
            long aidLow = 4891155+i;
            SelOrder selOrder = erzeugeSelOrder(ascending,aidHigh,aidLow,aaName);
            builder.addOrderBy(i, selOrder);
        }
        for(int i=0; i<def.getItemSelUIDCount();i++)
        {
            String aggregate= "SelUnitID aggregate "+i;
            String aaName = "SelUnitID aaName "+i;
            long aidHigh = 11111;
            long aidLow = 222222+i;
            long unitIDHigh = 3333333;
            long unitIDLow = 444+i;
            SelAIDNameUnitID unit= erzeugeSelNameUnitID(aggregate,unitIDHigh,unitIDLow,aidHigh,aidLow,aaName);
            builder.addAnuSeq(i, unit);
        }


        StruktInfo.StruktDef.Builder defBuilder = StruktInfo.StruktDef.newBuilder()
                .setItemAIDNameCount(def.getItemAIDNameCount())
                .setItemJoinDefCount(def.getItemJoinDefCount())
                .setItemSelItemCount(def.getItemSelItemCount())
                .setItemSelOrderCount(def.getItemSelOrderCount())
                .setItemSelUIDCount(def.getItemSelUIDCount());

        int size = builder.build().getSerializedSize();
        StruktInfo.Builder infobuilder = StruktInfo.newBuilder().setDef(defBuilder.build()).setId((int)(Calendar.getInstance().getTime().getTime()/2000)).setSize(size);
        builder.setInfo(infobuilder.build());
        Struktdaten strukt = builder.build();
        new DateiSpeichern().speicherStruktdaten(strukt);
        return strukt;
    }

    private static SelAIDNameUnitID erzeugeSelNameUnitID(String aggregate, long unitIDHigh, long unitIDLow, long aidHigh, long aidLow, String aaName) {
        SelAIDNameUnitID.Builder builder = SelAIDNameUnitID.newBuilder();
        builder.setAggregate(aggregate);
        builder.setUnitid(erzeugeLongLong(unitIDHigh,unitIDLow));
        builder.setAidname(erzeugeAIDName(aaName, erzeugeLongLong(aidHigh,aidLow)));
        return builder.build();
    }

    private static SelOrder erzeugeSelOrder(boolean ascending,long aidHigh, long aidLow, String aaName) {
        SelOrder.Builder builder = SelOrder.newBuilder();
        builder.setAscending(ascending);
        builder.setAttr(erzeugeAIDName(aaName,erzeugeLongLong(aidHigh,aidLow)));
        return builder.build();

    }

    private static SelItem erzeugeSelItem(String operator, String oper, String u, short flag, long unitIDHigh, long unitIDLow, long aidHigh, long aidlow, String aaName) {
        SelItem.Builder builder = SelItem.newBuilder();

        builder.setValue(erzeugeSelValueExt(
                erzeugeAIDNameUnitID(
                        erzeugeAIDName(aaName, erzeugeLongLong(aidHigh,aidlow)),
                        erzeugeLongLong(unitIDHigh,unitIDLow)),
                erzeugeTSValue(flag,u),
                oper)
        );

        builder.setOperator(operator);
        return builder.build();
    }

    private static AIDNameUnitID erzeugeAIDNameUnitID(AIDName aidname, LongLong longLong) {
        AIDNameUnitID.Builder builder = AIDNameUnitID.newBuilder();
        builder.setAttr(aidname);
        builder.setUnitID(longLong);
        return builder.build();
    }

    private static SelValueExt erzeugeSelValueExt (AIDNameUnitID unit, TS_Value value, String oper){
        SelValueExt.Builder builder = SelValueExt.newBuilder();
        builder.setAttr(unit);
        builder.setValue(value);
        builder.setOper(oper);
        return builder.build();
    }

    private static JoinDef erzeugeJoinDef(long fromHigh,long fromLow,long toHigh, long toLow, String refname, String joiningtype) {
        JoinDef.Builder builder= Struktdaten.JoinDef.newBuilder();
        builder.setFromAID(erzeugeLongLong(fromHigh,fromLow));
        builder.setToAID(erzeugeLongLong(fromHigh,toHigh));
        builder.setRefName(refname);
        builder.setJoiningType(joiningtype);


        return builder.build();
    }

    private static AIDName erzeugeAIDName(String aaName, LongLong aid)
    {
        AIDName.Builder builder = AIDName.newBuilder();
        builder.setAaName(aaName);
        builder.setAid(aid);
        return builder.build();
    }

    private static TS_Value erzeugeTSValue (short flag, String u)
    {
        TS_Value.Builder builder = TS_Value.newBuilder();
        builder.setFlag((int)flag);
        builder.setU(u);
        return builder.build();
    }

    private static LongLong erzeugeLongLong(long high, long low) {
        LongLong.Builder builder = LongLong.newBuilder();
        builder.setHigh(high);
        builder.setLow(low);
        return builder.build();
    }
}
