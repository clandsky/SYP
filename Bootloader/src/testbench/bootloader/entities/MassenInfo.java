package testbench.bootloader.entities;

import testbench.bootloader.grenz.MassenDef;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class MassenInfo {
    private int id;
    private int paketgroesseKB;
    private String path;
    private MassenDef def;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaketgroesseKB() {
        return paketgroesseKB;
    }

    public void setPaketgroesseKB(int paketgroesseKB) {
        this.paketgroesseKB = paketgroesseKB;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MassenDef getDef() {
        return def;
    }

    public void setDef(MassenDef def) {
        this.def = def;
    }
}
