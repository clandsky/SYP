package testbench.bootloader;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class Werkzeug {

    public void printProgressBar(int progress) {
        StringBuffer progressBuffer = new StringBuffer();
        progressBuffer.append('|');

        for(int i=1 ; i<99 ; i++) {
            if(i < progress) progressBuffer.append('=');
            else progressBuffer.append(' ');
        }
        progressBuffer.append('|');

        if(progress >= 100) {
            System.out.print("\r"+progressBuffer+" 100%\n");
            System.out.println("Fertig!\n");
        }
        else System.out.print("\r"+progressBuffer+" "+progress+"%");
    }

    public List<Double> werteListToDoubleList(List<Werte> werteList) {
        List<Double> doubleList = new ArrayList<>();
        for(Werte w : werteList) {
            doubleList.add(w.getNumber());
        }
        return doubleList;
    }
}
