package testbench.datenverwaltung.dateiverwaltung.gui;

import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiSpeichern;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.Generator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
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
    private JTable tableFrequencies;

    public GeneratorGUI()
    {
        super("Generator GUI");
        setSize( new Dimension( 500, 300 ) );
        setContentPane( panelCentral );

        DefaultTableModel model = (DefaultTableModel) tableFrequencies.getModel();
        model.setColumnCount( 3 );

        speichernButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Generator generator = new Generator();
                MassenDef config = null;

                DefaultTableModel model = (DefaultTableModel) tableFrequencies.getModel();

                try
                {
                    config = new MassenDef( Double.parseDouble(textFieldAbtastrate.getText()));
                    int count = model.getRowCount();
                    for( int i = 0; i < count; i++ )
                    {
                        config.getFrequencies().add(
                                new Frequency(
                                        Double.parseDouble( model.getValueAt( i, 0 ).toString() ),
                                        Double.parseDouble( model.getValueAt( i, 0 ).toString() ),
                                        Double.parseDouble( model.getValueAt( i, 0 ).toString() )
                                )
                        );
                    }
                }
                catch( Exception ex )
                {
                    ex.printStackTrace();

                    JOptionPane.showConfirmDialog( panelCentral, "Massendaten Definition konnte nicht erstellt werden." );

                    return;
                }

                MassendatenProtos.Massendaten massendaten;
                massendaten = generator.generatorMassData( config, Integer.parseInt( textFieldSize.getText() ) );

                DateiSpeichern ds = new DateiSpeichern();
                ds.speicherMassendaten(massendaten);
            }
        });
        spinner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                System.out.println( "Spinner Value: " + spinner.getValue() );

                DefaultTableModel model = (DefaultTableModel) tableFrequencies.getModel();

                int count = model.getRowCount();
                for( int i = 0; i < Integer.parseInt( spinner.getValue().toString() ) - count; i++ )
                {
                    model.addRow( new Object[]{ "1.0", "1.0", "0.0" } );
                }

                while( model.getRowCount() > Integer.parseInt( spinner.getValue().toString() ) )
                {
                    model.removeRow( model.getRowCount()-1 );
                }
            }
        });
    }
}
