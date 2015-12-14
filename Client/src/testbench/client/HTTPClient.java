package testbench.client;

import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *   Created by Sven Riedel (30.11.2015)
 */
public class HTTPClient {
    private boolean printStackTrace = true;
    private static HTTPClient httpClient;
    private Client client;
    private WebTarget target;

    private final String MASSENDATEN = "massendaten";
    private final String STRUKTDATEN = "struktdaten";
    private final String HELLO_SERVER = "server";

    private HTTPClient(){}

    public static HTTPClient getExemplar() {
        if(httpClient == null) httpClient = new HTTPClient();
        return httpClient;
    }

    public boolean connect(String adresse) throws Exception {
        client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        target = client.target(adresse);

        Response response = target.path( HELLO_SERVER ).request().accept( MediaTypeExt.TEXT_PLAIN ).get();

        if(response != null) {
            if(response.getStatus() == 200) return true;
        }

        return false;
    }

    public Response sendeMassendaten(Massendaten m) {
        try{
            return target.path( MASSENDATEN ).request().post(Entity.entity(m,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        } catch (Exception e) {
            Printer.println("Exception in HTTPClient/sendeMassendaten : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    public Response sendeStruktdaten(Struktdaten s) {
        return null;
    }

    public Massendaten empfangeMassendaten(int id) {
        try{
            Response res = target.path( MASSENDATEN+"/"+id ).request().accept(MediaTypeExt.APPLICATION_PROTOBUF).get(Response.class);
            Massendaten m = res.readEntity(Massendaten.class);
            return m;
        } catch (Exception e) {
            Printer.println("Exception in HTTPClient/empfangeMassendaten : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    public Struktdaten empfangeStruktdaten(int id) {
        return null;
    }

    public List<MassenInfo> empfangeMassendatenInfoListe() {
        try {
            List<MassenInfo> mInfo = target.path( MASSENDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<MassenInfo>>() {} );
            return mInfo;
        } catch(Exception e) {
            Printer.println("Exception in HTTPClient/empfangeMassendatenInfoListe : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    public List<StruktInfo> empfangeStruktdatenInfoListe() {

        try {
            List<StruktInfo> sInfo = target.path( STRUKTDATEN ).request().accept( MediaTypeExt.APPLICATION_XML ).get( new GenericType<List<StruktInfo>>() {} );
            return sInfo;
        } catch(Exception e) {
            Printer.println("Exception in HTTPClient/empfangeStruktdatenInfoListe : Verbindung fehlgeschlagen");
            if(printStackTrace) e.printStackTrace();
            return null;
        }
    }

    public String getServerIP() {
        return target.getUri().toString();
    }
}
