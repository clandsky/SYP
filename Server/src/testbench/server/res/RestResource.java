package testbench.server.res;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/")

public class RestResource {
    @GET
    @Path("testlauf")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Massendaten getTest() {
        System.out.println("GET /testlauf");
        System.out.println();

        Massendaten.Builder builder = Massendaten.newBuilder();

        for(int i=0 ; i<1000000 ; i++) {
            builder.addValue(Massendaten.Werte.newBuilder().setNumber(Math.random()));
        }



        return builder.build();
    }

    @POST
    @Path("testlauf")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postTest(Massendaten massendaten) {
        System.out.println("POST /testlauf");
        System.out.println();

        List<Massendaten.Werte> liste = massendaten.getValueList();
    /*         for (int i=0 ; i<liste.size() ; i++) {
            System.out.println("Wert "+i+": "+liste.get(i).getNumber());
        } */

        return Response.status(200).build();
    }


}