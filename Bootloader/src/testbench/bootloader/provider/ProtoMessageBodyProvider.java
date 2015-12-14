package testbench.bootloader.provider;

import com.google.protobuf.Message;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.service.StaticHolder;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayOutputStream;
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
        if (Massendaten.class.isAssignableFrom(aClass)) {
            Message message;
            byte[] buffer = new byte[8192];
            int counter = 0;
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                counter += bytesRead;
                StaticHolder.currentTransferCount = counter; //für progressbar client
                output.write(buffer, 0, bytesRead);
            }

            message = Massendaten.parseFrom(output.toByteArray());

            return message;
        }
        else if (Struktdaten.class.isAssignableFrom(aClass)) {
            Message message = Struktdaten.parseFrom(inputStream);
            return message;
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
        byte[] bArray = m.toByteArray();

        for (int i = 0; i < bArray.length; i++) {
            entityStream.write(bArray[i]);
            StaticHolder.currentTransferCount = i; //für progressbar client
        }
    }
}