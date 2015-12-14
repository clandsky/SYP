package testbench.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sven Riedel on 14.12.2015
 */
public class ProgressBarWindow extends JFrame {
    private JPanel formPanel;
    private JProgressBar progressBar;


    public ProgressBarWindow() {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();
        initGuiProperties(300,150);
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
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
