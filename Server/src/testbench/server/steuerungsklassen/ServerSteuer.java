package testbench.server.steuerungsklassen;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chrizzle Manizzle on 26.11.2015.xD
 */
public class ServerSteuer {
    IDatenVerwaltungImpl idat  = new IDatenVerwaltungImpl();
    public List<MassenInfo> ladeMassenListe() {
        //return idat.ladeMassenInfo();
        List<MassenInfo> massenList = new ArrayList<MassenInfo>();
        MassenInfo massenInfo1, massenInfo2;
        MassenDef massenDef1, massenDef2;
        Frequency freq1, freq2, freq3;
        freq1=new Frequency(60, 2, -30);
        freq2=new Frequency(90, 2, -10);
        freq3=new Frequency(10, 1, 45);

        massenDef1=new MassenDef(50);
        massenDef1.addFreqeuncy(freq1);
        massenDef2=new MassenDef(50);
        massenDef2.addFreqeuncy(freq2);
        massenDef2.addFreqeuncy(freq3);

        massenInfo1=new MassenInfo();
        massenInfo1.setId(1);
        massenInfo1.setPaketgroesseKB(1);
        massenInfo1.setPath("/massendaten/1");
        massenInfo1.setDef(massenDef1);

        massenInfo2=new MassenInfo();
        massenInfo2.setId(2);
        massenInfo2.setPaketgroesseKB(1);
        massenInfo2.setPath("/massendaten/2");
        massenInfo2.setDef(massenDef2);
        massenList.add(massenInfo1);
        massenList.add(massenInfo2);
        return massenList;

    }

    public List<StruktInfo> ladeStruktListe() {
        return idat.ladeStruktInfo();
    }

    public Massendaten ladeMassendaten (int id){
        return idat.holeMassendaten(id);
    }

    public Struktdaten ladeStruktdaten (int id){
        return idat.holeStrukturierteDaten(id);
    }

    public boolean schreibeMassendaten (Massendaten massendaten) {
        return idat.schreibeMassendaten(massendaten);
    }
    public boolean schreibeStruktdaten (Struktdaten struktdaten) {
        return idat.schreibeStrukturierteDaten(struktdaten);
    }

}
