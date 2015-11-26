package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import testbench.server.res.RestResource;
import java.net.URI;

/**
 * Created by svenm on 08.11.2015.
 */
public class HTTPServer {
    public static void main (String args[]) throws Exception {
        URI endpoint = new URI("http://localhost:80/");
        ResourceConfig rc = new ResourceConfig(RestResource.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
    }
}
