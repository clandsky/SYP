package testbench.datenverwaltung.dateiverwaltung.impl;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.masseninfo.MasseninfoProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.service.IDatenVerwaltung;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;
import testbench.bootloader.entities.MassenInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015
 */
public class IDatenVerwaltungImpl implements IDatenVerwaltung
{
    private final String saveDirectory = "protodaten/";
    private final String saveMassendatenDirectory = saveDirectory+"massendaten/";
    private final String saveStruktdatenDirectory = saveDirectory+"struktdaten/";
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
        return null;
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
        FileInputStream fin;
        File directory = new File(saveMassendatenDirectory);
        String[] fileNameArray;
        ArrayList<MassenInfo> massenInfoArrayList = new ArrayList<>();

        MasseninfoProtos.Masseninfo protoInfo;
        MassenInfo mInfo;
        MassenDef mDef;
        ArrayList<Frequency> frequencyList;

        if(directory.exists()) {
            fileNameArray = directory.list();

            for(int i=0 ; i<fileNameArray.length ; i++) {
                File mInfoFile = new File(saveMassendatenDirectory+fileNameArray[i]+"/"+infoFileName+".protobyte");
                frequencyList = new ArrayList<>();

                try {
                    fin = new FileInputStream(mInfoFile);
                    byte fileContent[] = new byte[(int) mInfoFile.length()];
                    fin.read(fileContent);
                    protoInfo = MasseninfoProtos.Masseninfo.parseFrom(fileContent);

                    for(MasseninfoProtos.Masseninfo.Frequency frequency : protoInfo.getDef().getFrequencyList()) {
                        frequencyList.add(new Frequency(frequency.getFrequency(),frequency.getAmplitude(),frequency.getPhase()));
                    }
                    mDef = new MassenDef(protoInfo.getDef().getAbtastrate(), frequencyList);
                    mInfo = new MassenInfo(protoInfo.getId(),protoInfo.getPaketGroesseKB(),protoInfo.getPath(),mDef);
                    massenInfoArrayList.add(mInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return massenInfoArrayList;
    }
}
