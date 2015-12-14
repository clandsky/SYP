package testbench.bootloader.provider;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.sun.javafx.tk.Toolkit;
import testbench.bootloader.Printer;
import testbench.bootloader.StartBootloader;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.client.gui.ClientGUI;
import testbench.client.gui.ProgressBarWindow;

import javax.swing.*;
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
import java.util.concurrent.ExecutionException;

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
            /* CLIENT PROGRESSBAR */
            ProgressBarWindow pWindow = null;
            int receiveLength = 1;
            if(StartBootloader.programType.equalsIgnoreCase("Client")) {
                receiveLength = ClientGUI.transferSize;
                pWindow = new ProgressBarWindow("Protobuf Testbench Fortschritt", true, receiveLength/1000);
            }
            long counter = 1;
            /* /CLIENT PROGRESSBAR */

            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);

                    /* CLIENT PROGRESSBAR */
                    counter += bytesRead;
                    if(pWindow != null) {
                        if(counter%(int)(receiveLength / 1000000) == 0) {
                            pWindow.setProgressBar((int) ((counter*100) / receiveLength));
                        }
                    }
                    /* /CLIENT PROGRESSBAR */
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* CLIENT PROGRESSBAR */
            if(pWindow != null) {
                pWindow.setProgressBar(100);
                pWindow.enableOkButton(true);
            }
            /* /CLIENT PROGRESSBAR */

            ByteMessage bm = new ByteMessage(output.toByteArray());
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
    public void writeTo(ByteMessage m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        byte[] bArray = m.getByteArray();

        ProgressBarWindow pWindow = null;
        if(StartBootloader.programType.equalsIgnoreCase("Client")) pWindow = new ProgressBarWindow("Protobuf Testbench Fortschritt", false, bArray.length / 1000);

        for (int i = 0; i < bArray.length; i++) {
            if(pWindow != null) {
                if(i%(int)(bArray.length / 1000000) == 0) {
                    pWindow.setProgressBar((int) (((long)(i)*100) / bArray.length));
                }
            }
            entityStream.write(bArray[i]);
        }

        if(pWindow != null) {
            pWindow.setProgressBar(100);
            pWindow.enableOkButton(true);
        }
    }

}