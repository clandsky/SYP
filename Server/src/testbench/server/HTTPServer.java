package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.res.RestResource;
import java.net.URI;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */
public class HTTPServer {
    public static void main (String args[]) throws Exception {
        URI endpoint = new URI("http://localhost:8000/");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ProtoMessageBodyProvider.class, ByteMessageBodyProvider.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
    }
}
