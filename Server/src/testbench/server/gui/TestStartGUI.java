package testbench.server.gui;

import testbench.server.steuerungsklassen.ServerConfiguration;

import javax.swing.*;

/**
 * Created by Chrizzle Manizzle on 22.12.2015.
 */
public class TestStartGUI {
    public static void main (String args[])
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ServerSettings s = new ServerSettings(new ServerConfiguration());

        s.setVisible(true);
    }
}
