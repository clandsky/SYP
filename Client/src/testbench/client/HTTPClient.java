package testbench.client;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Sven on 26.11.15.
 */
public class HTTPClient {
    public static void main(String args[]) {
        Client client = ClientBuilder.newBuilder().register(ProtoMessageBodyReader.class).build();

        WebTarget target = client.target("http://localhost:80/");

        Massendaten response = target.path( "testlauf" ).request().accept("application/x-protobuf").get(Massendaten.class);

        List<Werte> liste = response.getValueList();

        for (Werte w : liste)
        {
            System.out.println("Wert: "+w.getNumber());
        }

    }
}
