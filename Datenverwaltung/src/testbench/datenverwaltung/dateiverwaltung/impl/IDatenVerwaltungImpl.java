package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015
 */
public class IDatenVerwaltungImpl implements IDatenVerwaltung
{
    private final String saveDirectory = "Protodaten/";
    private final String saveMassendatenDirectory = saveDirectory+"Massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory+"Struktdaten/";
    private final String fileName = "ByteArray";
    private final String infoFileName = "Info";

    @Override
    public Massendaten holeMassendaten(int id)
    {
        DateiLaden dl = new DateiLaden();
        return dl.ladeMassendaten( id );

    }

    @Override
    public Struktdaten holeStrukturierteDaten(int id)
    {
        return new DateiLaden().ladeStruktdaten(id);
    }

    @Override
    public boolean schreibeMassendaten(Massendaten m)
    {
        DateiSpeichern save = new DateiSpeichern();
        return save.speicherMassendaten(m);
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
        return new DateiLaden().ladeStruktInfo();
    }

    @Override
    public ArrayList<MassenInfo> ladeMassenInfo() {
        return new DateiLaden().ladeMassenInfo();
    }
}
