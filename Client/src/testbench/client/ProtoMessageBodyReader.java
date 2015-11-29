package testbench.client;

import com.google.protobuf.Message;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Huskey on 29.11.2015.
 */
@Provider

@Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
public class ProtoMessageBodyReader implements MessageBodyReader<Message> {


    public boolean decider (Class type){
        boolean flag;
        System.out.println("---");
        flag =  MassendatenProtos.Massendaten.class.isAssignableFrom(type)
                ||StruktdatenProtos.Struktdaten.class.isAssignableFrom(type);
        return flag;
    }
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return decider(aClass);
    }

    @Override
    public Message readFrom(Class<Message> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        System.out.println("---");

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
}
