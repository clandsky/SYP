package testbench.datenverwaltung.dateiverwaltung.entities;

/**
 * Created by CGrings on 03.12.2015.
 */
public class SelValueExtGrenz
{
    private SelAIdNameUnitIdGrenz attr;
    private SelOpCodeGrenz oper;
    private TS_ValueGrenz value;

    public SelValueExtGrenz(SelAIdNameUnitIdGrenz attr, SelOpCodeGrenz oper, TS_ValueGrenz value)
    {
        this.attr = attr;
        this.oper = oper;
        this.value = value;
    }

    public TS_ValueGrenz getValue()
    {
        return value;
    }

    public void setValue(TS_ValueGrenz value)
    {
        this.value = value;
    }

    public SelOpCodeGrenz getOper()
    {
        return oper;
    }

    public void setOper(SelOpCodeGrenz oper)
    {
        this.oper = oper;
    }

    public SelAIdNameUnitIdGrenz getAttr()
    {
        return attr;
    }

    public void setAttr(SelAIdNameUnitIdGrenz attr)
    {
        this.attr = attr;
    }
}
