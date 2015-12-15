package testbench.server.dummy;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;

import java.util.ArrayList;

/**
 * Created by Huskey on 09.12.2015.
 */
public class IDatenverwaltungImpl implements IDatenVerwaltung{
    IDatenVerwaltungImpl idat = new IDatenVerwaltungImpl();
    @Override
    public MassendatenProtos.Massendaten holeMassendaten(int id) {
        return idat.holeMassendaten(id);
    }

    @Override
    public StruktdatenProtos.Struktdaten holeStrukturierteDaten(int id) {
        return idat.holeStrukturierteDaten(id);
    }

    @Override
    public boolean schreibeMassendaten(MassendatenProtos.Massendaten m) {
        return true;
    }

    @Override
    public boolean schreibeStrukturierteDaten(StruktdatenProtos.Struktdaten s) {
        return true;
    }

    @Override
    public MassendatenProtos.Massendaten generiereRandomMassendaten(int size) {
        return idat.generiereRandomMassendaten(size);
    }

    @Override
    public ArrayList<Messdaten> ladeAlleMessdaten() {
        return idat.ladeAlleMessdaten();
    }

    @Override
    public boolean schreibeMessdaten(Messdaten messdaten) {
        return true;
    }

    @Override
    public ArrayList<StruktInfo> ladeStruktInfo() {
        ArrayList<StruktInfo> liste=new ArrayList<StruktInfo>();
        StruktInfo struktInfo1, struktInfo2, struktInfo3;
        StruktDef struktDef1, struktDef2,struktDef3;
        struktDef1=new StruktDef(4,1,2,5,2);
        struktDef2=new StruktDef(2,3,3,1,1);
        struktDef3=new StruktDef(3,4,4,4,2);
        struktInfo1=new StruktInfo();
        struktInfo1.setDef(struktDef1);
        struktInfo1.setId(1);
        struktInfo1.setPath("/struktdaten/1");
        struktInfo1=new StruktInfo(1, "/struktdaten/1",struktDef1);
        struktInfo2=new StruktInfo(2, "/struktdaten/2",struktDef2);
        struktInfo3=new StruktInfo(3, "/struktdaten/3",struktDef3);
        liste.add(struktInfo1);
        liste.add(struktInfo2);
        liste.add(struktInfo3);
        return liste;
    }

    @Override
    public ArrayList<MassenInfo> ladeMassenInfo() {
        ArrayList<MassenInfo> massenList = new ArrayList<MassenInfo>();
        MassenInfo massenInfo1, massenInfo2;
        MassenDef massenDef1, massenDef2;
        Frequency freq1, freq2, freq3;
        freq1=new Frequency(60, 2, -30);
        freq2=new Frequency(90, 2, -10);
        freq3=new Frequency(10, 1, 45);

        massenDef1=new MassenDef(50);
        massenDef1.getFrequencies().add(freq1);
        massenDef2=new MassenDef(50);
        massenDef2.getFrequencies().add(freq2);
        massenDef2.getFrequencies().add(freq3);

        massenInfo1=new MassenInfo();
        massenInfo1.setId(1);
        massenInfo1.setPaketGroesseKB(1);
        massenInfo1.setDef(massenDef1);

        massenInfo2=new MassenInfo();
        massenInfo2.setId(2);
        massenInfo2.setPaketGroesseKB(1);
        massenInfo2.setDef(massenDef2);
        massenList.add(massenInfo1);
        massenList.add(massenInfo2);
        return massenList;
    }
}

