package testbench.server.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.StruktInfo.StruktDef;
import testbench.bootloader.provider.MediaTypeExt;
import testbench.bootloader.provider.ProtoMessageBodyProvider;
import testbench.datenverwaltung.dateiverwaltung.impl.IDatenVerwaltungImpl;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;
import testbench.server.steuerungsklassen.SessionHandler;
import testbench.server.StartServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Chrizzle Manizzle on 08.01.2016.
 */
public class ServerTest {
    private static SessionHandler session;

    private static Client client;
    private static WebTarget target;

    private static IDatenVerwaltungImpl idat;

    @BeforeClass
    public static void setUp() throws Exception {
        session = new SessionHandler();
        session.setPort(4000);
        session.startServer();

        idat = new IDatenVerwaltungImpl();
        client = ClientBuilder.newBuilder().register(ProtoMessageBodyProvider.class).build();
        target = client.target("http://localhost:4000");
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void testHelloWorld(){
        Response response = target.path("server").request().accept( MediaTypeExt.TEXT_PLAIN ).get();
        boolean test=false;
        if (response.getStatus()==200){
            test = true;
        }
        assertTrue ("testHelloWorld_00",test);
    }
    @Test
    public void testWrongPath(){
        Response response = target.path("serv").request().accept( MediaTypeExt.TEXT_PLAIN ).get();
        boolean test=false;
        if (response.getStatus()==404){
            test = true;
        }
        assertTrue (test);
    }
    @Test
    public void testWrongParameter(){
        Response response = target.path("server").request().accept( MediaTypeExt.APPLICATION_PROTOBUF ).get();
        boolean test=false;
        Printer.println(""+response.getStatus());
        if (response.getStatus()==406){
            test = true;
        }
        assertTrue (test);
    }

    @Test
    public void postStruktdaten_00(){
        StruktdatenProtos.Struktdaten struktdaten=idat.holeStrukturierteDaten(725305135);
        Response response = target.path("struktdaten").request().post(Entity.entity(struktdaten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        if (response.getStatus()==200)
        {
            test=true;
        }
        assertTrue(test);
    }
    @Test
    public void postStruktdaten_01(){
        MassendatenProtos.Massendaten massendaten=idat.holeMassendaten(1019071188);
        Response response = target.path("struktdaten").request().post(Entity.entity(massendaten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        Printer.println(""+response.getStatus());
        if (response.getStatus()==406)
        {
            test=true;
        }
        assertTrue(test);
    }
    @Test
    public void postStruktdaten_02(){
        StruktdatenProtos.Struktdaten struktdaten=null;
        Response response = target.path("struktdaten").request().post(Entity.entity(struktdaten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        if (response.getStatus()==406)
        {
            test=true;
        }
        assertTrue(test);
    }

    @Test
    public void getStruktdaten_00(){
        StruktdatenProtos.Struktdaten struktdaten=idat.holeStrukturierteDaten(725305135);
        StruktdatenProtos.Struktdaten struktdatenEmpfangen = target.path("struktdaten/725305135").request().accept( MediaTypeExt.APPLICATION_PROTOBUF ).get(StruktdatenProtos.Struktdaten.class);
        assertEquals(struktdaten,struktdatenEmpfangen);
    }

    @Test
    public void getStruktdaten_01(){

        StruktdatenProtos.Struktdaten struktdatenEmpfangen = target.path("struktdaten/1").request().accept( MediaTypeExt.APPLICATION_PROTOBUF ).get(StruktdatenProtos.Struktdaten.class);
        assertNull(struktdatenEmpfangen);
    }

    @Test
    public void getStruktInfo(){
        List<StruktInfo> info = idat.ladeStruktInfo();
        boolean test = true;
        List<StruktInfo> get = target.path("struktdaten").request().accept(MediaTypeExt.APPLICATION_XML).get(new GenericType<List<StruktInfo>>(){});
        if (!get.isEmpty())
        {
            for (int i = 0; i<info.size();i++)
            {
                if (info.get(i).getId()!=get.get(i).getId()) test=false;
            }
        }
        else if(!info.isEmpty()){test=false;}

        assertTrue(test);
    }


    @Test
    public void postMassendaten_00(){
        MassendatenProtos.Massendaten daten=idat.holeMassendaten(1019071188);
        Response response = target.path("massendaten").request().post(Entity.entity(daten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        if (response.getStatus()==200)
        {
            test=true;
        }
        assertTrue(test);
    }
    @Test
    public void postMassendaten_01(){
        StruktdatenProtos.Struktdaten struktdaten=idat.holeStrukturierteDaten(725305135);
        Response response = target.path("massendaten").request().post(Entity.entity(struktdaten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        Printer.println(""+response.getStatus());
        if (response.getStatus()==406)
        {
            test=true;
        }
        assertTrue(test);
    }
    @Test
    public void postMassendaten_02(){
        MassendatenProtos.Massendaten daten=null;
        Response response = target.path("massendaten").request().post(Entity.entity(daten,MediaTypeExt.APPLICATION_PROTOBUF), Response.class);
        boolean test=false;
        if (response.getStatus()==406)
        {
            test=true;
        }
        assertTrue(test);
    }

    @Test
    public void getMassendaten_00(){
        MassendatenProtos.Massendaten daten=idat.holeMassendaten(1019071188);
        MassendatenProtos.Massendaten massendatenEmpfangen = target.path("massendaten/1019071188").request().accept( MediaTypeExt.APPLICATION_PROTOBUF ).get(MassendatenProtos.Massendaten.class);
        assertEquals(daten,massendatenEmpfangen);
    }

    @Test
    public void getMassendaten_01(){
        MassendatenProtos.Massendaten massen = target.path("massendaten/1").request().accept( MediaTypeExt.APPLICATION_PROTOBUF ).get(MassendatenProtos.Massendaten.class);
        assertNull(massen);
    }

    @Test
    public void getMassenInfo(){
        List<MassenInfo> info = idat.ladeMassenInfo();
        boolean test = true;
        List<MassenInfo> get = target.path("massendaten").request().accept(MediaTypeExt.APPLICATION_XML).get(new GenericType<List<MassenInfo>>(){});
        if (!get.isEmpty())
        {
            for (int i = 0; i<info.size();i++)
            {
                if (info.get(i).getId()!=get.get(i).getId()) test=false;
            }
        }
        else if(!info.isEmpty()){test=false;}

        assertTrue(test);
    }
}