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
    private JLabel textLabel;

    public ProgressBarWindow(boolean isDownload) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle("Fortschritt");
        pack();
        initGuiProperties(350,150);

        String transfer;
        if(isDownload) transfer = "Herunterladen"; else transfer = "Hochladen";

        textLabel.setText(transfer+" von "+ StaticHolder.currentTransferSizeByte/1000+" KB");
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
        progressBar.setString(String.valueOf(value)+"%");
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
