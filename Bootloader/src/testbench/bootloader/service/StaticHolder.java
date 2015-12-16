package testbench.bootloader.service;

import javax.swing.*;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class StaticHolder {

    /* CLIENT */
    /* PROGRESSBAR */
    public static int currentTransferCount = 0;
    public static SwingWorker<Integer, Integer> activeWorker = null;
    public static int currentTransferSizeByte = 0;

    /* ZEITMESSUNGEN */
    public static long serialisierungsZeitMs;
    public static long deSerialisierungsZeitMs;
    public static long gesamtZeit;
}
