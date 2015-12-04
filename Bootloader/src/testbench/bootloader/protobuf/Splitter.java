package testbench.bootloader.protobuf;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenm on 04.12.2015.
 */
public class Splitter {
    /* 1 Double-Wert = 11 Byte || 90909 Double-Werte = 999999 Byte*/
    public List<Massendaten> splitMassendaten(Massendaten massendaten, int packetSizeKB) {
        System.out.println("\nSPLIT MASSENDATEN 1MB");
        int divider = packetSizeKB*1000/11;

        List<Massendaten> splittedMassendatenList = new ArrayList<>();
        List<Werte> werteList = massendaten.getValueList();

        int temp;
        if(werteList.size()%divider == 0) temp = werteList.size()/divider;
        else temp = werteList.size()/divider+1;

        System.out.println("temp: "+temp);

        for(int x=0 ; x<temp ; x++) {
            Massendaten.Builder builder = Massendaten.newBuilder();
            List<Werte> chunkList;

            if(x == temp-1) {
                chunkList = werteList.subList(x*divider,werteList.size()-1);
            } else {
                chunkList = werteList.subList(x*divider,x*divider+divider-1);
            }


            for(int i=0 ; i<chunkList.size() ; i++) {
                builder.addValue(chunkList.get(i));
            }

            splittedMassendatenList.add(builder.build());
        }

        /*
        for(int i=0 ; i<maxWerte ; i++) {
            Massendaten.Builder builder = Massendaten.newBuilder();

            if(i == maxWerte-1) {
                for(int j=0 ; j<werteList.size()-divider*i ; j++) {
                    builder.addValue(Massendaten.Werte.newBuilder().setNumber(werteList.get(i*divider+j).getNumber()));
                }
            } else {
                for(int j=0 ; j<divider ; j++) {
                    builder.addValue(werteList.get(i*divider+j));
                }
            }
            splittedMassendatenList.add(builder.build());
        } */

        return splittedMassendatenList;
    }

    public Massendaten mergeMassendaten(List<Massendaten> massendatenList) {

        return null;
    }

    public List<Struktdaten> splitStruktddaten(Struktdaten struktdaten) {

        return null;
    }

    public Struktdaten mergeStruktdaten(List<Struktdaten> struktdatenList) {

        return null;
    }

}
