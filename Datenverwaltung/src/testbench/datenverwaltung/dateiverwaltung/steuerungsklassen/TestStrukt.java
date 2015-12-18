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
        def.setItemAIDNameCount(5);
        def.setItemJoinDefCount(8);
        def.setItemSelItemCount(6);
        def.setItemSelOrderCount(2);
        def.setItemSelUIDCount(15);
        Struktdaten struktAlt = StruktGen.erzeugeStrukt(def);
        Struktdaten strukt = new DateiLaden().ladeStruktdaten(struktAlt.getInfo().getId());
        Printer.println(""+strukt.getCondSeqList().get(1).getValue().getValue().getU());
        Printer.println(""+strukt.getJoinSeqList().get(1).getFromAID().getLow());
        Printer.println("ID: "+strukt.getInfo().getId());
        Printer.println("ItemCounts: "+strukt.getInfo().getDef().getItemAIDNameCount());
    }
}
