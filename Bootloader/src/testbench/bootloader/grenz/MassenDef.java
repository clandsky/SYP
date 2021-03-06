package testbench.bootloader.grenz;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CGrings on 04.12.2015
 */
@XmlRootElement
public class MassenDef
{
    private double abtastrate;
    private ArrayList<Frequency> frequencies = new ArrayList<>();

    public MassenDef() {}

    public MassenDef(double abtastrate, ArrayList<Frequency> frequencies) {
        this.abtastrate = abtastrate;
        this.frequencies = frequencies;
    }

    public MassenDef(double abtastrate)
    {
        this.abtastrate = abtastrate;
    }

    @XmlElement
    public ArrayList<Frequency> getFrequencies()
    {
        return frequencies;
    }
    public void setFrequencies(List<Frequency> frequencies) {
        this.frequencies = (ArrayList<Frequency>) frequencies;
    }

    @XmlElement
    public double getAbtastrate()
    {
        return abtastrate;
    }

    public void setAbtastrate(double abtastrate)
    {
        this.abtastrate = abtastrate;
    }


}
