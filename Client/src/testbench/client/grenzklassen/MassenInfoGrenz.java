package testbench.client.grenzklassen;

import testbench.bootloader.entities.MassenInfo;
import testbench.bootloader.grenz.MassenDef;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class MassenInfoGrenz {
    private int id;
    private int paketGroesseKB;
    private MassenDef def;

    public MassenInfoGrenz(MassenInfo massenInfo) {
        this.id = massenInfo.getId();
        this.paketGroesseKB = massenInfo.getPaketgroesseKB();
        this.def = massenInfo.getDef();
    }

    public int getId() {
        return id;
    }

    public int getPaketGroesseKB() {
        return paketGroesseKB;
    }

    public MassenDef getDef() {
        return def;
    }
}
