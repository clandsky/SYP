package testbench.datenverwaltung.dateiverwaltung.grenz;

/**
 * Created by CGrings on 03.12.2015.
 */
public class OrderByGrenz
{
    private LongLongGrenz aid;
    private String aaName;

    public OrderByGrenz(LongLongGrenz aid, String aaName)
    {
        this.aid = aid;
        this.aaName = aaName;
    }

    public String getAaName()
    {
        return aaName;
    }

    public void setAaName(String aaName)
    {
        this.aaName = aaName;
    }

    public LongLongGrenz getAid()
    {
        return aid;
    }

    public void setAid(LongLongGrenz aid)
    {
        this.aid = aid;
    }
}
