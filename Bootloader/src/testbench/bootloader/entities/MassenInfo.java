package testbench.bootloader.entities;

import testbench.bootloader.grenz.MassenDef;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Sven Riedel on 04.12.2015
 */
@XmlRootElement
public class MassenInfo {
    private int id;
    private int paketgroesseKB;
    private MassenDef def;

    public MassenInfo() {}

    public MassenInfo(int id, int paketgroesseKB, MassenDef def) {
        this.id = id;
        this.paketgroesseKB = paketgroesseKB;
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
    public int getPaketgroesseKB() {
        return paketgroesseKB;
    }

    public void setPaketgroesseKB(int paketgroesseKB) {
        this.paketgroesseKB = paketgroesseKB;
    }

    @XmlElement
    public MassenDef getDef() {
        return def;
    }

    public void setDef(MassenDef def) {
        this.def = def;
    }
}
