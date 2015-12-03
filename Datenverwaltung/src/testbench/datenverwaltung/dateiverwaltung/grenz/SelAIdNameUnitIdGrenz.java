package testbench.datenverwaltung.dateiverwaltung.grenz;

/**
 * Created by CGrings on 03.12.2015.
 */
public class SelAIdNameUnitIdGrenz
{
    private AIdNameGrenz attr;
    private LongLongGrenz unitId;
    private Aggregate aggregate;

    public SelAIdNameUnitIdGrenz(AIdNameGrenz attr, LongLongGrenz unitId, Aggregate aggregate)
    {
        this.attr = attr;
        this.unitId = unitId;
        this.aggregate = aggregate;
    }

    public enum Aggregate
    {
        NONE, COUNT, DCOUNT, MIN, MAX, AVG, STDDEV, SUM, DISTINCT, POINT
    }

    public AIdNameGrenz getAttr()
    {
        return attr;
    }

    public void setAttr(AIdNameGrenz attr)
    {
        this.attr = attr;
    }

    public LongLongGrenz getUnitId()
    {
        return unitId;
    }

    public void setUnitId(LongLongGrenz unitId)
    {
        this.unitId = unitId;
    }

    public Aggregate getAggregate()
    {
        return aggregate;
    }

    public void setAggregate(Aggregate aggregate)
    {
        this.aggregate = aggregate;
    }

}
