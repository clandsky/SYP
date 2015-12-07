package testbench.server.steuerungsklassen;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;

import java.util.List;

/**
 * Created by Chrizzle Manizzle on 26.11.2015.xD
 */
public class ServerSteuer {
    IDatenVerwaltungImpl idat  = new IDatenVerwaltungImpl();
    public List<MassenInfo> ladeMassenListe() {
        return idat.ladeMassenInfo();
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
