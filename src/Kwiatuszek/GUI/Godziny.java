package Kwiatuszek.GUI;

import javax.swing.*;
import java.awt.*;

public class Godziny extends JPanel {
    JLabel napis;
    JComboBox<String> spisGodzin;
    public Godziny(){
        super();
        setLayout(new BorderLayout());

        napis = new JLabel("Wybierz godzine rezerwacji");

        // Kolejne dodawanie godzin rezerwacji
        spisGodzin = new JComboBox<>();
        for (int i = 10; i <= 22; i++){
            spisGodzin.addItem(String.valueOf(i));
        }

        add(napis, BorderLayout.NORTH);
        add(spisGodzin, BorderLayout.SOUTH);
    }

    public int getGodzina(){
        return Integer.parseInt((String) spisGodzin.getSelectedItem());
    }
}
