package testbench.bootloader.entities;

import java.util.Date;

/**
 * Created by Sven Riedel on 04.12.2015.
 */
public class Messdaten {
    private int id;
    private long serizeit;
    private long transzeit;
    private long deserizeit;
    private int serializedSize;
    private Date timestamp;
    private int packetGroesseKB;
    private String typ;
}
