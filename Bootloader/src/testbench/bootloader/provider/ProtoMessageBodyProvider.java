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
 *   Dies ist das Herzstück der Serialisierung/Deserialisierung der Protodaten. Die Input-/Outputstreams werden hier
 *   behandelt. Die Annotationen geben Jersey an, dass dies ein "Provider" für das Empfangen (Consumes) und Senden (Produces)
 *   von "APPLICATION_PROTOBUF"-Formaten ist. Es müssen dafür lediglich die Interfaces MessageBodyReader und MessageBodyWriter implementiert werden.
 */
@Provider
@Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
@Produces(MediaTypeExt.APPLICATION_PROTOBUF)
public class ProtoMessageBodyProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {
    /**
     * Prüft, ob die eingegangene Klasse (aClass) eine Message (Protobuf) ist.
     * Das meiste passiert hier automatisch und läuft Jersey-intern. Die Parameter sind entsprechend des Interfaces vorgegeben.
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @return Gibt zurück, ob aus der eingegangenen Klasse eine Message gebaut werden kann
     */
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(aClass);
    }

    /**
     * Diese Methode liest aus einem eingehenden InputStream die Bytes ein und prüft, ob der eingegangene Stream auf Massendaten oder
     * Struktdaten geparset werden kann. Für unseren Zweck werden hier noch die möglichst genauen Deserialisierungszeiten gemessen.
     *
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @param multivaluedMap
     * @param inputStream
     * @return Message, die entweder als Struktdaten oder Massendaten zurückgegeben wird
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public Message readFrom(Class<Message> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        Message message = null;
        byte[] buffer = new byte[8192];
        int counter = 0;
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            counter += bytesRead;
            StaticHolder.currentTransferCount = counter; //für progressbar client
            output.write(buffer, 0, bytesRead);
        }

        StaticHolder.deSerialisierungsZeitMs = System.currentTimeMillis();
        if(Massendaten.class.isAssignableFrom(aClass)) message = Massendaten.parseFrom(output.toByteArray());
        if (Struktdaten.class.isAssignableFrom(aClass)) message = Struktdaten.parseFrom(output.toByteArray());
        StaticHolder.deSerialisierungsZeitMs = System.currentTimeMillis() - StaticHolder.deSerialisierungsZeitMs;

        if(message==null) throw new BadRequestException("Can't Deserailize");

        StaticHolder.currentTransferCount = 0;

        return message;
    }

    /**
     * Prüft, ob das Message-Objekt als byteStream geschrieben werden kann.
     *
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    /**
     * ermittelt die Größe der Message
     * @param m
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @return Serialisierte Größe der Message
     */
    @Override
    public long getSize(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return m.getSerializedSize();
    }

    /**
     * Schreibt die Message per toByteArray auf ein ByteArray. Dieses wird dann auf einen OutputStream geschrieben
     * @param m
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @param httpHeaders
     * @param entityStream
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public void writeTo(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        StaticHolder.serialisierungsZeitMs = System.currentTimeMillis();
        byte[] bArray = m.toByteArray();
        StaticHolder.serialisierungsZeitMs = System.currentTimeMillis() - StaticHolder.serialisierungsZeitMs;

        for (int i = 0; i < bArray.length; i++) {
            entityStream.write(bArray[i]);
            StaticHolder.currentTransferCount = i; //für progressbar client
        }
    }
}