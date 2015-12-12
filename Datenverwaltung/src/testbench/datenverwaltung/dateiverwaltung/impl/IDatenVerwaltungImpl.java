package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015
 */
public class IDatenVerwaltungImpl implements IDatenVerwaltung
{
    @Override
    public Massendaten holeMassendaten(int id)
    {
        DateiLaden dl = new DateiLaden();
        return dl.ladeMassendaten( id );

    }

    @Override
    public Struktdaten holeStrukturierteDaten(int id)
    {
        return null;
    }

    @Override
    public boolean schreibeMassendaten(Massendaten m)
    {
        DateiSpeichern save = new DateiSpeichern();
        return save.speicherMasendaten(m);
    }

    @Override
    public boolean schreibeStrukturierteDaten(Struktdaten s)
    {
        return true;
    }

    @Override
    public Massendaten generiereRandomMassendaten(int size)
    {
        Generator gen = new Generator();

        int rand = (int)( Math.random() * 20 );
        MassenDef massenDef = new MassenDef( Math.random() );

        for( int i = 0; i < rand; i++ )
        {
            double frequency = Math.random() * 3;
            double amplitude = Math.random() * 3;
            double phase = Math.random() * 3;
            massenDef.getFrequencies().add( new Frequency( frequency, amplitude, phase ) );
        }

        return gen.generatorMassData( massenDef, size );
    }

    @Override
    public ArrayList<Messdaten> ladeAlleMessdaten()
    {
        return new ArrayList<Messdaten>();
    }

    @Override
    public boolean schreibeMessdaten(Messdaten messdaten)
    {
        return true;
    }

    @Override
    public ArrayList<StruktInfo> ladeStruktInfo()
    {
        // VORLÄUFIG EINGEFÜGT, DAMIT GUI FUNKTIONIERT
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
    public ArrayList<MassenInfo> ladeMassenInfo()
    {
        // VORLÄUFIG EINGEFÜGT, DAMIT GUI FUNKTIONIERT
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
}
