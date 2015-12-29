package testbench.bootloader.grenz;

import testbench.bootloader.protobuf.messdaten.MessdatenProtos.Messdaten;

/**
 * Created by Sven Riedel on 26.11.2015
 */
public class MessdatenGrenz {
    private int id;
    private long serizeit;
    private long transmitTime;
    private long deserizeit;
    private int serializedSize;
    private String timestamp;
    private int paketGroesseByte;
    private String typ;

    public MessdatenGrenz(Messdaten messdaten) {
        this.id = messdaten.getId();
        this.serizeit = messdaten.getSerialisierungsZeit();
        this.transmitTime = messdaten.getTransmitTime();
        this.deserizeit = messdaten.getDeserialisierungsZeit();
        this.serializedSize = messdaten.getSerializedSize();
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

    public long getTransmitTime() {
        return transmitTime;
    }

    public long getDeserizeit() {
        return deserizeit;
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
