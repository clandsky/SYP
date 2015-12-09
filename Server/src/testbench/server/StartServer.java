package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.gui.ServerGUI;
import testbench.server.res.RestResource;
import testbench.bootloader.Printer;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Huskey on 08.12.2015.
 */
public class StartServer {
    ServerGUI serverGUI;
    URI endpoint;
    ResourceConfig rc;
    HttpServer server;

    public StartServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.serverGUI = new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);
        initServer();

    }

    private void initServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {

        Printer.printWelcome();
        String uri = "http://localhost:8000/";

        endpoint = new URI(uri);
        Printer.println("Configurating ResourceConfig with MessageBodyProvider... ");
        rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        Printer.println("Booting Server... ");
        server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        Printer.println("Server is running... ");
    }

    public JFrame getGUI(){
        return serverGUI;
    }

    public static void main (String args[]) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ServerGUI serverGUI = new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);

        Printer.printWelcome();
        String uri = "http://localhost:8000/";
        URI endpoint = new URI(uri);
        Printer.println("Configurating ResourceConfig with MessageBodyProvider... ");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        Printer.println("Booting Server... ");
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        Printer.println("Server is running... ");
    }
}
