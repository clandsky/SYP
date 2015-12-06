package testbench.client;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    private static HTTPClient httpClient;
    private Client client;
    private WebTarget target;

    private HTTPClient(){}

    public static HTTPClient getExemplar() {
        if(httpClient == null) httpClient = new HTTPClient();
        return httpClient;
    }

    public boolean connect(String adresse) {
        client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        target = client.target(adresse);

        return true;
    }

    public Response sendeMassendaten(Massendaten m) {
        Response response = target.path( "testlauf" ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).post(Entity.entity(m,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        return response;
    }

    public Response sendeStruktdaten(Struktdaten s) {
        return null;
    }

    public Massendaten empfangeMassendaten(int id) {
        return target.path( "testlauf" ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Massendaten.class);
    }

    public Struktdaten empfangeStruktdaten(int id) {
        return null;
    }

    public List<MassenInfo> empfangeMassendatenInfoListe() {
        return null;
    }

    public List<StruktInfo> empfangeStruktdatenInfoListe() {
        return null;
    }

    public String getServerIP() {
        return target.getUri().toString();
    }
}
