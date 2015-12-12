package testbench.client;

import java.io.*;

/**
 * Created by Sven Riedel on 11.12.2015
 */
public class FileTest {
    private final String directory = "filetest/";

    public boolean writeByteArrayToFile(byte[] bArray, String fileName) {
        File file = new File(directory+fileName);

        try{
            if(!file.exists()) file.createNewFile();
            else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArray);
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public byte[] readByteArrayFromFile(String fileName) {
        File file = new File(directory+fileName);

        if(file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] bArray = new byte[(int)file.length()];
                int bytesRead = 0;
                int temp;

                try {
                    while ((temp = fis.read()) != -1) {
                        bArray[bytesRead] = (byte)temp;
                        bytesRead++;
                    }
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bArray;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
