package testbench.bootloader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Diese Klasse dient einer geordneten Ausgabe auf der Konsole und fügt Datum, sowie Zeit hinzu. Desweiteren
 * wurde eine Progressbar für das Debuggen entwickelt.
 */
 public class Printer {
    /**
     * Dient zur Ausgabe auf der Konsole und fügt den aktuellen Timestamp hinzu. erzeugt einen println()
     * @param s Message zur Ausgabe
     */
    public static void println(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.println(output);
    }
    /**
     * Dient zur Ausgabe auf der Konsole und fügt den aktuellen Timestamp hinzu. erzeugt einen print()
     * @param s Message zur Ausgabe
     */
    public static void print(String s)
    {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String output="";
        output+=df.format(cal.getTime());
        output+=" : "+s;
        System.out.print(output);
    }

    /**
     * erzeugt einen Seperator, welcher Ausgaben voneinander abgrenzen kann.
     * @deprecated
     *
     */
    public static void printSeperator()
    {
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Standardausgabe auf dem Server-Log
     */
    public static void printWelcome()
    {
        System.out.println("******************************************************************************************");
        System.out.println("*                                                                                        *");
        System.out.println("*                               Protobuf/REST-Testbench Server                           *");
        System.out.println("*      created by Christoph Landsky, Sven Riedel, Carsten Grings und Murat Tasdemir      *");
        System.out.println("*                                                                                        *");
        System.out.println("******************************************************************************************");
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * Progress-Bar für das Erstellen von Massen-/Struktdaten
     * @param progress Fortschritt
     * @param size Größe der Datei
     */
    public static void printProgressBar(int progress, float size) {
        if(size == 0f) return;
        StringBuilder progressBuffer = new StringBuilder();
        progressBuffer.append('|');

        for(int i=1 ; i<99*size ; i++) {
            if(i < progress*size) progressBuffer.append('=');
            else progressBuffer.append(' ');
        }
        progressBuffer.append('|');

        if(progress >= 100) {
            System.out.print("\r"+progressBuffer+" 100%\n");
            println("Fertig!");
        }
        else System.out.print("\r"+progressBuffer+" "+progress+"%");
    }
}
