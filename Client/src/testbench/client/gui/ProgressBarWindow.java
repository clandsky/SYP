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
    private JPanel cardPanel;
    private JPanel transferPanel;
    private JPanel initPanel;
    private JPanel donePanel;

    private CardLayout cl = (CardLayout) cardPanel.getLayout();

    public ProgressBarWindow(boolean isDownload) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Fortschritt");
        pack();
        initGuiProperties(350,150);

        cl.show(cardPanel, "initCard");

        String transfer;
        if(isDownload) transfer = "Herunterladen..."; else transfer = "Hochladen...";

        textLabel.setText(transfer+" ("+StaticHolder.currentTransferSizeByte/1000+" KB)");
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
        progressBar.setString(String.valueOf(value)+"%");
    }

    public void enableProgressBar(boolean bool) {
        progressBar.setEnabled(bool);
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void changeCard(String cardName) {
        cl.show(cardPanel, cardName);
    }
}
