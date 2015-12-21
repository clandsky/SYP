package testbench.bootloader.entities;

import testbench.bootloader.grenz.MassenDef;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sven Riedel on 04.12.2015
 */
@XmlRootElement
public class MassenInfo {
    private int id;
    private int paketGroesseByte;
    private MassenDef def;

    public MassenInfo() {}

    public MassenInfo(int id, int paketGroesseByte, MassenDef def) {
        this.id = id;
        this.paketGroesseByte = paketGroesseByte;
        this.def = def;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlAttribute
    public int getPaketGroesseByte() {
        return paketGroesseByte;
    }

    public void setPaketGroesseByte(int paketgroesseByte) {
        this.paketGroesseByte = paketgroesseByte;
    }

    @XmlElement
    public MassenDef getDef() {
        return def;
    }

    public void setDef(MassenDef def) {
        this.def = def;
    }
}
