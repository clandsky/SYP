package testbench.bootloader.service;

import javax.swing.*;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class StaticHolder {
    /* DATENVERWALTUNG */
    public static final String saveDirectory = "Protodaten/";
    public static final String saveMassendatenDirectory = saveDirectory+"Massendaten/";
    public static final String saveStruktdatenDirectory = saveDirectory+"Struktdaten/";
    public static final String saveMessdatenDirectory = saveDirectory+"Messdaten/";
    public static final String fileName = "ByteArray";
    public static final String infoFileName = "Info";


    /* CLIENT */
    /* PROGRESSBAR */
    public static int currentTransferCount = 0;
    public static SwingWorker<Integer, Integer> activeWorker = null;
    public static int currentTransferSizeByte = 0;

    /* ZEITMESSUNGEN (NUR CLIENT) */
    public static long gesamtZeit;

    /* ZEITMESSUNGEN (MESSAGE-BODY-READER) */
    public static long serialisierungsZeitMs;
    public static long deSerialisierungsZeitMs;

}
