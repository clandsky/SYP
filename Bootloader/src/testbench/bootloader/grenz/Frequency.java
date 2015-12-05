package testbench.bootloader.grenz;

/**
 * Created by CGrings on 29.11.2015.
 */
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

    public double getPhase() {
        return phase;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public double getFrequency() {
        return frequency;
    }
}
