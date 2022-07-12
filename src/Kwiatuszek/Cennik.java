package Kwiatuszek;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Cennik {
    // Klasa kopiująca wygląd pliku JSON
    String Rodzaj;
    double Cena;



    // Konstruktor pusty klasy Kwiatuszek.Cennik
    public Cennik() throws JsonSyntaxException, JsonIOException, FileNotFoundException {}


   //* Gettery klasy Kwiatuszek.Cennik *//

    public String getRodzaj(){
        return Rodzaj;
    }

    public double getCena(){
        return Cena;
    }

    //***********************//


    /**
    * 
    * Metoda wypisujaca wszystkie dane o cenie
    * 
    */
    @Override
    public String toString() {
        return "[ Rodzaj: " + String.valueOf(Rodzaj) + ", Cena: " + String.valueOf(Cena) + " ]";
    }


    /**
    * 
    * Metoda testujaca
    * 
    */
    public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
      Gson gson = new Gson();
      Cennik[] cenniki = gson.fromJson(new FileReader("data/cennik.json"), Cennik[].class);
      for(Cennik cennik : cenniki)
        System.out.println(cennik.toString());
    }
}