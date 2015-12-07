package testbench.bootloader.grenz;

import testbench.bootloader.Werkzeug;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;

import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class MassendatenGrenz {
    private List<Double> values;

    public MassendatenGrenz(List<Double> values) {
        this.values = values;
    }
    public MassendatenGrenz(Massendaten m) {
        this.values = new Werkzeug().werteListToDoubleList(m.getValueList());
    }

    public List<Double> getValues() {
        return values;
    }

    public Massendaten getMassendaten() {
        Massendaten.Builder builder = Massendaten.newBuilder();
        for(Double d : values) builder.addValue(Massendaten.Werte.newBuilder().setNumber(d));
        return builder.build();
    }
}
