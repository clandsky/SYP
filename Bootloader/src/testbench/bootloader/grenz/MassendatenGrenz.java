package testbench.bootloader.grenz;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten.Werte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class MassendatenGrenz {
    private List<Double> values;

    public MassendatenGrenz(List<Double> values) {
        this.values = values;
    }
    public MassendatenGrenz(Massendaten m) {
        this.values = werteListToDoubleList(m.getValueList());
    }

    public List<Double> getValues() {
        return values;
    }

    public Massendaten getMassendaten() {
        Massendaten.Builder builder = Massendaten.newBuilder();
        for(Double d : values) builder.addValue(Werte.newBuilder().setNumber(d));
        return builder.build();
    }

    private List<Double> werteListToDoubleList(List<Werte> werteList) {
        List<Double> doubleList = new ArrayList<>();

        for(Werte w : werteList)
            doubleList.add(w.getNumber());

        return doubleList;
    }
}
