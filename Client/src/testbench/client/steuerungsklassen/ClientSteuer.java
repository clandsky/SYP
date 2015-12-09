package testbench.client.steuerungsklassen;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
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
    private boolean PRINT_DEBUG = true;
    private HTTPClient httpClient = HTTPClient.getExemplar();
    private DatenService dServe = new DatenService();
    private Printer printer = new Printer();

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) throws InvalidProtocolBufferException {
        Massendaten m;

        ByteMessage byteMessage = httpClient.empfangeMassendaten(id);
        m = byteMessage.getMassendatenFromByteArray();

        if(PRINT_DEBUG) printer.println("Letzter empfangener Wert: "+m.getValueList().get(m.getValueCount()-1));

        dServe.schreibeMassendaten(m);

        return new MassendatenGrenz(m);
    }

    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    public boolean sendeMassendaten(int id) {
        Massendaten m = dServe.ladeMassendaten(id);
        if(PRINT_DEBUG) printer.println("Letzter gesendeter Wert: "+m.getValueList().get(m.getValueCount()-1).getNumber());
        return httpClient.sendeMassendaten(new ByteMessage(m, 1000, 1f)).getStatus() == 200;
    }

    public boolean sendeStruktdaten(int id) {
        return true;
    }

    public List<MassenInfoGrenz> getMassenInfoGrenzList(boolean getFromServer) {
        List<MassenInfo> massenInfoList;

        if(getFromServer) massenInfoList = httpClient.empfangeMassendatenInfoListe();
        else massenInfoList = dServe.ladeMassenListe();

        List<MassenInfoGrenz> massenInfoGrenzList = new ArrayList<>();
        if(massenInfoList != null) {
            for(MassenInfo m : massenInfoList) massenInfoGrenzList.add(new MassenInfoGrenz(m));
        }
        return massenInfoGrenzList;
    }

    public List<StruktInfoGrenz> getStruktInfoGrenzList(boolean getFromServer) {
        List<StruktInfo> struktInfoList;

        if(getFromServer) struktInfoList = httpClient.empfangeStruktdatenInfoListe();
        else struktInfoList = dServe.ladeStruktListe();

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
        try {
            httpClient.connect(adresse);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void starteDatenverwaltung() {
        testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl iActivate = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
        iActivate.startComponent();
        iActivate.getComponentGui().setVisible(true);
    }
}
