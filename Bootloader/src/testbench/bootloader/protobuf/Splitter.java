package testbench.bootloader.protobuf;

import testbench.bootloader.Printer;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenm on 04.12.2015.
 */
public class Splitter {
    private boolean PRINT_DEBUG = false;

    /* 1 Double-Wert = 11 Byte || 90909 Double-Werte = 999999 Byte*/
    public List<Massendaten> splitMassendaten(Massendaten massendaten, int packetSizeKB, float progressBarSizeMulti) {
        int divider = packetSizeKB*1000/11;
        Printer printer = new Printer();
        List<Massendaten> splittedMassendatenList = new ArrayList<>();
        List<Werte> werteList = massendaten.getValueList();
        int serializedSize = 0;
        int temp;

        if(PRINT_DEBUG) {
            if(packetSizeKB <= 10) System.out.println("\nSplitte Massendaten ... Paketgroeße: "+divider*11+" B");
            else System.out.println("\nSplitte Massendaten ... Paketgroeße: "+divider*11/1000+" KB");
        }

        if(werteList.size()%divider == 0) temp = werteList.size()/divider;
        else temp = werteList.size()/divider+1;

        for(int x=0 ; x<temp ; x++) {
            Massendaten.Builder builder = Massendaten.newBuilder();
            List<Werte> chunkList;

            if(x == temp-1)chunkList = werteList.subList(x*divider,werteList.size());
            else chunkList = werteList.subList(x*divider,x*divider+divider);

            for(int i=0 ; i<chunkList.size() ; i++) builder.addValue(chunkList.get(i));

            printer.printProgressBar(((x+1)*100)/temp,progressBarSizeMulti);

            splittedMassendatenList.add(builder.build());
        }

        if(PRINT_DEBUG) System.out.println("Zahl der Massendaten nach splitten: " + splittedMassendatenList.size());

        for(int x=0 ; x<splittedMassendatenList.size() ; x++)
            serializedSize += splittedMassendatenList.get(x).getSerializedSize();

        if(PRINT_DEBUG) System.out.println("Groesse der aufgeteilten Daten (serialisiert): " + serializedSize/1000 +" KB");

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

    public byte[] combineByteArrays(List<Massendaten> massendatenList) {
        List<byte[]> byteArrayList = new ArrayList<>();
        byte[] bigByte;
        int counter = 0;

        for(Massendaten m : massendatenList) {
            byte[] byteArray = m.toByteArray();
            byteArrayList.add(byteArray);
            counter+=byteArray.length;
        }

        bigByte = new byte[counter];
        counter = 0;

        for(byte[] bArray : byteArrayList) {
            for(int i=0 ; i<bArray.length ; i++) {
                bigByte[counter] = bArray[i];
                counter++;
            }
        }

        return bigByte;
    }

}
