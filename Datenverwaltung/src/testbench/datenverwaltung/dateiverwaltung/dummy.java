package testbench.datenverwaltung.dateiverwaltung;

import testbench.datenverwaltung.dateiverwaltung.entities.Frequency;
import testbench.datenverwaltung.dateiverwaltung.grenz.QueryStuctureExtGrenz;
import testbench.datenverwaltung.dateiverwaltung.services.Generator;

import javax.management.Query;
import java.util.ArrayList;

/**
 * Created by murattasdemir on 26.11.15.
 */
public class dummy
{
    static public void main(String[] args)
    {
        Generator gen = new Generator();

        ArrayList<Frequency> arr = new ArrayList<>();
        arr.add( new Frequency( 0.3, 4.0, 0.0 ) );
        arr.add( new Frequency( 1.0, 1.0, 0.0 ) );
        arr.add( new Frequency( 3.0, 0.4, 0.0 ) );
        arr.add( new Frequency( 5.0, 0.2, 0.0 ) );

        double step = 0.1;

        ArrayList<Object> d = gen.generatorMassData( step, arr, 500, Generator.dataType.dtInt16 );

        QueryStuctureExtGrenz qse = gen.generatorDeepStructure( 1, 2, 3, 4, 5 );

        int i = 0;
        for( Object o : d )
        {
            Double in = new Double( (step * i));
            System.out.println( in.toString().replace( '.', ',' ) + "\t" + (o.toString()).replace('.', ',') );
            i++;
        }

        return;
    }
}
