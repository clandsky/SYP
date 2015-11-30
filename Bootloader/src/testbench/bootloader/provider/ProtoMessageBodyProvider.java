package testbench.bootloader.provider;

import com.google.protobuf.Message;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 *   Created by Christoph Landsky and Sven Riedel (30.11.2015)
 */
@Provider
@Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
@Produces(MediaTypeExt.APPLICATION_PROTOBUF)
public class ProtoMessageBodyProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {


    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(aClass);
    }

    @Override
    public Message readFrom(Class<Message> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        if (MassendatenProtos.Massendaten.class.isAssignableFrom(aClass)) {
            return MassendatenProtos.Massendaten.parseFrom(inputStream);
        }
        else if (StruktdatenProtos.Struktdaten.class.isAssignableFrom(aClass)) {
            return StruktdatenProtos.Struktdaten.parseFrom(inputStream);
        }
        else {
            throw new BadRequestException("Can't Deserailize");
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return m.getSerializedSize();
    }

    @Override
    public void writeTo(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(m.toByteArray());
    }
}