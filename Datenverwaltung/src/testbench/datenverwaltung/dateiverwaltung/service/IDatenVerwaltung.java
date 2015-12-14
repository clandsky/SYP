package testbench.datenverwaltung.dateiverwaltung.service;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import java.util.ArrayList;

/**
 * Created by CGrings on 07.12.2015
 */
public interface IDatenVerwaltung
{
    MassendatenProtos.Massendaten holeMassendaten( int id );
    StruktdatenProtos.Struktdaten holeStrukturierteDaten( int id );
    boolean schreibeMassendaten(MassendatenProtos.Massendaten m);
    boolean schreibeStrukturierteDaten(StruktdatenProtos.Struktdaten s);
    MassendatenProtos.Massendaten generiereRandomMassendaten( int size );
    ArrayList<Messdaten> ladeAlleMessdaten();
    boolean schreibeMessdaten( Messdaten messdaten );
    ArrayList<StruktInfo> ladeStruktInfo();
    ArrayList<MassenInfo> ladeMassenInfo();
}
