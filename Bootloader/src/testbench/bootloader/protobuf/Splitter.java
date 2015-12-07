package testbench.bootloader.protobuf;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.Werkzeug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenm on 04.12.2015.
 */
public class Splitter {
    /* 1 Double-Wert = 11 Byte || 90909 Double-Werte = 999999 Byte*/
    public List<Massendaten> splitMassendaten(Massendaten massendaten, int packetSizeKB) {
        int divider = packetSizeKB*1000/11;
        Werkzeug w = new Werkzeug();
        List<Massendaten> splittedMassendatenList = new ArrayList<>();
        List<Werte> werteList = massendaten.getValueList();
        int serializedSize = 0;
        int temp;

        if(packetSizeKB <= 10) System.out.println("\nSplitte Massendaten ... Paketgroeße: "+divider*11+" B");
        else System.out.println("\nSplitte Massendaten ... Paketgroeße: "+divider*11/1000+" KB");

        if(werteList.size()%divider == 0) temp = werteList.size()/divider;
        else temp = werteList.size()/divider+1;

        for(int x=0 ; x<temp ; x++) {
            Massendaten.Builder builder = Massendaten.newBuilder();
            List<Werte> chunkList;

            if(x == temp-1)chunkList = werteList.subList(x*divider,werteList.size()-1);
            else chunkList = werteList.subList(x*divider,x*divider+divider-1);

            for(int i=0 ; i<chunkList.size() ; i++) builder.addValue(chunkList.get(i));

            w.printProgressBar(((x+1)*100)/temp);

            splittedMassendatenList.add(builder.build());
        }

        System.out.println("Zahl der Massendaten nach splitten: " + splittedMassendatenList.size());

        for(int x=0 ; x<splittedMassendatenList.size() ; x++)
            serializedSize += splittedMassendatenList.get(x).getSerializedSize();

        System.out.println("Groesse der aufgeteilten Daten (serialisiert): " + serializedSize/1000 +" KB");

        return splittedMassendatenList;
    }

    public Massendaten mergeMassendaten(List<Massendaten> massendatenList) {
        Massendaten.Builder builder = Massendaten.newBuilder();

        for(Massendaten m : massendatenList) {
            for(Werte w : m.getValueList())
                builder.addValue(w);
        }
        return builder.build();
    }

    public List<Struktdaten> splitStruktddaten(Struktdaten struktdaten) {

        return null;
    }

    public Struktdaten mergeStruktdaten(List<Struktdaten> struktdatenList) {

        return null;
    }

}
