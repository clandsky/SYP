package testbench.bootloader.grenz;

import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
@XmlRootElement
public class StruktDef {
    private int itemJoinDefCount;
    private int itemSelItemCount;
    private int itemSelUIDCount;
    private int itemSelOrderCount;
    private int itemAIDNameCount;

    public StruktDef() {}

    public StruktDef(StruktdatenProtos.Struktdaten.StruktInfo.StruktDef def) {
        this.itemJoinDefCount = def.getItemJoinDefCount();   //1
        this.itemAIDNameCount = def.getItemAIDNameCount();   //2
        this.itemSelItemCount = def.getItemSelItemCount();   //3
        this.itemSelOrderCount = def.getItemSelOrderCount(); //4
        this.itemSelUIDCount = def.getItemSelUIDCount();     //5
    }

    @XmlElement
    public int getItemJoinDefCount() {
        return itemJoinDefCount;
    }

    public void setItemJoinDefCount(int itemJoinDefCount) {
        this.itemJoinDefCount = itemJoinDefCount;
    }

    @XmlElement
    public int getItemSelItemCount() {
        return itemSelItemCount;
    }

    public void setItemSelItemCount(int itemSelItemCount) {
        this.itemSelItemCount = itemSelItemCount;
    }

    @XmlElement
    public int getItemSelUIDCount() {
        return itemSelUIDCount;
    }

    public void setItemSelUIDCount(int itemSelUIDCount) {
        this.itemSelUIDCount = itemSelUIDCount;
    }

    @XmlElement
    public int getItemSelOrderCount() {
        return itemSelOrderCount;
    }

    public void setItemSelOrderCount(int itemSelOrderCount) {
        this.itemSelOrderCount = itemSelOrderCount;
    }

    @XmlElement
    public int getItemAIDNameCount() {
        return itemAIDNameCount;
    }

    public void setItemAIDNameCount(int itemAIDNameCount) {
        this.itemAIDNameCount = itemAIDNameCount;
    }
}
