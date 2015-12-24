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
 * Created by Sven Riedel on 07.12.2015
 */
public class DatenService {
    private IDatenVerwaltungImpl iDat = new IDatenVerwaltungImpl();

    /**
     * Diese Methode liefert eine Liste aller lokalen Massendaten
     * als MassenInfo-Liste zurueck.
     * @return Liste aller Massendaten als MassenInfo-Liste
     */
    public List<MassenInfo> ladeMassenListe() {
        return iDat.ladeMassenInfo();
    }

    /**
     * Diese Methode liefert eine Liste aller lokalen Struktdaten
     * als StruktInfo-Liste zurueck.
     * @return Liste aller Struktdaten als StruktInfo-Liste
     */
    public List<StruktInfo> ladeStruktListe() {
        return iDat.ladeStruktInfo();
    }

    /**
     * Diese Methode laedt Massendaten mit einer bestimmten ID und liefert diese zurueck.
     * Dies geschieht durch Aufruf der Schnittstellen-Methode ladeMassendaten()
     * in IDatenverwaltungIMPL.
     * @param id ID der zu ladenden Daten.
     * @return Die geladenen Massendaten. Null wenn Massendaten mit gegebener ID nicht vorhanden.
     */
    public Massendaten ladeMassendaten (int id){
        return iDat.holeMassendaten(id);
    }

    /**
     * Diese Methode laedt Struktdaten mit einer bestimmten ID und liefert diese zurueck.
     * Dies geschieht durch Aufruf der Schnittstellen-Methode ladeStrukturiertedaten()
     * in IDatenverwaltungIMPL.
     * @param id ID der zu ladenden Daten.
     * @return Die geladenen Struktdaten. Null wenn Struktdaten mit gegebener ID nicht vorhanden.
     */
    public Struktdaten ladeStruktdaten (int id){
        return iDat.holeStrukturierteDaten(id);
    }

    /**
     * Diese Methode speichert die gegebenen Massendaten auf die Festplatte.
     * Dies geschieht durch Aufruf der Schnittstellen-Methode schreibeMassendaten()
     * in IDatenverwaltungIMPL.
     * @param massendaten Die zu speichernden Daten.
     * @return Wenn erfolgreich: True. Sonst: False.
     */
    public boolean schreibeMassendaten (Massendaten massendaten) {
        return iDat.schreibeMassendaten(massendaten);
    }

    /**
     * Diese Methode speichert die gegebenen Struktdaten auf die Festplatte.
     * Dies geschieht durch Aufruf der Schnittstellen-Methode schreibeStrukturierteDaten()
     * in IDatenverwaltungIMPL.
     * @param struktdaten Die zu speichernden Daten.
     * @return Wenn erfolgreich: True. Sonst: False.
     */
    public boolean schreibeStruktdaten (Struktdaten struktdaten) {
        return iDat.schreibeStrukturierteDaten(struktdaten);
    }

    /**
     * Diese Methode liefert zufaellig generierte Massendaten mit Hilfe zurueck.
     * Dies geschieht durch Aufruf der Schnittstellen-Methode generiereRandomMassendaten()
     * in IDatenverwaltungIMPL.
     * @param size Groeße in KiloByte, die die generierten Daten haben sollen.
     * @return Die generierten Massendaten.
     */
    public Massendaten generiereZufallsMassendaten(int size) {
        return iDat.generiereRandomMassendaten(size);
    }
}
