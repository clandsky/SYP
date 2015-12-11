package testbench.client;

import java.io.*;

/**
 * Created by Sven Riedel on 11.12.2015
 */
public class FileTest {
    public boolean writeByteArrayToFile(byte[] bArray, String filePath) {
        File file = new File(filePath);

        try{
            if(!file.exists()) file.createNewFile();
            else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(bArray);
            fos.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public byte[] readByteArrayFromFile(String filePath) {
        File file = new File(filePath);

        if(file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(filePath);
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
