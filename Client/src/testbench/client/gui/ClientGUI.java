package testbench.client.gui;

import testbench.bootloader.Printer;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
import testbench.client.service.ClientConfig;
import testbench.client.steuerungsklassen.ClientSteuer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private JButton herunterladenButton;
    private JTable massenTableDownload;
    private JTable struktTableDownload;
    private JTable massenTableUpload;
    private JTable struktTableUpload;
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
    private JButton hochladenButton;
    private JPanel connectHeaderPanel;
    private JPanel connectBottomPanel;
    private JLabel connectTitleLabel;
    private JPanel connectMiddlePanel;
    private JLabel protobufLogoLabel;
    private JLabel thLogoLabel;
    private JLabel infoLogoLabel;
    private JTextField currentIpTextField;
    private JPanel underTitlePanel;
    private JButton changeIpButton;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel splitpanePanelDown;
    private JPanel leftPanelDownload;
    private JPanel massenLabelPanelDown;
    private JPanel struktLabelPanelDown;
    private JPanel rightPanelDownload;
    private JPanel detailsPanelDown;
    private JPanel buttonsPanelDown;
    private JPanel splitPanePanelUp;
    private JPanel leftPanelUpload;
    private JPanel massenLabelPanelUp;
    private JPanel struktLabelPanelUp;
    private JPanel rightPanelUpload;
    private JPanel detailsPanelUp;
    private JPanel buttonsPanelUp;
    private JPanel leftPanelMessdaten;
    private JPanel massenLabelPanelMess;
    private JPanel struktLabelPanelMess;
    private JTable massenTableMess;
    private JTable struktTableMess;
    private JPanel rightPanelMessdaten;
    private JButton einstellungenButtonMain;
    private JButton einstellungenButtonConnect;

    /* OBEN -> automatisch generiert */

    /* ############## VARIABLEN ################ */
    private final int DIVIDER_LOCATION = 250; //divider position zwischen jsplitpanes
    private ClientSteuer cSteuer = new ClientSteuer();
    private CardLayout cl = (CardLayout) cardPanel.getLayout();
    private JFrame frame = new JFrame(); //fuer popups
    private boolean isIpTextFirstClicked = false;  //wenn false wird beim klick auf ip-textfield inhalt geleert

    /* ############## RESSOURCEN PFADE ################ */
    private final String IMAGEFOLDER = "/resources/images/";
    private final String IMAGE_REFRESH_PATH = IMAGEFOLDER+"refresh.png";
    private final String IMAGE_PROTOBUF_PATH = IMAGEFOLDER+"logo_protobuf.png";
    private final String IMAGE_TH_PATH = IMAGEFOLDER+"logo_th.png";
    private final String IMAGE_INFO_PATH = IMAGEFOLDER+"infogrey.png";

    /* ############## AUSGABE_STRINGS ################ */
    private final String CHANGE_IP_MESSAGE = "Bitte die gewünschte IP-Adresse eingeben";
    private final String DATA_NOT_UPLOADED_ERROR = "Daten konnten nicht hochgeladen werden!";
    private final String DATA_NOT_DOWNLOADED_ERROR = "Daten konnten nicht empfangen werden!";
    private final String SERVER_NOT_FOUND_STRING = "Server konnte nicht gefunden werden!";
    private final String NO_EMPTY_SPACES_STRING = "Bitte Leerstellen und Sonderzeichen aus der IP entfernen!";

    /* ############## DATENLISTEN ################ */
    private List<MassenInfoGrenz> massenInfoServer;
    private List<StruktInfoGrenz> struktInfoServer;
    private List<MassenInfoGrenz> massenInfoClient;
    private List<StruktInfoGrenz> struktInfoClient;

    public ClientGUI(int guiSizeX, int guiSizeY) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        initGuiProperties(guiSizeX,guiSizeY);
        initImages();
        initListener();
        initSplitPanes();
    }

    private void fillMassenTable(JTable table, List<MassenInfoGrenz> mInfoGrenzList) {
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
    }

    private void fillStruktTable(JTable table, List<StruktInfoGrenz> sInfoGrenzList) {
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
    }

    private void setIcon(JLabel label, BufferedImage image, int sizeX, int sizeY ) {
        label.setText("");
        label.setIcon(new ImageIcon(image.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT)));
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
        Printer.println("refresh messdaten");
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

    private BufferedImage loadImageResource(String filePath) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bufferedImage;
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Protobuf Testbench Client");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setVisible(true);
    }

    private void actualizeCurrentIpTextField(String ip) {
        currentIpTextField.setText(removeIpSyntax(ip));
    }

    private void initSplitPanes() {
        splitPaneDown.setDividerLocation(DIVIDER_LOCATION);
        splitPaneUp.setDividerLocation(DIVIDER_LOCATION);
        splitPaneMess.setDividerLocation(DIVIDER_LOCATION);
    }

    private void initImages() {
        setIcon(refreshIconDownload, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(refreshIconUpload, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(refreshIconMessdaten, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(thLogoLabel, loadImageResource(IMAGE_TH_PATH),75,43);
        setIcon(protobufLogoLabel, loadImageResource(IMAGE_PROTOBUF_PATH),130,40);
        setIcon(infoLogoLabel, loadImageResource(IMAGE_INFO_PATH),26,26);
    }

    private void initListener() {
        verbindenButton.addActionListener(new ActionListener() {
            @Override // Listener für verbindenButton-Klick
            public void actionPerformed(ActionEvent e) {
                connectIp(ipTextField.getText());
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
                connectIp(ipTextField.getText());
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
                MassendatenGrenz mGrenz = null;
                String text = idLabelDown.getText();
                if(!text.equals("/")) {
                    try {
                        mGrenz = cSteuer.empfangeMassendaten(Integer.valueOf(idLabelDown.getText()));

                        if(mGrenz == null) JOptionPane.showMessageDialog(frame, DATA_NOT_DOWNLOADED_ERROR);
                        else{
                            String daten = "Empfangene Werte:\n\n";
                            for(int i=0 ; i<mGrenz.getValues().size() ; i++) {
                                System.out.println(mGrenz.getValues().get(i));
                                daten += "Wert "+(i+1)+": "+mGrenz.getValues().get(i)+"\n";
                            }
                            daten += "\n";
                            JOptionPane.showMessageDialog(frame, daten);
                        }
                    } catch (Exception e1) {
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
                    MassendatenGrenz mGrenz = cSteuer.ladeLokaleMassendaten(Integer.valueOf(idLabelUp.getText()));
                    boolean success = cSteuer.sendeMassendaten(Integer.valueOf(idLabelUp.getText()));

                    if(success) {
                        String daten = "Gesendete Werte:\n\n";
                        for (int i = 0; i < mGrenz.getValues().size(); i++) {
                            System.out.println(mGrenz.getValues().get(i));
                            daten += "Wert " + (i + 1) + ": " + mGrenz.getValues().get(i) + "\n";
                        }
                        daten += "\n";
                        JOptionPane.showMessageDialog(frame, daten);
                    } else JOptionPane.showMessageDialog(frame, DATA_NOT_UPLOADED_ERROR);
                } else JOptionPane.showMessageDialog(frame, "Bitte Daten aus der Liste wählen!");
            }
        });
        changeIpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newIp = JOptionPane.showInputDialog(frame, CHANGE_IP_MESSAGE);
                if(newIp != null) {
                    if(!newIp.equals("") && !newIp.contains(" ")) {
                        connectIp(newIp);
                    } else JOptionPane.showMessageDialog(frame, NO_EMPTY_SPACES_STRING);
                }
            }
        });
        einstellungenButtonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientSettings(true);
            }
        });
        einstellungenButtonMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientSettings(false);
            }
        });
    }

    /* hinzufügen von http:// und :port   */
    private String addIpSyntax(String ip, String port) {
        String ipNew = new String(ip);
        if(!ipNew.startsWith("http://")) ipNew = "http://"+ip;
        ipNew+=":"+port;
        if(!ipNew.endsWith("/")) ipNew += "/";
        return ipNew;
    }

    /* entfernen von http:// und :port    */
    private String removeIpSyntax(String ip) {
        String ipNew = new String(ip);
        ipNew = ipNew.replace("http://","");
        ipNew = ipNew.substring(0,ipNew.indexOf(':'));
        return ipNew;
    }

    private void connectIp(String ip) {
        boolean isConnected;

        if(ip != null) {
            if(!ip.equals("")) {
                if(!ip.contains(" ") && !ip.contains(":")) {
                    String newIP = new String(ip);

                    newIP = addIpSyntax(newIP,ClientConfig.getExemplar().getPort());

                    isConnected = cSteuer.connect(newIP);
                    if(!isConnected) {
                        JOptionPane.showMessageDialog(frame, SERVER_NOT_FOUND_STRING);
                    }
                    else {
                        initDataLists();
                        actualizeCurrentIpTextField(cSteuer.getServerIP());
                        cl.show(cardPanel, "mainCard");
                    }
                } else JOptionPane.showMessageDialog(frame, NO_EMPTY_SPACES_STRING);
            }
        }
    }
}
