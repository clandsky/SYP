package testbench.bootloader.grenz;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by CGrings on 29.11.2015.
 */
@XmlRootElement
public class Frequency
{
    /**
     * Phasenverschiebung der Frequenz
     */
    private double phase;

    /**
     * Amplitude der Frequenz
     */
    private double amplitude;

    /**
     * Frequenz
     */
    private double frequency;

    public Frequency() {
    }

    /**
     * Constuctor zur Erstellung einer Frequenz für den Generator
     * @param frequency Winkel
     * @param amplitude Amplitude der Frequenz
     * @param phase Phasenverschiebung
     */
    public Frequency( double frequency, double amplitude, double phase )
    {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.phase = phase;
    }
    @XmlElement
    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }
    @XmlElement
    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }
    @XmlElement
    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
