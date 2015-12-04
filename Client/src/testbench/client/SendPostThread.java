package testbench.client;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Created by svenm on 04.12.2015.
 */
public class SendPostThread extends Thread {
    private Massendaten[] massendatenArray;
    private WebTarget target;
    private Client client;
    private int startIndex;
    private int endIndex;

    public SendPostThread(String ipForTarget, Massendaten[] massendatenArray, int startIndex, int endIndex) {
        this.massendatenArray = massendatenArray;
        this.client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        this.target = client.target(ipForTarget);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void run() {
        for(int i=startIndex ; i<endIndex ; i++) {
            target.path("testlauf").request().post(Entity.entity(massendatenArray[i], MediaTypeExt.APPLICATION_PROTOBUF), Massendaten.class);
        }

    }
}
