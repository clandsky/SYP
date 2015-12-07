package dummy;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.client.PrototypDaten;

/**
 * Created by svenm on 07.12.2015.
 */
public class IDatenverwaltungImpl {
    public Massendaten holeMassendaten(int id) {
        return PrototypDaten.getMassendaten(id);
    }

    public boolean schreibeMassendaten(Massendaten m) {
        return PrototypDaten.speichereMassendaten(m);
    }
}
