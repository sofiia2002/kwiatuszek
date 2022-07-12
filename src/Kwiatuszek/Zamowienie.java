package Kwiatuszek;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import com.google.gson.*;

public class Zamowienie {
    private int id;
    private double kwota;
    private boolean karta;
    private int godzina;
    public LinkedList<Kwiat> kwiaty = new LinkedList<Kwiat>();
    public boolean przydzielono = false;
    //private Kwiatuszek.Coordinates coords = null;

    public int getGodzina(){ return this.godzina; }
    public double getKwota() { return this.kwota; }
    public int getId() { return this.id; }
    public boolean getKarta() { return this.karta; }

    public void setKwota(double kwota) { this.kwota = kwota; }
    public void setId(int id) { this.id = id; }
    public void setKarta(boolean karta) { this.karta = karta; }
    public void setGodzina(int godzina) { this.godzina = godzina; }

    public Zamowienie(int id, LinkedList kwiaty, double kwota, boolean karta, int godzina) {
        this.id = id;
        this.kwiaty = kwiaty;
        this.kwota = kwota;
        this.karta = karta;
        this.godzina = godzina;
    }

    public Zamowienie(int id, LinkedList kwiaty, boolean karta, int godzina)
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        this.id = id;
        this.kwiaty = kwiaty;
        this.karta = karta;
        this.godzina = godzina;
        wyznaczKwote(); //Sonya: dodalam automatycznie wyznaczanie kwoty
    }

    public Zamowienie() {}

    public void wyznaczKwote() throws JsonSyntaxException, JsonIOException, FileNotFoundException { //lub rowniez wczytujemy cennik i wtedy zmieniamy troche tej metody
        // MOZE DAJMY TA METODE DO KLASY ZAMOWIENIE :D ~Janek
        // Sonya: Kuba, dodalam tej metody z Bazy, mam nadzieje ze nie jestes przeciwko
        double kwota = 0;
        Gson gson = new Gson(); // podlega zmianie przy wczytaniu cenniku
        Cennik[] cenniki;// podlega zmianie przy wczytaniu cenniku
        cenniki = gson.fromJson(new FileReader("data/cennik.json"), Cennik[].class); // podlega zmianie przy wczytaniu cenniku
        for(Cennik cennik : cenniki){
            for(Kwiat kwiat: kwiaty){
                if (kwiat.getRodzaj().equals(cennik.getRodzaj())) {
                    kwota = kwota + cennik.getCena()*kwiat.getIlosc();
                }
            }
        }
        this.kwota = kwota;
    }

    @Override
    public String toString() {
        return "\nZamowienie:" + "\nid = " + id + ", \nkwiaty = " + kwiaty + ", \nkwota = " + kwota + ", \nkarta = "
                + karta + ", \ngodzina = " + godzina + "\n";
    }
}
