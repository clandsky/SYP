package testbench.bootloader.provider;

import com.google.protobuf.InvalidProtocolBufferException;
import testbench.bootloader.protobuf.massendaten.MassendatenProtos.Massendaten;
import testbench.bootloader.protobuf.struktdaten.StruktdatenProtos.Struktdaten;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sven Riedel on 07.12.2015
 */
public class ByteMessage {
    private byte[] byteArray;

    public ByteMessage(Massendaten m) {
        this.byteArray = m.toByteArray();
    }

    public ByteMessage(Struktdaten s) {
        this.byteArray = s.toByteArray();
    }

    public ByteMessage(InputStream inputStream) {
        this.byteArray = readFully(inputStream);
    }

    public Massendaten getMassendaten() {
        try {
            return Massendaten.parseFrom(byteArray);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Struktdaten getStruktdaten() {
        try {
            return Struktdaten.parseFrom(byteArray);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    private byte[] readFully(InputStream input) {
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
