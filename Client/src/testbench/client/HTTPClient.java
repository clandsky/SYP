package testbench.client;


import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    public static void main(String args[]) {
        Client client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        WebTarget target = client.target("http://localhost:80/");

        Massendaten response = target.path( "testlauf" ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Massendaten.class);
        List<Werte> liste = response.getValueList();
        for (int i=0 ; i<liste.size() ; i++) {
            System.out.println("Wert "+i+": "+liste.get(i).getNumber());
        }
    }
}
