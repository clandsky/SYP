package testbench.datenverwaltung.dateiverwaltung.service;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.messdaten.MessdatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import java.util.ArrayList;

/**
 * @author CGrings
 * @version 1.0
 */
public interface IDatenVerwaltung
{

    /**
     * holeMassendaten
     *
     * Läd die Massendaten mit der ID "id"
     *
     * @param id ID des zu ladenen Massendatensatzes
     * @return Massendatensatz
     */
    MassendatenProtos.Massendaten holeMassendaten(int id);

    /**
     * holeStrukturierteDaten
     *
     * Läd die Strukdaten mit der ID "id"
     *
     * @param id ID der zu ladenen Struktdaten
     * @return Struktdatensatz
     */
    StruktdatenProtos.Struktdaten holeStrukturierteDaten(int id);

    /**
     * schreibeMassendaten
     *
     * Speichert einen Massendatensatz
     *
     * @param m Massendaten Objekt welches geschrieben werden soll
     * @return Gibt true zurück wenn erfolgreich anderenfalls false
     */
    boolean schreibeMassendaten(MassendatenProtos.Massendaten m);

    /**
     * schreibeStrukturierteDaten
     *
     * Speichert einen Strukturdatensatz
     *
     * @param s Strukturdaten Objekt welches geschrieben werden soll
     * @return Gibt true zurück wenn erfolgreich anderenfalls false
     */
    boolean schreibeStrukturierteDaten(StruktdatenProtos.Struktdaten s);

    /**
     * generiereRandomMassendaten
     *
     * Generiert einen zufälligen Datensatz an Massendaten mit der größe "size"
     *
     * @param size Größe des zu generierenden Datensatzes
     * @return Massendaten Objekt
     */
    MassendatenProtos.Massendaten generiereRandomMassendaten(int size);

    /**
     * ladeAlleMessdaten
     *
     * Läd alle Messdaten und übergibt eine Liste dieser
     *
     * @return Liste aller Messadatensätze
     */
    ArrayList<MessdatenProtos.Messdaten> ladeAlleMessdaten();

    /**
     * ladeMessdatenByID
     *
     * Läd einen Messdatensatz anhand einer bestimmten ID
     *
     * @param id ID des zu ladenen Messdatensatzes
     * @return Messdatensatz mit der ID
     */
    MessdatenProtos.Messdaten ladeMessdatenByID(int id);

    /**
     * schreibeMessdaten
     *
     * Speichert einen Messdatensatz
     *
     * @param messdaten Zu speichernder Messdatensatz
     * @return true wenn erfolgreich gespeichert anderenfalls false
     */
    boolean schreibeMessdaten(MessdatenProtos.Messdaten messdaten);

    /**
     * ladeStruktInfo
     *
     * Läd eine Liste aller Strukturdaten die auf dem System verfügbar sind
     *
     * @return Liste aller Strukturdaten
     */
    ArrayList<StruktInfo> ladeStruktInfo();

    /**
     * ladeMassenInfo
     *
     * Läd eine Liste aller Massendaten die auf dem System verfügbar sind
     *
     * @return Liste aller Massendaten
     */
    ArrayList<MassenInfo> ladeMassenInfo();
}
