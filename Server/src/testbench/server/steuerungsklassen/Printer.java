package testbench.server.steuerungsklassen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Huskey on 08.12.2015.
 */
public class Printer {
    public void printOutputWithDate(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.println(output);
    }
    public void printSeperator(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.println(output);
    }
}
