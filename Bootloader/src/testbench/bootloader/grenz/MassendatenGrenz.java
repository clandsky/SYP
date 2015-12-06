package testbench.bootloader.grenz;

import java.util.List;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class MassendatenGrenz {
    private List<Double> values;

    public MassendatenGrenz(List<Double> values) {
        this.values = values;
    }

    public List<Double> getValues() {
        return values;
    }
}
