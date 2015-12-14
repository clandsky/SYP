package testbench.server.res;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.ByteMessage;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.server.steuerungsklassen.ServerSteuer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")

public class RestResourceServer {
    ServerSteuer steuer;
    @GET
    @Path("massendaten")
    @Produces(MediaType.APPLICATION_XML)
    public Response getMassendatenListe() {
        System.out.println("/******************************/");
        System.out.println("/*       GET /Massendaten     */");
        System.out.println("/******************************/");
        return Response.status(200).entity(steuer.ladeMassenListe()).build();
    }

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response postMassendaten(ByteMessage daten) {
        System.out.println("/******************************/");
        System.out.println("/*      POST /Massendaten     */");
        System.out.println("/******************************/");
        Massendaten massendaten=null;
        try
        {
            massendaten = Massendaten.parseFrom(daten.getByteArray());
        }catch(Exception e){
            e.printStackTrace();
        }
        if (steuer.schreibeMassendaten(massendaten))return Response.status(200).entity("Eintrag eingefügt...").build();
        return Response.status(200).entity("Eintrag konnte nicht eingefügt werden...").build();
    }

    @POST
    @Path("massendaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postMassendaten(Massendaten daten) {
        System.out.println("/******************************/");
        System.out.println("/*      POST /Massendaten     */");
        System.out.println("/******************************/");
        if (steuer.schreibeMassendaten(daten))return Response.status(200).entity("Eintrag eingefügt...").build();
        return Response.status(200).entity("Eintrag konnte nicht eingefügt werden...").build();
    }

    @GET
    @Path("massendaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
    public Response getMassendatenById(@PathParam("id") String number) {
        System.out.println("/******************************/");
        System.out.println("/*     GET /MassendatenById   */");
        System.out.println("/******************************/");
        int id=0;

        try {
            id = Integer.parseInt(number);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (id>0) {
            ByteMessage bm = new ByteMessage(steuer.ladeMassendaten(id));

            return Response.status(200).entity(bm).build();
        }
        else return Response.status(200).entity(null).build();
    }


    @GET
    @Path("struktdaten")
    @Produces(MediaType.APPLICATION_XML)
    public Response getStruktdatenListe() {
        System.out.println("/******************************/");
        System.out.println("/*       GET /Struktdaten     */");
        System.out.println("/******************************/");
        return Response.status(200).entity(steuer.ladeStruktListe()).build();
    }

    @POST
    @Path("struktdaten")
    @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response postStruktdaten(Struktdaten daten) {
        System.out.println("/******************************/");
        System.out.println("/*      POST /Struktdaten     */");
        System.out.println("/******************************/");
        if (steuer.schreibeStruktdaten(daten))return Response.status(200).entity("Eintrag eingefügt...").build();
        return Response.status(200).entity("Eintrag konnte nicht eingefügt werden...").build();
    }

    @GET
    @Path("struktdaten/{id}")
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Response getStruktdatenById(@PathParam("id") String number) {
        System.out.println("/******************************/");
        System.out.println("/*    GET /StruktdatenById    */");
        System.out.println("/******************************/");
        int id=0;

        try {
            id = Integer.parseInt(number);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (id>0)return Response.status(200).entity(steuer.ladeStruktdaten(id)).build();
        else return Response.status(200).entity(null).build();
    }

}