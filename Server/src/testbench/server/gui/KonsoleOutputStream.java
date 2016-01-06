package testbench.server.gui;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Ein OutputStream für das Log-Fenster in der Server GUI.
 * Hiermit ist es möglich, dass System.out.print direkt auf der GUI angezeigt werden kann, ohne dass ein Terminal im Hintergrund läuft.
 * Dies wird durch ein Umleiten des outputStreams der JavaVM in diesen OutputStreams umgesetzt, welcher den eintreffenden ByteStream in
 * einen String umwandelt und diesen dann an eine JTextArea ausgibt.
 * Die Klasse beinhaltet eine Nested-Class, welche unabhängig vom Hauptthread agieren soll und somit "extends Runnable" ist.
 * Desweiteren nutzt der Thread eine EventQueue, um alle Anfragen richtig abarbeiten zu können.
 */
public class KonsoleOutputStream extends OutputStream
{
    /**ByteArray zum Schreiben des Streams*/
    private byte[] bytes;
    /**Runnable Thread als Nested-Class, der das Ganze steuert*/
    private Appender appender;

    /**
     * Konstruktor, der das Objekt initialisiert
     * @param txtarea TextArea, auf die der OutputStream umgeleitet werden soll
     * @param maxlin Maximale Zeilenanzahl
     */
    public KonsoleOutputStream(JTextArea txtarea, int maxlin) {
        if(maxlin<1) { throw new IllegalArgumentException("KonsoleOutputStream maximum lines must be positive (value="+maxlin+")"); }
        bytes =new byte[1];
        appender=new Appender(txtarea,maxlin);
    }

    /** Löscht die aktuelle Ansicht. */
    public synchronized void clear() {
        if(appender!=null) { appender.clear(); }
    }
    /** Beendet den OutputStream */
    @Override
    public synchronized void close() {
        appender=null;
    }

    /** @see OutputStream */
    @Override
    public synchronized void flush() {
    }
    /**
     * Nimmt einen Integer entgegen und wandelt diesen in ein ByteArray zur weiteren Verarbeitung um
     * @param val Integer
     */
    public synchronized void write(int val) {
        bytes[0]=(byte)val;
        write(bytes,0,1);
    }

    /**
     * Nimmt ein ByteArray entgegen und übergibt dieses an die untenstehende Write-Methode
     * @param b ByteArray
     */
    @Override
    public synchronized void write(byte[] b) {
        write(b,0,b.length);
    }

    /**
     * Übergibt die Parameter an bytesToString und gibt den dort erzeugten String an den Appender weiter.
     * @param b Übergebenes ByteArray
     * @param off Offset
     * @param len Länge
     */
    @Override
    public synchronized void write(byte[] b,int off, int len) {
        if(appender!=null) { appender.append(bytesToString(b,off,len)); }
    }

    /**
     * Wandelt ein ByteArray in einen String um.
     * @param b eingehendes ByteArray
     * @param off Offset
     * @param len Länge
     * @return
     */
    static private String bytesToString(byte[] b, int off, int len) {
        try { return new String(b,off,len,"UTF-8"); } catch(UnsupportedEncodingException thr) { return new String(b,off,len); } // all JVMs are required to support UTF-8
    }

    /**
     * Runnable-Klasse zur parallelen Verarbeitung des Outputstreams.
     */
    static class Appender implements Runnable {
        /** Definition des Line-Seperators*/
        static private final String EOL1="\n";
        /** Definition des Line-Seperators*/
        static private final String EOL2=System.getProperty("line.separator",EOL1);
        /** Ziel TextArea*/
        private final JTextArea textArea;
        /** maximale Anzahl an Zeilen */
        private final int maxLines;
        /** Länge der Zeilen */
        private final LinkedList<Integer> lengths;
        /** eingehende Strings, die angefügt werden sollen */
        private final ArrayList<String> values;

        /** momentane Zeilenlänge */
        private int curLength;
        /** Zustandsanzeige "clear". Wurde clear() aufgerufen? */
        private boolean clear;
        /** Zustandsanzeige "queue". Ist die Queue frei? */
        private boolean queue;

        /**
         * Konstruktor zur Initialisierung der Elemente
         * @param textarea Ziel-Textarea
         * @param maxlin Maximale Zeilenanzahl
         */
        Appender(JTextArea textarea, int maxlin) {
            textArea=textarea;
            maxLines=maxlin;
            lengths=new LinkedList<Integer>();
            values=new ArrayList<String>();
            curLength=0;
            clear=false;
            queue=true;
        }

        /**
         * Fügt einen String hinzu, setzt die Queue auf "belegt" und übergibt den Appender an die EventQueue (GUI-Relevant)
         * Wenn queue bereits belegt ist, wird nur die Liste mit den Strings erweitert. Dies geschieht solange, bis Queue frei ist und
         * der Appender an die EventQueue per .invokeLater() übergeben werden kann. Der GUI-Thread führt dann die run()-Operation aus und ändert die Anzeige
         * auf der TextArea entsprechend der bereits gefüllten value-Liste.
         * @param val String mit den Änderungen
         */
        synchronized void append(String val) {
            values.add(val);
            //Falls run() noch läuft, darf der Appender noch nicht an die Eventqueue übergeben werden
            if(queue) { queue=false; EventQueue.invokeLater(this); }
        }

        /**
         * Setzt 'clear' und löscht die aktuellen Values.
         * Danach übergibt dies die Laufzeit an die EventQueue.
         * Näheres siehe append()
         */
        synchronized void clear() {
            clear=true;
            curLength=0;
            lengths.clear();
            values.clear();
            //Falls run() noch läuft, darf der Appender noch nicht an die Eventqueue übergeben werden
            if(queue) { queue=false; EventQueue.invokeLater(this); }
        }


        /**
         * dies wird über die EventQueue aufgerufen (Runnable-Methode)
         * Diese Methode setzt den append() bzw clear() Aufruf auf der TextArea um.
         * Falls die unterste Zeile (Maxlin) erreicht wurde, wird die oberste gelöscht und alle folgenden eingerück (replaceRange())
         */
        @Override
        public synchronized void run() {
            if(clear) { textArea.setText(""); }

            for(String val: values) {
                curLength+=val.length();
                if(val.endsWith(EOL1) || val.endsWith(EOL2)) {
                    if(lengths.size()>=maxLines) { textArea.replaceRange("",0,lengths.removeFirst()); }
                    lengths.addLast(curLength);
                    curLength=0;
                }
                textArea.append(val);
            }
            values.clear();
            clear =false;
            //Gibt den Thread wieder frei, sodass wieder clear() oder append() weitergegeben werden dürfen.
            queue =true;
        }
    }
}
