package testbench.client.steuerungsklassen;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.client.HTTPClient;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
import testbench.client.gui.ProgressBarWindow;
import testbench.client.service.ClientConfig;
import testbench.client.service.DatenService;

import javax.swing.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015
 */

public class ClientSteuer {
    public boolean isDebugMode = ClientConfig.getExemplar().getDebugMode();
    private HTTPClient httpClient;
    private DatenService dServe;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
        dServe = new DatenService();
    }

    /**
     *
     * @return
     */
    public List<Messdaten> holeMessdaten() {
        return null;
    }

    /**
     * Diese Methode versucht, Massendaten mit gegebener ID vom Server zu laden.
     * Dazu wird die empfangeMassendaten(int id) Methode in HTTPClient aufgerufen.
     * Die empfangenen Daten werden dann automatisch in MassendatenGrenz umgewandelt.
     * @param id Die ID der zu empfangenen Massendaten
     * @return Die empfangenen Massendaten als MassendatenGrenz. Sonst null.
     */
    public MassendatenGrenz empfangeMassendaten(int id) {
        Printer.println("Empfange Massendaten mit ID: "+id);

        Massendaten m = httpClient.empfangeMassendaten(id);

        if(m != null) {
            dServe.schreibeMassendaten(m);
            Printer.println("Paketgroeße in KB: "+m.getInfo().getPaketGroesseKB());
            return new MassendatenGrenz(m);
        }
        return null;
    }

    /**
     * Diese Methode versucht, Struktdaten mit gegebener ID vom Server zu laden.
     * Dazu wird die empfangeStruktdaten(int id) Methode in HTTPClient aufgerufen.
     * Die empfangenen Daten werden dann automatisch in StruktdatenGrenz umgewandelt.
     * @param id Die ID der zu empfangenen Struktdaten
     * @return Die empfangenen Struktdaten als StruktdatenGrenz. Sonst null.
     */
    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    /**
     * Diese Methode sendet Massendaten mit einer bestimmten ID an den Server.
     * Geladen werden diese lokalen Daten von der Methode ladeMassendaten aus DatenService.
     * Diese Daten werden durch die sendeMassendaten Methode im HTTPClient gesendet.
     * @param id ID der zu sendenden Massendaten
     * @return Wenn erfolgreich und HTTP-Status==200: True. Sonst False.
     */
    public boolean sendeMassendaten(int id) {
        Response response;
        Printer.println("Sende Massendaten mit ID: "+id);
        Massendaten m = dServe.ladeMassendaten(id);
        Printer.println("Anzahl der verschickten Werte: "+String.valueOf(m.getValueCount()));

        response = httpClient.sendeMassendaten(m);

        if(response != null) {
            if(response.getStatus() == 200) return true;
            return false;
        }
        return false;
    }

    /**
     * Diese Methode sendet Struktdaten mit einer bestimmten ID an den Server.
     * Geladen werden diese lokalen Daten von der Methode ladeStruktdaten aus DatenService.
     * Diese Daten werden durch die sendeStruktdaten Methode im HTTPClient gesendet.
     * @param id ID der zu sendenden Struktdaten
     * @return Wenn erfolgreich und HTTP-Status==200: True. Sonst False.
     */
    public boolean sendeStruktdaten(int id) {
        return true;
    }

    /**
     * Diese Methode empfaengt eine Liste aller Massendaten.
     * Wenn getFromServer=true, so wird eine Liste der Massendaten auf dem Server geliefert.
     * Wenn getFromServer=false, so wird eine Liste der lokalen Massendaten geliefert.
     * @param getFromServer Wenn true: Hole Liste vom Server. Wenn false: Hole lokale Liste.
     * @return Liste aller Massendaten als MassenInfoGrenz-Liste.
     */
    public List<MassenInfoGrenz> getMassenInfoGrenzList(boolean getFromServer) {
        List<MassenInfo> massenInfoList;

        if(getFromServer) massenInfoList = httpClient.empfangeMassendatenInfoListe();
        else massenInfoList = dServe.ladeMassenListe();

        List<MassenInfoGrenz> massenInfoGrenzList = new ArrayList<>();
        if(massenInfoList != null) {
            for(MassenInfo m : massenInfoList) massenInfoGrenzList.add(new MassenInfoGrenz(m));
        } else return null;
        return massenInfoGrenzList;
    }

    /**
     * Diese Methode empfaengt eine Liste aller Struktdaten.
     * Wenn getFromServer=true, so wird eine Liste der Struktdaten auf dem Server geliefert.
     * Wenn getFromServer=false, so wird eine Liste der lokalen Struktdaten geliefert.
     * @param getFromServer Wenn true: Hole Liste vom Server. Wenn false: Hole lokale Liste.
     * @return Liste aller Struktdaten als StruktInfoGrenz-Liste.
     */
    public List<StruktInfoGrenz> getStruktInfoGrenzList(boolean getFromServer) {
        List<StruktInfo> struktInfoList;

        if(getFromServer) struktInfoList = httpClient.empfangeStruktdatenInfoListe();
        else struktInfoList = dServe.ladeStruktListe();

        List<StruktInfoGrenz> struktInfoGrenzList = new ArrayList<>();
        if(struktInfoList != null) {
            for(StruktInfo s : struktInfoList) struktInfoGrenzList.add(new StruktInfoGrenz(s));
        } else return null;
        return struktInfoGrenzList;
    }

    /**
     * Diese Methode generiert mithilfe des Daten-Generators zufaellige
     * Massendaten der Groeße "size" (Übergabeparameter).
     * Diese werden dann durch die Methode schreibeMassendaten des DatenService gespeichert.
     * @param size Groeße der zu generierenden Daten.
     * @return Erzeugte Massendaten.
     */
    public Massendaten generiereZufallsMassendaten(int size) {
        Massendaten m = dServe.generiereZufallsMassendaten(size);
        dServe.schreibeMassendaten(m);
        return m;
    }

    /**
     * Diese Methode lädt lokale Massendaten und liefert diese als
     * MassendatenGrenz zurueck.
     * @param id ID der zu ladenden Massendaten.
     * @return Die geladenen Massendaten als MassendatenGrenz.
     */
    public MassendatenGrenz ladeLokaleMassendaten(int id) {
        return new MassendatenGrenz(dServe.ladeMassendaten(id));
    }

    /**
     * Diese Methode laedt lokale Struktdaten und liefert diese als
     * StruktdatenGrenz zurueck.
     * @param id ID der zu ladenden Struktdaten.
     * @return Die geladenen Struktdaten als StruktdatenGrenz.
     */
    public StruktdatenGrenz ladeLokaleStruktdaten(int id) {
        return null;
    }

    /**
     * Diese Methode versucht, eine Verbindung zum Server herzustellen und
     * den Client sowie das Target in HTTPClient mit der gegebenen IP
     * zu initialisieren. Dies geschieht durch den Aufruf der connect() Methode
     * in HTTPClient.
     * @param IP Adresse des Servers, zu dem verbunden werden soll.
     * @return Wenn erfolgreich: True. Sonst False.
     */
    public boolean connect(String IP) {
        try {
            httpClient.connect(IP);
        } catch (Exception e) {
            if(isDebugMode) e.printStackTrace();
            Printer.println("!!! Fehler in ClientSteuer/connect() | Konnte nicht zum Server verbinden !!!");
            return false;
        }
        return true;
    }

    /**
     * Liefert die IP des aktuell verbundenen Servers zurueck.
     * Dazu wird die Methode getServerIP() in HTTPClient aufgerufen.
     * @return IP des Servers.
     */
    public String getServerIP() {
        return httpClient.getServerIP();
    }

    /**
     * Diese Methode startet die Komponente Datenverwaltung.
     */
    public void starteDatenverwaltung() {
        testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl iActivate = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
        iActivate.startComponent();
        iActivate.getComponentGui().setVisible(true);
        iActivate.getComponentGui().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // damit das Programm nicht mit dem Fenster geschlossen wird.
    }
}
