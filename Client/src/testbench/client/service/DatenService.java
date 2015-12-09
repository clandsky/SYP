package testbench.client.service;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 07.12.2015.
 */
public class DatenService {
    private IDatenVerwaltungImpl iDat = new IDatenVerwaltungImpl();

    public List<MassenInfo> ladeMassenListe() {
        return iDat.ladeMassenInfo();
    }

    public List<StruktInfo> ladeStruktListe() {
        return iDat.ladeStruktInfo();
    }

    public Massendaten ladeMassendaten (int id){
        return iDat.holeMassendaten(id);
    }

    public Struktdaten ladeStruktdaten (int id){
        return iDat.holeStrukturierteDaten(id);
    }

    public boolean schreibeMassendaten (Massendaten massendaten) {
        return iDat.schreibeMassendaten(massendaten);
    }
    public boolean schreibeStruktdaten (Struktdaten struktdaten) {
        return iDat.schreibeStrukturierteDaten(struktdaten);
    }

    public Massendaten generiereZufallsMassendaten(int size) {
        return iDat.generiereRandomMassendaten(size);
    }
}
