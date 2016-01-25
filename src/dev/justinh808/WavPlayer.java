package dev.justinh808;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;

//  USING STANDARD JAVA LIBRARIES THE AUDIO FILE FORMAT MUST BE .wav!!!!!
public class WavPlayer implements Runnable
{

    private AudioInputStream audioInputStream = null;
    public Clip clip = null;
    JSlider slide;

    public WavPlayer()
    {
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(new File("path.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            slide = new JSlider();
        }
        catch (UnsupportedAudioFileException uafe)
        {
            uafe.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (LineUnavailableException lue)
        {
            lue.printStackTrace();
        }
    }

    public void setSlider(JSlider slider)
    {
        slide = slider;
    }

    public static void main(String[] args)
    {
        WavPlayer wp = new WavPlayer();
        Thread myThread = new Thread(wp);
        myThread.start();
        /*
         * try { Thread.sleep(30000); System.out.println(wp.clip.getMicrosecondPosition()); } catch (InterruptedException e) {
         * e.printStackTrace(); }
         */
    }

    @Override
    public void run()
    {
        // System.out.println(clip.getMicrosecondLength());
        clip.start();
        while (clip.getMicrosecondPosition() != clip.getMicrosecondLength())
        {
            slide.setValue((int) (clip.getMicrosecondPosition() / 1000000));
        }

    }

}
