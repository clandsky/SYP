package testbench.bootloader.grenz;

import testbench.bootloader.entities.MassenInfo;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class MassenInfoGrenz {
    private int id;
    private int paketGroesseKB;
    private MassenDef def;

    public MassenInfoGrenz(MassenInfo massenInfo) {
        this.id = massenInfo.getId();
        this.paketGroesseKB = massenInfo.getPaketGroesseKB();
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
