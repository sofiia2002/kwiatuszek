package Kwiatuszek;

import java.io.*;
import java.util.*;

import com.google.gson.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Baza {

    private ArrayList<Sklep> sklepy = new ArrayList<>();
    private LinkedList<Zamowienie> zamowienia = new LinkedList<Zamowienie>();

    public Baza() {
        wczytajZamowienia("data/zamowienia.json");
        wczytajSklepy("data/magazyn.json");
    }

    public Baza(boolean flag){
        wczytajSklepy("data/magazyn.json");
    }

    /**
     *Metoda odczytuje plik z lista zamowien. Nastepnie wczytuje kazde z zamowien i przypisuje mu id, godzine,
     *liste kwiatow, czy bedzie platnosc karta. Z odczytanych zmiennych tworzy nowe zamowienie. Nastepnie kazde
     * zamowienie dodawane jest jako element do Listy this.zamowienia obiektu
     * @param sciezkaPliku sciezka pliku json z lista zamowien.
     */
    public void wczytajZamowienia(String sciezkaPliku) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(sciezkaPliku)) {
            //JSONObject jsonObject = (JSONObject) parser.parse(reader);
            //JSONArray zamowieniaWJSON = (JSONArray) jsonObject.get("zamowienia");
            JSONArray zamowieniaWJSON = (JSONArray) parser.parse(reader); //Sonya: plik JSON zostal zmienniony i dlatego wczyt w inny sposob
            for (int i = 0; i < zamowieniaWJSON.size(); i++) {
                JSONObject jsonObjectNoweZamowienie = (JSONObject) zamowieniaWJSON.get(i);// tutaj każde zamowienie
                                                                                          // tworzy nowy obiekt typu
                                                                                          // JSON

                long id = (Long) jsonObjectNoweZamowienie.get("id");
                boolean karta = (Boolean) jsonObjectNoweZamowienie.get("karta");
                long godzina = (Long) jsonObjectNoweZamowienie.get("godzina");

                LinkedList<Kwiat> listaKwiatow = new LinkedList<Kwiat>();
                JSONArray kwiatyWZamowieniu = (JSONArray) jsonObjectNoweZamowienie.get("kwiaty");
                for (int j = 0; j < kwiatyWZamowieniu.size(); j++) {
                    JSONObject jsonObjectKwiatyWZamoiweniu = (JSONObject) kwiatyWZamowieniu.get(j); // tutaj tworzy nowy
                                                                                                    // obiekt dla kżdego
                                                                                                    // kwiatka w każdym
                                                                                                    // zamowieniu
                    String rodzaj = (String) jsonObjectKwiatyWZamoiweniu.get("rodzaj");
                    long ilosc = (Long) jsonObjectKwiatyWZamoiweniu.get("ilosc");
                    listaKwiatow.add(new Kwiat(rodzaj, (int) ilosc));

                }
                Zamowienie zamowienie = new Zamowienie((int) id, listaKwiatow, karta, (int) godzina);
                zamowienia.add(zamowienie);
                Log.zlozonoZamowienie(zamowienie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

     /**
     * Metoda przypisuje zamowienie sklepu (przy wykorzystaniu GUI).
     */
    public boolean przydzielZamowienie(Zamowienie zamowienie){
        try {
            Log.zlozonoZamowienie(zamowienie);
            for (Sklep sklep : sklepy) {
                if (sklep.sprawdzSklep(zamowienie)) {
                    sklep.przypiszZamowienie(zamowienie);
                    Log.przydzielonoDoSklepu(zamowienie, sklep);
                    update();
                    return true;
                }
            }
            Log.nieprzydzieloneZamowienia(zamowienie);
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metoda, ktora sczytuje obiekty jsonowe z pliku, a nastepnie za ich pomoca zmienia zawartosc atrybutu
     * this.sklepy.
     * @param sciezkaPliku sciezka pliku json, w ktorym zapisane sa sklepy.
     */
    public void wczytajSklepy(String sciezkaPliku) {
        try {
            this.sklepy = new Gson().fromJson(new FileReader(sciezkaPliku), new TypeToken<ArrayList<Sklep>>(){}.getType());
            System.out.println(sklepy);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metoda przypisuje wszystkie zamowienia sklepom (przy wykorzystaniu pliku z zamowieniami).
     */
    public void przydzielZamowienia(){
        try {
            for (Zamowienie zamowienie : zamowienia) {
                outerloop:
                for (Sklep sklep : sklepy) {
                    if (sklep.sprawdzSklep(zamowienie)) {
                        sklep.przypiszZamowienie(zamowienie);
                        Log.przydzielonoDoSklepu(zamowienie, sklep);
                        break outerloop;
                    }
                }
                if(!zamowienie.przydzielono){
                    Log.nieprzydzieloneZamowienia(zamowienie); // komunikat
                } else {
                    update();
                    Collections.sort(sklepy); // Sortowanie wzgledem ilosci zamowien sklepu (najwiecej zamowien - koniec listy, najmniej zamowien - poczatek listy)
                }
            }
            System.out.println(sklepy);
            PrintWriter writer = new PrintWriter("data/zamowieniaŻtest.json");
            writer.print("");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metoda uaktualnia plik jsonowym ze stanem sklepow.
     */
    public void update() throws JsonSyntaxException, JsonIOException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(sklepy, new TypeToken<ArrayList<Sklep>>(){}.getType());
        FileWriter writer = new FileWriter("data/magazyn.json"); //Sonya: Przetestowane, mozna zmieniac na magazyn.json
        writer.write(json);
        writer.close();
    }

    /**
     * Metoda uaktualnia plik jsonowym z zamowieniami.
     */
    public void updateZamowienia(ArrayList<Zamowienie> zamow) throws JsonSyntaxException, JsonIOException, IOException { // Co z tym zrobić?
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(zamow, new TypeToken<ArrayList<Zamowienie>>(){}.getType());
        FileWriter writer = new FileWriter("data/GUItest.json"); //Sonya: Przetestowane, mozna zmieniac na magazyn.json
        writer.write(json);
        writer.close();
    }

    public static void main(String args[]){
        Baza test = new Baza();
        test.przydzielZamowienia();
    }
}

