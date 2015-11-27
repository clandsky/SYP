package testbench.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sven on 26.11.15.
 */
public class HTTPClient {
    public static void main(String args[]) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:80/");

        String response = target.path( "testlauf" ).request().accept(MediaType.TEXT_PLAIN).get(String.class);

        String test;
        test="Der Server der Hurensohn!";

        target.path( "testlauf/hahahaha" ).request().post( Entity.entity(test, MediaType.TEXT_PLAIN),String.class );

        System.out.println(response);
    }
}
