package testbench.datenverwaltung.dateiverwaltung.grenz;

import java.util.ArrayList;

/**
 * Created by CGrings on 04.12.2015.
 */
public class MassenDef
{
    private double abtastrate;
    private final ArrayList<Frequency> frequencies = new ArrayList<>();

    public MassenDef(double abtastrate)
    {
        this.abtastrate = abtastrate;
    }

    public ArrayList<Frequency> getFrequencies()
    {
        return frequencies;
    }

    public void addFreqeuncy( Frequency f )
    {
        frequencies.add(f);
    }

    public double getAbtastrate()
    {
        return abtastrate;
    }

    public void setAbtastrate(double abtastrate)
    {
        this.abtastrate = abtastrate;
    }
}
