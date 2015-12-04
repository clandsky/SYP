package testbench.bootloader.protobuf;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class WerteListConverter {
    public List<Double> werteListToDoubleList(List<Werte> werteList) {
        List<Double> doubleList = new ArrayList<>();
        for(Werte w : werteList) {
            doubleList.add(w.getNumber());
        }
        return doubleList;
    }
}
