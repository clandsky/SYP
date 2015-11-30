package testbench.server.res;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.protobuf.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;


import javax.ws.rs.*;


@Path("/")

public class RestResource {
    @GET
    @Path("testlauf")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Massendaten getTest() {
        System.out.println("GET /testlauf");
        System.out.println();
        Massendaten md = Massendaten.newBuilder()
                .addValue(Massendaten.Werte.newBuilder().setNumber(0.123))
                .addValue(Massendaten.Werte.newBuilder().setNumber(0.456))
                .addValue(Massendaten.Werte.newBuilder().setNumber(0.789))
                .build();


        return md;
    }

}