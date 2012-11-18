package pomocnikinwestora;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 * @author kasia
 */
public class Ustawienia {
    private String nazwa;
    private String instrument;
    private Date dataOd;
    private Date dataDo;
    private String kolor;
    private String okres;
    private String typWykresu;
    private Boolean siatka;
    private HashMap<String, ArrayList<String>> ulubione;
    
    // TODO sprawdzić, jaka jest najwcześniejsza i najpóźniejsza data
    private static Date minData = new Date(671752800);  //1991 04 16;
    private static Date maxData = new Date();
    
    public Ustawienia() {
        // TODO wczytywanie
        ustawDomyslne();
    }
    
    public void zapisz() {
        
    }
    
    public void wczytaj() {
        
    }
    
    private void ustawDomyslne() {
        dataDo = new Date();
        dataOd = new Date(dataDo.getTime() - 14515200000L); // 86400000 * 28 * 6 = 6 miesięcy
        instrument = Model.instrumenty[0];
        nazwa = null;
        kolor = "red";
        okres = "Tygodniowe";
        typWykresu = "Słupkowy";
        siatka = false;
        ulubione = new HashMap<String, ArrayList<String>>();
        ulubione.put(Model.instrumenty[0], new ArrayList<String>()); // indeksy
        ulubione.put(Model.instrumenty[1], new ArrayList<String>()); // waluty
        ulubione.put(Model.instrumenty[2], new ArrayList<String>()); // towary
    }
    
    public boolean czyUlubiony(String nazwa, String instrument) {
        return ulubione.get(instrument).contains(nazwa);
    }

    public void dodajDoUlubionych(String nazwa, String instrument) {
        ulubione.get(instrument).add(nazwa);
    }

    public void usunZUlubionych(String nazwa) {
        int index;
        for (String key : ulubione.keySet()) {
            index = ulubione.get(key).indexOf(nazwa);
            if (index >= 0) {
                ulubione.get(key).remove(index);
            }
        }
    }

    // gettery
    public String getNazwa() {
        return nazwa;
    }

    public String getInstrument() {
        return instrument;
    }

    public Date getDataOd() {
        return dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }

    public String getDataOd(String s) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(dataOd);
        return Model.utworzDate(c, (s != null ? s : "Ymd") );
    }

    public String getDataDo(String s) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(dataDo);
        return Model.utworzDate(c, (s != null ? s : "Ymd") );
    }

    public String getKolor() {
        return kolor;
    }

    public String getOkres() {
        return okres;
    }

    public String getTypWykresu() {
        return typWykresu;
    }

    public Boolean getSiatka() {
        return siatka;
    }
    
    public HashMap<String, ArrayList<String>> getUlubione() {
        return ulubione;
    }

    public ArrayList<String> getUlubione(String instrument) {
        return ulubione.get(instrument);
    }
    
    public ArrayList<String> getUlubioneSuma() {
        ArrayList<String> tmp = new ArrayList<String>();
        for(String k: ulubione.keySet()) {
            tmp.addAll(ulubione.get(k));
        }
        return tmp;
    }
    
    public Date getMinData() {
        return minData;
    }
    
    public Date getMaxData() {
        return maxData;
    }

    // settery
    public void setNazwa(String _nazwa) {
        nazwa = _nazwa;
    }

    public void setInstrument(String _instrument) {
        instrument = _instrument;
    }

    public void setDataOd(Date _dataOd) {
        dataOd = _dataOd;
    }

    public void setDataDo(Date _dataDo) {
        dataDo = _dataDo;
    }

    public void setKolor(String _kolor) {
        kolor = _kolor;
    }

    public void setOkres(String _okres) {
        okres = _okres;
    }

    public void setTypWykresu(String _typWykresu) {
        typWykresu = _typWykresu;
    }

    public void setSiatka(Boolean _siatka) {
        siatka = _siatka;
    }
    
    public void setUlubione(HashMap<String, ArrayList<String>> _ulubione) {
        ulubione = _ulubione;
    }

    public void setDataOd(String _dataOd) {
        GregorianCalendar c = new GregorianCalendar();
        c.set(Integer.valueOf(_dataOd.substring(0, 4)), Integer.valueOf(_dataOd.substring(4, 6)) - 1, Integer.valueOf(_dataOd.substring(6, 8)));
        dataOd = new Date(c.getTimeInMillis());
    }

    public void setDataDo(String _dataDo) {
        GregorianCalendar c = new GregorianCalendar();
        c.set(Integer.valueOf(_dataDo.substring(0, 4)), Integer.valueOf(_dataDo.substring(4, 6)) - 1, Integer.valueOf(_dataDo.substring(6, 8)));
        dataDo = new Date(c.getTimeInMillis());
    }
    
    public void setDataOd(long milis) {
        dataOd.setTime(dataOd.getTime() + milis);
    }
    
    public void setDataDo(long milis) {
        dataDo.setTime(dataDo.getTime() + milis);
    }
    
    public void setMinData(String d) {
        minData = Model.utworzDate(d);
    }
    
    // zmienia datę o x ms
    public void setData(long milis) {
        setDataOd(milis);
        setDataDo(milis);
    }
        
    // mnożnik do przeliczania dzienne-tygodniowe-miesięczne
    public int getMnoznik() {
        int mnoznik = 0;
        if (getOkres().equals("Tygodniowe")) {
            mnoznik = 7;
        } else if (getOkres().equals("Dzienne")) {
            mnoznik = 1;
        } else if (getOkres().equals("Miesięczne")) {
            mnoznik = 28; 
        }
        return mnoznik;
    }
}
