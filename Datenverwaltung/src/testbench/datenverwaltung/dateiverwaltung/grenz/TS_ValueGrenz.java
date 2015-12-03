package testbench.datenverwaltung.dateiverwaltung.grenz;

/**
 * Created by CGrings on 03.12.2015.
 */
public class TS_ValueGrenz
{
    private TS_UnionGrenz u;

    public TS_ValueGrenz(TS_UnionGrenz u)
    {
        this.u = u;
    }

    public TS_UnionGrenz getU()
    {
        return u;
    }

    public void setU(TS_UnionGrenz u)
    {
        this.u = u;
    }
}
