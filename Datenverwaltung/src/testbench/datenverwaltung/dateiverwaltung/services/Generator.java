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

        AIdNameSequenceGrenz aIdNameSeq = qse.getaIdNameSequenceGrenz();
        for( int i = 0; i < aIdNameCount; i++ )
        {
            aIdNameSeq.getGroupBy().add(
                    new AIdNameGrenz(
                            new LongLongGrenz( 1, 2 ), "AIdNameGrenz"
                    ) );
        }

        JoinDefSequenceGrenz joinDefSeq = qse.getJoinDefSequenceGrenz();
        for( int i = 0; i < joinDefCount; i++ )
        {
            joinDefSeq.getJoinSeq().add(
                    new JoinDefGrenz(
                            new LongLongGrenz( 4, 8 ),
                            new LongLongGrenz( 16, 32 ),
                            "JoinDefGrenz",
                            JoinDefGrenz.JoinType.JTDEFAILT
                    )
            );
        }

        SelAIdNameUnitIdSequenceGrenz selAidNameUnitIdSeq = qse.getSelAIdNameUnitIdSequenceGrenz();
        for( int i = 0; i < selAidNameUnitIdCount; i++ )
        {
            selAidNameUnitIdSeq.getAnuSeq().add(
                    new SelAIdNameUnitIdGrenz(
                            new AIdNameGrenz(
                                    new LongLongGrenz( 64, 128 ), "AIdNameGrenz"
                            ),
                            new LongLongGrenz( 256, 512 ),
                            SelAIdNameUnitIdGrenz.Aggregate.COUNT
                    )
            );
        }

        SelItemSequenceGrenz selItemSeq = qse.getSelItemSequenceGrenz();
        for( int i = 0; i < selItemCount; i++ )
        {
            selItemSeq.getSelItem().add(
                    new SelItemGrenz(
                            SelOpCodeGrenz.CI_NOTINSET,
                            new SelValueExtGrenz(
                                    new SelAIdNameUnitIdGrenz(
                                            new AIdNameGrenz(
                                                    new LongLongGrenz( 3, 5 ),
                                                    "AIdNameGrenz"
                                            ),
                                            new LongLongGrenz( 7, 10 ),
                                            SelAIdNameUnitIdGrenz.Aggregate.POINT
                                    ),
                                    SelOpCodeGrenz.EQ,
                                    new TS_ValueGrenz(
                                            new TS_UnionGrenz( new Double( 1234.24 ), TS_UnionGrenz.DataType.DS_DOUBLE )
                                    )
                            )
                    )
            );
        }

        SelOrderSequenceGrenz selOrderSeq = qse.getSelOrderSequenceGrenz();
        for( int i = 0; i < selOrderCount; i++ )
        {
            selOrderSeq.getOrderBy().add(
                    new OrderByGrenz(
                            new LongLongGrenz( 45, 23 ),
                            "OrderByGrenz"
                    )
            );
        }
        return qse;
    }
}
