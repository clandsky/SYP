package testbench.bootloader.entities;

import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sven Riedel on 04.12.2015
 */
@XmlRootElement
public class StruktInfo {
    private int id;
    private int paketGroesseByte;
    private StruktDef def;

    public StruktInfo(StruktdatenProtos.Struktdaten.StruktInfo info) {
        this.id = info.getId();
        this.paketGroesseByte = info.getSize();
        this.def = new StruktDef(info.getDef());
    }

    public StruktInfo() {    }

    @XmlElement
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
    public void setPaketGroesseByte(int paketgroesseKB) {
        this.paketGroesseByte = paketgroesseKB;
    }

    @XmlElement
    public StruktDef getDef() {
        return def;
    }
    public void setDef(StruktDef def) {
        this.def = def;
    }
}
