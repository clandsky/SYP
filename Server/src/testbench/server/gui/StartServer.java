package testbench.server.gui;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.res.RestResource;

import javax.swing.*;
import java.net.URI;

/**
 * Created by Huskey on 08.12.2015.
 */
public class StartServer {
    public static void main (String args[]) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ServerGUI serverGUI=new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        URI endpoint = new URI("http://localhost:8000/");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);

    }
}
