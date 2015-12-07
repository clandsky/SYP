package testbench.client.steuerungsklassen;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.Werkzeug;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.provider.ByteMessage;
import testbench.client.HTTPClient;
import testbench.client.PrototypDaten;
import testbench.client.grenzklassen.MassendatenListeGrenz;
import testbench.client.grenzklassen.StruktdatenListeGrenz;
import testbench.client.service.DatenService;
import testbench.datenverwaltung.dateiverwaltung.impl.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015.
 */

public class ClientSteuer {
    private HTTPClient httpClient;
    private DatenService dServe;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
        dServe = new DatenService();
    }

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) throws InvalidProtocolBufferException {
        Massendaten massendaten;
        MassendatenGrenz massendatenGrenz;

        ByteMessage byteMessage = httpClient.empfangeMassendaten(id);

        massendaten = Massendaten.parseFrom(byteMessage.getByteArray());

        System.out.println("Empfangene Massendaten werden verarbeitet...");
        dServe.schreibeMassendaten(massendaten);

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

        Massendaten massendaten = dServe.ladeMassendaten(id);
        massendatenList = new Splitter().splitMassendaten(massendaten, 1000);

        System.out.println("\nSenden der Massendaten wird vorbereitet...\n");

        try {
            ByteMessage bm = new ByteMessage(new Splitter().combineByteArrays(massendatenList));
            return httpClient.sendeMassendaten(bm).getStatus() == 200;
        } catch (Exception e) {
            e.printStackTrace();
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
        testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl iActivate = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
        iActivate.startComponent();
        iActivate.getComponentGui().setVisible(true);
    }
}
