import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPanel extends JFrame
{
    private JButton buttonGameComputer;
    private JButton buttonGameHuman;
    private JPanel JMainPanel;

    public SelectionPanel()
    {
        add(JMainPanel);
        setTitle("Wybierz tryb gry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void actionListener(MainMenu mainMenuGUI)
    {
        buttonGameComputer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GameBoard game = new GameBoard(true);
                game.setVisible(true);
                mainMenuGUI.dispose();
                dispose();
                RegistrationMenu registrationMenu = new RegistrationMenu("Player register");
                registrationMenu.setVisible(true);
            }
        });

        buttonGameHuman.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GameBoard game = new GameBoard(false);
                game.setVisible(true);
                mainMenuGUI.dispose();
                dispose();
                RegistrationMenu registrationMenu2 = new RegistrationMenu("Player2 register");
                registrationMenu2.setVisible(true);
                RegistrationMenu registrationMenu1 = new RegistrationMenu("Player1 register");
                registrationMenu1.setVisible(true);
            }
        });
    }
}