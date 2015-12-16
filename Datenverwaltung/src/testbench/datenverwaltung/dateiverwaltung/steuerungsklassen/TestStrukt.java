package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

/**
 * Created by Huskey on 16.12.2015.
 */
public class TestStrukt {
    public static void main (String args[])
    {
        StruktDef def = new StruktDef();
        def.setItemAIDNameCount(10);
        def.setItemJoinDefCount(10);
        def.setItemSelItemCount(10);
        def.setItemSelOrderCount(10);
        def.setItemSelUIDCount(10);
        StruktGen.erzeugeStrukt(def);
        Struktdaten strukt = DateiLaden.ladeStruktdaten(12345);
        Printer.println(strukt.getCondSeqList().get(1).getValue().getValue().getU());
        Printer.println(""+strukt.getJoinSeqList().get(9).getFromAID().getLow());
    }
}
