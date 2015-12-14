package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.ByteMessage;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.Printer;
import testbench.server.steuerungsklassen.ServerSteuer;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/")

public class RestResource {
    ServerSteuer s = new ServerSteuer();

    @GET
    @Path("massendaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<MassenInfo> getMassendatenListe() throws IOException {
        Printer.println("[GET] Massendaten/");
        List<MassenInfo> list = s.ladeMassenListe();
        Printer.println("[SUCCESS] Returning Massendaten-Liste...");
        return list;
    }

    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getMassendatenById(@PathParam("id")String number) throws IOException {
        Printer.println("[GET] Massendaten/"+number);
        int id=0;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            e.printStackTrace();
            Printer.println("[FATAL] Pfad besitzt ungültige ID!");
            return null;
        }
        if (id>0) {
            Massendaten massendaten = s.ladeMassendaten(id);
            if(massendaten!=null) {
                Printer.println("[SUCCESS] Massendaten geladen...");
                return new ByteMessage(massendaten);
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

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        Printer.println("[POST] on /Massendaten");
        Massendaten massendaten = null;
        try {
            massendaten = daten.getMassendaten();
            double d=massendaten.getValue(massendaten.getValueCount()-1).getNumber();
            Printer.println("Letztes erhaltenes Element: "+d);
            if (s.schreibeMassendaten(massendaten)){
                Printer.println("[SUCCESS] Massendaten erzeugt... Response 'Test'");
                return Response.status(200).entity("[SUCCESS] Massendaten erzeugt...").build();
            }
            else {
                Printer.println("[ERROR] Massendaten konnten nicht erzeugt werden...");
                return Response.status(200).entity("[ERROR] Massendaten konnten nicht erzeugt werden...").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String res = "[FATAL] Gesendete Daten konnten nicht geladen werden...";
            Printer.println(res);
            return Response.status(200).entity(res).build();
        }
    }

    @GET
    @Path("struktdaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<StruktInfo> getStruktdatenListe() throws IOException {
        Printer.println("[GET] Struktdaten/");
        List<StruktInfo> list = s.ladeStruktListe();
        Printer.println("[SUCCESS] Returning Struktdaten-Liste...");
        return list;
    }

    @GET
    @Path("struktdaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getStruktdatenById(@PathParam("id")String number) throws IOException {
        Printer.println("[GET] Struktdaten/"+number);
        int id=0;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            e.printStackTrace();
            Printer.println("[FATAL] Pfad besitzt ungültige ID!");
            return null;
        }
        if (id>0) {
            Struktdaten struktdaten = s.ladeStruktdaten(id);
            if(struktdaten!=null) {
                Printer.println("[SUCCESS] Strukturierte Daten geladen...");
                return new ByteMessage(struktdaten);
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

    @POST
    @Path("struktdaten")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postStruktdaten(ByteMessage daten) {
        Printer.println("[POST] on /Struktdaten");
        Struktdaten struktdaten = null;
        try {
            struktdaten = daten.getStruktdaten();
            if (s.schreibeStruktdaten(struktdaten))
            {
                Printer.println("[SUCCESS] Strukturierte Daten erzeugt...");
                return Response.status(200).entity("[SUCCESS] Strukturierte Daten erzeugt").build();
            }
            else
            {
                String res = "[ERROR] Fehler beim Speichern der Struktdaten...";
                Printer.println(res);
                return Response.status(200).entity(res).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String res = "[FATAL] Gesendete Daten konnten nicht geladen werden...";
            Printer.println(res);
            return Response.status(200).entity(res).build();

        }
    }
}