package testbench.bootloader.entities;

import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Dies ist die Entity für die StruktInfo. Hier stehen alle Parameter, sowie die ID der Struktdaten drin. Jedes Struktdaten-Element
 * besitzt eine zugehörige StruktInfo zur Identifikation. Eine Liste aller StruktInfos kann vom Server angefordert werden
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
