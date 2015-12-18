package testbench.bootloader.grenz;

import testbench.bootloader.entities.StruktInfo;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.SelOrder;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.AIDName;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.JoinDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.SelAIDNameUnitID;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten.SelItem;
import testbench.client.grenzklassen.StruktInfoGrenz;

import java.util.List;

/**
 * Created by svenm on 04.12.2015.
 */
public class StruktdatenGrenz {
    private List<SelOrder> selOrderList;
    private List<AIDName> aidNameList;
    private List<JoinDef> joinDefList;
    private List<SelAIDNameUnitID> selAIDNameUnitIDList;
    private List<SelItem> selItemList;
    private StruktInfo info;

    public StruktdatenGrenz(Struktdaten s) {
        this.selOrderList = s.getOrderByList();
        this.aidNameList = s.getGroupByList();
        this.joinDefList = s.getJoinSeqList();
        this.selAIDNameUnitIDList = s.getAnuSeqList();
        this.selItemList = s.getCondSeqList();
        this.info = new StruktInfo(s.getInfo());
    }

    public List<SelOrder> getSelOrderList() {
        return selOrderList;
    }

    public void setSelOrderList(List<SelOrder> selOrderList) {
        this.selOrderList = selOrderList;
    }

    public List<AIDName> getAidNameList() {
        return aidNameList;
    }

    public void setAidNameList(List<AIDName> aidNameList) {
        this.aidNameList = aidNameList;
    }

    public List<JoinDef> getJoinDefList() {
        return joinDefList;
    }

    public void setJoinDefList(List<JoinDef> joinDefList) {
        this.joinDefList = joinDefList;
    }

    public List<SelAIDNameUnitID> getSelAIDNameUnitIDList() {
        return selAIDNameUnitIDList;
    }

    public void setSelAIDNameUnitIDList(List<SelAIDNameUnitID> selAIDNameUnitIDList) {
        this.selAIDNameUnitIDList = selAIDNameUnitIDList;
    }

    public List<SelItem> getSelItemList() {
        return selItemList;
    }

    public void setSelItemList(List<SelItem> selItemList) {
        this.selItemList = selItemList;
    }

    public StruktInfo getInfo() {
        return info;
    }

    public void setInfo(StruktInfo info) {
        this.info = info;
    }
}
