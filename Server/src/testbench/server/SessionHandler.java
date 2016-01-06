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
 *   Dies ist die Klasse, welcher für die Bereitstellung eines Servers verantwortlich ist. Hier wird der Server gestartet, gestoppt und gesteuert.
 *   @author Christoph Landsky
 */
public class SessionHandler {

    // Klassenattribute

    /** Baut die Adresse für den URI zusammen */
    private String uri;
    /** Beinhaltet den Zustand, ob der Server läuft.*/
    private boolean running;
    /** Config für das Serverprogramm (dieses Modul). In diesem Fall ist nur der Port der Config relevant, kann jedoch erweitert werden.*/
    private ServerConfiguration config;


    // Diese Attribute werden für den WebServer selbst benötigt

    /** Uniform Resource Identifier. Dieser gibt dem Server den Pfad (samt Port), auf dem er im Netzwerk seinen Root legt*/
    private URI endpoint;

    /** Die ResourceConfig kümmert sich um die Resourcenverwaltung.
     * Der RC wird die RestResource übergeben, welche diese in den Server einbindet.
     * Des weiteren wird hier auch der MessageBodyProvider angegeben.
     */
    private ResourceConfig rc;

    /** Dies ist ein HTTP server, welcher ausgeführt wird.*/
    private HttpServer server;

    /**
     * Standardkonstruktor, welcher für die Initialisierung der benötigten Attribute verwendet wird.
     * Standardport ist in unserem Fall der Port 4000
     */
    public SessionHandler()
    {
        this.config=new ServerConfiguration();
        this.config.setPort(4000);
        this.running=false;
    }

    /**
     * Gibt die ServerConfiguration zurück.
     * @return eine ServerConfiguration
     */
    private ServerConfiguration getConfiguration ()
    {
        return config;
    }

    /**
     * Startet den Server.
     * @return Bei erfolg ist dies true, falls nicht false.
     */
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
    /**
     * Stoppt den Server.
     * @return Bei erfolg ist dies true, falls nicht false.
     */
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

    /**
     * Übergibt einen neuen Port an die ServerConfiguration. Allerdings muss der Server dennoch manuell gestoppt und neu gestartet werden.
     * @param port Integer mit der Port ID
     */
    public void setPort(int port)
    {
        this.config.setPort(port);
    }
    /**
     * Übergibt den aktuellen Port des Servers
     * @return Integer mit der Portnummer
     */
    public int getPort() {
        return config.getPort();
    }
    /**
     * Übergibt den aktuellen Status des Servers
     * @return Falls der Server läuft, kommt true zurück, ansonsten false
     */
    public boolean isRunning (){
        return running;
    }

}
