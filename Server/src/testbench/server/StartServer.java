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
    Printer p;
    URI endpoint;
    ResourceConfig rc;
    HttpServer server;

    public StartServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.serverGUI = new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);
        p=new Printer();
        initServer();

    }

    private void initServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {

        p.printWelcome();
        String uri = "http://localhost:8000/";

        endpoint = new URI(uri);
        p.printlnWithDate("Configurating ResourceConfig with MessageBodyProvider... ");
        rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        p.printlnWithDate("Booting Server... ");
        server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        p.printlnWithDate("Server is running... ");
    }

    public JFrame getGUI(){
        return serverGUI;
    }

    public static void main (String args[]) throws Exception {
        Printer p=new Printer();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ServerGUI serverGUI = new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        serverGUI.setResizable(false);

        p.printWelcome();
        String uri = "http://localhost:8000/";
        URI endpoint = new URI(uri);
        p.printlnWithDate("Configurating ResourceConfig with MessageBodyProvider... ");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        p.printlnWithDate("Booting Server... ");
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        p.printlnWithDate("Server is running... ");
    }
}
