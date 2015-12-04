package testbench.client;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenm on 04.12.2015.
 */
public class PrototypDatenbank {
    public static List<Massendaten> massendatenList = new ArrayList<>();
    public static List<Struktdaten> struktdatenList = new ArrayList<>();

    public static int addMassendaten(Massendaten m) {
        massendatenList.add(m);
        return massendatenList.size()-1;
    }

    public static int addStruktdaten(Struktdaten s) {
        struktdatenList.add(s);
        return struktdatenList.size()-1;
    }

    public static Massendaten getMassendaten(int id) {
        return massendatenList.get(id);
    }

    public static Struktdaten getStruktdaten(int id) {
        return struktdatenList.get(id);
    }
}
