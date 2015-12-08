package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.provider.ByteMessage;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.server.steuerungsklassen.ServerSteuer;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;


@Path("/")

public class RestResource {

    ServerSteuer s = new ServerSteuer();
/*
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
*/
    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getTest(@PathParam("id")String number) throws IOException {

        int id=1;
        try
        {
            id=Integer.parseInt(number);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        if (id>0) {
            Massendaten massendaten = s.ladeMassendaten(1);
            System.out.println(Calendar.getInstance().getTime());
            System.out.println("******************************");
            System.out.println("*         GET /Header        *");
            System.out.println("******************************");

            Splitter splitter = new Splitter();
            List<Massendaten> data = splitter.splitMassendaten(massendaten, 1000);
            return new ByteMessage(splitter.combineByteArrays(data));
        }
        else return null;

    }

    @POST
    @Path("testlauf")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        s.createOutput("[POST] on /Massendaten");

        Massendaten massendaten = null;
        try {
            massendaten = Massendaten.parseFrom(daten.getByteArray());
            double d=massendaten.getValue(massendaten.getValueCount()-1).getNumber();
            s.createOutput("Last recieved Item: "+d);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        s.createOutput("[SUCCESS] Massendaten created... Response 'Test'");
        Runtime r = Runtime.getRuntime();
        r.gc();
        r.freeMemory();

        return Response.status(200).entity("Test...").build();
    }


}