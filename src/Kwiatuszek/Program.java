package Kwiatuszek;

import javax.swing.*;
import Kwiatuszek.GUI.MainFrame;

public class Program {

    public static void main(String[] args){
        try{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MainFrame();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("TEST");
      }
}