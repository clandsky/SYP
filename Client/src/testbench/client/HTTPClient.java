package testbench.client;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.ByteMessage;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    private static HTTPClient httpClient;
    private Client client;
    private WebTarget target;

    private final String MASSENDATEN = "massendaten";
    private final String STRUKTDATEN = "struktdaten";

    private HTTPClient(){}

    public static HTTPClient getExemplar() {
        if(httpClient == null) httpClient = new HTTPClient();
        return httpClient;
    }

    public boolean connect(String adresse) throws Exception {
        client = ClientBuilder.newBuilder().register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class).build();
        target = client.target(adresse);

        List<MassenInfo> massenInfoList = target.path( MASSENDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<MassenInfo>>() {} );

        if(massenInfoList != null) return true;
        return false;
    }

    public Response sendeMassendaten(ByteMessage m) {
        return target.path( MASSENDATEN ).request().post(Entity.entity(m,MediaTypeExt.APPLICATION_BYTEMESSAGE), Response.class);
    }

    public Response sendeStruktdaten(Struktdaten s) {
        return null;
    }

    public ByteMessage empfangeMassendaten(int id) {

        Response res = target.path( MASSENDATEN+"/"+id ).request().accept(MediaTypeExt.APPLICATION_BYTEMESSAGE).get(Response.class);
        return res.readEntity(ByteMessage.class);
    }

    public Struktdaten empfangeStruktdaten(int id) {
        return null;
    }

    public List<MassenInfo> empfangeMassendatenInfoListe() {
        List<MassenInfo> mInfo = target.path( MASSENDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<MassenInfo>>() {} );
        return mInfo;
    }

    public List<StruktInfo> empfangeStruktdatenInfoListe() {
        return null;
    }

    public String getServerIP() {
        return target.getUri().toString();
    }
}
