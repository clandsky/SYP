package testbench.bootloader;

import testbench.bootloader.protobuf.massendaten.MassendatenProtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public void printProgressBar(int progress) {
        StringBuffer progressBuffer = new StringBuffer();
        progressBuffer.append('|');

        for(int i=1 ; i<99 ; i++) {
            if(i < progress) progressBuffer.append('=');
            else progressBuffer.append(' ');
        }
        progressBuffer.append('|');

        if(progress >= 100) {
            System.out.print("\r"+progressBuffer+" 100%\n");
            System.out.println("Fertig!\n");
        }
        else System.out.print("\r"+progressBuffer+" "+progress+"%");
    }

    public List<Double> werteListToDoubleList(List<MassendatenProtos.Massendaten.Werte> werteList) {
        List<Double> doubleList = new ArrayList<>();
        for(MassendatenProtos.Massendaten.Werte w : werteList) {
            doubleList.add(w.getNumber());
        }
        return doubleList;
    }
}
