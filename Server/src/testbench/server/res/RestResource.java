package testbench.server.res;

/**
 * Created by Chrizzle Manizzle on 26.11.2015.
 */
//import sun.plugin2.message.Message;
import com.google.protobuf.*;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
//import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
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
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        System.out.println("GET /testlauf");
        System.out.println();
        return "Hallo Sven!!";
    }


    @POST
    @Path("testlauf/{name}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void insertTest(String name) {
        System.out.println("Input: " + name);
        System.out.println();
    }

    /*-------------------------------------------------------------------------------------------------------------------------------*/
    @Provider
    @Produces("application/protobuf")
    @Consumes("application/protobuf")
    public class WidgetProtocMessageBodyProvder
            implements MessageBodyReader, MessageBodyWriter {

        @Override
        public boolean isReadable(Class type, Type type1,
                                  Annotation[] antns, MediaType mt) {
            return Massendaten.class.isAssignableFrom(type);
        }

        @Override
        public Object readFrom(Class type, Type type1, Annotation[] antns,
                               MediaType mt, MultivaluedMap mm, InputStream in)
                throws IOException, WebApplicationException {
            if (Massendaten.class.isAssignableFrom(type)) {
                return Massendaten.parseFrom(in);
            } else {
                throw new BadRequestException("Can't Deserailize");
            }
        }

        @Override
        public boolean isWriteable(Class type, Type type1,
                                   Annotation[] antns, MediaType mt) {
            return Massendaten.class.isAssignableFrom(type);
        }

        @Override
        public long getSize(Object t, Class type, Type type1,
                            Annotation[] antns, MediaType mt) {
            return -1;
        }

        @Override
        public void writeTo(Object t, Class type, Type type1,
                            Annotation[] antns, MediaType mt,
                            MultivaluedMap mm, OutputStream out)
                throws IOException, WebApplicationException {
            if (t instanceof Massendaten) {
                Massendaten md = (Massendaten) t;
                md.writeTo(out);
            }
        }

    }
}