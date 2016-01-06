package testbench.server.steuerungsklassen;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;

import java.util.List;

/**
 * Behandelt die Anfragen des Restservers. In diesem Fall diese lediglich an die IDatenverwaltungImpl weitergegeben
 */
public class ServerSteuer {
    /** Objekt der Datenverwaltung zur Anforderung der Daten.*/
    IDatenVerwaltungImpl idat  = new IDatenVerwaltungImpl();

    /**
     * Lädt die Liste aller verfügbaren Massendaten (bzw. deren Beschreibungen) auf dem Server über die IDatenVerwaltung.
     * @return Liste der MassenInfos.
     */
    public List<MassenInfo> ladeMassenListe() {
        return idat.ladeMassenInfo();
    }


    /**
     * Lädt die Liste aller verfügbaren Struktdaten (bzw. deren Beschreibungen) auf dem Server über die IDatenVerwaltung.
     * @return Liste der StruktInfos.
     */
    public List<StruktInfo> ladeStruktListe() {
        return idat.ladeStruktInfo();
    }

    /**
     * Lädt Massendaten per ID aus der Datenverwaltung
     * @param id ID der angeforderten Massendaten
     * @return Massendaten
     */
    public Massendaten ladeMassendaten (int id){
        return idat.holeMassendaten(id);
    }

    /**
     * Lädt Struktdaten per ID aus der Datenverwaltung
     * @param id ID der angeforderten Struktdaten
     * @return Struktdaten
     */
    public Struktdaten ladeStruktdaten (int id){
        return idat.holeStrukturierteDaten(id);
    }

    /**
     * Gibt Massendaten zum speichern an die Datenverwaltung weiter
     * @param massendaten Massendaten, welche gespeichert werden sollen
     * @return true=Erfolg
     */
    public boolean schreibeMassendaten (Massendaten massendaten) {
        return idat.schreibeMassendaten(massendaten);
    }
    /**
     * Gibt Struktdaten zum speichern an die Datenverwaltung weiter
     * @param struktdaten Struktdaten, welche gespeichert werden sollen
     * @return true=Erfolg
     */
    public boolean schreibeStruktdaten (Struktdaten struktdaten) {
        return idat.schreibeStrukturierteDaten(struktdaten);
    }



}
