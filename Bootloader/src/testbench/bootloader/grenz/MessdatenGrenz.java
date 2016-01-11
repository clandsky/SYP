package testbench.bootloader.grenz;

import testbench.bootloader.protobuf.messdaten.MessdatenProtos.Messdaten;

/**
 * Created by Sven Riedel on 26.11.2015
 */
public class MessdatenGrenz {
    private int id;
    private long serizeit;
    private long deserizeit;
    private long gesamtZeit;
    private int serializedSize;
    private String timestamp;
    private int paketGroesseByte;
    private String typ;

    public MessdatenGrenz(Messdaten messdaten) {
        this.id = messdaten.getId();
        this.serizeit = messdaten.getSerialisierungsZeit();
        this.deserizeit = messdaten.getDeserialisierungsZeit();
        this.serializedSize = messdaten.getSerializedSize();
        this.gesamtZeit = messdaten.getGesamtZeit();
        this.timestamp = messdaten.getTimeStamp();
        this.paketGroesseByte = messdaten.getPaketGroesseByte();
        this.typ = messdaten.getTyp();
    }

    public int getId() {
        return id;
    }

    public long getSerizeit() {
        return serizeit;
    }

    public long getDeserizeit() {
        return deserizeit;
    }

    public long getGesamtZeit() {
        return gesamtZeit;
    }

    public int getSerializedSize() {
        return serializedSize;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getPaketGroesseByte() {
        return paketGroesseByte;
    }

    public String getTyp() {
        return typ;
    }
}
