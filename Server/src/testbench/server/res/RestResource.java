package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.Printer;
import testbench.server.steuerungsklassen.ServerSteuer;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/")

public class RestResource {
    ServerSteuer s = new ServerSteuer();

    @GET
    @Path("server")
    @Produces(MediaType.TEXT_PLAIN)
    public Response helloServer(){
        return Response.status(200).build();
    }
    @GET
    @Path("massendaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<MassenInfo> getMassendatenListe() throws IOException {
        Printer.println("[GET] Massendaten/");
        List<MassenInfo> list = s.ladeMassenListe();
        return list;
    }

    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Massendaten getMassendatenById(@PathParam("id")String number) throws IOException {
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

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postMassendaten(Massendaten daten) {
        Printer.println("[POST] on /Massendaten");
        try {
            /*double d=massendaten.getValue(massendaten.getValueCount()-1).getNumber();
            Printer.println("Letztes erhaltenes Element: "+d);
            */
            if (s.schreibeMassendaten(daten)){
                return Response.status(200).entity("[SUCCESS] Massendaten erzeugt...").build();
            }
            else {
                Printer.println("[ERROR] Massendaten konnten nicht erzeugt werden...");
                return Response.status(200).entity("[ERROR] Massendaten konnten nicht erzeugt werden...").build();
            }
        } catch (Exception e) {
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
        return list;
    }

    @GET
    @Path("struktdaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Struktdaten getStruktdatenById(@PathParam("id")String number) throws IOException {
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

    @POST
    @Path("struktdaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postStruktdaten(Struktdaten daten) {
        Printer.println("[POST] on /Struktdaten");
        try {
            if (s.schreibeStruktdaten(daten))
            {
                return Response.status(200).entity("[SUCCESS] Strukturierte Daten erzeugt").build();
            }
            else
            {
                String res = "[ERROR] Fehler beim Speichern der Struktdaten...";
                Printer.println(res);
                return Response.status(200).entity(res).build();
            }
        } catch (Exception e) {
            String res = "[FATAL] Gesendete Daten konnten nicht geladen werden...";
            Printer.println(res);
            return Response.status(200).entity(res).build();
        }
    }
}