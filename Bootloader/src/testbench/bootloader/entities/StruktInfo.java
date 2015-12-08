package testbench.bootloader.entities;

import testbench.bootloader.grenz.StruktDef;

/**
 * Created by Sven Riedel on 04.12.2015
 */
public class StruktInfo {
    private int id;
    private String path;
    private StruktDef def;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StruktDef getDef() {
        return def;
    }

    public void setDef(StruktDef def) {
        this.def = def;
    }
}
