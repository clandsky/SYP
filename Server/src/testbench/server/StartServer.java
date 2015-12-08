package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.gui.ServerGUI;
import testbench.server.res.RestResource;
import testbench.server.steuerungsklassen.Printer;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Huskey on 08.12.2015.
 */
public class StartServer {
    ServerGUI serverGUI;
    Printer p;
    public StartServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.serverGUI = new ServerGUI();
        serverGUI.setTitle("Testbench-Protobuf/REST Server");
        serverGUI.setVisible(true);
        p=new Printer();
        initServer();

    }

    private void initServer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {

        p.printWelcome();
        String uri = "http://localhost:8000/";

        URI endpoint = new URI(uri);
        p.printOutputWithDate("Configurating ResourceConfig with MessageBodyProvider... ");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        p.printOutputWithDate("Booting Server... ");
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        p.printOutputWithDate("Server is running... ");
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

        p.printWelcome();
        String uri = "http://localhost:8000/";
        URI endpoint = new URI(uri);
        p.printOutputWithDate("Configurating ResourceConfig with MessageBodyProvider... ");
        ResourceConfig rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
        p.printOutputWithDate("Booting Server... ");
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
        p.printOutputWithDate("Server is running... ");
    }
}
