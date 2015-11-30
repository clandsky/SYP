package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import testbench.bootloader.protobuf.ProtoMessageBodyReader;
import testbench.bootloader.protobuf.ProtoMessageBodyWriter;
import testbench.server.res.RestResource;
import java.net.URI;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */
public class HTTPServer {
    public static void main (String args[]) throws Exception {
        URI endpoint = new URI("http://localhost:80/");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ProtoMessageBodyReader.class).register(ProtoMessageBodyWriter.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
    }
}
