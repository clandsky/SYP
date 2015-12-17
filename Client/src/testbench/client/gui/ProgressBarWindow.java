package testbench.client.gui;

import testbench.bootloader.service.StaticHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class ProgressBarWindow extends JFrame {
    private JPanel formPanel;
    private JProgressBar progressBar;
    private JLabel downloadLabel;
    private JPanel cardPanel;
    private JPanel downloadPanel;
    private JPanel initPanel;
    private JPanel donePanel;
    private JPanel uploadPanel;
    private JLabel uploadLabel;
    private JLabel doneLabel;

    private CardLayout cl = (CardLayout) cardPanel.getLayout();
    private boolean isDownload;

    public ProgressBarWindow(boolean isDownload) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Fortschritt");
        pack();
        initGuiProperties(350,150);

        this.isDownload = isDownload;

        cl.show(cardPanel, "initCard");

        if(isDownload) downloadLabel.setText("Herunterladen..."+" ("+StaticHolder.currentTransferSizeByte/1000+" KB)");
        else uploadLabel.setText("Hochladen..."+" ("+StaticHolder.currentTransferSizeByte/1000+" KB)");
    }

    /**
     * Diese Methode setzt den Fortschritt der JProgressBar.
     *
     * Wenn der Boolean-Wert isDownload false ist:
     * Die JProgressBar wird nicht angezeigt (Upload zeigt keine Bar an)
     *
     * Sonst:
     * Ist der Fortschritt größer als 0, so wird die JProgressBar angezeigt.
     * Ist der Fortschritt Größer oder gleich 100, so verschwindet die JProgressBar.
     *
     * @param value Fortschritt
     */
    public void setProgressBar(int value) {
        progressBar.setValue(value);
        progressBar.setString(String.valueOf(value)+"%");
        if(value > 0) {
            if(isDownload) cl.show(cardPanel,"downloadCard");
            else cl.show(cardPanel,"uploadCard");
        }
        if(value >= 100)  {
            if(!isDownload) doneLabel.setText("Server empfängt und verarbeitet Daten...");
            cl.show(cardPanel,"doneCard");
        }
    }

    /**
     * Hier werden einige allgemeine GUI Einstellungen festgelegt.
     * @param guiSizeX Breite des GUI-Fensters.
     * @param guiSizeY Höhe des GUI-Fensters.
     */
    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
