package testbench.server.gui;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Huskey on 08.12.2015.
 */
public class KonsoleOutputStream extends OutputStream
{
    private byte[] bytes; //ByteArray zum Schreiben des Streams
    private Appender appender; //Runnable Thread als Nested-Class, der das ganze steuert

    public KonsoleOutputStream(JTextArea txtarea) {
        this(txtarea,1000);
    }

    public KonsoleOutputStream(JTextArea txtarea, int maxlin) {
        if(maxlin<1) { throw new IllegalArgumentException("KonsoleOutputStream maximum lines must be positive (value="+maxlin+")"); }
        bytes =new byte[1];
        appender=new Appender(txtarea,maxlin);
    }

    /** Löscht die aktuelle Ansicht. */
    public synchronized void clear() {
        if(appender!=null) { appender.clear(); }
    }

    @Override
    public synchronized void close() {
        appender=null;
    }

    @Override
    public synchronized void flush() {
    }
    @Override
    public synchronized void write(int val) {
        bytes[0]=(byte)val;
        write(bytes,0,1);
    }

    @Override
    public synchronized void write(byte[] ba) {
        write(ba,0,ba.length);
    }

    @Override
    public synchronized void write(byte[] ba,int str,int len) {
        if(appender!=null) { appender.append(bytesToString(ba,str,len)); }
    }

    static private String bytesToString(byte[] ba, int str, int len) {
        try { return new String(ba,str,len,"UTF-8"); } catch(UnsupportedEncodingException thr) { return new String(ba,str,len); } // all JVMs are required to support UTF-8
    }
    
    static class Appender implements Runnable {
        private final JTextArea textArea;
        private final int maxLines; // maximale Anzahl an Zeilen
        private final LinkedList<Integer> lengths; // Länge der Zeilen
        private final ArrayList<String> values; // Stringwerte, die angefügt werden sollen

        private int curLength; // Momentane Länge
        private boolean clear; // wurde clear() ausgeführt?
        private boolean queue; // ist ein Aufruf in der Queue

        Appender(JTextArea textarea, int maxlin) {
            textArea =textarea;
            maxLines =maxlin;
            lengths  =new LinkedList<Integer>();
            values   =new ArrayList<String>();
            curLength=0;
            clear    =false;
            queue    =true;
        }

        synchronized void append(String val) {
            values.add(val);
            if(queue) { queue=false; EventQueue.invokeLater(this); }
        }

        synchronized void clear() {
            clear=true;
            curLength=0;
            lengths.clear();
            values.clear();
            if(queue) { queue=false; EventQueue.invokeLater(this); }
        }

        // MUST BE THE ONLY METHOD THAT TOUCHES textArea!
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
            queue =true;
        }

        static private final String EOL1="\n";
        static private final String EOL2=System.getProperty("line.separator",EOL1);
    }

}
