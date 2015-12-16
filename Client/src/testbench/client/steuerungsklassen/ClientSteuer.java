package testbench.client.steuerungsklassen;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.Printer;
import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.entities.Messdaten;
import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktdatenGrenz;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.client.HTTPClient;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
import testbench.client.gui.ProgressBarWindow;
import testbench.client.service.ClientConfig;
import testbench.client.service.DatenService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 26.11.2015
 */

public class ClientSteuer {
    public boolean isDebugMode = ClientConfig.getExemplar().getDebugMode();
    private HTTPClient httpClient;
    private DatenService dServe;

    public ClientSteuer() {
        httpClient = HTTPClient.getExemplar();
        dServe = new DatenService();
    }

    public List<Messdaten> holeMessdaten() {
        return null;
    }

    public MassendatenGrenz empfangeMassendaten(int id) {
        Printer.println("Empfange Massendaten mit ID: "+id);

        Massendaten m = httpClient.empfangeMassendaten(id);

        if(m != null) {
            dServe.schreibeMassendaten(m);
            Printer.println("Paketgroeße in KB: "+m.getInfo().getPaketGroesseKB());
            return new MassendatenGrenz(m);
        }
        return null;
    }

    public StruktdatenGrenz empfangeStruktdaten(int id) {
        return null;
    }

    public boolean sendeMassendaten(int id) {
        Response response;
        Printer.println("Sende Massendaten mit ID: "+id);
        Massendaten m = dServe.ladeMassendaten(id);
        Printer.println("Anzahl der verschickten Werte: "+String.valueOf(m.getValueCount()));

        response = httpClient.sendeMassendaten(m);

        if(response != null) {
            if(response.getStatus() == 200) return true;
            return false;
        }
        return false;
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
        } else return null;
        return massenInfoGrenzList;
    }

    public List<StruktInfoGrenz> getStruktInfoGrenzList(boolean getFromServer) {
        List<StruktInfo> struktInfoList;

        if(getFromServer) struktInfoList = httpClient.empfangeStruktdatenInfoListe();
        else struktInfoList = dServe.ladeStruktListe();

        List<StruktInfoGrenz> struktInfoGrenzList = new ArrayList<>();
        if(struktInfoList != null) {
            for(StruktInfo s : struktInfoList) struktInfoGrenzList.add(new StruktInfoGrenz(s));
        } else return null;
        return struktInfoGrenzList;
    }

    public Massendaten generiereZufallsMassendaten(int size) {
        Massendaten m = dServe.generiereZufallsMassendaten(size);
        dServe.schreibeMassendaten(m);
        return m;
    }

    public MassendatenGrenz ladeLokaleMassendaten(int id) {
        return new MassendatenGrenz(dServe.ladeMassendaten(id));
    }

    public StruktdatenGrenz ladeLokaleStruktdaten(int id) {
        return null;
    }

    public boolean connect(String adresse) {
        try {
            httpClient.connect(adresse);
        } catch (Exception e) {
            if(isDebugMode) e.printStackTrace();
            Printer.println("!!! Fehler in ClientSteuer/connect() | Konnte nicht zum Server verbinden !!!");
            return false;
        }
        return true;
    }

    public String getServerIP() {
        return HTTPClient.getExemplar().getServerIP();
    }

    public void starteDatenverwaltung() {
        testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl iActivate = new testbench.datenverwaltung.dateiverwaltung.impl.IActivateComponentImpl();
        iActivate.startComponent();
        iActivate.getComponentGui().setVisible(true);
    }

    public boolean isDebugMode() {
        return isDebugMode;
    }

    public void setDebugMode(boolean bool) {
        isDebugMode = bool;
    }
}
