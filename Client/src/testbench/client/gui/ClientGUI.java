package testbench.client.gui;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import testbench.bootloader.Printer;
import testbench.bootloader.grenz.MassenDef;
import testbench.bootloader.grenz.MassendatenGrenz;
import testbench.bootloader.grenz.StruktDef;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.service.StaticHolder;
import testbench.client.grenzklassen.MassenInfoGrenz;
import testbench.client.grenzklassen.StruktInfoGrenz;
import testbench.client.service.ClientConfig;
import testbench.client.steuerungsklassen.ClientSteuer;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.DateiLaden;
import testbench.datenverwaltung.dateiverwaltung.steuerungsklassen.StruktGen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private JTabbedPane mainTabbedPane;
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
    private JButton datenverwaltungButton;
    private JPanel chartPanel;
    private JPanel splitPanePanelDetails;
    private JSplitPane splitPaneDetails;
    private JPanel leftPanelDetails;
    private JPanel cardPanelDetails;
    private JPanel rightPanelDetails;
    private JPanel treePanel;
    private JLabel refreshIconDetails;
    private JScrollPane scrollPaneMassenDetails;
    private JScrollPane scrollPaneStruktDetails;
    private JTable massenTableDetails;
    private JTable struktTableDetails;
    private JTree struktTree;
    private JScrollPane treeScrollPane;

    /* OBEN -> automatisch generiert */

    /* ############## VARIABLEN ################ */
    private final int DIVIDER_LOCATION = 250; //divider position zwischen jsplitpanes
    private ClientSteuer cSteuer = new ClientSteuer();
    DecimalFormat df = new DecimalFormat();
    private CardLayout mainCardLayout = (CardLayout) cardPanel.getLayout();
    private CardLayout detailsCardLayout = (CardLayout) cardPanelDetails.getLayout();
    private JFrame frame = new JFrame(); //fuer popups
    private boolean isIpTextFirstClicked = false;  //wenn false wird beim klick auf ip-textfield inhalt geleert
    private ClientConfig clientConfig = ClientConfig.getExemplar();
    private JFrame datenVerwaltungGUI;
    private ChartPanel CP = null;

    /* ############## RESSOURCEN PFADE ################ */
    private final String IMAGEFOLDER = "/resources/images/";
    private final String IMAGE_REFRESH_PATH = IMAGEFOLDER+"refresh.png";
    private final String IMAGE_PROTOBUF_PATH = IMAGEFOLDER+"logo_protobuf.png";
    private final String IMAGE_PROTOBUFMICRO_PATH = IMAGEFOLDER+"protobuf_micro.png";
    private final String IMAGE_TH_PATH = IMAGEFOLDER+"logo_th.png";
    private final String IMAGE_INFO_PATH = IMAGEFOLDER+"infogrey.png";

    /* ############## AUSGABE_STRINGS ################ */
    private final String CHANGE_IP_MESSAGE = "Bitte die gewünschte IP-Adresse eingeben";
    private final String DATA_NOT_UPLOADED_ERROR = "Daten konnten nicht hochgeladen werden!";
    private final String DATA_NOT_DOWNLOADED_ERROR = "Daten konnten nicht empfangen werden!";
    private final String SERVER_NOT_FOUND_STRING = "Server konnte nicht gefunden werden!";
    private final String NO_EMPTY_SPACES_STRING = "Bitte Leerstellen und Sonderzeichen aus der IP entfernen!";
    private final String DATA_UPLOADED_SUCCESS = "Daten wurden erfolgreich hochgeladen!";
    private final String DATA_DOWNLOAD_SUCCESS = "Daten wurden erfolgreich heruntergeladen!";
    private final String WAIT_FOR_TRANSFER = "Bitte warten bis die aktuelle Übertragung beendet wurde!";
    private final String CHOOSE_FROM_LIST = "Bitte Daten aus der Liste wählen!";
    private final String SERVER_OFFLINE = "Server ist offline!";
    private final String DATA_NOT_FOUND_ERROR = "Daten nicht gefunden!\nBitte aktualisieren!";
    private final String PLEASE_WAIT = "Bitte warten...";

    /* ############## FINAL VARIABLES ################ */
    private final int CHART_VALUES = 100;


    /* ############## DATENLISTEN ################ */
    private List<MassenInfoGrenz> massenInfoServer;
    private List<StruktInfoGrenz> struktInfoServer;
    private List<MassenInfoGrenz> massenInfoClient;
    private List<StruktInfoGrenz> struktInfoClient;

    public ClientGUI(int guiSizeX, int guiSizeY) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setIconImage(loadImageResource(IMAGE_PROTOBUFMICRO_PATH));

        initGuiProperties(guiSizeX,guiSizeY);
        initImages();
        initListener();
        initSplitPanes();
    }

    /**
     * Diese Methode laedt Struktdaten über die ClientSteuer und zeichnet
     * mit den in den Massendaten enthaltenen Werten einen JTree.
     * @param selectedTableRow Nummer der gewaehlten Table Spalte.
     */
    private void drawTree(int selectedTableRow) {
        StruktDef def = new StruktDef();
        def.setItemAIDNameCount(10);
        def.setItemJoinDefCount(10);
        def.setItemSelItemCount(10);
        def.setItemSelOrderCount(10);
        def.setItemSelUIDCount(10);
        StruktGen.erzeugeStrukt(def);
        StruktdatenProtos.Struktdaten strukt = new DateiLaden().ladeStruktdaten(12345);


       // StruktInfoGrenz sig = struktInfoClient.get(selectedTableRow);
        //StruktDef sDef = sig.getDef();
        StruktDef sDef =def;
        List<List<DefaultMutableTreeNode>> listList = new ArrayList<>();

        DefaultTreeModel model = (DefaultTreeModel)struktTree.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Strukturierte Daten");

        DefaultMutableTreeNode queryStructureExtList = new DefaultMutableTreeNode("QueryStructureExt");

        List<DefaultMutableTreeNode> selAIDNameUnitIDList = new ArrayList<>();
        List<DefaultMutableTreeNode> selItemList = new ArrayList<>();
        List<DefaultMutableTreeNode> joinDefList = new ArrayList<>();
        List<DefaultMutableTreeNode> selOrderList = new ArrayList<>();
        List<DefaultMutableTreeNode> aIDNameList = new ArrayList<>();
        DefaultMutableTreeNode selValueExt = null;
        DefaultMutableTreeNode aIDNameUnitId = null;

        List<DefaultMutableTreeNode> ts_ValueList = new ArrayList<>();

        /* ############# SelAidNameUnitID ################ */
        for(int i=0 ; i<strukt.getAnuSeqList().size() ; i++) {
            DefaultMutableTreeNode selAidUnitID = new DefaultMutableTreeNode("SelAIDNameUnitID");

            DefaultMutableTreeNode unitID = new DefaultMutableTreeNode("unitID");

            DefaultMutableTreeNode highUnitID = new DefaultMutableTreeNode("high");
            highUnitID.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getUnitid().getHigh()));
            unitID.add(highUnitID);

            DefaultMutableTreeNode lowUnitID = new DefaultMutableTreeNode("low");
            lowUnitID.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getUnitid().getLow()));
            unitID.add(lowUnitID);
            selAidUnitID.add(unitID);

            DefaultMutableTreeNode aggregate = new DefaultMutableTreeNode("Aggregate");
            aggregate.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getAggregate()));
            selAidUnitID.add(aggregate);

            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");

            DefaultMutableTreeNode aid = new DefaultMutableTreeNode("aid");

            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getAidname().getAid().getHigh()));
            aid.add(highAID);

            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            lowAID.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getAidname().getAid().getLow()));
            aid.add(lowAID);

            aidName.add(aid);

            DefaultMutableTreeNode aaName = new DefaultMutableTreeNode("aaName");
            aaName.add(new DefaultMutableTreeNode(strukt.getAnuSeqList().get(i).getAidname().getAaName()));
            aidName.add(aaName);

            selAidUnitID.add(aidName);
            selAIDNameUnitIDList.add(selAidUnitID);

        }
         /* #################################################################### */


    /*    for(int i=0 ; i<strukt.getCondSeqList().size() ; i++) {
            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(strukt.getCondSeqList().get(i).));
            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");
            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");
            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");
        } */




        listList.add(selAIDNameUnitIDList);

        for(int i=0 ; i<sDef.getItemSelItemCount() ; i++) selItemList.add(new DefaultMutableTreeNode("SelItem"));
        listList.add(selItemList);

        for(int i=0 ; i<sDef.getItemJoinDefCount() ; i++) joinDefList.add(new DefaultMutableTreeNode("JoinDef"));
        listList.add(joinDefList);

        for(int i=0 ; i<sDef.getItemSelOrderCount() ; i++) selOrderList.add(new DefaultMutableTreeNode("SelOrder"));
        listList.add(selOrderList);

        for(int i=0 ; i<sDef.getItemAIDNameCount() ; i++) aIDNameList.add(new DefaultMutableTreeNode("AIDName"));
        listList.add(aIDNameList);

        for(List<DefaultMutableTreeNode> list : listList) {
            for(DefaultMutableTreeNode dmt : list) {
                root.add(dmt);
            }
        }

        model.setRoot(root);
    }

    /**
     * Diese Methode laedt Massendaten über die ClientSteuer und zeichnet
     * mit den in den Massendaten enthaltenen Werten einen Graphen.
     * @param selectedTableRow Nummer der gewaehlten Table Spalte.
     * @param panel JPanel in das gezeichnet werden soll.
     * @param howMuchValuesToDraw ID der zu ladenden Massendaten.
     */
    private void drawChart(int selectedTableRow, int howMuchValuesToDraw, JPanel panel ) {
        MassenInfoGrenz mig = massenInfoClient.get(selectedTableRow);
        MassenDef mDef = mig.getDef();
        MassendatenGrenz mGrenz;

        mGrenz = cSteuer.ladeLokaleMassendaten(mig.getId());

        if(mGrenz == null) {
            JOptionPane.showMessageDialog(frame, DATA_NOT_FOUND_ERROR);
            return;
        }

        XYSeries signal = new XYSeries("Signal");
        XYSeriesCollection dataset;
        int abbruch;
        double abtastrate = mig.getDef().getAbtastrate();

        if(mGrenz.getValues().size() > howMuchValuesToDraw) abbruch = howMuchValuesToDraw;
        else abbruch = mGrenz.getValues().size();

        for(int i=0 ; i<abbruch ; i++) {
            signal.add(abtastrate*i,mGrenz.getValues().get(i).getNumber());
        }

        dataset = new XYSeriesCollection();
        dataset.addSeries(signal);

        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4)); //größe der punkte bei XYSplineRenderer
        //renderer.setDotHeight(2);
        //renderer.setDotWidth(2);
        NumberAxis xAxis = new NumberAxis("x - Abtastrate: "+mDef.getAbtastrate());
        NumberAxis yAxis = new NumberAxis("y");

        XYPlot plot = new XYPlot(dataset,xAxis,yAxis, renderer);
        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Massendaten "+(selectedTableRow+1)+" - "+howMuchValuesToDraw+" Werte sichtbar");

        if(CP == null) {
            CP = new ChartPanel(chart);
            panel.setLayout(new java.awt.BorderLayout());
            panel.add(CP,BorderLayout.CENTER);
        }
        else CP.setChart(chart);

        panel.validate();
    }

    /**
     * Diese Methode fuellt den gegebenen JTable mit den Elementen
     * der gegebenen MassenInfoGrenz-Liste.
     * @param table JTable der gefuellt werden soll.
     * @param mInfoGrenzList MassenInfoGrenz Liste, mit der der JTable gefuellt werden soll.
     */
    private void fillMassenTable(JTable table, List<MassenInfoGrenz> mInfoGrenzList) {
            DefaultTableModel model;

            Object[][] data = new Object[mInfoGrenzList.size()][2];
            String[] columnNames = {"#", "KB"};

            if (!mInfoGrenzList.isEmpty()) {
                for (int i=0; i < mInfoGrenzList.size(); i++) {
                    data[i][0] = i+1;
                    data[i][1] = df.format(mInfoGrenzList.get(i).getPaketGroesseKB());
                }

                model = new DefaultTableModel(data, columnNames) {
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                };
                table.setModel(model);
            }
    }

    /**
     * Diese Methode fuellt den gegebenen JTable mit den Elementen
     * der gegebenen StruktInfoGrenz-Liste.
     * @param table JTable der gefuellt werden soll.
     * @param sInfoGrenzList StruktInfoGrenz Liste, mit der der JTable gefuellt werden soll.
     */
    private void fillStruktTable(JTable table, List<StruktInfoGrenz> sInfoGrenzList) {
            DefaultTableModel model;

            Object[][] data = new Object[sInfoGrenzList.size()][2];
            String[] columnNames = {"#", "KB"};

            if (!sInfoGrenzList.isEmpty()) {
                for (int i=0; i < sInfoGrenzList.size(); i++) {
                    data[i][0] = i+1;
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

    /**
     * Diese Methode weist einem gegebenen JLabel ein Icon zu.
     * @param label Label, dem das Icon zugewiesen werden soll.
     * @param image Das Icon, das dem Label zugewiesen werden soll.
     * @param sizeX Breite des Icons.
     * @param sizeY Höhe des Icons.
     */
    private void setIcon(JLabel label, BufferedImage image, int sizeX, int sizeY ) {
        label.setText("");
        label.setIcon(new ImageIcon(image.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT)));
    }

    /**
     * Diese Methode aktualisiert die Listen, die die verfuegbaren Daten auf dem Server anzeigen.
     */
    private void refreshDownload() {
        artLabelDown.setText("/");
        idLabelDown.setText("/");
        groesseLabelDown.setText("/");
        massenInfoServer = cSteuer.getMassenInfoGrenzList(true);
        struktInfoServer = cSteuer.getStruktInfoGrenzList(true);

        if(massenInfoServer==null || struktInfoServer==null) {
            JOptionPane.showMessageDialog(frame, SERVER_OFFLINE);
        } else {
            fillMassenTable(massenTableDownload,massenInfoServer);
            fillStruktTable(struktTableDownload,struktInfoServer);
        }
    }

    /**
     * Diese Methode aktualisiert die Listen, die die lokal verfuegbaren Daten anzeigen.
     */
    private void refreshUpload() {
        artLabelUp.setText("/");
        idLabelUp.setText("/");
        groesseLabelUp.setText("/");
        massenInfoClient = cSteuer.getMassenInfoGrenzList(false);
        struktInfoClient = cSteuer.getStruktInfoGrenzList(false);
        fillMassenTable(massenTableUpload,massenInfoClient);
        fillStruktTable(struktTableUpload,struktInfoClient);
    }

    /**
     * Diese Methode aktualisiert die Listen, die die lokal verfuegbaren Daten anzeigen.
     */
    private void refreshDetails() {
        massenInfoClient = cSteuer.getMassenInfoGrenzList(false);
        struktInfoClient = cSteuer.getStruktInfoGrenzList(false);
        fillMassenTable(massenTableDetails,massenInfoClient);
        fillStruktTable(struktTableDetails,struktInfoClient);
    }

    /**
     * Diese Methode aktualisiert die Listen, die die lokal vorhandenen Messdaten anzeigen.
     */
    private void refreshMessdaten() {
        Printer.println("refresh messdaten");
    }

    /**
     * Hier werden die Listen, die die verfuegbaren Daten anzeigen mit den
     * entsprechenden InfoGrenz-Listen gefuellt.
     */
    private void initDataLists() {
        massenInfoServer = cSteuer.getMassenInfoGrenzList(true);
        fillMassenTable(massenTableDownload,massenInfoServer);

        massenInfoClient = cSteuer.getMassenInfoGrenzList(false);
        fillMassenTable(massenTableUpload,massenInfoClient);
        fillMassenTable(massenTableDetails, massenInfoClient);

        struktInfoServer = cSteuer.getStruktInfoGrenzList(true);
        fillStruktTable(struktTableDownload,struktInfoServer);

        struktInfoClient = cSteuer.getStruktInfoGrenzList(false);
        fillStruktTable(struktTableUpload,struktInfoClient);
        fillStruktTable(struktTableDetails,struktInfoClient);
    }

    /**
     * Diese Methode aktualisiert die Details-Anzeigenden JLabel mit gegebenen Texten.
     * @param artLabel Das JLabel, das die Art der Daten repraesentiert.
     * @param idLabel Das JLabel, das die ID der Daten repraesentiert.
     * @param groesseLabel Das JLabel, das die Groeße der Daten repraesentiert.
     * @param daten Die Daten, die die Informationen zum aktualisieren beinhalten.
     */
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

    /**
     * Diese Methode laedt eine Bild-Ressource.
     * @param filePath Pfad, unter dem die Ressource zu finden ist.
     * @return Das Bild als BufferedImage.
     */
    private BufferedImage loadImageResource(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource(filePath));
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Hier werden einige allgemeine GUI Einstellungen festgelegt.
     * @param guiSizeX Breite des GUI-Fensters.
     * @param guiSizeY Hoehe des GUI-Fensters.
     */
    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Protobuf Testbench Client");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setVisible(true);
    }

    /**
     * Diese Methode aktualisiert das JLabel, welches die
     * aktuelle Server-IP anzeigt.
     * @param ip IP, die in das JLabel geschrieben werden soll.
     */
    private void actualizeCurrentIpTextField(String ip) {
        currentIpTextField.setText(removeIpSyntax(ip));
    }

    /**
     * Hier werden die SplitPanes initialisiert bzw. die Divider-Position festgelegt.
     */
    private void initSplitPanes() {
        splitPaneDown.setDividerLocation(DIVIDER_LOCATION);
        splitPaneUp.setDividerLocation(DIVIDER_LOCATION);
        splitPaneDetails.setDividerLocation(DIVIDER_LOCATION);
        splitPaneMess.setDividerLocation(DIVIDER_LOCATION);
    }

    /**
     * Hier werden einigen JLabels Icons zugewiesen.
     * Dies geschieht mit Hilfe der setIcon() Methode.
     */
    private void initImages() {
        setIcon(refreshIconDownload, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(refreshIconUpload, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(refreshIconDetails, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(refreshIconMessdaten, loadImageResource(IMAGE_REFRESH_PATH),25,25);
        setIcon(thLogoLabel, loadImageResource(IMAGE_TH_PATH),75,43);
        setIcon(protobufLogoLabel, loadImageResource(IMAGE_PROTOBUF_PATH),130,40);
        setIcon(infoLogoLabel, loadImageResource(IMAGE_INFO_PATH),26,26);
    }

    /**
     * Hier werden alle ActionListener sowie MouseListener der GUI erstellt.
     */
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
        refreshIconDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshDetails();
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
        massenTableDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = massenTableDetails.getSelectedRow();
                drawChart(row,CHART_VALUES,chartPanel);
                detailsCardLayout.show(cardPanelDetails,"chartCard");
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
        struktTableDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = struktTableDetails.getSelectedRow();
                drawTree(row);
                detailsCardLayout.show(cardPanelDetails,"treeCard");
            }
        });

        herunterladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelDown.getText();

                if(!text.equals("/")) {
                    StaticHolder.currentTransferSizeByte = getPacketSizeFromList(massenInfoServer,Integer.valueOf(text))*1000;
                    if(StaticHolder.activeWorker == null) {
                        try {
                            StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                @Override
                                protected Integer doInBackground() throws Exception {
                                    ProgressBarThread pThread = new ProgressBarThread(new ProgressBarWindow(true));
                                    pThread.start();
                                    MassendatenGrenz mGrenz = cSteuer.empfangeMassendaten(Integer.valueOf(idLabelDown.getText()));
                                    if(mGrenz == null) JOptionPane.showMessageDialog(frame, DATA_NOT_DOWNLOADED_ERROR);
                                    else {
                                        JOptionPane.showMessageDialog(frame, DATA_DOWNLOAD_SUCCESS+"\n\nDeserialisierungszeit: "+StaticHolder.deSerialisierungsZeitMs +"ms"+
                                                "\nÜbertragungszeit: "+(StaticHolder.gesamtZeit-StaticHolder.deSerialisierungsZeitMs)+"ms"+
                                                "\nGesamtzeit: "+StaticHolder.gesamtZeit+"ms\n");
                                    }
                                    pThread.abbrechen();
                                    StaticHolder.activeWorker = null;
                                    return null;
                                }
                            };
                            StaticHolder.activeWorker.execute();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else JOptionPane.showMessageDialog(frame, WAIT_FOR_TRANSFER);
                } else JOptionPane.showMessageDialog(frame, CHOOSE_FROM_LIST);
            }
        });
        hochladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelUp.getText();
                if(!text.equals("/")) {
                    StaticHolder.currentTransferSizeByte = getPacketSizeFromList(massenInfoClient,Integer.valueOf(text))*1000;
                    if(StaticHolder.activeWorker == null) {
                            StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                @Override
                                protected Integer doInBackground() throws Exception {
                                ProgressBarThread pThread = new ProgressBarThread(new ProgressBarWindow(false));
                                pThread.start();
                                boolean success = cSteuer.sendeMassendaten(Integer.valueOf(idLabelUp.getText()));
                                if (success) {
                                    JOptionPane.showMessageDialog(frame, DATA_UPLOADED_SUCCESS+"\n\nSerialisierungszeit: "+StaticHolder.serialisierungsZeitMs+"ms"+
                                            "\nÜbertragungszeit: "+(StaticHolder.gesamtZeit-StaticHolder.serialisierungsZeitMs)+"ms"+
                                            "\nGesamtzeit: "+StaticHolder.gesamtZeit+"ms\n");
                                }
                                else JOptionPane.showMessageDialog(frame, DATA_NOT_UPLOADED_ERROR);
                                pThread.abbrechen();
                                StaticHolder.activeWorker = null;
                                return null;
                                }
                            };
                            StaticHolder.activeWorker.execute();
                    } else JOptionPane.showMessageDialog(frame, WAIT_FOR_TRANSFER);
                } else JOptionPane.showMessageDialog(frame, CHOOSE_FROM_LIST);
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
                new ClientSettingsWindow(true);
            }
        });
        einstellungenButtonMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientSettingsWindow(false);
            }
        });
        datenverwaltungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(datenVerwaltungGUI == null) datenVerwaltungGUI = cSteuer.starteDatenverwaltung();
                datenVerwaltungGUI.setVisible(true);
            }
        });
        mainTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                switch(mainTabbedPane.getSelectedIndex()) {
                    case 0: //herunterladen
                        refreshDownload();
                        break;

                    case 1: //hochladen
                        refreshUpload();
                        break;

                    case 2: //details
                        refreshDetails();
                        break;
                }
            }
        });
    }

    /**
     * Diese Methode baut aus gegebener IP und gegebenem PORT einen String nach
     * dem Muster "http://xxxxx:xxxx/" zusammen und liefert diesen zurueck.
     * @param ip Gewuenschte IP.
     * @param port Gewuenschter PORT.
     * @return Den zusammengesetzten String.
     */
    private String addIpSyntax(String ip, String port) {
        String ipNew = new String(ip);
        if(!ipNew.startsWith("http://")) ipNew = "http://"+ip;
        ipNew+=":"+port;
        if(!ipNew.endsWith("/")) ipNew += "/";
        return ipNew;
    }

    /**
     * Diese Methode entfernt aus gegebener Adresse das "http://" sowie den PORT
     * @param adresse Adresse, aus der die IP extrahiert werden soll.
     * @return Die extrahierte IP.
     */
    private String removeIpSyntax(String adresse) {
        String ipNew = new String(adresse);
        ipNew = ipNew.replace("http://","");
        ipNew = ipNew.substring(0,ipNew.indexOf(':'));
        return ipNew;
    }

    /**
     * Diese Methode durchsucht eine gegebene Liste nach Daten mit gegebener IP
     * und liefert die Größe dieser Daten zurueck.
     * @param datenList Liste, die durchsucht werden soll.
     * @param id ID der Daten, nach denen gesucht werden soll.
     * @return Größe der Daten. Sonst 0.
     */
    private int getPacketSizeFromList(List<?> datenList, int id) {
        if(datenList != null) {
            if(datenList.size() > 0) {
                if(datenList.get(0).getClass() == MassenInfoGrenz.class) {
                    for(Object o : datenList) {
                        if(((MassenInfoGrenz) o).getId() == id) {
                            return ((MassenInfoGrenz) o).getPaketGroesseKB();
                        }
                    }
                }

            }
        }
        return 0;
    }

    /**
     * Diese Methode nimmt eine gegebene IP und ueberprueft diese auf Zulaessigkeit.
     * Ist diese gegeben, so wird ueberprueft ob der Server erreichbar ist.
     * Ist dies der Fall, verschwindet das Login-Fenster und das eigentliche
     * Programm erscheint.
     * @param ip IP zu der verbunden werden soll.
     */
    private void connectIp(String ip) {
        boolean isConnected;

        if(ip != null) {
            if(!ip.equals("")) {
                if(!ip.contains(" ") && !ip.contains(":")) {
                    String newIP = new String(ip);
                    newIP = addIpSyntax(newIP,clientConfig.getPort());
                    isConnected = cSteuer.connect(newIP);
                    if(!isConnected) {
                        JOptionPane.showMessageDialog(frame, SERVER_NOT_FOUND_STRING);
                    }
                    else {
                        initDataLists();
                        actualizeCurrentIpTextField(cSteuer.getServerIP());
                        mainCardLayout.show(cardPanel, "mainCard");
                    }
                } else JOptionPane.showMessageDialog(frame, NO_EMPTY_SPACES_STRING);
            }
        }
    }

    public class ProgressBarThread extends Thread {
        private ProgressBarWindow pWindow;
        private boolean abbruch;

        public ProgressBarThread(ProgressBarWindow pWindow) {
            this.pWindow = pWindow;
        }

        public void run() {
            long value;
            int progress;
            while(!abbruch) {
                value = (long)(StaticHolder.currentTransferCount)*100;
                progress = (int)(value/StaticHolder.currentTransferSizeByte);

                pWindow.setProgressBar(progress);
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void abbrechen() {
            abbruch = true;
            StaticHolder.currentTransferCount = 0;
            pWindow.dispose();
        }
    }
}
