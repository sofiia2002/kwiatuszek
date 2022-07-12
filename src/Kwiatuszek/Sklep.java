package Kwiatuszek;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Sklep implements Comparable {
  // Klasa kopiująca wygląd pliku JSON
  public int id;
  public int iloscZamowien = 0;
  public ArrayList<HashMap<String, String>> magazyn;
  public ArrayList<HashMap<String, Integer>> godzinyOtwarcia;
  public ArrayList<Integer> dostepneGodziny;


  /**
   * Metoda wczytuje podane zamowienie i sprawdza, czy podany sklep w stanie jego wykonac
   * Sprawdzenie polega na obejrzeniu magazynu sklepu (czy sa te kwiaty 
   * w tym sklepie i czy jest ich wystarczajaca ilosc)
   * 
   * @return True, jezeli sklep jest w stanie obsluzyc zamowienie
   * @return False, jezeli sklep niu jest w stanie obsluzyc zamowienie
   */
  public Boolean sprawdzSklep(Zamowienie zamowienie) {
    int n = 0;
    for (HashMap<String, String> magazyny: magazyn) {
      for (Kwiat kwiat: zamowienie.kwiaty) {
        if (magazyny.get("rodzaj").equals(kwiat.getRodzaj())) {
          if ((Integer.valueOf(magazyny.get("ilosc"))) > kwiat.getIlosc()) {
            n++;
          }
      }
    }
    if (n == zamowienie.kwiaty.size()) {
      for (Integer godzina: dostepneGodziny) {
        if (godzina == zamowienie.getGodzina()) {
          return true;
        }
      }
    }
  }
  return false;
  }


   /**
    * 
   * Metoda wczytuje podane zamowienie i wykonuje go
   * (Metoda ta jest wywolywana po sprawdzeniu, czy sklep moze obsluzyc dane zamowienie)
   * 
   * Wykonanie zamowienia polega na zmianie zawartosci magazynu oraz dostepnych godzin
   * rezerwacji w obiekcie sklepu
   * 
   */

  public void przypiszZamowienie(Zamowienie zamowienie){
    int ilosc;
    this.iloscZamowien++;
    dostepneGodziny.remove((Integer) (zamowienie.getGodzina()));
    for (HashMap<String, String> magazyny: magazyn) {
      for (Kwiat kwiat: zamowienie.kwiaty) {
        if (magazyny.get("rodzaj").equals(kwiat.getRodzaj())) {
          ilosc = Integer.valueOf(magazyny.get("ilosc")) - kwiat.getIlosc();
          magazyny.replace("ilosc", String.valueOf(ilosc));
        }
      }
    }
    zamowienie.przydzielono = true;
  }


  /**
   *
  * Metoda wypisujaca wszystkie dane o sklepie jako String
   *
  */
  @Override
  public String toString() {
    return "[ ilosc zamowien: " + String.valueOf(iloscZamowien)
            + ", id: " + String.valueOf(id)
            + ", godziny otwarcia: " + String.valueOf(godzinyOtwarcia)
            + ", dostepne godziny: " + String.valueOf(dostepneGodziny)
            + ", magazyn: " + String.valueOf(magazyn) + " ]";
  }

  /**
   *
   * Metoda wykorzystywana do porownywania obiektow Kwiatuszek.Sklep w kolekcji za pomocja atrybutu iloscZamowien
   *
   */
  @Override
  public int compareTo(Object o) {
    int compare = ((Sklep) o).iloscZamowien;
    return this.iloscZamowien - compare;
  }

  /**
  * 
  * Metoda testujaca
  * 
  */
    public static void main(String[] args) throws JsonSyntaxException, JsonIOException, IOException {
      Gson gson = new Gson();
      Sklep[] sklepy = gson.fromJson(new FileReader("data/magazyn.json"), Sklep[].class);
      for(Sklep sklep : sklepy)
        System.out.println(sklep.toString());

    }
}

