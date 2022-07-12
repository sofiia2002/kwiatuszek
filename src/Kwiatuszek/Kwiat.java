package Kwiatuszek;

import java.util.Objects;

public class Kwiat {
    private String rodzaj;
    private int ilosc;

    public String getRodzaj() { return rodzaj; }
    public int getIlosc() { return ilosc; }


	public void setIlosc(int i) {
        ilosc = i;
	}

    public Kwiat (String rodzaj, int ilosc){
        this.rodzaj = rodzaj;
        this.ilosc = ilosc;
    }

    public Kwiat(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kwiat kwiat = (Kwiat) o;
        return ilosc == kwiat.ilosc &&
                Objects.equals(rodzaj, kwiat.rodzaj);
    }

    @Override
    public int hashCode() { return Objects.hash(rodzaj, ilosc); }

    @Override
    public String toString(){ return rodzaj + ilosc; }

}
