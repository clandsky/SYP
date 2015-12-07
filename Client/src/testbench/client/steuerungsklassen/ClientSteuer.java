package testbench.client.steuerungsklassen;

import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.Werkzeug;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.provider.ByteMessage;
import testbench.client.HTTPClient;
import testbench.client.PrototypDaten;
import testbench.client.grenzklassen.MassendatenListeGrenz;
import testbench.client.grenzklassen.StruktdatenListeGrenz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015.
 */

public class ClientSteuer {
    private boolean PRINT_STACKTRACE_CONSOLE = true;
    HTTPClient httpClient;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
    }

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) {
        Werkzeug w = new Werkzeug();
        Massendaten massendaten = null;
        MassendatenGrenz massendatenGrenz;
        ByteMessage byteMessage = httpClient.empfangeMassendaten(id);

        try
        {
            massendaten = Massendaten.parseFrom(byteMessage.getByteArray());
        }catch(Exception e){
            e.printStackTrace();
        }

        massendatenGrenz = new MassendatenGrenz(massendaten);

        return massendatenGrenz;
    }

    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    public MassendatenListeGrenz empfangeMassenInfo() {
        return null;
    }

    public StruktdatenListeGrenz empfangeStruktInfo() {
        return null;
    }

    public boolean sendeMassendaten(int id) {
        List<Massendaten> massendatenList;

        Massendaten massendaten = PrototypDaten.getMassendaten(id);
        massendatenList = new Splitter().splitMassendaten(massendaten, 1000);

        System.out.println("\nSenden der Massendaten wird vorbereitet...\n");

        try {
            ByteMessage bm = new ByteMessage(new Splitter().combineByteArrays(massendatenList));

            System.out.println("Erster wert: "+massendaten.getValueList().get(0));
            System.out.println("Letzter wert: "+massendaten.getValueList().get(massendaten.getValueList().size()-1));
            return httpClient.sendeMassendaten(bm).getStatus() == 200;
        } catch (Exception e) {
            if(PRINT_STACKTRACE_CONSOLE) e.printStackTrace();
            System.out.println("\n!!! Verbindung zum Server fehlgeschlagen !!!");
        }

            return true;
    }

    public boolean sendeStruktdaten(int id) {
        return true;
    }

    public boolean connect(String adresse) {
        httpClient.connect(adresse);
        return true;
    }

    public void starteDatenverwaltung() {

    }

    public MassendatenGrenz erzeugeZufallsMassendaten() {
        return null;
    }

}
