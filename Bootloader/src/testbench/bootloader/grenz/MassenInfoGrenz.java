package testbench.bootloader.grenz;

import testbench.bootloader.entities.MassenInfo;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class MassenInfoGrenz {
    private int id;
    private int paketGroesseByte;
    private MassenDef def;

    public MassenInfoGrenz(MassenInfo massenInfo) {
        this.id = massenInfo.getId();
        this.paketGroesseByte = massenInfo.getPaketGroesseByte();
        this.def = massenInfo.getDef();
    }

    public int getId() {
        return id;
    }

    public int getPaketGroesseByte() {
        return paketGroesseByte;
    }

    public MassenDef getDef() {
        return def;
    }
}
