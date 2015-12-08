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
        System.out.println("--------------------------------------------------------------------------------");
    }
    public void printWelcome()
    {
        System.out.println("********************************************************************************");
        System.out.println("*                                                                              *");
        System.out.println("*                        Protobuf/REST-Testbench Server                        *");
        System.out.println("* created by Christoph Landsky, Sven Riedel, Carsten Grings und Murat Tasdemir *");
        System.out.println("*                                                                              *");
        System.out.println("*                                                                              *");
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        System.out.println("*                                                                              *");
        System.out.println("*                        Starting Server Initialization                        *");
        System.out.println("*                                                                              *");
        System.out.println("********************************************************************************");
        System.out.println("--------------------------------------------------------------------------------");
    }
}
