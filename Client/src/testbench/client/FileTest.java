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
            System.out.println("IO Exception in writeByteArrayToFile()");
            return false;
        }
    }

    public byte[] readByteArrayFromFile(String filePath) {
        File file = new File(filePath);
        byte[] buffer;

        if(file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(filePath);
                buffer = new byte[(int)file.length()];
                int bytesRead = 0;
                int temp;

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((temp = fis.read()) != -1) {
                        buffer[bytesRead] = (byte)temp;
                        bytesRead++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return buffer;


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
