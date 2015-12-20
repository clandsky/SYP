package testbench.bootloader.grenz;

import testbench.bootloader.entities.StruktInfo;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class StruktInfoGrenz {
    private int id;
    private int paketGroesseByte;
    private StruktDef def;

    public StruktInfoGrenz(StruktInfo s) {
        this.id = s.getId();
        this.paketGroesseByte = s.getPaketGroesseByte();
        this.def = s.getDef();
    }

    public int getId() {
        return id;
    }

    public int getPaketGroesseByte() {
        return paketGroesseByte;
    }

    public StruktDef getDef() {
        return def;
    }
}
