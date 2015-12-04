package testbench.datenverwaltung.dateiverwaltung.entities;

/**
 * Created by CGrings on 03.12.2015.
 */
public class LongLongGrenz
{
    private long high;
    private long low;

    public LongLongGrenz(long high, long low)
    {
        this.high = high;
        this.low = low;
    }

    public long getHigh()
    {
        return high;
    }

    public void setHigh(long high)
    {
        this.high = high;
    }

    public long getLow()
    {
        return low;
    }

    public void setLow(long low)
    {
        this.low = low;
    }
}
