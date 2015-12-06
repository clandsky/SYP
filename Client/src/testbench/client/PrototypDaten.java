package testbench.client;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 05.12.2015.
 */
public class PrototypDaten {
    public static List<Massendaten> mList = new ArrayList<>();

    public static Massendaten getMassendaten(int id) {
        return mList.get(id);
    }
}
