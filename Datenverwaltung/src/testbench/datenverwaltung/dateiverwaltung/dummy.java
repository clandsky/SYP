package testbench.datenverwaltung.dateiverwaltung;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.XmlFormat;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.Frequency;
import testbench.datenverwaltung.dateiverwaltung.gui.GeneratorGUI;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * Created by murattasdemir on 26.11.15.
 */
public class dummy
{
    static public void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //GeneratorGUI g = new GeneratorGUI();
        //g.setVisible(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MassendatenProtos.Massendaten massendaten = null;

        boolean exit = false;
        System.out.println(".......... Datenverwaltung ..........");
        System.out.println("  Bitte geben Sie eine Nummer ein");
        while (!exit)
        {
            System.out.println(".....................................");
            System.out.println("[1]" + " Massendaten generien");
            System.out.println("[2]" + " Generierte Daten Massendaten in XML schreiben");
            System.out.println("[3]" + " Hole Massendaten aus XML");
            System.out.println("[4]" + " Beenden der Dateiverwaltung");


            int n = Integer.parseInt(reader.readLine());
            switch (n)
            {
                /*
                 * Generiere Massendaten
                 */
                case 1:
                    Generator gen = new Generator();
                    // Konfiguration für den Generator füllen
                    MassenDef config = new MassenDef(0.1);
                    config.addFreqeuncy(new Frequency(0.3, 4.0, 0.0));
                    config.addFreqeuncy(new Frequency(1.0, 1.0, 0.0));
                    config.addFreqeuncy(new Frequency(3.0, 0.4, 0.0));
                    config.addFreqeuncy(new Frequency(5.0, 0.2, 0.0));
                    massendaten = gen.generatorMassData(config, 50000);

                    List<MassendatenProtos.Massendaten.Werte> list = massendaten.getValueList();
                    break;

                /*
                 * Speichere in XML
                 */
                case 2:
                    DateiSpeichern save = new DateiSpeichern();
                    save.speicherMasendaten(massendaten);

                    break;

                /*
                 * Lade aus XML
                 */
                case 3:
                    DateiLaden dl = new DateiLaden();
                    dl.ladeMassendaten(1);
                    break;

                case 4:
                    System.out.println("Die Datenverwaltung wurde beendet.");
                    exit = true;
                    break;

                default:
                    break;
            }
        }
        return;
    }
}

