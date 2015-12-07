package testbench.datenverwaltung.dateiverwaltung.gui;

import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by CGrings on 07.12.2015.
 *
 */
public class GeneratorGUI extends JFrame
{
    private JTabbedPane tabbedPaneData;
    private JPanel panelCentral;
    private JPanel panelMassendaten;
    private JPanel panelStruktdaten;
    private JButton speichernButton;
    private JTextField textFieldSize;
    private JTextField textFieldAbtastrate;
    private JSpinner spinner;

    public GeneratorGUI()
    {
        super("Generator GUI");
        setSize( new Dimension( 500, 300 ) );
        setContentPane( panelCentral );

        speichernButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Generator generator = new Generator();
                MassenDef config = new MassenDef( Double.parseDouble(textFieldAbtastrate.getText()));

                config.addFreqeuncy( new Frequency( 1.0, 1.0, 0.0 ) );
                config.addFreqeuncy( new Frequency( 0.5, 0.5, 0.0 ) );
                config.addFreqeuncy( new Frequency( 0.2, 0.4, 0.0 ) );

                generator.generatorMassData( config, Integer.parseInt( textFieldSize.getText() ) );
            }
        });
    }
}
