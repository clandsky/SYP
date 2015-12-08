package testbench.client.grenzklassen;

import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.StruktDef;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class StruktInfoGrenz {
    private int id;
    private String path;
    private StruktDef def;

    public StruktInfoGrenz(StruktInfo s) {
        this.id = s.getId();
        this.path = s.getPath();
        this.def = s.getDef();
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public StruktDef getDef() {
        return def;
    }
}
