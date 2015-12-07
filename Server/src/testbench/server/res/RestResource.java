package testbench.server.res;

/**
 * Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.provider.ByteMessage;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.server.steuerungsklassen.ServerSteuer;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/")

public class RestResource {

    ServerSteuer s = new ServerSteuer();

    @GET
    @Path("testlauf")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public ByteMessage getTest() throws IOException {
        Massendaten massendaten = s.ladeMassendaten(1);

        System.out.println("/******************************/");
        System.out.println("/*       GET /Massendaten     */");
        System.out.println("/******************************/");

        Splitter splitter = new Splitter();
        List<Massendaten> data = splitter.splitMassendaten(massendaten, 1000);
        return new ByteMessage(splitter.combineByteArrays(data));

    }

    @POST
    @Path("testlauf")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        System.out.println("******************************");
        System.out.println("*      POST /Massendaten     *");
        System.out.println("******************************");
        Massendaten massendaten = null;
        try {
            massendaten = Massendaten.parseFrom(daten.getByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime r = Runtime.getRuntime();
        r.gc();
        r.freeMemory();

        return Response.status(200).entity("Test...").build();
    }


}