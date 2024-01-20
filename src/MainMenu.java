import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MainMenu extends JFrame {
    private JButton buttonNewGame;
    private JButton buttonAuthors;
    private JButton buttonQuitGame;
    private JPanel JMainPanel;

    public static void main(String[] args) {
        MainMenu mainMenuGUI = new MainMenu();
        mainMenuGUI.setVisible(true);
    }

    public MainMenu() {
        super("Tic-Tac-Toe");
        this.setContentPane(this.JMainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addSound("menuMusic.wav");
        actionListener();
    }

    private void actionListener() {
        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("buttonClick.wav"); // Play button click sound
                SelectionPanel selectionPanelGUI = new SelectionPanel();
                selectionPanelGUI.actionListener(MainMenu.this);
            }
        });

        buttonAuthors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("buttonClick.wav"); // Play button click sound
                JOptionPane.showMessageDialog(null, "Autor: Maksymilian Przypek", "Autor", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonQuitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("buttonClick.wav"); // Play button click sound
                dispose();
            }
        });
    }

    private void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void addSound(String soundFile) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }
}