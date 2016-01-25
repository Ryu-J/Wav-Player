package dev.justinh808;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class AudioWindow implements ActionListener
{

    private JFrame frmWavPlayer;
    private JButton btnPlay;
    private JSlider slider;
    private JButton btnStop;
    private WavPlayer wp;
    private Thread myThread;
    private JButton btnPause;
    private JLabel lblPathwav;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try
                {
                    AudioWindow window = new AudioWindow();
                    window.frmWavPlayer.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AudioWindow()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        try
        {
            if (System.getProperty("os.name").toLowerCase().contains("windows"))
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        wp = new WavPlayer();
        myThread = new Thread(wp);

        frmWavPlayer = new JFrame();
        frmWavPlayer.setTitle("Wav Player");
        frmWavPlayer.setBounds(100, 100, 535, 331);
        frmWavPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmWavPlayer.getContentPane().setLayout(new BorderLayout(0, 0));

        btnPlay = new JButton("Play");
        frmWavPlayer.getContentPane().add(btnPlay, BorderLayout.WEST);
        btnPlay.addActionListener(this);

        slider = new JSlider();
        frmWavPlayer.getContentPane().add(slider, BorderLayout.CENTER);
        slider.setMaximum((int) (wp.clip.getMicrosecondLength() / 1000000));
        slider.setValue(0);
        wp.setSlider(slider);

        btnStop = new JButton("Stop");
        frmWavPlayer.getContentPane().add(btnStop, BorderLayout.EAST);
        btnStop.addActionListener(this);

        btnPause = new JButton("Pause");
        frmWavPlayer.getContentPane().add(btnPause, BorderLayout.SOUTH);
        
        lblPathwav = new JLabel("path.wav");
        frmWavPlayer.getContentPane().add(lblPathwav, BorderLayout.NORTH);
        btnPause.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == this.btnPlay)
        {
            if (myThread.isAlive() && !wp.clip.isRunning())
            {
                wp.clip.start();
            }
            else
            {
                myThread = new Thread(wp);
                myThread.start();
            }
        }
        else if (event.getSource() == this.btnStop)
        {
            if (myThread.isAlive() && wp.clip.isRunning())
            {
                wp.clip.stop();
            }
            wp.clip.setMicrosecondPosition(0);
        }
        else if (event.getSource() == this.btnPause)
        {
            if (myThread.isAlive() && wp.clip.isRunning())
            {
                wp.clip.stop();
            }
        }

    }

}
