package testbench.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class ProgressBarWindow extends JFrame {
    private JPanel formPanel;
    private JProgressBar progressBar;
    private JLabel textLabel;
    private JButton schließenButton;

    public ProgressBarWindow(String title, boolean isDownload, int fileSize) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle(title);
        pack();
        initGuiProperties(350,150);

        String transfer;
        if(isDownload) transfer = "Herunterladen"; else transfer = "Hochladen";

        textLabel.setText(transfer+" von "+fileSize+" KB");

        initListeners();
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
        progressBar.setString(String.valueOf(value)+"%");
    }

    public void enableOkButton(boolean enable) {
        schließenButton.setEnabled(enable);
    }

    private void initListeners() {
        schließenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setTitle("Protobuf Testbench Client Settings");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
