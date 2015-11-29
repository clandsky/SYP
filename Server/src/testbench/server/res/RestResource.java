package testbench.server.res;

/**
 * Created by Chrizzle Manizzle on 26.11.2015. JAX Resource File
 */

import com.google.protobuf.*;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;



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