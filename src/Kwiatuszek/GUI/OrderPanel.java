package Kwiatuszek.GUI;

import Kwiatuszek.Kwiat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;


public class OrderPanel extends JPanel {

//    Integer index = 0;
//    Boolean karta = true;
//    int godzina = 0;


    public OrderPanel(){

        super();
            String text = "<br>" + "<br>" + "Wybierz kwiaty z których chcesz zrobić bukiet: " + "<br>" + "<br>" + "<br>";
            ;

            //Odpowiednie napisy do kazdego z pol
            JLabel label = new JLabel("<html>" + text + "</html>");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
    
            JLabel labelKwiat = new JLabel("Wybierz kwiat:");
            labelKwiat.setAlignmentX(Component.CENTER_ALIGNMENT);
        
            JLabel labelIlosc = new JLabel("Napisz ilość cyfrą:");
            labelIlosc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
            // Dodanie wyboru kwitow roznego rodzaju
            JComboBox<String> jcb = new JComboBox<String>();
            jcb.addItem("Tulipan");
            jcb.addItem("Stokrotka");
            jcb.addItem("Róża");
            jcb.addItem("Lilia");

//            jcb.addItem("Gerbera");
//            jcb.addItem("Margaretka");
//            jcb.addItem("Goździk");
//            jcb.addItem("Słonecznik");
//            jcb.addItem("Santini");
//            jcb.addItem("Anturium");
//            jcb.addItem("Frezja");
//            jcb.addItem("Eustoma");


            // Dodanie pola do wpisywania ilosci oraz obsluga wpisywania niepoprawnych wartosci
            NumberFormat format = NumberFormat.getInstance();
            format.setGroupingUsed(false);
            NumberFormatter formatter = new NumberFormatter(format);
            JFormattedTextField jt = new JFormattedTextField(formatter);
            jt.setText("1");
            formatter.setValueClass(Integer.class);
            formatter.setMinimum(1);
            formatter.setAllowsInvalid(false);
        
            // Dodanie przyciska dodawania kwiatu do bukietu
            JButton jbtnKwiat = new JButton("Dodaj kwiat do bukietu");
            jbtnKwiat.setAlignmentX(Component.CENTER_ALIGNMENT);
        
            // Sluchac przycisku dodawania kwiatu do bukietu
            jbtnKwiat.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    boolean wynik = true;
        
                        Kwiat kwiat = new Kwiat((String) jcb.getSelectedItem(), Integer.parseInt(jt.getText()));
        
                        // Sprawdzenie, czy taki kwiat juz istnieje w bukiecie (jezeli tak, to dodaje ilosci istniejacego i dodawanego
                        // a jezeli nie, to dodaje kwiat nowego rodzaju)
                         if (!MainFrame.zamowienie.kwiaty.isEmpty()) {
                         for (Kwiat kwiatuszek : MainFrame.zamowienie.kwiaty) {
                                 if ((kwiatuszek.getRodzaj().equals((String) jcb.getSelectedItem()))) {
                                    kwiatuszek.setIlosc(kwiatuszek.getIlosc() + Integer.parseInt(jt.getText()));
                                    wynik = false;
                                }
                            }
                        }
                        if (wynik) {
                            MainFrame.zamowienie.kwiaty.add(kwiat);
                        }

                        //Uaktualnianie koszyka
                        System.out.println("Aktualny koszyk: " + MainFrame.zamowienie.kwiaty.toString());
                        jt.setText("");
                        BasketPanel.showBasket();
                    }
            });
            
                
            // Dodawanie VerticalBox dla lepszego wygladu wkladki
            Box itemBox = Box.createVerticalBox();

            // Dodawanie pustych miejsc dla wstawiania miedzy komponentami
            JLabel labelBlank = new JLabel("<html><br></html>");
            JLabel labelBlank0 = new JLabel("<html><br></html>");
            JLabel labelBlank2 = new JLabel("<html><br></html>");
            JLabel labelBlank3 = new JLabel("<html><br></html>");
        

            // Dodanie marginesu lewego dla wszystkich elementow
            add(Box.createRigidArea(new Dimension(20, 20)));

            // Dodawanie poszczegolnych komponentow do VerticalBox
            itemBox.add(labelBlank);
            itemBox.add(label);
            itemBox.add(labelBlank2);
            itemBox.add(labelKwiat);
            itemBox.add(jcb);
            itemBox.add(labelBlank0);
            itemBox.add(labelIlosc);
            itemBox.add(jt);
            itemBox.add(labelBlank3);
            itemBox.add(jbtnKwiat);
        
            //Dodanie VerticalBox do zakladku
            add(itemBox);

            // Dodanie marginesu prawego dla wszystkich elementow
            add(Box.createRigidArea(new Dimension(20, 20)));
        
    }
    
}
