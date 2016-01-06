package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.eclipse.persistence.sessions.Session;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.ExtendedConfig;
import testbench.bootloader.Printer;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.res.RestResource;
import testbench.server.steuerungsklassen.ServerConfiguration;

import java.io.File;
import java.net.URI;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */
public class SessionHandler {
    private URI endpoint;
    private ResourceConfig rc;
    private HttpServer server;
    private ServerConfiguration config;
    private boolean running=false;
    private String uri;


    public SessionHandler()
    {
        this.config=new ServerConfiguration();
        this.config.setPort(4000);
    }

    private ServerConfiguration getConfiguration ()
    {
        return config;
    }

    public boolean startServer() {
        if (!running) {
            try {
                this.uri = "http://localhost:" + config.getPort() + "/";
                this.endpoint = new URI(uri);
                Printer.println("Configurating ResourceConfig with MessageBodyProvider... ");
                this.rc = new ResourceConfig(RestResource.class).register(ProtoMessageBodyProvider.class);
                Printer.println("Booting Server -> http://localhost:"+config.getPort()+"/");
                this.server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
                Printer.println("Server gestartet... ");
                this.running=true;
            } catch (Exception e) {
                Printer.println("Server konnte nicht gestartet werden. \nBitte prüfen Sie, ob eine weitere Instanz bereits läuft...");
                return false;
            }
        }
        else
        {
            running=false;
            Printer.println("Server läuft bereits...");
        }
        return running;
    }

    public boolean stopServer(){
        if (running) {
            try {
                this.server.stop(0);
                this.running=false;
                Printer.println("Server gestoppt...");
            } catch (Exception e) {
                e.printStackTrace();
                Printer.println("Server konnte nicht beendet werden...");
                return false;
            }
            return true;
        } else {
            Printer.println("Kein Server gestartet...");
            return false;
        }
    }
    public void setPort(int port)
    {
        this.config.setPort(port);
    }
    public int getPort() {
        return config.getPort();
    }

    public boolean isRunning (){
        return running;
    }

}
