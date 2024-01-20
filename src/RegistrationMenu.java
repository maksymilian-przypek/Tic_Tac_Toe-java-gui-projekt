import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class RegistrationMenu extends JFrame {

    private JTextField textFieldName;
    private JLabel labelImie;
    private JTextField textFieldSurname;
    private JLabel Nazwisko;
    private JFormattedTextField formattedTextFieldEmail;
    private JLabel Email;
    private JSlider sliderWiek;
    private JButton buttonRegister;
    private JPanel registerpanel;
    private JLabel labelNazwisko;
    private JLabel emailLabel;
    private JLabel labelWiek;
    private JLabel labelWhoRegister;

    public RegistrationMenu(String who) {
        super("Registration Menu");
        this.setContentPane(this.registerpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(700, 550));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        labelWhoRegister.setText(who);
        actionListener();
    }

    private void actionListener() {
        sliderWiek.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelWiek.setText("Wiek: " + sliderWiek.getValue());
            }
        });

        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imie = textFieldName.getText();
                String nazwisko = textFieldSurname.getText();
                String email = formattedTextFieldEmail.getText();
                int wiek = sliderWiek.getValue();

                if (imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please complete all fields", "Error", JOptionPane.WARNING_MESSAGE);
                } else if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email format", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                                                  "Correctly registered player",
                                                  "Correct",
                                                  JOptionPane.INFORMATION_MESSAGE);
                    GameBoard.player.add(new Object[]{imie, nazwisko, email, wiek});
                    dispose();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a simple email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}