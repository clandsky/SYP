package testbench.client.steuerungsklassen;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.*;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.messdaten.MessdatenProtos.Messdaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.service.StaticHolder;
import testbench.client.HTTPClient;
import testbench.client.service.DatenService;

import javax.swing.*;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015
 */

public class ClientSteuer {
    private HTTPClient httpClient;
    private DatenService dServe;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
        dServe = new DatenService();
    }

    /**
     * Diese Methode laedt alle lokal gespeicherten Messdaten, konvertiert
     * diese in MessdatenGrenz und liefert diese als Liste zurueck.
     * @return Die geladenen Messdaten als Liste.
     */
    public List<MessdatenGrenz> holeMessdaten() {
        List<Messdaten> messdatenList = dServe.ladeMessdatenListe();
        List<MessdatenGrenz> messdatenGrenzList = new ArrayList<>();

        for(Messdaten m : messdatenList) messdatenGrenzList.add(new MessdatenGrenz(m));

        return messdatenGrenzList;
    }

    /**
     * Diese Methode versucht, Massendaten mit gegebener ID vom Server zu laden.
     * Dazu wird die empfangeMassendaten(int id) Methode in HTTPClient aufgerufen.
     * Es werden automatisch Messdaten auf der Festplatte gespeichert.
     * Die empfangenen Daten werden dann in MassendatenGrenz umgewandelt.
     * @param id Die ID der zu empfangenen Massendaten
     * @return Die empfangenen Massendaten als MassendatenGrenz. Sonst null.
     */
    public MassendatenGrenz empfangeMassendaten(int id) {
        Printer.println("Empfange Massendaten mit ID: "+id);

        Massendaten m = httpClient.empfangeMassendaten(id);

        if(m != null) {
            long deseri = StaticHolder.deSerialisierungsZeitMs;
            long gesamt = StaticHolder.gesamtZeit;
            dServe.schreibeMessdaten(buildMessdaten(id, 0, deseri, gesamt, m.getSerializedSize(), new SimpleDateFormat("dd.MM.yyyy").format(new Date()),"Massendaten"));
            dServe.schreibeMassendaten(m);
            return new MassendatenGrenz(m);
        }
        return null;
    }

    /**
     * Diese Methode versucht, Struktdaten mit gegebener ID vom Server zu laden.
     * Dazu wird die empfangeStruktdaten(int id) Methode in HTTPClient aufgerufen.
     * Es werden automatisch Messdaten auf der Festplatte gespeichert.
     * Die empfangenen Daten werden dann in StruktdatenGrenz umgewandelt.
     * @param id Die ID der zu empfangenen Struktdaten
     * @return Die empfangenen Struktdaten als StruktdatenGrenz. Sonst null.
     */
    public StruktdatenGrenz empfangeStruktdaten(int id) {
        Printer.println("Empfange Struktdaten mit ID: "+id);

        Struktdaten s = httpClient.empfangeStruktdaten(id);

        if(s != null) {
            long deseri = StaticHolder.deSerialisierungsZeitMs;
            long gesamt = StaticHolder.gesamtZeit;
            dServe.schreibeMessdaten(buildMessdaten(id, 0, deseri, gesamt, s.getSerializedSize(), new SimpleDateFormat("dd.MM.yyyy").format(new Date()),"Struktdaten"));
            dServe.schreibeStruktdaten(s);
            return new StruktdatenGrenz(s);
        }
        return null;
    }

    /**
     * Diese Methode sendet Massendaten mit einer bestimmten ID an den Server.
     * Geladen werden diese lokalen Daten von der Methode ladeMassendaten aus DatenService.
     * Diese Daten werden durch die sendeMassendaten Methode im HTTPClient gesendet.
     * Nach dem erfolgreichen Senden werden die gemessenen Zeiten als Messdatenproto
     * auf der Festplatte gespeichert.
     * @param id ID der zu sendenden Massendaten
     * @return Wenn erfolgreich und HTTP-Status==200: True. Sonst False.
     */
    public boolean sendeMassendaten(int id) {
        Printer.println("Sende Massendaten mit ID: "+id);
        Massendaten m = dServe.ladeMassendaten(id);
        Printer.println("Anzahl der Werte: "+String.valueOf(m.getValueCount()));
        Response response = httpClient.sendeMassendaten(m);

        if(response != null) {
            if(response.getStatus() == 200) {
                long seri = StaticHolder.serialisierungsZeitMs;
                long deseri = 0;
                long gesamt = StaticHolder.gesamtZeit;
                dServe.schreibeMessdaten(buildMessdaten(id, seri, deseri, gesamt, m.getSerializedSize(), new SimpleDateFormat("dd.MM.yyyy").format(new Date()),"Massendaten"));
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Diese Methode sendet Struktdaten mit einer bestimmten ID an den Server.
     * Geladen werden diese lokalen Daten von der Methode ladeStruktdaten aus DatenService.
     * Diese Daten werden durch die sendeStruktdaten Methode im HTTPClient gesendet.
     * Nach dem erfolgreichen Senden werden die gemessenen Zeiten als Messdatenproto
     * auf der Festplatte gespeichert.
     * @param id ID der zu sendenden Struktdaten
     * @return Wenn erfolgreich und HTTP-Status==200: True. Sonst False.
     */
    public boolean sendeStruktdaten(int id) {
        Printer.println("Sende Struktdaten mit ID: "+id);
        Struktdaten s = dServe.ladeStruktdaten(id);
        Response response = httpClient.sendeStruktdaten(s);

        if(response != null) {
            if(response.getStatus() == 200) {
                long seri = StaticHolder.serialisierungsZeitMs;
                long deseri = 0;
                dServe.schreibeMessdaten(buildMessdaten(id, seri, deseri, StaticHolder.gesamtZeit, s.getSerializedSize(), new SimpleDateFormat("dd.MM.yyyy").format(new Date()),"Struktdaten"));
                return true;
            }
            return false;
        }
        return false;
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
        return new StruktdatenGrenz(dServe.ladeStruktdaten(id));
    }

    /**
     * Diese Methode laedt lokale Messdaten und liefert diese als
     * MessdatenGrenz zurueck.
     * @param id ID der zu ladenden Messdaten.
     * @return Die geladenen Messdaten als MessdatenGrenz.
     */
    public MessdatenGrenz ladeLokaleMessdaten(int id) {
        return new MessdatenGrenz(dServe.ladeMessdaten(id));
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
        return httpClient.connect(IP);
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
    public JFrame starteDatenverwaltung() {
        testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl iActivate = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
        iActivate.startComponent();
        iActivate.getComponentGui().setVisible(true);
        iActivate.getComponentGui().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // damit das Programm nicht mit dem Fenster geschlossen wird.
        return iActivate.getComponentGui();
    }

    private Messdaten buildMessdaten(int id, long seriZeit, long deseriZeit, long gesamtZeit, int paketGroesseByte, String timeStamp, String typ) {
        Messdaten.Builder builder = Messdaten.newBuilder();

        builder.setId(id);
        builder.setSerialisierungsZeit(seriZeit);
        builder.setDeserialisierungsZeit(deseriZeit);
        builder.setGesamtZeit(gesamtZeit);
        builder.setPaketGroesseByte(paketGroesseByte);
        builder.setTimeStamp(timeStamp);
        builder.setTyp(typ);

        return builder.build();
    }
}
