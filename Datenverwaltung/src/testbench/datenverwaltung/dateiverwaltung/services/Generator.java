package testbench.datenverwaltung.dateiverwaltung.services;

import testbench.datenverwaltung.dateiverwaltung.entities.Frequency;
import testbench.datenverwaltung.dateiverwaltung.grenz.*;

import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * Created by CGrings on 29.11.2015.
 */
public class Generator
{

    public enum dataType
    {
        dbInt8,
        dtInt16,
        dtInt32,
        dtInt64,
        dtFloat32,
        dtFloat64
    }

    /**
     * @param sampleRate
     * @param fileSize
     * @param type
     * @return
     */
    /// @Todo Type hinzufügen
    public ArrayList<Object> generatorMassData(double sampleRate, ArrayList<Frequency> frequencies, int fileSize, dataType type)
    {

        double pos = 0.0f;
        ArrayList<Object> result = new ArrayList<>();
        int typeSize = 1;

        // Datentypen in Bytes umrechnen
        switch (type)
        {
            case dbInt8:
                typeSize = 1;
                break;
            case dtInt16:
                typeSize = 2;
                break;
            case dtInt32:
                typeSize = 4;
                break;
            case dtInt64:
                typeSize = 8;
                break;
            case dtFloat32:
                typeSize = 4;
                break;
            case dtFloat64:
                typeSize = 8;
                break;
        }

        for (int i = 0; i < fileSize / typeSize; i++)
        {
            double value = 0.0f;
            for (Frequency f : frequencies)
            {
                value += sin(f.getPhase() + f.getFrequency() * pos) * f.getAmplitude();
            }
            switch (type)
            {
                case dbInt8:
                    result.add(new Byte((byte)value));
                    break;
                case dtInt16:
                    result.add(new Short((short)value));
                    break;
                case dtInt32:
                    result.add(new Integer((int)value));
                    break;
                case dtInt64:
                    result.add(new Long((long)value));
                    break;
                case dtFloat32:
                    result.add(new Float(value));
                    break;
                case dtFloat64:
                    result.add(new Double(value));
                    break;
            }
            pos += sampleRate;
        }

        return result;
    }

    public QueryStuctureExtGrenz generatorDeepStructure(int aIdNameCount, int joinDefCount, int selAidNameUnitIdCount, int selItemCount, int selOrderCount)
    {
        QueryStuctureExtGrenz qse = new QueryStuctureExtGrenz(
                new AIdNameSequenceGrenz(),
                new JoinDefSequenceGrenz(),
                new SelAIdNameUnitIdSequenceGrenz(),
                new SelItemSequenceGrenz(),
                new SelOrderSequenceGrenz()
        );

        return qse;
    }
}
