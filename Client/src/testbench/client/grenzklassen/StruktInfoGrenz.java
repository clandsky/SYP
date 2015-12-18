package testbench.client.grenzklassen;

import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.grenz.StruktDef;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class StruktInfoGrenz {
    private int id;
    private int paketGroesseKB;
    private StruktDef def;

    public StruktInfoGrenz(StruktInfo s) {
        this.id = s.getId();
        this.paketGroesseKB = s.getPaketGroesseByte();
        this.def = s.getDef();
    }

    public int getId() {
        return id;
    }

    public int getPaketGroesseKB() {
        return paketGroesseKB;
    }

    public StruktDef getDef() {
        return def;
    }
}
