package testbench.bootloader;

import javax.swing.*;

/**
 * Created by Chrizzle Manizzle on 10.12.2015.
 */
public class Execute {
    public static void main (String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        StartBootloader start = new StartBootloader();

    }
}
