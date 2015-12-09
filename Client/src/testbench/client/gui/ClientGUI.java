package testbench.client.gui;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
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
    private JPanel connectPanel;
    private JPanel inputIpPanel;
    private JButton verbindenButton;
    private JTextField ipTextField;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel splitpanePanelDown;
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
    private JSplitPane splitPaneDown;
    private JSplitPane splitPaneUp;
    private JSplitPane splitPaneMess;
    private JLabel artLabelDown;
    private JLabel idLabelDown;
    private JLabel groesseLabelDown;
    private JLabel artLabelUp;
    private JLabel idLabelUp;
    private JLabel groesseLabelUp;
    private JPanel detailsPanelDown;
    private JPanel buttonsPanelDown;
    private JPanel buttonsPanelUp;
    private JPanel detailsPanelUp;
    private JPanel splitPanePanelUp;
    private JButton hochladenButton;
    private JPanel connectHeaderPanel;
    private JPanel connectBottomPanel;
    private JLabel connectTitleLabel;
    private JPanel connectMiddlePanel;
    private JLabel protobufLogoLabel;
    private JLabel thLogoLabel;

    /* OBEN -> automatisch generiert */

    /* ############## VARIABLEN ################ */
    private final String port = "8000";
    private final int DIVIDER_LOCATION = 250; //divider position zwischen jsplitpanes
    private ClientSteuer cSteuer = new ClientSteuer();
    private CardLayout cl = (CardLayout) cardPanel.getLayout();
    private JFrame frame = new JFrame(); //fuer popups
    private boolean isIpTextFirstClicked = false;  //wenn false wird beim klick auf ip-textfield inhalt geleert

    /* ############## RESSOURCEN PFADE ################ */
    private final String IMAGE_REFRESH_PATH = "Client/res/refresh.png";
    private final String IMAGE_PROTOBUF_PATH = "Client/res/logo_protobuf.png";
    private final String IMAGE_TH_PATH = "Client/res/logo_th.png";

    /* ############## AUSGABE_STRINGS ################ */
    private final String SERVER_NOT_FOUND_STRING = "Server konnte nicht gefunden werden!";
    private final String NO_EMPTY_SPACES_STRING = "Bitte Leerstellen aus der IP entfernen!";
    private final String FILL_IN_IP_STRING = "Bitte das IP-Feld ausfüllen!";

    /* ############## DATENLISTEN ################ */
    private List<MassenInfoGrenz> massenInfoServer;
    private List<StruktInfoGrenz> struktInfoServer;
    private List<MassenInfoGrenz> massenInfoClient;
    private List<StruktInfoGrenz> struktInfoClient;

    public ClientGUI() {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();


        initImages();
        initListener();
        initSplitPanes();
    }

    private void fillMassenTable(JTable table, List<MassenInfoGrenz> mInfoGrenzList) {
      // if(mInfoGrenzList != null) {
            DefaultTableModel model;

            Object[][] data = new Object[mInfoGrenzList.size()][2];
            String[] columnNames = {"ID", "KiloByte"};

            if (!mInfoGrenzList.isEmpty()) {
                for (int i=0; i < mInfoGrenzList.size(); i++) {
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
     //   }
    }

    private void fillStruktTable(JTable table, List<StruktInfoGrenz> sInfoGrenzList) {
      //  if(sInfoGrenzList != null) {
            DefaultTableModel model;

            Object[][] data = new Object[sInfoGrenzList.size()][2];
            String[] columnNames = {"ID", "KiloByte"};

            if (!sInfoGrenzList.isEmpty()) {
                for (int i=0; i < sInfoGrenzList.size(); i++) {
                    data[i][0] = sInfoGrenzList.get(i).getId();
                    //  data[i][1] = sInfoGrenzList.get(i).getPaketGroesseKB();
                }

                model = new DefaultTableModel(data, columnNames) {
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                };
                table.setModel(model);
            }
       // }
    }

    private void setIcon(JLabel label, String iconPath, int sizeX, int sizeY ) {
        label.setText("");
        label.setIcon(new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT)));
      //  label.setIcon(new ImageIcon(iconPath));
    }

    private void refreshDownload() {
        artLabelDown.setText("/");
        idLabelDown.setText("/");
        groesseLabelDown.setText("/");
        massenInfoServer = cSteuer.getMassenInfoGrenzList(true);
        struktInfoServer = cSteuer.getStruktInfoGrenzList(true);
        fillMassenTable(massenTableDownload,massenInfoServer);
        fillStruktTable(struktTableDownload,struktInfoServer);
    }

    private void refreshUpload() {
        artLabelUp.setText("/");
        idLabelUp.setText("/");
        groesseLabelUp.setText("/");
        massenInfoClient = cSteuer.getMassenInfoGrenzList(false);
        struktInfoClient = cSteuer.getStruktInfoGrenzList(false);
        fillMassenTable(massenTableUpload,massenInfoClient);
        fillStruktTable(struktTableUpload,struktInfoClient);
    }

    private void refreshMessdaten() {
        System.out.println("refresh messdaten");
    }

    private void initDataLists() {
        massenInfoServer = cSteuer.getMassenInfoGrenzList(true);
        fillMassenTable(massenTableDownload,massenInfoServer);

        massenInfoClient = cSteuer.getMassenInfoGrenzList(false);
        fillMassenTable(massenTableUpload,massenInfoClient);

        struktInfoServer = cSteuer.getStruktInfoGrenzList(true);
        fillStruktTable(struktTableDownload,struktInfoServer);

        struktInfoClient = cSteuer.getStruktInfoGrenzList(false);
        fillStruktTable(struktTableUpload,struktInfoClient);
    }



    private void fillDataInfoLabels(JLabel artLabel, JLabel idLabel, JLabel groesseLabel, Object daten) {
        if(daten.getClass() == MassenInfoGrenz.class) {
            MassenInfoGrenz mig = (MassenInfoGrenz) daten;
            artLabel.setText("Massendaten");
            idLabel.setText(String.valueOf(mig.getId()));
            groesseLabel.setText(String.valueOf(mig.getPaketGroesseKB()));
        }
        if(daten.getClass() == StruktInfoGrenz.class) {
            StruktInfoGrenz sig = (StruktInfoGrenz) daten;
            artLabel.setText("Strukturierte Daten");
            idLabel.setText(String.valueOf(sig.getId()));
            groesseLabel.setText(" - ");
        }
    }

    private void initSplitPanes() {
        splitPaneDown.setDividerLocation(DIVIDER_LOCATION);
        splitPaneUp.setDividerLocation(DIVIDER_LOCATION);
        splitPaneMess.setDividerLocation(DIVIDER_LOCATION);
    }

    private void initImages() {
        setIcon(refreshIconDownload,IMAGE_REFRESH_PATH,25,25);
        setIcon(refreshIconUpload,IMAGE_REFRESH_PATH,25,25);
        setIcon(refreshIconMessdaten,IMAGE_REFRESH_PATH,25,25);
        setIcon(thLogoLabel, IMAGE_TH_PATH,200,90);
    }

    private void initListener() {
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
        massenTableDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = massenTableDownload.getSelectedRow();
                fillDataInfoLabels(artLabelDown,idLabelDown,groesseLabelDown,massenInfoServer.get(row));
            }
        });
        massenTableUpload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = massenTableUpload.getSelectedRow();
                fillDataInfoLabels(artLabelUp,idLabelUp,groesseLabelUp,massenInfoClient.get(row));
            }
        });
        struktTableDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = struktTableDownload.getSelectedRow();
                fillDataInfoLabels(artLabelDown,idLabelDown,groesseLabelDown,struktInfoServer.get(row));
            }
        });
        struktTableUpload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = struktTableUpload.getSelectedRow();
                fillDataInfoLabels(artLabelUp,idLabelUp,groesseLabelUp,struktInfoClient.get(row));
            }
        });
        herunterladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelDown.getText();
                if(!text.equals("/")) {
                    try {
                        cSteuer.empfangeMassendaten(Integer.valueOf(idLabelDown.getText()));
                    } catch (InvalidProtocolBufferException e1) {
                        e1.printStackTrace();
                    }
                } else JOptionPane.showMessageDialog(frame, "Bitte Daten aus der Liste wählen!");
            }
        });
        hochladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelUp.getText();
                if(!text.equals("/")) {
                    cSteuer.sendeMassendaten(Integer.valueOf(idLabelUp.getText()));
                } else JOptionPane.showMessageDialog(frame, "Bitte Daten aus der Liste wählen!");
            }
        });
    }

    private void verbindenButtonAction() {
        boolean isConnected;

        if(ipTextField.getText() != null) {
            if(!ipTextField.getText().equals("")) {
                if(!ipTextField.getText().contains(" ")) {
                    String ip = ipTextField.getText();

                    if(!ip.startsWith("http://")) ip = "http://"+ip;
                    if(!ip.endsWith(port)) ip+=":"+port;
                    if(!ip.endsWith("/")) ip += "/";

                    isConnected = cSteuer.connect(ip);
                    if(!isConnected) {
                        JOptionPane.showMessageDialog(frame, SERVER_NOT_FOUND_STRING);
                    }
                    else {
                        initDataLists();
                        cl.show(cardPanel, "mainCard");
                    }
                } else JOptionPane.showMessageDialog(frame, NO_EMPTY_SPACES_STRING);
            } else JOptionPane.showMessageDialog(frame, FILL_IN_IP_STRING);
        } else JOptionPane.showMessageDialog(frame, FILL_IN_IP_STRING);
    }
}
