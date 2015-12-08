package testbench.client.steuerungsklassen;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.provider.ByteMessage;
import testbench.client.HTTPClient;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
import testbench.client.service.DatenService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015
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

        return new MassendatenGrenz(massendaten);
    }

    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    public boolean sendeMassendaten(int id) {
        List<Massendaten> massendatenList;
        ByteMessage bm;

        Massendaten massendaten = dServe.ladeMassendaten(id);
        massendatenList = new Splitter().splitMassendaten(massendaten, 1000);
        System.out.println(massendaten.getValueList().get(massendaten.getValueCount()-1));
        System.out.println("\nSenden der Massendaten wird vorbereitet...\n");

        try {
            bm = new ByteMessage(new Splitter().combineByteArrays(massendatenList));
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

    public List<MassenInfoGrenz> empfangeMassenInfoGrenzList() {
        List<MassenInfo> massenInfoList = httpClient.empfangeMassendatenInfoListe();
        List<MassenInfoGrenz> massenInfoGrenzList = new ArrayList<>();
        if(massenInfoList != null) {
            for(MassenInfo m : massenInfoList) massenInfoGrenzList.add(new MassenInfoGrenz(m));
        }
        return massenInfoGrenzList;
    }

    public List<MassenInfoGrenz> holeLokaleMassenInfoGrenzList() {
        List<MassenInfo> massenInfoList = dServe.ladeMassenListe();
        List<MassenInfoGrenz> massenInfoGrenzList = new ArrayList<>();
        if(massenInfoList != null) {
            for(MassenInfo m : massenInfoList) massenInfoGrenzList.add(new MassenInfoGrenz(m));
        }
        return massenInfoGrenzList;
    }

    public List<StruktInfoGrenz> empfangeStruktInfoGrenzList() {
        List<StruktInfo> struktInfoList = httpClient.empfangeStruktdatenInfoListe();
        List<StruktInfoGrenz> struktInfoGrenzList = new ArrayList<>();
        if(struktInfoList != null) {
            for(StruktInfo s : struktInfoList) struktInfoGrenzList.add(new StruktInfoGrenz(s));
        }
        return struktInfoGrenzList;
    }

    public List<StruktInfoGrenz> holeLokaleStruktInfoGrenzList() {
        List<StruktInfo> struktInfoList = dServe.ladeStruktListe();
        List<StruktInfoGrenz> struktInfoGrenzList = new ArrayList<>();
        if(struktInfoList != null) {
            for(StruktInfo s : struktInfoList) struktInfoGrenzList.add(new StruktInfoGrenz(s));
        }
        return struktInfoGrenzList;
    }

    public Massendaten generiereZufallsMassendaten(int size) {
        Massendaten m = dServe.generiereZufallsMassendaten(size);
        dServe.schreibeMassendaten(m);
        return m;
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
