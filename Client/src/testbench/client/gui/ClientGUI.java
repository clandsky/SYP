package testbench.client.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import testbench.bootloader.grenz.*;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos;
import testbench.bootloader.service.StaticHolder;
import testbench.client.service.ClientConfig;
import testbench.client.steuerungsklassen.ClientSteuer;

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
    private JTable messTable;
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
    private JButton graphAllerMessdatenAnzeigenButton;
    private JLabel messIdLabel;
    private JLabel messSizeLabel;
    private JLabel messSeriLabel;
    private JLabel messDeseriLabel;
    private JLabel messTransmitLabel;
    private JLabel messAllTimeLabel;

    /* OBEN -> automatisch generiert */

    /* ############## VARIABLEN ################ */
    private final int DIVIDER_LOCATION = 250; //divider position zwischen jsplitpanes
    private final int DIVIDER_LOCATION_MESS = 475; //divider position bei messdaten
    private ClientSteuer cSteuer;
    private DecimalFormat df;
    private CardLayout mainCardLayout;
    private CardLayout detailsCardLayout;
    private JFrame optionPaneFrame; //fuer popups
    private boolean isIpTextFirstClicked = false;  //wenn false wird beim klick auf ip-textfield inhalt geleert
    private ClientConfig clientConfig;
    private JFrame datenVerwaltungGUI;
    private ChartPanel CP;

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

    /* ############## FINAL VARIABLES ################ */
    private final int CHART_VALUES = 100;

    /* ############## DATENLISTEN ################ */
    private List<MassenInfoGrenz> massenInfoServer;
    private List<StruktInfoGrenz> struktInfoServer;
    private List<MassenInfoGrenz> massenInfoClient;
    private List<StruktInfoGrenz> struktInfoClient;
    private List<MessdatenGrenz> messDaten;

    public ClientGUI(int guiSizeX, int guiSizeY) {
        setContentPane(formPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        setIconImage(loadImageResource(IMAGE_PROTOBUFMICRO_PATH));

        initGuiProperties(guiSizeX,guiSizeY);
        objectInit();
        initImages();
        initListener();
        initSplitPanes();
    }

    /**
     * Diese Methode initialisiert die deklarierten Objekte.
     * Hilfsmethode, um den Konstruktor schlanker zu machen.
     */
    private void objectInit() {
        optionPaneFrame = new JFrame();
        cSteuer = new ClientSteuer();
        df = new DecimalFormat();
        clientConfig = ClientConfig.getExemplar();
        mainCardLayout = (CardLayout) cardPanel.getLayout();
        detailsCardLayout = (CardLayout) cardPanelDetails.getLayout();
    }

    /**
     * Diese Methode laedt Struktdaten über die ClientSteuer und zeichnet
     * mit den in den Massendaten enthaltenen Werten einen JTree.
     *
     * @param selectedTableRow Nummer der gewaehlten Table Spalte.
     */
    private void drawTree(int selectedTableRow) {
        StruktInfoGrenz sig = struktInfoClient.get(selectedTableRow);
        StruktdatenGrenz sGrenz = cSteuer.ladeLokaleStruktdaten(sig.getId());

        DefaultTreeModel model = (DefaultTreeModel)struktTree.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Strukturierte Daten");

        /* ############# SelAidNameUnitID ################ */
        DefaultMutableTreeNode selAidNameUnitIdElements = new DefaultMutableTreeNode("SelAIDNameUnitID Elemente");
        for(int i=0 ; i<sGrenz.getSelAIDNameUnitIDList().size() ; i++) {
            StruktdatenProtos.Struktdaten.SelAIDNameUnitID selAid = sGrenz.getSelAIDNameUnitIDList().get(i);
            DefaultMutableTreeNode selAidUnitID = new DefaultMutableTreeNode("SelAIDNameUnitID");

            DefaultMutableTreeNode unitID = new DefaultMutableTreeNode("unitID");

            DefaultMutableTreeNode highUnitID = new DefaultMutableTreeNode("high");
            highUnitID.add(new DefaultMutableTreeNode(selAid.getUnitid().getHigh()));
            unitID.add(highUnitID);

            DefaultMutableTreeNode lowUnitID = new DefaultMutableTreeNode("low");
            lowUnitID.add(new DefaultMutableTreeNode(selAid.getUnitid().getLow()));
            unitID.add(lowUnitID);
            selAidUnitID.add(unitID);

            DefaultMutableTreeNode aggregate = new DefaultMutableTreeNode("Aggregate");
            aggregate.add(new DefaultMutableTreeNode(selAid.getAggregate()));
            selAidUnitID.add(aggregate);

            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");

            DefaultMutableTreeNode aid = new DefaultMutableTreeNode("aid");

            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(selAid.getAidname().getAid().getHigh()));
            aid.add(highAID);

            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            lowAID.add(new DefaultMutableTreeNode(selAid.getAidname().getAid().getLow()));
            aid.add(lowAID);

            aidName.add(aid);

            DefaultMutableTreeNode aaName = new DefaultMutableTreeNode("aaName");
            aaName.add(new DefaultMutableTreeNode(selAid.getAidname().getAaName()));
            aidName.add(aaName);

            selAidUnitID.add(aidName);
            selAidNameUnitIdElements.add(selAidUnitID);
        }

        /* ############# SelAidNameUnitID ################ */
        DefaultMutableTreeNode SelItemElements = new DefaultMutableTreeNode("SelItem Elemente");
        List<StruktdatenProtos.Struktdaten.SelItem> condSeqList = sGrenz.getSelItemList();
        for(int i=0 ; i<condSeqList.size() ; i++) {
            StruktdatenProtos.Struktdaten.SelItem selItem = condSeqList.get(i);

            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");

            DefaultMutableTreeNode aid = new DefaultMutableTreeNode("aid");
            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAid().getHigh()));
            highAID.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAid().getHigh()));
            aid.add(highAID);

            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            lowAID.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAid().getLow()));
            aid.add(lowAID);
            aidName.add(aid);

            DefaultMutableTreeNode aaName = new DefaultMutableTreeNode("aaName");
            aaName.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAaName()));
            aidName.add(aaName);

            DefaultMutableTreeNode aidNameUnitID = new DefaultMutableTreeNode("AIDNameUnitID");

            DefaultMutableTreeNode unitID = new DefaultMutableTreeNode("unitID");
            DefaultMutableTreeNode highUnit = new DefaultMutableTreeNode("high");
            highUnit.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAid().getHigh()));
            unitID.add(highUnit);

            DefaultMutableTreeNode lowUnit = new DefaultMutableTreeNode("low");
            lowUnit.add(new DefaultMutableTreeNode(selItem.getValue().getAttr().getAttr().getAid().getLow()));
            unitID.add(lowUnit);

            aidNameUnitID.add(unitID);
            aidNameUnitID.add(aidName);

            DefaultMutableTreeNode ts_Value = new DefaultMutableTreeNode("TS_VALUE");

            DefaultMutableTreeNode u = new DefaultMutableTreeNode("u");
            u.add(new DefaultMutableTreeNode(selItem.getValue().getValue().getU()));
            ts_Value.add(u);

            DefaultMutableTreeNode flag = new DefaultMutableTreeNode("flag");
            flag.add(new DefaultMutableTreeNode(selItem.getValue().getValue().getFlag()));
            ts_Value.add(flag);

            DefaultMutableTreeNode selValueExt = new DefaultMutableTreeNode("SelValueExt");

            DefaultMutableTreeNode operSelValueExt = new DefaultMutableTreeNode("oper");
            operSelValueExt.add(new DefaultMutableTreeNode(selItem.getValue().getOper()));

            selValueExt.add(aidNameUnitID);
            selValueExt.add(operSelValueExt);
            selValueExt.add(ts_Value);


            DefaultMutableTreeNode selItemTreeNode = new DefaultMutableTreeNode("SelItem");
            DefaultMutableTreeNode operator = new DefaultMutableTreeNode("operator");
            operator.add(new DefaultMutableTreeNode(selItem.getOperator()));
            selItemTreeNode.add(operator);
            selItemTreeNode.add(selValueExt);

            SelItemElements.add(selItemTreeNode);
        }

        /* ############# JoinDef ################ */
        DefaultMutableTreeNode joinDefElements = new DefaultMutableTreeNode("JoinDef Elemente");
        List<StruktdatenProtos.Struktdaten.JoinDef> joinDefList = sGrenz.getJoinDefList();
        for(int i=0 ; i<joinDefList.size() ; i++) {
            StruktdatenProtos.Struktdaten.JoinDef joinDefObject = joinDefList.get(i);

            DefaultMutableTreeNode joinDef = new DefaultMutableTreeNode("JoinDef");

            DefaultMutableTreeNode fromAid = new DefaultMutableTreeNode("fromAID");
            DefaultMutableTreeNode highAid = new DefaultMutableTreeNode("high");
            highAid.add(new DefaultMutableTreeNode(joinDefObject.getFromAID().getHigh()));
            fromAid.add(highAid);

            DefaultMutableTreeNode lowAid = new DefaultMutableTreeNode("low");
            lowAid.add(new DefaultMutableTreeNode(joinDefObject.getFromAID().getLow()));
            fromAid.add(lowAid);
            joinDef.add(fromAid);

            DefaultMutableTreeNode toAid = new DefaultMutableTreeNode("toAID");
            DefaultMutableTreeNode highToAid = new DefaultMutableTreeNode("high");
            highToAid.add(new DefaultMutableTreeNode(joinDefObject.getToAID().getHigh()));
            toAid.add(highToAid);

            DefaultMutableTreeNode lowToAid = new DefaultMutableTreeNode("low");
            lowToAid.add(new DefaultMutableTreeNode(joinDefObject.getToAID().getLow()));
            toAid.add(lowToAid);
            joinDef.add(toAid);

            DefaultMutableTreeNode refName = new DefaultMutableTreeNode("refName");
            refName.add(new DefaultMutableTreeNode(joinDefObject.getRefName()));
            joinDef.add(refName);

            DefaultMutableTreeNode joiningType = new DefaultMutableTreeNode("joiningType");
            joiningType.add(new DefaultMutableTreeNode(joinDefObject.getJoiningType()));
            joinDef.add(joiningType);

            joinDefElements.add(joinDef);
        }

        /* ############# SelOrder ################ */
        DefaultMutableTreeNode selOrderElements = new DefaultMutableTreeNode("SelOrder Elemente");
        List<StruktdatenProtos.Struktdaten.SelOrder> selOrderList = sGrenz.getSelOrderList();
        for(int i=0 ; i<selOrderList.size() ; i++) {
            StruktdatenProtos.Struktdaten.SelOrder selOrderObject = selOrderList.get(i);

            DefaultMutableTreeNode selOrder = new DefaultMutableTreeNode("SelOrder");
            DefaultMutableTreeNode ascending = new DefaultMutableTreeNode("ascending");
            ascending.add(new DefaultMutableTreeNode(selOrderObject.getAscending()));
            selOrder.add(ascending);

            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");

            DefaultMutableTreeNode aid = new DefaultMutableTreeNode("aid");
            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(selOrderObject.getAttr().getAid().getHigh()));
            aid.add(highAID);

            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            lowAID.add(new DefaultMutableTreeNode(selOrderObject.getAttr().getAid().getLow()));
            aid.add(lowAID);
            aidName.add(aid);

            DefaultMutableTreeNode aaName = new DefaultMutableTreeNode("aaName");
            aaName.add(new DefaultMutableTreeNode(selOrderObject.getAttr().getAaName()));
            aidName.add(aaName);

            selOrder.add(aidName);

            selOrderElements.add(selOrder);
        }

        /* ############# AIDName ################ */
        DefaultMutableTreeNode aidNameElements = new DefaultMutableTreeNode("AIDName Elemente");
        List<StruktdatenProtos.Struktdaten.AIDName> aidNameList = sGrenz.getAidNameList();
        for(int i=0 ; i<aidNameList.size() ; i++) {
            StruktdatenProtos.Struktdaten.AIDName aidNameObject = aidNameList.get(i);

            DefaultMutableTreeNode aidName = new DefaultMutableTreeNode("AIDName");

            DefaultMutableTreeNode aid = new DefaultMutableTreeNode("aid");
            DefaultMutableTreeNode highAID = new DefaultMutableTreeNode("high");
            highAID.add(new DefaultMutableTreeNode(aidNameObject.getAid().getHigh()));
            aid.add(highAID);

            DefaultMutableTreeNode lowAID = new DefaultMutableTreeNode("low");
            lowAID.add(new DefaultMutableTreeNode(aidNameObject.getAid().getLow()));
            aid.add(lowAID);
            aidName.add(aid);

            DefaultMutableTreeNode aaName = new DefaultMutableTreeNode("aaName");
            aaName.add(new DefaultMutableTreeNode(aidNameObject.getAaName()));
            aidName.add(aaName);

            aidNameElements.add(aidName);
        }

        root.add(selAidNameUnitIdElements);
        root.add(SelItemElements);
        root.add(joinDefElements);
        root.add(selOrderElements);
        root.add(aidNameElements);

        model.setRoot(root);
    }

    /**
     * Diese Methode laedt Massendaten über die ClientSteuer und zeichnet
     * mit den in den Massendaten enthaltenen Werten einen Graphen.
     *
     * @param selectedTableRow    Nummer der gewaehlten Table Spalte.
     * @param panel               JPanel in das gezeichnet werden soll.
     * @param howMuchValuesToDraw ID der zu ladenden Massendaten.
     */
    private void drawChart(int selectedTableRow, int howMuchValuesToDraw, JPanel panel ) {
        MassenInfoGrenz mig = massenInfoClient.get(selectedTableRow);
        MassenDef mDef = mig.getDef();
        MassendatenGrenz mGrenz;

        mGrenz = cSteuer.ladeLokaleMassendaten(mig.getId());
        if(mGrenz == null) {
            JOptionPane.showMessageDialog(optionPaneFrame, DATA_NOT_FOUND_ERROR);
            return;
        }

        XYSeries signal = new XYSeries("Signal");
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSplineRenderer xySplineRenderer = new XYSplineRenderer();
        XYPlot plot;
        JFreeChart chart;
        NumberAxis xAxis;
        NumberAxis yAxis;

        int abbruch;
        double abtastrate = mig.getDef().getAbtastrate();

        if(mGrenz.getValues().size() > howMuchValuesToDraw) abbruch = howMuchValuesToDraw;
        else abbruch = mGrenz.getValues().size();

        for(int i=0 ; i<abbruch ; i++) {
            signal.add(abtastrate*i,mGrenz.getValues().get(i).getNumber());
        }
        dataset.addSeries(signal);

        xySplineRenderer.setSeriesPaint(0, Color.blue);
        xySplineRenderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4)); //größe der punkte bei XYSplineRenderer
        xAxis = new NumberAxis("x - Abtastrate: "+mDef.getAbtastrate());
        yAxis = new NumberAxis("y");

        plot = new XYPlot(dataset,xAxis,yAxis, xySplineRenderer);
        chart = new JFreeChart(plot);
        chart.setTitle("Massendaten "+(selectedTableRow+1)+" - "+howMuchValuesToDraw+" Werte sichtbar");

        if(CP == null) {
            CP = new ChartPanel(chart);
            panel.setLayout(new BorderLayout());
            panel.add(CP,BorderLayout.CENTER);
        }
        else CP.setChart(chart);

        panel.validate();
    }

    /**
     * Diese Methode fuellt den gegebenen JTable mit den Elementen
     * der gegebenen MassenInfoGrenz-Liste.
     *
     * @param table          JTable der gefuellt werden soll.
     * @param mInfoGrenzList MassenInfoGrenz Liste, mit der der JTable gefuellt werden soll.
     */
    private void fillMassenTable(JTable table, List<MassenInfoGrenz> mInfoGrenzList) {
            DefaultTableModel model;

            Object[][] data = new Object[mInfoGrenzList.size()][2];
            String[] columnNames = {"#", "KiloByte"};

            if (!mInfoGrenzList.isEmpty()) {
                for (int i=0; i < mInfoGrenzList.size(); i++) {
                    data[i][0] = i+1;
                    data[i][1] = df.format(mInfoGrenzList.get(i).getPaketGroesseByte()/1000); //format df.format formiert die zahl (1.000.000)
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
     *
     * @param table          JTable der gefuellt werden soll.
     * @param sInfoGrenzList StruktInfoGrenz Liste, mit der der JTable gefuellt werden soll.
     */
    private void fillStruktTable(JTable table, List<StruktInfoGrenz> sInfoGrenzList) {
            DefaultTableModel model;

            Object[][] data = new Object[sInfoGrenzList.size()][2];
            String[] columnNames = {"#", "Byte"};

            if (!sInfoGrenzList.isEmpty()) {
                for (int i=0; i < sInfoGrenzList.size(); i++) {
                    data[i][0] = i+1;
                    data[i][1] = df.format(sInfoGrenzList.get(i).getPaketGroesseByte()); //format df.format formiert die zahl (1.000.000)
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
     * der gegebenen MessdatenGrenz-Liste.
     *
     * @param table              JTable der gefuellt werden soll.
     * @param messdatenGrenzList MessdatenGrenz Liste, mit der der JTable gefuellt werden soll.
     */
    private void fillMessdatenTable(JTable table, List<MessdatenGrenz> messdatenGrenzList) {
        DefaultTableModel model;

        Object[][] data = new Object[messdatenGrenzList.size()][3];
        String[] columnNames = {"#", "Typ", "Datum"};

        if (!messdatenGrenzList.isEmpty()) {
            for (int i=0; i < messdatenGrenzList.size(); i++) {
                data[i][0] = i+1;
                data[i][1] = messdatenGrenzList.get(i).getTyp();
                data[i][2] = messdatenGrenzList.get(i).getTimestamp();
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
     *
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
            ((DefaultTableModel) massenTableDownload.getModel()).setRowCount(0);
            ((DefaultTableModel) struktTableDownload.getModel()).setRowCount(0);
            JOptionPane.showMessageDialog(optionPaneFrame, SERVER_OFFLINE);
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
        messDaten = cSteuer.holeMessdaten();
        fillMessdatenTable(messTable,messDaten);
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

        messDaten = cSteuer.holeMessdaten();
        fillMessdatenTable(messTable, messDaten);
    }

    /**
     * Diese Methode aktualisiert die Details-Anzeigenden JLabel mit gegebenen Texten.
     *
     * @param artLabel     Das JLabel, das die Art der Daten repraesentiert.
     * @param idLabel      Das JLabel, das die ID der Daten repraesentiert.
     * @param groesseLabel Das JLabel, das die Groeße der Daten repraesentiert.
     * @param daten        Die Daten, die die Informationen zum aktualisieren beinhalten.
     */
    private void fillDataInfoLabels(JLabel artLabel, JLabel idLabel, JLabel groesseLabel, Object daten) {
        if(daten.getClass() == MassenInfoGrenz.class) {
            MassenInfoGrenz mig = (MassenInfoGrenz) daten;
            artLabel.setText("Massendaten");
            idLabel.setText(String.valueOf(mig.getId()));
            groesseLabel.setText(df.format(mig.getPaketGroesseByte()/1000)+" KB");
        }
        if(daten.getClass() == StruktInfoGrenz.class) {
            StruktInfoGrenz sig = (StruktInfoGrenz) daten;
            artLabel.setText("Strukturierte Daten");
            idLabel.setText(String.valueOf(sig.getId()));
            groesseLabel.setText(df.format(sig.getPaketGroesseByte())+" B");
        }
    }

    /**
     * Diese Methode laedt eine Bild-Ressource.
     *
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
     *
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
     *
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
        splitPaneMess.setDividerLocation(DIVIDER_LOCATION_MESS);
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
                expandAllNodes(struktTree,0, struktTree.getRowCount());
                detailsCardLayout.show(cardPanelDetails,"treeCard");
            }
        });
        messTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = messTable.getSelectedRow();

                MessdatenGrenz m = messDaten.get(row);

                messIdLabel.setText(String.valueOf(m.getId()));

                if(m.getPaketGroesseByte() > 10000) messSizeLabel.setText(String.valueOf(m.getPaketGroesseByte()/1000)+" KB");
                else messSizeLabel.setText(String.valueOf(m.getPaketGroesseByte())+" Byte");

                if(m.getSerizeit() == 0) messSeriLabel.setText("Download - Zeit nicht ermittelbar.");
                else messSeriLabel.setText(String.valueOf(m.getSerizeit()) +" ms");

                if(m.getDeserizeit() == 0) messDeseriLabel.setText("Upload - Zeit nicht ermittelbar.");
                else messDeseriLabel.setText(String.valueOf(m.getDeserizeit()) +" ms");

                messAllTimeLabel.setText(String.valueOf(m.getGesamtZeit()) +" ms");
            }
        });
        herunterladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelDown.getText();

                if(!text.equals("/")) {
                    if(artLabelDown.getText().equals("Massendaten")) {
                        StaticHolder.currentTransferSizeByte = getPacketSizeFromList(massenInfoServer,Integer.valueOf(text));
                        if(StaticHolder.activeWorker == null) {
                            try {
                                StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                    @Override
                                    protected Integer doInBackground() throws Exception {
                                        ProgressBarThread pThread = new ProgressBarThread(true);
                                        MassendatenGrenz mGrenz = cSteuer.empfangeMassendaten(Integer.valueOf(idLabelDown.getText()));
                                        if(mGrenz == null) JOptionPane.showMessageDialog(optionPaneFrame, DATA_NOT_DOWNLOADED_ERROR);
                                        else {
                                            JOptionPane.showMessageDialog(optionPaneFrame, DATA_DOWNLOAD_SUCCESS+"\n\nDeserialisierungszeit: "+StaticHolder.deSerialisierungsZeitMs +"ms"+
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
                        } else JOptionPane.showMessageDialog(optionPaneFrame, WAIT_FOR_TRANSFER);
                    } else {
                        StaticHolder.currentTransferSizeByte = getPacketSizeFromList(struktInfoServer,Integer.valueOf(text));
                        if(StaticHolder.activeWorker == null) {
                            try {
                                StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                    @Override
                                    protected Integer doInBackground() throws Exception {
                                        ProgressBarThread pThread = new ProgressBarThread(true);
                                        StruktdatenGrenz sGrenz = cSteuer.empfangeStruktdaten(Integer.valueOf(idLabelDown.getText()));
                                        if(sGrenz == null) JOptionPane.showMessageDialog(optionPaneFrame, DATA_NOT_DOWNLOADED_ERROR);
                                        else {
                                            JOptionPane.showMessageDialog(optionPaneFrame, DATA_DOWNLOAD_SUCCESS+"\n\nDeserialisierungszeit: "+StaticHolder.deSerialisierungsZeitMs +"ms"+
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
                        } else JOptionPane.showMessageDialog(optionPaneFrame, WAIT_FOR_TRANSFER);
                    }

                } else JOptionPane.showMessageDialog(optionPaneFrame, CHOOSE_FROM_LIST);
            }
        });
        hochladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = idLabelUp.getText();
                if(!text.equals("/")) {
                    if(artLabelUp.getText().equals("Massendaten")) {
                        StaticHolder.currentTransferSizeByte = getPacketSizeFromList(massenInfoClient,Integer.valueOf(text));
                        if(StaticHolder.activeWorker == null) {
                            StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                @Override
                                protected Integer doInBackground() throws Exception {
                                    ProgressBarThread pThread = new ProgressBarThread(false);
                                    boolean success = cSteuer.sendeMassendaten(Integer.valueOf(idLabelUp.getText()));
                                    if (success) {
                                        JOptionPane.showMessageDialog(optionPaneFrame, DATA_UPLOADED_SUCCESS+"\n\nSerialisierungszeit: "+StaticHolder.serialisierungsZeitMs+"ms"+
                                                "\nDeserialisierungszeit Server: "+StaticHolder.deSerialisierungsZeitMs+"ms"+
                                                "\nÜbertragungszeit: "+(StaticHolder.gesamtZeit-StaticHolder.serialisierungsZeitMs-StaticHolder.deSerialisierungsZeitMs)+"ms"+
                                                "\nGesamtzeit: "+StaticHolder.gesamtZeit+"ms\n");
                                    }
                                    else JOptionPane.showMessageDialog(optionPaneFrame, DATA_NOT_UPLOADED_ERROR);
                                    pThread.abbrechen();
                                    StaticHolder.activeWorker = null;
                                    return null;
                                }
                            };
                            StaticHolder.activeWorker.execute();
                        } else JOptionPane.showMessageDialog(optionPaneFrame, WAIT_FOR_TRANSFER);
                    } else {
                        StaticHolder.currentTransferSizeByte = getPacketSizeFromList(struktInfoClient,Integer.valueOf(text));
                        if(StaticHolder.activeWorker == null) {
                            StaticHolder.activeWorker = new SwingWorker<Integer, Integer>() {
                                @Override
                                protected Integer doInBackground() throws Exception {
                                    ProgressBarThread pThread = new ProgressBarThread(false);
                                    boolean success = cSteuer.sendeStruktdaten(Integer.valueOf(idLabelUp.getText()));
                                    if (success) {
                                        JOptionPane.showMessageDialog(optionPaneFrame, DATA_UPLOADED_SUCCESS+"\n\nSerialisierungszeit: "+StaticHolder.serialisierungsZeitMs+"ms"+
                                                "\nDeserialisierungszeit Server: "+StaticHolder.deSerialisierungsZeitMs+"ms"+
                                                "\nÜbertragungszeit: "+(StaticHolder.gesamtZeit-StaticHolder.serialisierungsZeitMs-StaticHolder.deSerialisierungsZeitMs)+"ms"+
                                                "\nGesamtzeit: "+StaticHolder.gesamtZeit+"ms\n");
                                    }
                                    else JOptionPane.showMessageDialog(optionPaneFrame, DATA_NOT_UPLOADED_ERROR);
                                    pThread.abbrechen();
                                    StaticHolder.activeWorker = null;
                                    return null;
                                }
                            };
                            StaticHolder.activeWorker.execute();
                        } else JOptionPane.showMessageDialog(optionPaneFrame, WAIT_FOR_TRANSFER);
                    }

                } else JOptionPane.showMessageDialog(optionPaneFrame, CHOOSE_FROM_LIST);
            }
        });
        changeIpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newIp = JOptionPane.showInputDialog(optionPaneFrame, CHANGE_IP_MESSAGE);
                if(newIp != null) {
                    if(!newIp.equals("") && !newIp.contains(" ")) {
                        connectIp(newIp);
                    } else JOptionPane.showMessageDialog(optionPaneFrame, NO_EMPTY_SPACES_STRING);
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

                    case 3: //messdaten
                        refreshMessdaten();
                        break;
                }
            }
        });
        graphAllerMessdatenAnzeigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawFrameChartTime();
            }
        });
    }

    /**
     * Oeffnet einen Jtree.
     *
     * @param tree          Der zu oeffnende Tree.
     * @param startingIndex Anfangsindex.
     * @param rowCount      anzahl der zu oeffnenden Blaetter.
     */
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    /**
     * Diese Methode baut aus gegebener IP und gegebenem Port einen String nach
     * dem Muster "http://xxxxx:xxxx/" zusammen und liefert diesen zurueck.
     *
     * @param ip   Gewuenschte IP.
     * @param port Gewuenschter Port.
     * @return Zusammengesetzten String.
     */
    private String addIpSyntax(String ip, String port) {
        String ipNew = new String(ip);
        if(!ipNew.startsWith("http://")) ipNew = "http://"+ip;
        ipNew+=":"+port;
        if(!ipNew.endsWith("/")) ipNew += "/";
        return ipNew;
    }

    /**
     * Diese Methode entfernt aus gegebener Adresse das "http://" sowie den Port.
     *
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
     * und liefert die Groeße dieser Daten zurueck.
     *
     * @param datenList Liste, die durchsucht werden soll.
     * @param id        ID der Daten, nach denen gesucht werden soll.
     * @return Groeße der Daten. Sonst 0.
     */
    private int getPacketSizeFromList(List<?> datenList, int id) {
        if(datenList != null) {
            if(datenList.size() > 0) {
                if(datenList.get(0).getClass() == MassenInfoGrenz.class) {
                    for(Object o : datenList) {
                        if(((MassenInfoGrenz) o).getId() == id) {
                            return ((MassenInfoGrenz) o).getPaketGroesseByte();
                        }
                    }
                } else {
                    for(Object o : datenList) {
                        if(((StruktInfoGrenz) o).getId() == id) {
                            return ((StruktInfoGrenz) o).getPaketGroesseByte();
                        }
                    }
                }
            }
        }
        return 1;
    }

    /**
     * Diese Methode nimmt eine gegebene IP und ueberprueft diese auf Zulaessigkeit.
     * Ist diese gegeben, so wird ueberprueft ob der Server erreichbar ist.
     * Ist dies der Fall, verschwindet das Login-Fenster und das eigentliche
     * Programm erscheint.
     *
     * @param ip Die IP zu der verbunden werden soll.
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
                        JOptionPane.showMessageDialog(optionPaneFrame, SERVER_NOT_FOUND_STRING);
                    }
                    else {
                        initDataLists();
                        actualizeCurrentIpTextField(cSteuer.getServerIP());
                        mainCardLayout.show(cardPanel, "mainCard");
                    }
                } else JOptionPane.showMessageDialog(optionPaneFrame, NO_EMPTY_SPACES_STRING);
            }
        }
    }

    /**
     * Zeichnet Die Graphen fuer die Anzeige der Messdaten
     */
    private void drawFrameChartTime() {
        if(messDaten.size() == 0) {
            JOptionPane.showMessageDialog(optionPaneFrame, "Keine Messdaten vorhanden!");
            return;
        }

        XYSeries gesamt = new XYSeries("");
        XYSeries seri = new XYSeries("");
        XYSeries deseri = new XYSeries("");

        JFrame jFrame = new JFrame();

        Dimension d = new Dimension();
        d.setSize(100, 500);
        jFrame.setMinimumSize(d);
        jFrame.setResizable(true);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        for (MessdatenGrenz m : messDaten) {
            gesamt.add(m.getPaketGroesseByte()/1000, m.getGesamtZeit());
            if(m.getSerizeit() != 0) seri.add(m.getPaketGroesseByte()/1000, m.getSerizeit());
            if(m.getDeserizeit() != 0) deseri.add(m.getPaketGroesseByte()/1000, m.getDeserizeit());
        }

        jFrame.add(new ChartPanel(createChart(gesamt,"Gesamtzeit nach Größe")),BorderLayout.NORTH);
        if(seri.getItemCount() > 0) jFrame.add(new ChartPanel(createChart(seri,"Serialisierungszeit nach Größe")),BorderLayout.WEST);
        if(deseri.getItemCount() > 0) jFrame.add(new ChartPanel(createChart(deseri,"Deserialisierungszeit nach Größe")),BorderLayout.EAST);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    /**
     * Erzeugt einen Graphen mit der gegebenen XYSeries Funktion und der gegebenen Beschreibung.
     *
     * @param xys
     * @param chartDescription
     * @return Den erstellten Graphen.
     */
    private JFreeChart createChart(XYSeries xys, String chartDescription) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSplineRenderer xySplineRenderer = new XYSplineRenderer();
        XYPlot plot;
        NumberAxis xAxis;
        NumberAxis yAxis;
        JFreeChart chart;

        dataset.addSeries(xys);

        xySplineRenderer.setSeriesPaint(0, Color.blue);
        xySplineRenderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4)); //größe der punkte bei XYSplineRenderer
        xAxis = new NumberAxis("Dateigröße in KB");
        yAxis = new NumberAxis("Zeit in ms");

        plot = new XYPlot(dataset, xAxis, yAxis, xySplineRenderer);
        chart = new JFreeChart(plot);
        chart.setTitle(chartDescription);

        return chart;
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
    private void $$$setupUI$$$() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(7, 7));
        cardPanel.setBackground(new Color(-1513240));
        formPanel.add(cardPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        connectPanel = new JPanel();
        connectPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        connectPanel.setBackground(new Color(-1513240));
        connectPanel.setEnabled(true);
        cardPanel.add(connectPanel, "connectCard");
        connectMiddlePanel = new JPanel();
        connectMiddlePanel.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        connectMiddlePanel.setBackground(new Color(-1));
        connectPanel.add(connectMiddlePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        connectMiddlePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        inputIpPanel = new JPanel();
        inputIpPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        inputIpPanel.setBackground(new Color(-1));
        connectMiddlePanel.add(inputIpPanel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ipTextField = new JTextField();
        ipTextField.setFont(new Font(ipTextField.getFont().getName(), ipTextField.getFont().getStyle(), 14));
        ipTextField.setText("IP-Adresse des Servers");
        ipTextField.setToolTipText("");
        inputIpPanel.add(ipTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, -1), null, 0, false));
        verbindenButton = new JButton();
        verbindenButton.setFont(new Font(verbindenButton.getFont().getName(), verbindenButton.getFont().getStyle(), 14));
        verbindenButton.setText("Verbinden");
        inputIpPanel.add(verbindenButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        inputIpPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(3, -1), new Dimension(3, -1), new Dimension(3, -1), 0, false));
        final Spacer spacer2 = new Spacer();
        connectMiddlePanel.add(spacer2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        connectMiddlePanel.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        connectMiddlePanel.add(spacer4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1));
        connectMiddlePanel.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 14));
        label1.setHorizontalAlignment(2);
        label1.setText("Bitte Server für die Verbindung angeben");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        infoLogoLabel = new JLabel();
        infoLogoLabel.setText("infoLogoLabel");
        panel1.add(infoLogoLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        connectMiddlePanel.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        connectHeaderPanel = new JPanel();
        connectHeaderPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        connectHeaderPanel.setAutoscrolls(false);
        connectHeaderPanel.setBackground(new Color(-1513240));
        connectPanel.add(connectHeaderPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        protobufLogoLabel = new JLabel();
        protobufLogoLabel.setText("protobufLogoLabel");
        connectHeaderPanel.add(protobufLogoLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        connectTitleLabel = new JLabel();
        connectTitleLabel.setFont(new Font(connectTitleLabel.getFont().getName(), Font.PLAIN, 28));
        connectTitleLabel.setText(" Testbench ");
        connectHeaderPanel.add(connectTitleLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        einstellungenButtonConnect = new JButton();
        einstellungenButtonConnect.setEnabled(true);
        einstellungenButtonConnect.setFont(new Font(einstellungenButtonConnect.getFont().getName(), einstellungenButtonConnect.getFont().getStyle(), 14));
        einstellungenButtonConnect.setText("Einstellungen");
        connectHeaderPanel.add(einstellungenButtonConnect, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 30), new Dimension(120, 30), new Dimension(120, 30), 0, false));
        connectBottomPanel = new JPanel();
        connectBottomPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        connectBottomPanel.setBackground(new Color(-1513240));
        connectPanel.add(connectBottomPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        connectBottomPanel.add(spacer6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(1, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font(label2.getFont().getName(), Font.ITALIC, 14));
        label2.setText("Von Sven Riedel, Christoph Landsky, Carsten Grings und Murat Tasdemir ");
        connectBottomPanel.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        thLogoLabel = new JLabel();
        thLogoLabel.setText("thLogoLabel");
        connectBottomPanel.add(thLogoLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        connectBottomPanel.add(spacer7, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(5, -1), 0, false));
        final Spacer spacer8 = new Spacer();
        connectBottomPanel.add(spacer8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 5), new Dimension(-1, 5), new Dimension(-1, 5), 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setAutoscrolls(false);
        mainPanel.setBackground(new Color(-1513240));
        cardPanel.add(mainPanel, "mainCard");
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setBackground(new Color(-1513240));
        mainTabbedPane.setEnabled(true);
        mainTabbedPane.setFont(new Font(mainTabbedPane.getFont().getName(), Font.BOLD, 14));
        mainPanel.add(mainTabbedPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPane.addTab("Herunterladen", panel2);
        splitpanePanelDown = new JPanel();
        splitpanePanelDown.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(splitpanePanelDown, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        splitpanePanelDown.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null));
        splitPaneDown = new JSplitPane();
        splitpanePanelDown.add(splitPaneDown, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        leftPanelDownload = new JPanel();
        leftPanelDownload.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelDownload.setMaximumSize(new Dimension(150, 2147483647));
        splitPaneDown.setLeftComponent(leftPanelDownload);
        massenLabelPanelDown = new JPanel();
        massenLabelPanelDown.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelDownload.add(massenLabelPanelDown, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setFont(new Font(label3.getFont().getName(), label3.getFont().getStyle(), 14));
        label3.setText("Server Massendaten");
        massenLabelPanelDown.add(label3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        massenLabelPanelDown.add(spacer9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        massenLabelPanelDown.add(spacer10, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        struktLabelPanelDown = new JPanel();
        struktLabelPanelDown.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelDownload.add(struktLabelPanelDown, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font(label4.getFont().getName(), label4.getFont().getStyle(), 14));
        label4.setText("Server Strukturierte Daten");
        struktLabelPanelDown.add(label4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        struktLabelPanelDown.add(spacer11, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        struktLabelPanelDown.add(spacer12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        leftPanelDownload.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        massenTableDownload = new JTable();
        scrollPane1.setViewportView(massenTableDownload);
        final JScrollPane scrollPane2 = new JScrollPane();
        leftPanelDownload.add(scrollPane2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        struktTableDownload = new JTable();
        scrollPane2.setViewportView(struktTableDownload);
        rightPanelDownload = new JPanel();
        rightPanelDownload.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneDown.setRightComponent(rightPanelDownload);
        detailsPanelDown = new JPanel();
        detailsPanelDown.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        detailsPanelDown.setBackground(new Color(-1));
        rightPanelDownload.add(detailsPanelDown, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        detailsPanelDown.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JLabel label5 = new JLabel();
        label5.setFont(new Font(label5.getFont().getName(), label5.getFont().getStyle(), 14));
        label5.setText("Art:");
        detailsPanelDown.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setFont(new Font(label6.getFont().getName(), label6.getFont().getStyle(), 14));
        label6.setText("Id:");
        detailsPanelDown.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setFont(new Font(label7.getFont().getName(), label7.getFont().getStyle(), 14));
        label7.setText("Größe:");
        detailsPanelDown.add(label7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        artLabelDown = new JLabel();
        artLabelDown.setFont(new Font(artLabelDown.getFont().getName(), artLabelDown.getFont().getStyle(), 14));
        artLabelDown.setText("/");
        detailsPanelDown.add(artLabelDown, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groesseLabelDown = new JLabel();
        groesseLabelDown.setFont(new Font(groesseLabelDown.getFont().getName(), groesseLabelDown.getFont().getStyle(), 14));
        groesseLabelDown.setText("/");
        detailsPanelDown.add(groesseLabelDown, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        idLabelDown = new JLabel();
        idLabelDown.setFont(new Font(idLabelDown.getFont().getName(), idLabelDown.getFont().getStyle(), 14));
        idLabelDown.setText("/");
        detailsPanelDown.add(idLabelDown, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonsPanelDown = new JPanel();
        buttonsPanelDown.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        rightPanelDownload.add(buttonsPanelDown, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        herunterladenButton = new JButton();
        herunterladenButton.setText("Herunterladen");
        buttonsPanelDown.add(herunterladenButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        buttonsPanelDown.add(spacer13, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, new Dimension(5, -1), new Dimension(5, -1), null, 0, false));
        final Spacer spacer14 = new Spacer();
        buttonsPanelDown.add(spacer14, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        buttonsPanelDown.add(spacer15, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, 1, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer16 = new Spacer();
        buttonsPanelDown.add(spacer16, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setFont(new Font(label8.getFont().getName(), label8.getFont().getStyle(), 14));
        label8.setText("Gewählte Daten");
        rightPanelDownload.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setFont(new Font(label9.getFont().getName(), label9.getFont().getStyle(), 14));
        label9.setText("Daten auf dem Server");
        panel3.add(label9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        panel3.add(spacer17, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        refreshIconDownload = new JLabel();
        refreshIconDownload.setText("refreshicon");
        refreshIconDownload.setToolTipText("Zum Aktualisieren klicken");
        panel3.add(refreshIconDownload, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        panel2.add(spacer18, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 8), new Dimension(-1, 8), new Dimension(-1, 8), 0, false));
        final Spacer spacer19 = new Spacer();
        panel2.add(spacer19, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPane.addTab("Hochladen", panel4);
        splitPanePanelUp = new JPanel();
        splitPanePanelUp.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(splitPanePanelUp, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        splitPanePanelUp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null));
        splitPaneUp = new JSplitPane();
        splitPanePanelUp.add(splitPaneUp, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        leftPanelUpload = new JPanel();
        leftPanelUpload.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneUp.setLeftComponent(leftPanelUpload);
        massenLabelPanelUp = new JPanel();
        massenLabelPanelUp.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelUpload.add(massenLabelPanelUp, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setFont(new Font(label10.getFont().getName(), label10.getFont().getStyle(), 14));
        label10.setText("Lokale Massendaten");
        massenLabelPanelUp.add(label10, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer20 = new Spacer();
        massenLabelPanelUp.add(spacer20, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer21 = new Spacer();
        massenLabelPanelUp.add(spacer21, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        struktLabelPanelUp = new JPanel();
        struktLabelPanelUp.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelUpload.add(struktLabelPanelUp, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setFont(new Font(label11.getFont().getName(), label11.getFont().getStyle(), 14));
        label11.setText("Lokale Strukturierte Daten");
        struktLabelPanelUp.add(label11, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer22 = new Spacer();
        struktLabelPanelUp.add(spacer22, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer23 = new Spacer();
        struktLabelPanelUp.add(spacer23, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        leftPanelUpload.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        massenTableUpload = new JTable();
        scrollPane3.setViewportView(massenTableUpload);
        final JScrollPane scrollPane4 = new JScrollPane();
        leftPanelUpload.add(scrollPane4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        struktTableUpload = new JTable();
        scrollPane4.setViewportView(struktTableUpload);
        rightPanelUpload = new JPanel();
        rightPanelUpload.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneUp.setRightComponent(rightPanelUpload);
        detailsPanelUp = new JPanel();
        detailsPanelUp.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        detailsPanelUp.setBackground(new Color(-1));
        rightPanelUpload.add(detailsPanelUp, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        detailsPanelUp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JLabel label12 = new JLabel();
        label12.setFont(new Font(label12.getFont().getName(), label12.getFont().getStyle(), 14));
        label12.setHorizontalAlignment(0);
        label12.setText("Art:");
        detailsPanelUp.add(label12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setFont(new Font(label13.getFont().getName(), label13.getFont().getStyle(), 14));
        label13.setHorizontalAlignment(0);
        label13.setText("Id:");
        detailsPanelUp.add(label13, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setFont(new Font(label14.getFont().getName(), label14.getFont().getStyle(), 14));
        label14.setHorizontalAlignment(0);
        label14.setText("Größe:");
        detailsPanelUp.add(label14, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        artLabelUp = new JLabel();
        artLabelUp.setFont(new Font(artLabelUp.getFont().getName(), artLabelUp.getFont().getStyle(), 14));
        artLabelUp.setText("/");
        detailsPanelUp.add(artLabelUp, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        idLabelUp = new JLabel();
        idLabelUp.setFont(new Font(idLabelUp.getFont().getName(), idLabelUp.getFont().getStyle(), 14));
        idLabelUp.setText("/");
        detailsPanelUp.add(idLabelUp, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groesseLabelUp = new JLabel();
        groesseLabelUp.setFont(new Font(groesseLabelUp.getFont().getName(), groesseLabelUp.getFont().getStyle(), 14));
        groesseLabelUp.setText("/");
        detailsPanelUp.add(groesseLabelUp, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonsPanelUp = new JPanel();
        buttonsPanelUp.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        rightPanelUpload.add(buttonsPanelUp, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        hochladenButton = new JButton();
        hochladenButton.setText("Hochladen");
        buttonsPanelUp.add(hochladenButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer24 = new Spacer();
        buttonsPanelUp.add(spacer24, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, new Dimension(5, -1), new Dimension(5, -1), null, 0, false));
        final Spacer spacer25 = new Spacer();
        buttonsPanelUp.add(spacer25, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer26 = new Spacer();
        buttonsPanelUp.add(spacer26, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, 1, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer27 = new Spacer();
        buttonsPanelUp.add(spacer27, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setFont(new Font(label15.getFont().getName(), label15.getFont().getStyle(), 14));
        label15.setText("Gewählte Daten");
        rightPanelUpload.add(label15, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setFont(new Font(label16.getFont().getName(), label16.getFont().getStyle(), 14));
        label16.setText("Lokale Daten");
        panel5.add(label16, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer28 = new Spacer();
        panel5.add(spacer28, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        refreshIconUpload = new JLabel();
        refreshIconUpload.setText("refreshicon");
        refreshIconUpload.setToolTipText("Zum Aktualisieren klicken");
        panel5.add(refreshIconUpload, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer29 = new Spacer();
        panel4.add(spacer29, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final Spacer spacer30 = new Spacer();
        panel4.add(spacer30, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 8), new Dimension(-1, 8), new Dimension(-1, 8), 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPane.addTab("Daten - Details", panel6);
        splitPanePanelDetails = new JPanel();
        splitPanePanelDetails.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(splitPanePanelDetails, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        splitPanePanelDetails.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null));
        splitPaneDetails = new JSplitPane();
        splitPanePanelDetails.add(splitPaneDetails, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        leftPanelDetails = new JPanel();
        leftPanelDetails.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneDetails.setLeftComponent(leftPanelDetails);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelDetails.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setFont(new Font(label17.getFont().getName(), label17.getFont().getStyle(), 14));
        label17.setText("Lokale Massendaten");
        panel7.add(label17, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer31 = new Spacer();
        panel7.add(spacer31, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer32 = new Spacer();
        panel7.add(spacer32, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelDetails.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setFont(new Font(label18.getFont().getName(), label18.getFont().getStyle(), 14));
        label18.setText("Lokale Strukturierte Daten");
        panel8.add(label18, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer33 = new Spacer();
        panel8.add(spacer33, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer34 = new Spacer();
        panel8.add(spacer34, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scrollPaneMassenDetails = new JScrollPane();
        leftPanelDetails.add(scrollPaneMassenDetails, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        massenTableDetails = new JTable();
        scrollPaneMassenDetails.setViewportView(massenTableDetails);
        scrollPaneStruktDetails = new JScrollPane();
        leftPanelDetails.add(scrollPaneStruktDetails, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        struktTableDetails = new JTable();
        scrollPaneStruktDetails.setViewportView(struktTableDetails);
        rightPanelDetails = new JPanel();
        rightPanelDetails.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneDetails.setRightComponent(rightPanelDetails);
        final JLabel label19 = new JLabel();
        label19.setFont(new Font(label19.getFont().getName(), label19.getFont().getStyle(), 14));
        label19.setText("Details der Daten");
        rightPanelDetails.add(label19, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cardPanelDetails = new JPanel();
        cardPanelDetails.setLayout(new CardLayout(0, 0));
        cardPanelDetails.setAutoscrolls(false);
        cardPanelDetails.setBackground(new Color(-1));
        rightPanelDetails.add(cardPanelDetails, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cardPanelDetails.add(chartPanel, "chartCard");
        treePanel = new JPanel();
        treePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        cardPanelDetails.add(treePanel, "treeCard");
        treeScrollPane = new JScrollPane();
        treePanel.add(treeScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        struktTree = new JTree();
        struktTree.setFont(new Font(struktTree.getFont().getName(), struktTree.getFont().getStyle(), 14));
        treeScrollPane.setViewportView(struktTree);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setFont(new Font(label20.getFont().getName(), label20.getFont().getStyle(), 14));
        label20.setText("Lokale Daten");
        panel9.add(label20, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer35 = new Spacer();
        panel9.add(spacer35, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        refreshIconDetails = new JLabel();
        refreshIconDetails.setText("refreshicon");
        refreshIconDetails.setToolTipText("Zum Aktualisieren klicken");
        panel9.add(refreshIconDetails, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer36 = new Spacer();
        panel6.add(spacer36, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final Spacer spacer37 = new Spacer();
        panel6.add(spacer37, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 8), new Dimension(-1, 8), new Dimension(-1, 8), 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPane.addTab("Messungen", panel10);
        splitPaneMess = new JSplitPane();
        panel10.add(splitPaneMess, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        leftPanelMessdaten = new JPanel();
        leftPanelMessdaten.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelMessdaten.setEnabled(true);
        leftPanelMessdaten.setMinimumSize(new Dimension(100, 150));
        leftPanelMessdaten.setPreferredSize(new Dimension(100, 150));
        splitPaneMess.setLeftComponent(leftPanelMessdaten);
        massenLabelPanelMess = new JPanel();
        massenLabelPanelMess.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanelMessdaten.add(massenLabelPanelMess, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setFont(new Font(label21.getFont().getName(), label21.getFont().getStyle(), 14));
        label21.setText("Lokale Messdaten");
        massenLabelPanelMess.add(label21, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer38 = new Spacer();
        massenLabelPanelMess.add(spacer38, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer39 = new Spacer();
        massenLabelPanelMess.add(spacer39, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        leftPanelMessdaten.add(scrollPane5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        messTable = new JTable();
        scrollPane5.setViewportView(messTable);
        graphAllerMessdatenAnzeigenButton = new JButton();
        graphAllerMessdatenAnzeigenButton.setFont(new Font(graphAllerMessdatenAnzeigenButton.getFont().getName(), graphAllerMessdatenAnzeigenButton.getFont().getStyle(), 14));
        graphAllerMessdatenAnzeigenButton.setText("Graph aller Messdaten anzeigen");
        leftPanelMessdaten.add(graphAllerMessdatenAnzeigenButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rightPanelMessdaten = new JPanel();
        rightPanelMessdaten.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        rightPanelMessdaten.setBackground(new Color(-1));
        splitPaneMess.setRightComponent(rightPanelMessdaten);
        final JLabel label22 = new JLabel();
        label22.setFont(new Font(label22.getFont().getName(), label22.getFont().getStyle(), 14));
        label22.setText("ID:");
        rightPanelMessdaten.add(label22, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setFont(new Font(label23.getFont().getName(), label23.getFont().getStyle(), 14));
        label23.setHorizontalAlignment(2);
        label23.setText("Serialisierungszeit:");
        rightPanelMessdaten.add(label23, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messIdLabel = new JLabel();
        messIdLabel.setFont(new Font(messIdLabel.getFont().getName(), messIdLabel.getFont().getStyle(), 14));
        messIdLabel.setText("/");
        rightPanelMessdaten.add(messIdLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setFont(new Font(label24.getFont().getName(), label24.getFont().getStyle(), 14));
        label24.setHorizontalAlignment(2);
        label24.setText("Deserialisierungszeit:");
        rightPanelMessdaten.add(label24, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setFont(new Font(label25.getFont().getName(), label25.getFont().getStyle(), 14));
        label25.setText("Gesamtzeit:");
        rightPanelMessdaten.add(label25, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setFont(new Font(label26.getFont().getName(), label26.getFont().getStyle(), 14));
        label26.setText("Größe der zugehörigen Daten:");
        rightPanelMessdaten.add(label26, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messSizeLabel = new JLabel();
        messSizeLabel.setFont(new Font(messSizeLabel.getFont().getName(), messSizeLabel.getFont().getStyle(), 14));
        messSizeLabel.setText("/");
        rightPanelMessdaten.add(messSizeLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messDeseriLabel = new JLabel();
        messDeseriLabel.setFont(new Font(messDeseriLabel.getFont().getName(), messDeseriLabel.getFont().getStyle(), 14));
        messDeseriLabel.setText("/");
        rightPanelMessdaten.add(messDeseriLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messSeriLabel = new JLabel();
        messSeriLabel.setFont(new Font(messSeriLabel.getFont().getName(), messSeriLabel.getFont().getStyle(), 14));
        messSeriLabel.setText("/");
        rightPanelMessdaten.add(messSeriLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messAllTimeLabel = new JLabel();
        messAllTimeLabel.setFont(new Font(messAllTimeLabel.getFont().getName(), messAllTimeLabel.getFont().getStyle(), 14));
        messAllTimeLabel.setText("/");
        rightPanelMessdaten.add(messAllTimeLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel10.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setFont(new Font(label27.getFont().getName(), label27.getFont().getStyle(), 14));
        label27.setText("Messdaten");
        panel11.add(label27, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer40 = new Spacer();
        panel11.add(spacer40, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        refreshIconMessdaten = new JLabel();
        refreshIconMessdaten.setText("refreshicon");
        refreshIconMessdaten.setToolTipText("Zum Aktualisieren klicken");
        panel11.add(refreshIconMessdaten, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer41 = new Spacer();
        panel10.add(spacer41, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final Spacer spacer42 = new Spacer();
        panel10.add(spacer42, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 8), new Dimension(-1, 8), new Dimension(-1, 8), 0, false));
        underTitlePanel = new JPanel();
        underTitlePanel.setLayout(new GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        underTitlePanel.setAutoscrolls(false);
        underTitlePanel.setBackground(new Color(-1513240));
        mainPanel.add(underTitlePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, 1, null, null, null, 0, true));
        final JLabel label28 = new JLabel();
        label28.setFont(new Font(label28.getFont().getName(), label28.getFont().getStyle(), 14));
        label28.setText("Aktuelle Server IP:");
        underTitlePanel.add(label28, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentIpTextField = new JTextField();
        currentIpTextField.setEditable(false);
        currentIpTextField.setFont(new Font(currentIpTextField.getFont().getName(), currentIpTextField.getFont().getStyle(), 14));
        underTitlePanel.add(currentIpTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 30), new Dimension(300, 30), null, 0, false));
        final Spacer spacer43 = new Spacer();
        underTitlePanel.add(spacer43, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(5, -1), 0, false));
        changeIpButton = new JButton();
        changeIpButton.setFont(new Font(changeIpButton.getFont().getName(), changeIpButton.getFont().getStyle(), 14));
        changeIpButton.setText("Ändern");
        underTitlePanel.add(changeIpButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 30), new Dimension(120, 30), new Dimension(120, 30), 0, false));
        final JSeparator separator1 = new JSeparator();
        separator1.setOrientation(1);
        underTitlePanel.add(separator1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(-1, 1), new Dimension(-1, 10), new Dimension(-1, 30), 0, false));
        einstellungenButtonMain = new JButton();
        einstellungenButtonMain.setEnabled(true);
        einstellungenButtonMain.setFont(new Font(einstellungenButtonMain.getFont().getName(), einstellungenButtonMain.getFont().getStyle(), 14));
        einstellungenButtonMain.setText("Einstellungen");
        underTitlePanel.add(einstellungenButtonMain, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        datenverwaltungButton = new JButton();
        datenverwaltungButton.setFont(new Font(datenverwaltungButton.getFont().getName(), datenverwaltungButton.getFont().getStyle(), 14));
        datenverwaltungButton.setText("Datengenerator");
        underTitlePanel.add(datenverwaltungButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        final Spacer spacer44 = new Spacer();
        formPanel.add(spacer44, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 3), new Dimension(-1, 3), new Dimension(-1, 3), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return formPanel;
    }

    public class ProgressBarThread extends Thread {
        private ProgressBarWindow pWindow;
        private boolean abbruch;

        public ProgressBarThread(Boolean isDownload) {
            this.pWindow = new ProgressBarWindow(isDownload);
            start();
        }

        public void run() {
            long value;
            float progress;

            while(!abbruch) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                value = (long)(StaticHolder.currentTransferCount)*100;
                progress = value/StaticHolder.currentTransferSizeByte;

                pWindow.setProgressBar((int)progress);
            }
        }

        public void abbrechen() {
            abbruch = true;
            pWindow.dispose();
        }
    }
}
