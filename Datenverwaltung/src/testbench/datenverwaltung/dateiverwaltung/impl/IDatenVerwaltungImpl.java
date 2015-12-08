package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015.
 */
public class IDatenVerwaltungImpl implements IDatenVerwaltung
{
    @Override
    public MassendatenProtos.Massendaten holeMassendaten(int id)
    {
        DateiLaden dl = new DateiLaden();
        return dl.ladeMassendaten( id );

    }

    @Override
    public StruktdatenProtos.Struktdaten holeStrukturierteDaten(int id)
    {
        return null;
    }

    @Override
    public boolean schreibeMassendaten(MassendatenProtos.Massendaten m)
    {
        DateiSpeichern save = new DateiSpeichern();
        return save.speicherMasendaten(m);
    }

    @Override
    public boolean schreibeStrukturierteDaten(StruktdatenProtos.Struktdaten s)
    {
        return true;
    }

    @Override
    public MassendatenProtos.Massendaten generiereRandomMassendaten(int size)
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
        return new ArrayList<StruktInfo>();
    }

    @Override
    public ArrayList<MassenInfo> ladeMassenInfo()
    {
        return null;
    }
}
