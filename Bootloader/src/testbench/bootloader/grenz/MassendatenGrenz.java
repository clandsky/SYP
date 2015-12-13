package testbench.bootloader.grenz;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.MassenInfo;
import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class MassendatenGrenz {
    private List<Werte> values;
    private MassenInfo mInfo;

    public MassendatenGrenz(Massendaten m) {
        this.values = m.getValueList();
        this.mInfo = m.getInfo();
    }

    public List<Werte> getValues() {
        return values;
    }

    public MassenInfo getmInfo() {
        return mInfo;
    }
}
