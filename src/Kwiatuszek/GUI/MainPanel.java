package Kwiatuszek.GUI;

import javax.swing.*;

public class MainPanel extends JPanel {
    public MainPanel(){
        super();
        String text = "<br>" + "<br>" + "<br>" + "<br>" + "Dzień dobry!" + "<br>" + "<br>"
                + "Witamy w sieci kwiaciarni 'Kwiatuszek!'" + "<br>" + "<br>"
                + "Żeby zlożyć zamówienie proszę przejść" + "<br>" + "do zakładki 'Zamówienie'" + "<br>"
                + "Żeby wysłać zamówienie proszę przejść"+ "<br>"
                + "do zakładki 'Koszyk'" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>"
                + "<br>" + "<br>" + "<br>" + "<div>Icon made by Freepik from www.flaticon.com</div>"+
                  "<div>Background made by EvaBlue from www.evablue.com</div>";
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
        add(label);
    }
}
