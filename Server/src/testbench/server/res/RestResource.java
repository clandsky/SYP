package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.protobuf.Splitter;
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
        List<MassenInfo> list = s.ladeMassenListe();

        System.out.println("******************************");
        System.out.println("*       GET /Massendaten     *");
        System.out.println("******************************");

        return list;

    }

    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getTest(@PathParam("id")String number) throws IOException {
        p.printOutputWithDate("[GET] Massendaten/"+number);
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
                p.printOutputWithDate("Massendaten geladen...");
                Splitter splitter = new Splitter();
                List<Massendaten> data = splitter.splitMassendaten(massendaten, 1000);
                p.printOutputWithDate("[SUCCESS] Returning ByteArray...");
                return new ByteMessage(splitter.combineByteArrays(data));
            }
            else {
                p.printOutputWithDate("[ERROR] File not Found!");
                return null;
            }
        }
        else{
            p.printOutputWithDate("[ERROR] Could not Resolve Path!");
            return null;
        }

    }

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        p.printOutputWithDate("[POST] on /Massendaten");

        Massendaten massendaten = null;
        try {
            massendaten = Massendaten.parseFrom(daten.getByteArray());
            double d=massendaten.getValue(massendaten.getValueCount()-1).getNumber();
            p.printOutputWithDate("Letztes erhaltenes Element: "+d);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        p.printOutputWithDate("[SUCCESS] Massendaten erzeugt... Response 'Test'");
        Runtime r = Runtime.getRuntime();
        r.gc();
        r.freeMemory();

        return Response.status(200).entity("Test...").build();
    }


}