package testbench.bootloader.entities;

import testbench.bootloader.grenz.StruktDef;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sven Riedel on 04.12.2015
 */
@XmlRootElement
public class StruktInfo {
    private int id;
    private int paketGroesseKB;
    private StruktDef def;

    public StruktInfo(int id, int paketGroesseKB, StruktDef def) {
        this.id = id;
        this.paketGroesseKB = paketGroesseKB;
        this.def = def;
    }

    public StruktInfo() {
    }

    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute
    public int getPaketGroesseKB() {
        return paketGroesseKB;
    }
    public void setPaketGroesseKB(int paketgroesseKB) {
        this.paketGroesseKB = paketgroesseKB;
    }

    @XmlElement
    public StruktDef getDef() {
        return def;
    }
    public void setDef(StruktDef def) {
        this.def = def;
    }
}
