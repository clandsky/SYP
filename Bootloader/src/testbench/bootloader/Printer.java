package testbench.bootloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Huskey on 08.12.2015.
 */
public class Printer {
    public void printlnWithDate(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.println(output);
    }
    public void printWithDate(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.print(output);
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
        System.out.println("--------------------------------------------------------------------------------");
    }
    public void printProgressBar(int progress, float size) {
        if(size == 0f || size == 0) return;
        StringBuffer progressBuffer = new StringBuffer();
        progressBuffer.append('|');

        for(int i=1 ; i<99*size ; i++) {
            if(i*size < progress*size) progressBuffer.append('=');
            else progressBuffer.append(' ');
        }
        progressBuffer.append('|');

        if(progress >= 100) {
            System.out.print("\r"+progressBuffer+" 100%\n");
            printlnWithDate("Fertig!");
        }
        else System.out.print("\r"+progressBuffer+" "+progress+"%");
    }

}
