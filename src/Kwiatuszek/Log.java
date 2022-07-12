package Kwiatuszek;

import java.io.*;

// Sonya: na MacOS path.separator dziala OK
public class Log { // Zamienilem na static. Wieksza przejrzystosc w kodzie gdy piszemy np Kwiatuszek.Log.przydzielonoDoSklepu() niz samo przydzielonoDoSklepu()

    public static String logFilePath = "data/log.txt";

    public static void zlozonoZamowienie(Zamowienie zamowienie) throws IOException { // Sonia daj znac czy masz problemy z System.getProperty("line.separator") przez MacOS
        BufferedWriter zapis = new BufferedWriter(new FileWriter(new File(logFilePath), true));
        zapis.write(String.format("Zlozono zamowienie #%s."+zamowienie.toString()+ System.getProperty("line.separator"),zamowienie.getId()));//Sonya: dodalam wypisanie danych o zamowieniu do Loga
        zapis.close();
    }

    public static void przydzielonoDoSklepu(Zamowienie zamowienie, Sklep sklep) throws IOException { // Sonia daj znac czy masz problemy z System.getProperty("line.separator") przez MacOS
        BufferedWriter zapis = new BufferedWriter(new FileWriter(new File(logFilePath), true));
        zapis.write(String.format("Zamowienie #%s przydzielono do sklepu #%s" + System.getProperty("line.separator"), zamowienie.getId(), sklep.id));
        zapis.close();
    }

    public static void nieprzydzieloneZamowienia(Zamowienie zamowienie) throws IOException { // Sonia daj znac czy masz problemy z System.getProperty("line.separator") przez MacOS
        BufferedWriter zapis = new BufferedWriter(new FileWriter(new File(logFilePath), true));
        zapis.write(String.format("Zamowienie #%s nie jest możliwe do wykonania przez żaden ze sklepów" + System.getProperty("line.separator"), zamowienie.getId())); //Sonya: metoda sprawdzajaca sklepy uwzglednia rowniez godziny rezerwacji, dlatego "Brak dostepnych kwiatow" nie do konca pasuje w tym przypadku
        zapis.close();
    }

    public static void innyBlad() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter(logFilePath);
        zapis.println("Wystapil blad");
        zapis.close();
    }

    public static void stworzenieRezerwacji(Zamowienie zamowienie) throws IOException {
        BufferedWriter zapis = new BufferedWriter(new FileWriter(new File(logFilePath), true));
        zapis.write(String.format("Zostala stworzona rezerwacja dla zamowienia #%s", zamowienie.getId()));
        zapis.close();
    }

}
