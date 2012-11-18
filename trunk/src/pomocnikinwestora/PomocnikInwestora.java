package pomocnikinwestora;

import java.util.concurrent.*;
/**
 *
 * @author kasia
 */
public class PomocnikInwestora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //final Ustawienia ustawienia = Ustawienia.pobierzInstancje();
        //ustawienia.wczytaj();
        final Ustawienia ustawienia = new Ustawienia();
        final Model model = new Model(ustawienia);
        final GUI gui = new GUI();
        final Kontroler kontroler = new Kontroler(gui, ustawienia);

        // TODO test ile
        final ExecutorService exec = Executors.newFixedThreadPool(8);
        gui.set(model, ustawienia, kontroler, exec);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.StworzIPokaz();
            }
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO
                //ustawienia.zapisz();
            }
        }));
        
        Database.load();
		
		System.out.println(Commodity.get(4).name);
		System.out.println(Commodity.get(4).min());
		System.out.println(Commodity.get(4).max());
		System.out.println(Commodity.get(4).average());
		System.out.println(Commodity.get(4).getClose(1000));
		System.out.println(Commodity.get(4).value);
    }
}
