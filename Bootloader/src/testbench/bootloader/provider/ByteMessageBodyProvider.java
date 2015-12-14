package testbench.bootloader.provider;

import testbench.bootloader.StartBootloader;
import testbench.bootloader.service.StaticHolder;
import testbench.client.gui.ClientGUI;
import testbench.client.gui.ProgressBarWindow;

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
            long counter = 0; //progress
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();


            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                counter += bytesRead;

                if(counter%(int)(StaticHolder.currentTransferSize / 1000000) == 0) {
                    StaticHolder.currentTransferProgress = (int) ((counter*100) / StaticHolder.currentTransferSize);
                }
            }
            StaticHolder.currentTransferProgress = 100;
            return new ByteMessage(output.toByteArray());
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
    public void writeTo(ByteMessage m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        byte[] bArray = m.getByteArray();

        for (int i = 0; i < bArray.length; i++) {
            entityStream.write(bArray[i]);
            if(i%(int)(bArray.length / 1000000) == 0) {
                StaticHolder.currentTransferProgress = (int)(((long)(i)*100) / bArray.length);
            }
        }
        StaticHolder.currentTransferProgress = 100;
    }

}