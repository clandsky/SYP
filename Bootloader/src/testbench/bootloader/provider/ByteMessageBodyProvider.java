package testbench.bootloader.provider;

import com.google.protobuf.Message;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;

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
@Consumes(MediaTypeExt.APPLICATION_BYTEMESSAGE)
@Produces(MediaTypeExt.APPLICATION_BYTEMESSAGE)
public class ByteMessageBodyProvider implements MessageBodyReader<ByteMessage>, MessageBodyWriter<ByteMessage> {

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public ByteMessage readFrom(Class<ByteMessage> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        if (ByteMessage.class.isAssignableFrom(aClass)) {
            ByteMessage bm = new ByteMessage(inputStream);
            return bm;
        }
        else {
            throw new BadRequestException("Can't deserialize!");
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(ByteMessage m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return m.getByteArray().length*8;
    }

    @Override
    public void writeTo(ByteMessage m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(m.getByteArray());
    }
}