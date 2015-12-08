package testbench.bootloader.entities;

import testbench.bootloader.grenz.StruktDef;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sven Riedel on 04.12.2015
 */
@XmlRootElement
public class StruktInfo {
    private int id;
    private String path;
    private StruktDef def;

    public StruktInfo() {
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    @XmlElement
    public StruktDef getDef() {
        return def;
    }

    public void setDef(StruktDef def) {
        this.def = def;
    }
}
