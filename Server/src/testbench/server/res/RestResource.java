package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.entities.MassenInfo;
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
    Printer p = new Printer();

    @GET
    @Path("massendaten")
    @Produces(MediaTypeExt.APPLICATION_XML)
    public List<MassenInfo> getTest() throws IOException {

        p.printlnWithDate("[GET] Massendaten/");
        List<MassenInfo> list = s.ladeMassenListe();
        p.printlnWithDate("[SUCCESS] Returning Massendaten-Liste...");
        return list;

    }

    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getTest(@PathParam("id")String number) throws IOException {
        p.printlnWithDate("[GET] Massendaten/"+number);
        int id=1;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if (id>0) {
            Massendaten massendaten = s.ladeMassendaten(id);
            if(massendaten!=null) {
                p.printlnWithDate("Massendaten geladen...");
                p.printlnWithDate("Massendaten werden gesplitted...");
                p.printlnWithDate("[SUCCESS] Returning ByteArray...");
                return new ByteMessage(massendaten,1000,0.5f);
            }
            else {
                p.printlnWithDate("[ERROR] File not Found!");
                return null;
            }
        }
        else{
            p.printlnWithDate("[ERROR] Could not Resolve Path!");
            return null;
        }

    }

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        p.printlnWithDate("[POST] on /Massendaten");

        Massendaten massendaten = null;
        try {
            massendaten = daten.getMassendatenFromByteArray();
            double d=massendaten.getValue(massendaten.getValueCount()-1).getNumber();
            p.printlnWithDate("Letztes erhaltenes Element: "+d);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        p.printlnWithDate("[SUCCESS] Massendaten erzeugt... Response 'Test'");
        Runtime r = Runtime.getRuntime();
        r.gc();
        r.freeMemory();

        return Response.status(200).entity("Test...").build();
    }


}