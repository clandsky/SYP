package testbench.client;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.bootloader.service.StaticHolder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    private boolean printStackTrace = true;
    private static HTTPClient httpClient;
    private Client client;
    private WebTarget target;

    private final String MASSENDATEN = "massendaten";
    private final String STRUKTDATEN = "struktdaten";
    private final String HELLO_SERVER = "server";

    private HTTPClient(){} // Konstruktor private, SINGLETON

    /**
     * Diese Methode liefert den Singleton zurueck. Wurde er noch
     * nicht initialisiert, so wird er automatisch initialisiert.
     * @return Singleton Object, HTTPClient
     */
    public static HTTPClient getExemplar() {
        if(httpClient == null) httpClient = new HTTPClient();
        return httpClient;
    }

    /**
     * Diese Methode versucht, client und target zu initialisieren, welche für die
     * Verbindung zum Server benoetigt werden.
     * Nach der Initialisierung wird getestet, ob der Server online ist.
     * @param adresse IP-Adresse des Servers.
     * @return Falls erfolgreich und HTTP-Respone==200: True. Sonst: False.
     */
    public boolean connect(String adresse) {
        try{
            client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
            target = client.target(adresse);

            Response response = target.path( HELLO_SERVER ).request().accept( MediaTypeExt.TEXT_PLAIN ).get();

            if(response != null) {
                if(response.getStatus() == 200) return true;
            }
            return false;
        } catch (Exception e) {
            Printer.println("Exception in HTTPClient/connect() : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return false;
        }
    }

    /**
     * Diese Methode versucht, Massendaten mittels "POST-REQUEST" an den Server zu senden.
     * Ausserdem wird hier die benoetigte Gesamtzeit des "POST-REQUEST" gemessen.
     * @param m Massendaten, die gesendet werden sollen.
     * @return "Response-Objekt" des Servers bei Erfolg. Sonst null.
     */
    public Response sendeMassendaten(Massendaten m) {
        try{
            StaticHolder.gesamtZeit = System.currentTimeMillis();
            Response response = target.path( MASSENDATEN ).request().post(Entity.entity(m,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
            StaticHolder.gesamtZeit = System.currentTimeMillis() -  StaticHolder.gesamtZeit;
            return response;
        } catch (Exception e) {
            Printer.println("Exception in HTTPClient/sendeMassendaten() : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    /**
     * Diese Methode versucht, Struktdaten mittels "POST-REQUEST" an den Server zu senden.
     * Ausserdem wird hier die benoetigte Gesamtzeit des "POST-REQUEST" gemessen.
     * @param s Struktdaten, die gesendet werden sollen.
     * @return "Response-Objekt" des Servers bei Erfolg. Sonst null.
     */
    public Response sendeStruktdaten(Struktdaten s) {
        StaticHolder.gesamtZeit = System.currentTimeMillis();
        StaticHolder.gesamtZeit = System.currentTimeMillis() -  StaticHolder.gesamtZeit;
        return null;
    }

    /**
     * Diese Methode versucht, Massendaten mittels "GET-REQUEST" zu empfangen.
     * Ausserdem wird hier die benoetigte Gesamtzeit des "GET-REQUEST" gemessen.
     * @param id ID der Daten, die empfangen werden sollen.
     * @return Empfangene Massendaten bei Erfolg. Sonst null.
     */
    public Massendaten empfangeMassendaten(int id) {
        try{
            StaticHolder.gesamtZeit = System.currentTimeMillis();
            Response res = target.path( MASSENDATEN+"/"+id ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Response.class);
            Massendaten m = res.readEntity(Massendaten.class);
            StaticHolder.gesamtZeit = System.currentTimeMillis() -  StaticHolder.gesamtZeit;
            return m;
        } catch (Exception e) {
            Printer.println("Exception in HTTPClient/empfangeMassendaten() : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    /**
     * Diese Methode versucht, Struktdaten mittels "GET-REQUEST" zu empfangen.
     * Ausserdem wird hier die benoetigte Gesamtzeit des "GET-REQUEST" gemessen.
     * @param id ID der Daten, die empfangen werden sollen.
     * @return Empfangene Massendaten bei Erfolg. Sonst null.
     */
    public Struktdaten empfangeStruktdaten(int id) {
        StaticHolder.gesamtZeit = System.currentTimeMillis();
        StaticHolder.gesamtZeit = System.currentTimeMillis() -  StaticHolder.gesamtZeit;
        return null;
    }

    /**
     * Diese Methode versucht, eine Liste der vorhandenen Massendaten auf dem Server zu empfangen.
     * @return Empfangene Liste der vorhandenen Daten als MassenInfo bei Erfolg. Sonst null.
     */
    public List<MassenInfo> empfangeMassendatenInfoListe() {
        try {
            List<MassenInfo> mInfo = target.path( MASSENDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<MassenInfo>>() {} );
            return mInfo;
        } catch(Exception e) {
            Printer.println("Exception in HTTPClient/empfangeMassendatenInfoListe() : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    /**
     * Diese Methode versucht, eine Liste der vorhandenen Struktdaten auf dem Server zu empfangen.
     * @return Empfangene Liste der vorhandenen Daten als StruktInfo bei Erfolg. Sonst null.
     */
    public List<StruktInfo> empfangeStruktdatenInfoListe() {

        try {
            List<StruktInfo> sInfo = target.path( STRUKTDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<StruktInfo>>() {} );
            return sInfo;
        } catch(Exception e) {
            Printer.println("Exception in HTTPClient/empfangeStruktdatenInfoListe() : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    /**
     * Diese Methode liefert die IP des aktuell gesetzten Servers zurueck.
     * @return IP des aktuellen Servers.
     */
    public String getServerIP() {
        return target.getUri().toString();
    }
}
