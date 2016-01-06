package testbench.server.res;

/**
 * Resource-Definition für den REST-Server. Diese wird benötigt, um die einzelnen Resourcen zu definieren.
 * Die Funktionalität wird über die Anotationen definiert. Die dazugehörigen Methoden werden bei Aufruf ausgeführt und
 * liefern das gewünschte Ergebnis an den Server zurück.
 *
 * Für nähere Informationen hierzu, siehe Installationsanleitung
 */

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.Printer;
import testbench.bootloader.service.StaticHolder;
import testbench.server.steuerungsklassen.ServerSteuer;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/")
public class RestResource {
    /** Dient zur Steuerung und Weitergabe von Befehlen. Diese führt die Anfrage intern aus und gibt sie zurück an den Serverdienst */
    ServerSteuer s = new ServerSteuer();

    /**
     * Ein "Hello World", um den Server auf Erreichbarkeit zu prüfen
     * @return Response mit dem Status 200
     */
    @GET
    @Path("server")
    @Produces(MediaType.TEXT_PLAIN)
    public Response helloServer(){
        return Response.status(200).build();
    }

    /**
     * Gibt eine Liste mit allen Massendaten, die auf dem Server liegen, zurück
     * @return Liste mit den MassenInfos der verfügbaren Massendaten
     */
    @GET
    @Path("massendaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<MassenInfo> getMassendatenListe() throws IOException {
        Printer.println("[GET] Massendaten/");
        try{
            List<MassenInfo> list = s.ladeMassenListe();
            return list;
        }catch (Exception e)
        {
            Printer.println("[FATAL] Fehler bei der Anfrage... IOException");
        }
        return null;

    }

    /**
     * Gibt Massendaten anhand ihrer ID zurück
     * @param number ID der gewünschten Massendaten
     * @return die gewünschten Massendaten.
     */
    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Massendaten getMassendatenById(@PathParam("id")String number){
        Printer.println("[GET] Massendaten/"+number);
        int id=0;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            Printer.println("[FATAL] Pfad besitzt ungültige ID!");
            return null;
        }
        if (id>0) {
            Massendaten massendaten = s.ladeMassendaten(id);
            if(massendaten!=null) {
                return massendaten;
            }
            else {
                Printer.println("[ERROR] File not Found!");
                return null;
            }
        }
        else{
            Printer.println("[FATAL] Could not Resolve Path!");
            return null;
        }
    }

    /**
     * Nimmt Massendaten entgegen und gibt diese zum Speichern weiter
     * @param daten Übermittelte Massendaten
     * @return Response "200" zur Bestätigung
     */
    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postMassendaten(Massendaten daten) {
        Printer.println("[POST] on /Massendaten");
        new Writer(daten);
        return Response.status(200).entity(StaticHolder.deSerialisierungsZeitMs).build();
    }

    /**
     * Gibt eine Liste mit allen Struktdaten, die auf dem Server liegen, zurück
     * @return Liste mit den StruktInfos der verfügbaren Struktdaten
     */
    @GET
    @Path("struktdaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<StruktInfo> getStruktdatenListe() {
        Printer.println("[GET] Struktdaten/");
        try{
            List<StruktInfo> list = s.ladeStruktListe();
            return list;
        } catch (Exception e)
        {
            Printer.println("[FATAL] Fehler bei der Anfrage... IOException");
        }
        return null;
    }

    /**
    * Gibt Struktdaten anhand ihrer ID zurück
    * @param number ID der gewünschten Struktdaten
    * @return die gewünschten Struktdaten.
    */
    @GET
    @Path("struktdaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Struktdaten getStruktdatenById(@PathParam("id")String number) {
        Printer.println("[GET] Struktdaten/"+number);
        int id=0;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            Printer.println("[FATAL] Pfad besitzt ungültige ID!");
            return null;
        }
        if (id>0) {
            Struktdaten struktdaten = s.ladeStruktdaten(id);
            if(struktdaten!=null) {
                return struktdaten;
            }
            else {
                Printer.println("[ERROR] File not Found!");
                return null;
            }
        }
        else{
            Printer.println("[FATAL] Could not Resolve Path!");
            return null;
        }
    }

    /**
     * Nimmt Struktdaten entgegen und gibt diese zum Speichern weiter
     * @param daten Übermittelte Struktdaten
     * @return Response "200" zur Bestätigung
     */
    @POST
    @Path("struktdaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postStruktdaten(Struktdaten daten) {
        Printer.println("[POST] on /Struktdaten");
        new Writer(daten);
        return Response.status(200).entity(StaticHolder.deSerialisierungsZeitMs).build();
    }

    /**
     * Klasse (Thread) zum parallelen Speichern der Daten, damit dies die Zeitmessung nicht verfälscht.
     */
    class Writer extends Thread {
        /** 1=Massendaten, 2=Struktdaten */
        private int mode;
        /** Daten zum Schreiben */
        private Massendaten massendaten;
        /** Daten zum Schreiben */
        private Struktdaten struktdaten;

        /**
         * Konstruktor für Massendaten
         * Setzt mode=1 und übergibt die erhaltenen Massendaten
         * @param m Massendaten zum speichern
         */
        public Writer(Massendaten m){
            this.mode=1;
            this.massendaten=m;
            this.struktdaten=null;
            start();
        }

        /**
         * Konstruktor für Struktdaten
         * setzt mode=2 und übergibt die erhaltenen Struktdaten
         * @param s Struktdaten zum speichern
         */
        public Writer (Struktdaten s)
        {
            this.mode=2;
            this.massendaten=null;
            this.struktdaten=s;
            start();

        }

        /**
         * Führt die Operation in einem zweiten Thread aus. Per 'mode' wird bestimmt, ob schreibeMassendaten() oder
         * schreibeStruktdaten() ausgeführt wird.
         */
        @Override
        public void run(){
            switch (mode)
            {
                case 1:
                    try {
                        if (!s.schreibeMassendaten(massendaten)) {
                            Printer.println("[ERROR] Massendaten konnten nicht erzeugt werden...");
                        }
                    }catch (Exception e1) {
                        Printer.println("[FATAL] Gesendete Daten konnten nicht geladen werden...");
                    }
                    break;
                case 2:
                    try {
                        if (!s.schreibeStruktdaten(struktdaten)) {
                            Printer.println("[ERROR] Massendaten konnten nicht erzeugt werden...");
                        }
                    }catch (Exception e1) {
                        Printer.println("[FATAL] Gesendete Daten konnten nicht geladen werden...");
                    }

            }
        }
    }
}

