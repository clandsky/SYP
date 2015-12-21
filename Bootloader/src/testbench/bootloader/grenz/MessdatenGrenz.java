package testbench.bootloader.grenz;

import testbench.bootloader.entities.Messdaten;

import java.util.Date;

/**
 * Created by Sven Riedel on 26.11.2015
 */
public class MessdatenGrenz {
    private int id;
    private long serizeit;
    private long transzeit;
    private long deserizeit;
    private int serializedSize;
    private Date timestamp;
    private int paketGroesseKB;
    private String typ;

    public MessdatenGrenz(Messdaten messdaten) {
        this.id = messdaten.getId();
        this.serizeit = messdaten.getSerizeit();
        this.transzeit = messdaten.getTranszeit();
        this.deserizeit = messdaten.getDeserizeit();
        this.serializedSize = messdaten.getSerializedSize();
        this.timestamp = new Date();
        this.paketGroesseKB = messdaten.getPaketGroesseKB();
        this.typ = messdaten.getTyp();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSerizeit() {
        return serizeit;
    }

    public void setSerizeit(long serizeit) {
        this.serizeit = serizeit;
    }

    public long getTranszeit() {
        return transzeit;
    }

    public void setTranszeit(long transzeit) {
        this.transzeit = transzeit;
    }

    public long getDeserizeit() {
        return deserizeit;
    }

    public void setDeserizeit(long deserizeit) {
        this.deserizeit = deserizeit;
    }

    public int getSerializedSize() {
        return serializedSize;
    }

    public void setSerializedSize(int serializedSize) {
        this.serializedSize = serializedSize;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPaketGroesseKB() {
        return paketGroesseKB;
    }

    public void setPaketGroesseKB(int paketGroesseKB) {
        this.paketGroesseKB = paketGroesseKB;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
