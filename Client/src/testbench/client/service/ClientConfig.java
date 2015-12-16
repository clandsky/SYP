package testbench.client.service;

import testbench.bootloader.Printer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by Sven Riedel on 10.12.2015
 */
public class ClientConfig {
    private static ClientConfig clientConfig;

    private final String directoryPath = "config/client/";
    private final String filePath = directoryPath+"config.cfg";

    public HashMap<String, String> currentSettingsMap = new LinkedHashMap<>();
    public HashMap<String, String> standartSettingsMap = new LinkedHashMap<>();

    private ClientConfig() {
        initStandartSettingsMap();
        readConfigToMap();
        writeCurrentConfig();
    }

    public static ClientConfig getExemplar() {
        if(clientConfig == null) clientConfig = new ClientConfig();
        return clientConfig;
    }
    public void writeCurrentConfig() {
        writeConfig(currentSettingsMap);
    }

    public void readConfigToMap() {
        BufferedReader br;
        File config = new File(filePath);
        ArrayList<String> standartKeys = new ArrayList<>(standartSettingsMap.keySet());
        ArrayList<String> currentKeys;
        String temp;
        int equalsPos;

        if(!config.exists()) writeStandartConfig();

        try {
            br = new BufferedReader(new FileReader(config));
            while((temp = br.readLine()) != null) {
                equalsPos = temp.indexOf("=");
                if(standartSettingsMap.containsKey(temp.substring(0,equalsPos))) currentSettingsMap.put(temp.substring(0,equalsPos),temp.substring(equalsPos+1,temp.length()));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentKeys = new ArrayList(currentSettingsMap.keySet());

        if(compareStringLists(standartKeys,currentKeys)) {
            return;
        }
        else {
            for(String s : standartKeys) {
                if(!currentSettingsMap.containsValue(s)) currentSettingsMap.put(s,standartSettingsMap.get(s));
            }
        }
    }

    private boolean compareStringLists(List<String> list1, List<String> list2) {
        if(list1.size() != list2.size()) return false;

        for(int i=0 ; i<list1.size() ; i++) if(!list1.get(i).equals(list2.get(i))) return false;

        return true;
    }

    public void writeStandartConfig() {
        writeConfig(standartSettingsMap);
    }

    private void writeConfig(HashMap<String, String> map) {
        PrintWriter printWriter;
        ArrayList<String> valueList;
        File directory = new File(directoryPath);
        File config = new File(filePath);

        if(!directory.exists()) Printer.println("Config-Verzeichnis erstellt:" +directory.mkdirs());
        if(config.exists()) config.delete();
        else Printer.println("Config-Datei erstellt:" +directory.mkdirs());

        try {
            config.createNewFile();
            printWriter = new PrintWriter(config);
            valueList = new ArrayList<>(map.keySet());
            for(int i=0 ; i<valueList.size() ; i++) {
                printWriter.println(valueList.get(i)+"="+map.get(valueList.get(i)));
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStandartSettingsMap() {
        standartSettingsMap.put("port","8000");
        standartSettingsMap.put("debugmode","true");
    }

    public String getPort() {
        return currentSettingsMap.get("port");
    }
    public void setPort(String port) {
        currentSettingsMap.put("port", port);
    }
    public boolean getDebugMode() {
        return Boolean.valueOf(currentSettingsMap.get("debugmode"));
    }
    public void setDebugMode(boolean bool) {
        currentSettingsMap.put("debugmode", String.valueOf(bool));
    }
}
