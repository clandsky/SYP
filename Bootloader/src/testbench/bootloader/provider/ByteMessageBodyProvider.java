package testbench.bootloader.provider;

import com.google.protobuf.Message;
import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

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
        ProgressBarWindow pWindow = new ProgressBarWindow();

        WriteToThread wtt = new WriteToThread(m,pWindow,entityStream);
        try {
            wtt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pWindow.setVisible(false);
        pWindow.dispose();
    }

    public class WriteToThread extends Thread {
        ByteMessage m;
        ProgressBarWindow pWindow;
        OutputStream entityStream;

        public WriteToThread(ByteMessage m, ProgressBarWindow pWindow, OutputStream entityStream) {
            this.m = m;
            this.pWindow = pWindow;
            this.entityStream = entityStream;
        }

        public void run() {
            long longWert;
            byte[] bArray = m.getByteArray();

            for(int i=0 ; i<bArray.length ; i++) {
                longWert = (long)(i)*100;
                if(bArray.length<10000000) {
                    if(longWert%10000==0) {
                        pWindow.setProgressBar((int)(longWert/bArray.length));
                    }
                } else {
                    if(longWert%1000==0) {
                        pWindow.setProgressBar((int)(longWert/bArray.length));
                    }
                }
                try {
                    entityStream.write(bArray[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}