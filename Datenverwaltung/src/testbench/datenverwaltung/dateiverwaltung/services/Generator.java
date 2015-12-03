package testbench.datenverwaltung.dateiverwaltung.services;

import testbench.datenverwaltung.dateiverwaltung.entities.Frequency;

import java.util.ArrayList;
import static java.lang.Math.*;

/**
 * Created by CGrings on 29.11.2015.
 */
public class Generator
{
    /**
     *
     * @param sampleRate
     * @param fileSize
     * @param type
     * @return
     */
    /// @Todo Type hinzufügen
    public ArrayList<Object> generatorMassData(double sampleRate, ArrayList<Frequency> frequencies, int fileSize, int typeSize )
    {
        double pos = 0.0f;
        ArrayList<Object> result = new ArrayList<>();

        for( int i = 0; i < fileSize / typeSize; i++ )
        {
            double value = 0.0f;
            for( Frequency f : frequencies )
            {
                value += sin( f.getPhase() + f.getFrequency() * pos ) * f.getAmplitude();
            }

            result.add( new Double(value) );
            pos += sampleRate;
        }

        return result;
    }
}
