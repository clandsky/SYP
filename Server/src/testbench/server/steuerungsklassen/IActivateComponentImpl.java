package testbench.server.steuerungsklassen;

import testbench.bootloader.service.IActivateComponent;
import testbench.server.StartServer;

import javax.swing.*;
import java.net.URISyntaxException;

/**
 * Created by Chrizzle Manizzle on 26.11.2015.
 */
public class IActivateComponentImpl implements IActivateComponent{
    StartServer s;
    @Override
    public boolean startComponent() throws Exception {
        s = new StartServer();
        return true;
    }

    @Override
    public JFrame getComponentGui() {
        return s.getGUI();
    }

    @Override
    public String getCompType() {
        return "Server";
    }
}
