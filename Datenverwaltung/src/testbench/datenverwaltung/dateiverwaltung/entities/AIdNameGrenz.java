package testbench.datenverwaltung.dateiverwaltung.entities;

/**
 * Created by CGrings on 03.12.2015.
 */
public class AIdNameGrenz
{
    private LongLongGrenz aid;
    private String aName;

    public AIdNameGrenz(LongLongGrenz aid, String aName)
    {
        this.aid = aid;
        this.aName = aName;
    }

    public LongLongGrenz getAid()
    {
        return aid;
    }

    public void setAid(LongLongGrenz aid)
    {
        this.aid = aid;
    }

    public String getaName()
    {
        return aName;
    }

    public void setaName(String aName)
    {
        this.aName = aName;
    }
}
