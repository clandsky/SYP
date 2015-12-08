package testbench.client.gui;

import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by Sven Riedel on 08.12.2015
 */
public class ClientGUI extends JFrame {
    private JPanel formPanel;
    private JPanel cardPanel;
    private JButton einstellungenButton;
    private JPanel menuPanel;
    private JPanel connectPanel;
    private JPanel inputIpPanel;
    private JButton verbindenButton;
    private JTextField ipTextField;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel splitpanePanel;
    private JPanel leftPanelDownload;
    private JPanel rightPanelDownload;
    private JPanel massenLabelPanelDown;
    private JPanel struktLabelPanelDown;
    private JButton herunterladenButton;
    private JTable massenTableDownload;
    private JTable struktTableDownload;
    private JPanel leftPanelUpload;
    private JPanel rightPanelUpload;
    private JPanel massenLabelPanelUp;
    private JPanel struktLabelPanelUp;
    private JTable massenTableUpload;
    private JTable struktTableUpload;
    private JPanel leftPanelMessdaten;
    private JPanel massenLabelPanelMess;
    private JPanel struktLabelPanelMess;
    private JTable massenTableMess;
    private JTable struktTableMess;
    private JPanel rightPanelMessdaten;
    private JLabel refreshIconDownload;
    private JLabel refreshIconUpload;
    private JLabel refreshIconMessdaten;

    /* OBEN -> automatisch generiert */

    private final String ICON_REFRESH_PATH = "Client/res/refresh.png";



    ClientSteuer cSteuer = new ClientSteuer();
    CardLayout cl = (CardLayout) cardPanel.getLayout();
    JFrame frame = new JFrame(); //fuer popups
    boolean isIpTextFirstClicked = false;

    public ClientGUI() {
        setContentPane(formPanel);
       // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        setIcon(refreshIconDownload,ICON_REFRESH_PATH);
        setIcon(refreshIconUpload,ICON_REFRESH_PATH);
        setIcon(refreshIconMessdaten,ICON_REFRESH_PATH);



        verbindenButton.addActionListener(new ActionListener() {
            @Override // Listener für verbindenButton-Klick
            public void actionPerformed(ActionEvent e) {
                verbindenButtonAction();
            }
        });
        ipTextField.addMouseListener(new MouseAdapter() {
            @Override //Listener für Maus-Klick auf ipTextField
            public void mouseClicked(MouseEvent e) {
                if(!isIpTextFirstClicked) {
                    ipTextField.setText("");
                    isIpTextFirstClicked = true;
                }
            }
        });
        ipTextField.addActionListener(new ActionListener() {
            @Override //Listener für Enter auf ipTextField
            public void actionPerformed(ActionEvent e) {
                verbindenButtonAction();
            }
        });
        refreshIconMessdaten.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshMessdaten();
            }
        });
        refreshIconUpload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshUpload();
            }
        });
        refreshIconDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshDownload();
            }
        });
    }

    private void fillMassendatenList(JTable table, List<MassenInfoGrenz> mInfoGrenzList) {
        DefaultTableModel model;

        Object[][] data = new Object[mInfoGrenzList.size()][2];
        String[] columnNames = {"ID", "Größe"};

        if (!mInfoGrenzList.isEmpty()) {
            for (int i = 0; i < mInfoGrenzList.size(); i++) {
                data[i][0] = mInfoGrenzList.get(i).getId();
                data[i][1] = mInfoGrenzList.get(i).getPaketGroesseKB();
            }

            model = new DefaultTableModel(data, columnNames) {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            table.setModel(model);
        }
    }

    private void verbindenButtonAction() {
        if(ipTextField.getText() != null) {
            if(!ipTextField.getText().equals("")) {
                if(!ipTextField.getText().contains(" ")) {
                    String ip = ipTextField.getText();
                    boolean isConnected = false;

                    if(!ip.startsWith("http://")) ip = "http://"+ip;
                    if(!ip.endsWith("/")) ip += "/";
                    System.out.println(ip);
                   // isConnected = cSteuer.connect(ip);
                    isConnected = true;

                    if(!isConnected) JOptionPane.showMessageDialog(frame, "Server konnte nicht gefunden werden!");
                    else {
                        fillMassendatenList(massenTableUpload, cSteuer.holeLokaleMassenInfoGrenzList());
                        cl.show(cardPanel, "mainCard");
                    }
                } else JOptionPane.showMessageDialog(frame, "Bitte Leerstellen aus der IP entfernen!");
            } else JOptionPane.showMessageDialog(frame, "Bitte das IP-Feld ausfüllen!");
        } else JOptionPane.showMessageDialog(frame, "Bitte das IP-Feld ausfüllen!");
    }

    private void setIcon(JLabel label, String iconPath) {
        label.setText("");
        label.setIcon(new ImageIcon(iconPath));
    }

    private void refreshDownload() {
        System.out.println("refresh download");
    }
    private void refreshUpload() {
        System.out.println("refresh upload");
    }
    private void refreshMessdaten() {
        System.out.println("refresh messdaten");
    }
}
