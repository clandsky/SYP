package testbench.datenverwaltung.dateiverwaltung.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import testbench.bootloader.grenz.Frequency;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
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
    private JTextField dataset4TextField;
    private JTextField dataset1TextField;
    private JTextField dataset3TextField;
    private JTextField dataset2TextField;
    private JButton saveDeepStructButton;
    private JTextField dataset5TextField;

    public GeneratorGUI()
    {
        super("Generator GUI");
        setSize(new Dimension(500, 300));
        setContentPane(panelCentral);
        setLocationRelativeTo(null);
        setVisible(true);

        DefaultTableModel model = (DefaultTableModel) tableFrequencies.getModel();
        model.setColumnCount(3);

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
                    config = new MassenDef(Double.parseDouble(textFieldAbtastrate.getText()));
                    int count = model.getRowCount();
                    for (int i = 0; i < count; i++)
                    {
                        config.getFrequencies().add(
                                new Frequency(
                                        Double.parseDouble(model.getValueAt(i, 0).toString()),
                                        Double.parseDouble(model.getValueAt(i, 0).toString()),
                                        Double.parseDouble(model.getValueAt(i, 0).toString())
                                )
                        );
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();

                    JOptionPane.showConfirmDialog(panelCentral, "Massendaten Definition konnte nicht erstellt werden.");

                    return;
                }

                MassendatenProtos.Massendaten massendaten;
                massendaten = generator.generatorMassData(config, Integer.parseInt(textFieldSize.getText()));

                DateiSpeichern ds = new DateiSpeichern();
                ds.speicherMassendaten(massendaten);
            }
        });
        spinner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                System.out.println("Spinner Value: " + spinner.getValue());

                DefaultTableModel model = (DefaultTableModel) tableFrequencies.getModel();

                int count = model.getRowCount();
                for (int i = 0; i < Integer.parseInt(spinner.getValue().toString()) - count; i++)
                {
                    model.addRow(new Object[]{"1.0", "1.0", "0.0"});
                }

                while (model.getRowCount() > Integer.parseInt(spinner.getValue().toString()))
                {
                    model.removeRow(model.getRowCount() - 1);
                }
            }
        });
        saveDeepStructButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StruktDef def = new StruktDef();
                def.setItemAIDNameCount(Integer.parseInt(dataset1TextField.getText()));
                def.setItemJoinDefCount(Integer.parseInt(dataset2TextField.getText()));
                def.setItemSelItemCount(Integer.parseInt(dataset3TextField.getText()));
                def.setItemSelOrderCount(Integer.parseInt(dataset4TextField.getText()));
                def.setItemSelUIDCount(Integer.parseInt(dataset5TextField.getText()));

                Generator generator = new Generator();

                StruktdatenProtos.Struktdaten struktdaten;
                struktdaten = generator.generatorDeepStructure(def);

                DateiSpeichern ds = new DateiSpeichern();
                ds.speicherStruktdaten(struktdaten);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneData = new JTabbedPane();
        tabbedPaneData.setName("");
        panelCentral.add(tabbedPaneData, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        panelMassendaten = new JPanel();
        panelMassendaten.setLayout(new GridLayoutManager(1, 2, new Insets(4, 4, 4, 4), -1, -1));
        tabbedPaneData.addTab("Massendaten", panelMassendaten);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 2, new Insets(4, 4, 4, 4), -1, -1));
        panelMassendaten.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textFieldSize = new JTextField();
        panel1.add(textFieldSize, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Dateigröße (Nutzdaten in Byte):");
        panel1.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Abtastrate ( 0,1 entspricht 31 Sample / PI ):");
        panel1.add(label2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldAbtastrate = new JTextField();
        panel1.add(textFieldAbtastrate, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        speichernButton = new JButton();
        speichernButton.setText("Speichern");
        panel1.add(speichernButton, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 1, new Insets(4, 4, 4, 4), -1, -1));
        panelMassendaten.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        spinner = new JSpinner();
        panel2.add(spinner, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Anzahl Frequenzen");
        panel2.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tableFrequencies = new JTable();
        panel2.add(tableFrequencies, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        panelStruktdaten = new JPanel();
        panelStruktdaten.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneData.addTab("Stukturierte-Daten", panelStruktdaten);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(4, 4, 4, 4), -1, -1));
        panelStruktdaten.add(panel3, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        dataset4TextField = new JTextField();
        dataset4TextField.setText("50");
        panel3.add(dataset4TextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dataset1TextField = new JTextField();
        dataset1TextField.setText("1");
        panel3.add(dataset1TextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dataset3TextField = new JTextField();
        dataset3TextField.setText("20");
        panel3.add(dataset3TextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dataset2TextField = new JTextField();
        dataset2TextField.setText("10");
        panel3.add(dataset2TextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Datensatz 1:");
        panel3.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Anzahl Datensätze:");
        panel3.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Datensatz 2:");
        panel3.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Datensatz 3:");
        panel3.add(label7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Datensatz 4:");
        panel3.add(label8, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveDeepStructButton = new JButton();
        saveDeepStructButton.setText("Speichern");
        panel3.add(saveDeepStructButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Datensatz 5:");
        panel3.add(label9, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dataset5TextField = new JTextField();
        dataset5TextField.setText("5");
        panel3.add(dataset5TextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return panelCentral;
    }
}
