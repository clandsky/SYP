package testbench.bootloader.provider;

import testbench.bootloader.protobuf.Splitter;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Sven Riedel on 07.12.2015.
 */
public class ByteMessage {
    private byte[] byteArray;

    public ByteMessage(Massendaten m, int packetSizeKB, float progressBarSizeMulti) {
        Splitter splitter = new Splitter();
        List<Massendaten> massendatenList = splitter.splitMassendaten(m,packetSizeKB,progressBarSizeMulti);
        this.byteArray = splitter.combineByteArrays(massendatenList);
    }

    public ByteMessage(byte[] byteArray) {
        this.byteArray = byteArray;
    }


    public byte[] getByteArray() {
        return byteArray;
    }

    public static byte[] readFully(InputStream input) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
