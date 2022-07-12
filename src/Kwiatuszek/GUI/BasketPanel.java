package Kwiatuszek.GUI;

import Kwiatuszek.Kwiat;
import Kwiatuszek.Zamowienie;
import Kwiatuszek.Baza;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class BasketPanel extends JPanel {

    Godziny godziny;
    JCheckBox checkKarta;
    JButton btnWyslij;
    JButton btnWyczysc;
    static JLabel cena;
    static JLabel napis;

    public BasketPanel() {
        super();
        setLayout(new GridBagLayout());

        Box itemBox = new Box(BoxLayout.Y_AXIS);
        Box przyciski = new Box(BoxLayout.X_AXIS);

        napis = new JLabel("<html><font size=\"5\"> Twój koszyk jest pusty </font></html>");
        cena = new JLabel("");
        godziny = new Godziny();
        this.checkKarta = new JCheckBox("Płatność kartą"); // Zostawic this!!

        btnWyczysc = new JButton("Wyczyść koszyk");
        btnWyczysc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                clearBasket();
            }
        });

        btnWyslij = new JButton("Wyślij zamówienie");
        btnWyslij.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                sendOrder(); // Nowy Thread?
            }
        });

        checkKarta.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        napis.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        cena.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btnWyczysc.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btnWyslij.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        itemBox.add(napis);
        itemBox.add(Box.createRigidArea(new Dimension(0, 8)));
        itemBox.add(cena);
        itemBox.add(Box.createRigidArea(new Dimension(0, 15)));
        itemBox.add(godziny);
        itemBox.add(Box.createRigidArea(new Dimension(0, 8)));
        itemBox.add(checkKarta);
        itemBox.add(Box.createRigidArea(new Dimension(0, 8)));
        itemBox.add(btnWyczysc);
        itemBox.add(Box.createRigidArea(new Dimension(0, 8)));

        przyciski.add(btnWyczysc);
        itemBox.add(Box.createHorizontalGlue());
        przyciski.add(btnWyslij);
        itemBox.add(przyciski);

        add(itemBox);
    }

    public void clearBasket() {
        if (MainFrame.zamowienie.kwiaty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Koszyk jest pusty!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Koszyk został opróżniony", "Komunikat", JOptionPane.PLAIN_MESSAGE);
            MainFrame.zamowienie.kwiaty = new LinkedList<>();
            System.out.println(MainFrame.zamowienie.kwiaty);
            BasketPanel.showBasket();
        }
    }

    public void sendOrder(){ // TO DO: Refactoring!!!
        if(MainFrame.zamowienie.kwiaty.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nie można złożyć pustego zamówienia!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        else{
           try {
               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               Type token = new TypeToken<ArrayList<Zamowienie>>(){}.getType();

               // Ustawianie godziny zamowienia
               MainFrame.zamowienie.setGodzina(godziny.getGodzina());

               // Ustawianie platnosci karta zamowienia
               MainFrame.zamowienie.setKarta(checkKarta.isSelected());

               // Wczytywanie poprzednich zamowien z pliku
               Scanner scanner = new Scanner(new File("data/zamowienia2.json"));
               String text = "";
               while(scanner.hasNext()){
                   text += scanner.nextLine();
               }
               ArrayList<Zamowienie> stareZamowienia = gson.fromJson(text, token);
               MainFrame.zamowienie.setId(stareZamowienia.get(stareZamowienia.size()-1).getId()+1);
               stareZamowienia.add(MainFrame.zamowienie);

               // Nadpisiwanie pliku zamowien (z nowym zamowieniem)
               String json = gson.toJson(stareZamowienia, token);
               FileWriter writer = new FileWriter("data/zamowienia2.json");
               writer.write(json);
               writer.close();
               System.out.println(MainFrame.zamowienie);
           } catch (IOException e){
               e.printStackTrace();
           }

            MainFrame.siecKwiaciarni = new Baza(true);

            if(MainFrame.siecKwiaciarni.przydzielZamowienie(MainFrame.zamowienie)){
                MainFrame.zamowienie = new Zamowienie();
                JOptionPane.showMessageDialog(this, "Twoje zamówienie zostało złożone pomyślnie", "Komunikat", JOptionPane.PLAIN_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(this, "Twoje zamówienie nie jest możliwe do zrealizowania przez żaden ze sklepów", "Komunikat", JOptionPane.WARNING_MESSAGE);
            }
            BasketPanel.showBasket();
        }
    }

    public static void showBasket() {
        // napis oraz cena sa static :)
        LinkedHashMap<String, Integer> mapaKwiatow= new LinkedHashMap<>();
        for (Kwiat i: MainFrame.zamowienie.kwiaty){
            String kwiat = i.getRodzaj();
            int liczba = i.getIlosc();
            mapaKwiatow.put(kwiat, liczba);
        }

        String mapaKwiatowToString = mapaKwiatow.toString().substring(1,mapaKwiatow.toString().length()-1)
                .replaceAll(",","<br>").replaceAll("=",": ");

        if (MainFrame.zamowienie.kwiaty.isEmpty()) {
            napis.setText("<html><font size=\"5\"> Twój koszyk jest pusty </font></html>");
            cena.setText("");
        } else {
            napis.setText("<html> <font size=\"5\"> Twoje kwiaty: </font> <br>" + mapaKwiatowToString + "</html>");
            try {
                MainFrame.zamowienie.wyznaczKwote();
                // Formatowanie double zeby ladnie wygladala cena
                BigDecimal db = new BigDecimal(MainFrame.zamowienie.getKwota()).setScale(2, BigDecimal.ROUND_HALF_UP);
                cena.setText("<html> <font color=green> Cena = " + db.toString() + "$ </font> </html>");
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}

//    JButton odswiezKoszyk = new JButton("Odśwież koszyk");
//        odswiezKoszyk.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                LinkedHashMap<String, Integer> mapaKwiatow= new LinkedHashMap<>();
//                for (Kwiat i: MainFrame.zamowienie.kwiaty){
//                    String kwiat = i.getRodzaj();
//                    int liczba = i.getIlosc();
//                    mapaKwiatow.put(kwiat, liczba);
//                }
//
//                String mapaKwiatowToString = mapaKwiatow.toString().substring(1,mapaKwiatow.toString().length()-1)
//                        .replaceAll(",","<br>").replaceAll("=",": ");
//
//                if (MainFrame.zamowienie.kwiaty.isEmpty()) {
//                    napis.setText("Twój koszyk jest pusty");
//                } else {
//                    napis.setText("<html> Twoje kwiaty: <br>" + mapaKwiatowToString + "</html>");
//                }
//            }
//        });
//        itemBox.add(napis);
//        itemBox.add(odswiezKoszyk);
