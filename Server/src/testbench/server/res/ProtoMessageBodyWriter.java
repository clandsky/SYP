package testbench.server.res;

import com.google.protobuf.Message;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Huskey on 29.11.2015.
 */
@Provider

@Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public class ProtoMessageBodyWriter implements MessageBodyWriter<Message> {

        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,

                                   MediaType mediaType) {
            return Message.class.isAssignableFrom(type);

        }


        public long getSize(Message m, Class<?> type, Type genericType, Annotation[] annotations,

                            MediaType mediaType) {
            return m.getSerializedSize();

        }

        public void writeTo(Message m, Class<?> type, Type genericType, Annotation[] annotations,

                            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,

                WebApplicationException {

            entityStream.write(m.toByteArray());

        }
    }


