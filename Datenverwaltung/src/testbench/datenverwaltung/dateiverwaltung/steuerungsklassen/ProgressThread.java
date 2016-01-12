package testbench.datenverwaltung.dateiverwaltung.steuerungsklassen;

import testbench.datenverwaltung.dateiverwaltung.gui.GeneratorGuiProgress;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CGrings
 * @version 1.0
 * @see java.lang.Thread
 */
public class ProgressThread extends Thread
{
    /**
     * Variable die anzeigt ob der Thread beendet werden soll
     */
    private boolean terminated = false;

    /**
     * Aktueller wert des Fortschrittes
     */
    private AtomicInteger progress = new AtomicInteger(0);

    /**
     * GUI des Fortschrittbalkens
     */
    private GeneratorGuiProgress progressBar = new GeneratorGuiProgress();

    /**
     * Setzt den Fortschirtt der ProgrssBar
     * @param progress Fortschritt zwischen 0 und 100 der ProgressBar
     */
    public void setPorgress( AtomicInteger progress )
    {
        this.progress = progress;
    }

    /**
     * Setzt das Flag zum beenden des Threads
     */
    public void killProgress()
    {
        terminated = true;
        progressBar.setVisible(false);
    }

    /**
     * Run-Methode des Threads
     * @see Thread
     */
    @Override
    public void run()
    {
        progressBar.setVisible(true);
        while( !terminated )
        {
            progressBar.setProgress( progress.get() );
            try
            {
                sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        progressBar.setVisible(false);
        progressBar.dispose();
    }
}
