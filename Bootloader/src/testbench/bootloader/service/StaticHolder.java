package testbench.bootloader.service;

import javax.swing.*;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class StaticHolder {

    /* FOR CLIENT */
    public static int currentTransferProgress = 0;
    public static SwingWorker<Integer, Integer> activeWorker = null;
    public static int currentTransferSize;
}
