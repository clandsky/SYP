package testbench.server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import testbench.bootloader.Printer;
import testbench.bootloader.provider.ByteMessageBodyProvider;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.server.res.RestResource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *   Created by Christoph Landsky (30.11.2015)
 */
public class SessionHandler {
    private URI endpoint;
    private ResourceConfig rc;
    private HttpServer server;
    private int port=8000;
    private boolean running=false;


    public boolean startServer() {
        if (!running) {
            try {
                String uri = "http://localhost:" + port + "/";
                this.endpoint = new URI(uri);
                Printer.println("Configurating ResourceConfig with MessageBodyProvider... ");
                this.rc = new ResourceConfig(RestResource.class).register(ByteMessageBodyProvider.class).register(ProtoMessageBodyProvider.class);
                Printer.println("Booting Server -> http://localhost:"+port+"/");
                this.server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
                Printer.println("Server is running... ");
                this.running=true;
            } catch (Exception e) {
                Printer.println("Server konnte nicht gestartet werden. Bitte prüfen Sie,\nob eine weitere Instanz bereits läuft...");
            }
        }
        else
        {
            Printer.println("Server läuft bereits...");
        }
        return running;
    }

    public boolean stopServer(){
        if (running) {
            try {
                this.server.stop(0);
                this.running=false;
                Printer.println("Server gestopt");
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
        this.port=port;
    }
    public int getPort() {
        return port;
    }

    public boolean isRunning (){
        return running;
    }

}
