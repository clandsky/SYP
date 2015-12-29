package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.messdaten.MessdatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CGrings on 07.12.2015
 */
public class IDatenVerwaltungImpl implements IDatenVerwaltung
{
    private DateiLaden dateiLaden = new DateiLaden();
    private DateiSpeichern dateiSpeichern = new DateiSpeichern();

    public IDatenVerwaltungImpl() {
        dateiLaden = new DateiLaden();
        dateiSpeichern = new DateiSpeichern();
    }

    @Override
    public Massendaten holeMassendaten(int id)
    {
        return dateiLaden.ladeMassendaten( id );
    }

    @Override
    public Struktdaten holeStrukturierteDaten(int id)
    {
        return dateiLaden.ladeStruktdaten(id);
    }

    @Override
    public boolean schreibeMassendaten(Massendaten m)
    {
        return dateiSpeichern.speicherMassendaten(m);
    }

    @Override
    public boolean schreibeStrukturierteDaten(Struktdaten s)
    {
        return dateiSpeichern.speicherStruktdaten(s);
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
    public ArrayList<MessdatenProtos.Messdaten> ladeAlleMessdaten()
    {
        return new ArrayList<>(dateiLaden.ladeMessdatenListe());
    }

    @Override
    public MessdatenProtos.Messdaten ladeMessdatenByID(int id) {
        return dateiLaden.ladeMessdaten(id);
    }

    @Override
    public boolean schreibeMessdaten(MessdatenProtos.Messdaten messdaten)
    {
        return dateiSpeichern.speicherMessdaten(messdaten);
    }

    @Override
    public ArrayList<StruktInfo> ladeStruktInfo()
    {
        return dateiLaden.ladeStruktInfo();
    }

    @Override
    public ArrayList<MassenInfo> ladeMassenInfo() {
        return dateiLaden.ladeMassenInfo();
    }
}
