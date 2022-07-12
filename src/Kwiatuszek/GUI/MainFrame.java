package Kwiatuszek.GUI;

import Kwiatuszek.Zamowienie;
import Kwiatuszek.Baza;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/*
<script>
function SetBack(dir) {
    document.getElementById('body').style.backgroundImage=dir;
}
SetBack('url(background.jpg)');
</script>
*/

public class MainFrame extends JFrame {

    public static Zamowienie zamowienie = new Zamowienie();
    public static Baza siecKwiaciarni;

    public MainFrame() {

        super("Kwiatuszek");

        // Dodanie ikony aplikacji
        setIconImage(new ImageIcon("data/ikona.png").getImage()); // ustawianie ikony

        // Dodanie zakładek
        JTabbedPane zakladki = new JTabbedPane();
        zakladki.addTab("Główna", new MainPanel());
        zakladki.addTab("Zamówienie", new OrderPanel());
        zakladki.addTab("Koszyk", new BasketPanel());

        // Dodanie zdjecia jako background za pomoca metody paintComponent
        setContentPane(new JPanel(new BorderLayout()) {
            @Override
            public void paintComponent(Graphics g) {
                try {
                    g.drawImage(javax.imageio.ImageIO.read(new File("data/background.jpg")), 0, 0, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        setLayout(new FlowLayout());
        add(zakladki);
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        try {
            // Testowanie
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MainFrame();
                }
            });
        } catch (Exception exc) {
            System.out.println("Can't create because of " + exc);
        }
    }

}
