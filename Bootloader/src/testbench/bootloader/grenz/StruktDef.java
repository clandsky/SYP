package testbench.bootloader.grenz;

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

    public StruktDef(int itemJoinDefCount, int itemSelItemCount, int itemSelUIDCount, int itemSelOrderCount, int itemAIDNameCount) {
        this.itemJoinDefCount = itemJoinDefCount;
        this.itemSelItemCount = itemSelItemCount;
        this.itemSelUIDCount = itemSelUIDCount;
        this.itemSelOrderCount = itemSelOrderCount;
        this.itemAIDNameCount = itemAIDNameCount;
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
