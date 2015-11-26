package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.Resources;
import java.net.URI;

/**
 * Created by svenm on 08.11.2015.
 */
public class HTTPServer {
    public static void main (String args[]) throws Exception {
        URI endpoint = new URI("http://localhost/");
        ResourceConfig rc = new ResourceConfig(Resources.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
    }
}
