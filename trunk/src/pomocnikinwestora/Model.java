package pomocnikinwestora;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author kasia
 */
public class Model {
    public static final String[] instrumenty = {"Indeksy giełdowe", "Waluty", "Towary", "Ulubione"};
    public static final String[] listaWalut = new String[]{"usd", "pln", "jpy", "chf", "cad", "aud", "brl", "rub"};
    public static final String[] listaTowarow = new String[]{"ropa", "gaz", "złoto", "srebro", "miedź", "soja", "pszenica", "kukurydza"};
    private Ustawienia u;
    
    public Model(Ustawienia _u) {
        u = _u;
    }
    
    public static String[] getInstrumenty() { return instrumenty; }
    
    public static String[] pobierzListeInstrumentow(Ustawienia u, String s) {
        if (s.equals(instrumenty[0])) { // Indeksy giełdowe
            return listaIndeksow();
        } else if (s.equals(instrumenty[1])) { // Waluty
            return listaWalut();
        } else if (s.equals(instrumenty[2])) { // Towary
            return listaTowarow();
        } else if (s.equals(instrumenty[3])) { // Ulubione
            return u.getUlubioneSuma().toArray(new String[0]);
            //return u.getUlubione(u.getInstrument()).toArray(new String[0]);
        } else {
            return new String[]{};
        }
    }
    
    private static String[] listaIndeksow() {
        // TODO
        return new String[]{"instrument testowy 1","instrument testowy 2" , "instrument testowy 3"};
    }
    
    private static String[] listaWalut() {
        return Model.listaWalut;
    }
    
    private static String[] listaTowarow() {
        return Model.listaTowarow;
    }
    
    public static String utworzDate(GregorianCalendar c, String format) {
        StringBuilder b = new StringBuilder();
        for (Character ch : format.toCharArray()) {
            switch (ch) {
                case 'Y':
                    b.append(c.get(Calendar.YEAR));
                    break;
                case 'm':
                    Integer sm = c.get(Calendar.MONTH) + 1;
                    b.append(sm <= 9 ? "0" + sm : sm);
                    break;
                case 'd':
                    Integer sd = c.get(Calendar.DAY_OF_MONTH);
                    b.append(sd <= 9 ? "0" + sd : sd);
                    break;
                default:
                    b.append(ch);
                    break;
            }
        }
        return b.toString();
    }
    
    public static Date utworzDate(String s) {
        GregorianCalendar c = new GregorianCalendar();
        int miesiac = Integer.parseInt(s.substring(4, 6));
        c.set(
                Integer.parseInt(s.substring(0, 4)), 
                miesiac > 1 ? miesiac-1 : 11, 
                Integer.parseInt(s.substring(6)));
        
        return new Date(c.getTimeInMillis());
    }
}
