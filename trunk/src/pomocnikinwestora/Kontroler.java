package pomocnikinwestora;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.AbstractButton;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author kasia
 */
public class Kontroler {
    GUI gui;
    Ustawienia ustawienia;
    
    Kontroler(GUI g, Ustawienia u) {
        gui = g;
        ustawienia = u;
    }
    
    class PrzyciskUlubionych implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (((AbstractButton) actionEvent.getSource()).getModel().isSelected()) {
                ustawienia.dodajDoUlubionych(ustawienia.getNazwa(), ustawienia.getInstrument());
            } else {
                ustawienia.usunZUlubionych(ustawienia.getNazwa());
                gui.odswiezUlubione();
                // TODO jeśli jesteśmy w zakładce ulu, odśwież
            }
        }
    }
    
    class UstawDateOd implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String data = ((JTextField) actionEvent.getSource()).getText();
            Date ustawiana = Model.utworzDate(data);
            if(ustawiana.before(ustawienia.getMaxData()) && ustawiana.after(ustawienia.getMinData()) 
                    && ustawiana.before(ustawienia.getDataDo()) ) {
                ustawienia.setDataOd( ((JTextField) actionEvent.getSource()).getText() );
                gui.odswiez();
            }
        }
    }
    
    class UstawDateDo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String data = ((JTextField) actionEvent.getSource()).getText();
            Date ustawiana = Model.utworzDate(data);
            if(ustawiana.before(ustawienia.getMaxData()) && ustawiana.after(ustawienia.getMinData()) 
                    && ustawiana.after(ustawienia.getDataOd()) ) {
                ustawienia.setDataDo(data);
                gui.odswiez();
            }
        }
    }
    
    class WybierzPozycjeListy implements ListSelectionListener {
        private JList lista;
        private JToggleButton ulubioneButton;
        
        WybierzPozycjeListy(JList l, JToggleButton ulb) {
            lista = l;
            ulubioneButton = ulb;
        }
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                return;
            }
            
            String selected = (String) lista.getSelectedValue();
            String instrument = ustawienia.getInstrument();

            if (instrument.equals("Ulubione")) {
                ulubioneButton.setSelected(true);
            }
            if (selected != null) {
                ustawienia.setNazwa(selected);
                //gui.pobierzModel().setModel(ustawienia);
                if (!instrument.equals("Ulubione")) {
                    ulubioneButton.setSelected(ustawienia.czyUlubiony(selected, instrument));
                }
                gui.odswiez();
            }
        }
    }
    
    class WybierzZMenuRozwijanego implements ActionListener {
        private JList lista;
        
        WybierzZMenuRozwijanego(JList l) {
            lista = l;
        }
        
        @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selected = (String) ((ItemSelectable) actionEvent.getSource()).getSelectedObjects()[0];
                lista.setListData(Model.pobierzListeInstrumentow(ustawienia, selected));
                ustawienia.setInstrument(selected);
        }
    }

    class OdswiezWykres implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            gui.odswiez();
        }
    }
    
    class ZmienTypWykresu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ustawienia.setTypWykresu((String) ((ItemSelectable) actionEvent.getSource()).getSelectedObjects()[0]);
            gui.odswiez();
        }
    }
    
    class UstawOkres implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ustawienia.setOkres((String) ((ItemSelectable) actionEvent.getSource()).getSelectedObjects()[0]);
            gui.odswiez();
        }
    }
    
    class PokazSiatke implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ustawienia.setSiatka(((AbstractButton) actionEvent.getSource()).getModel().isSelected());
            gui.odswiez();
        }
    }
    
    class WidocznoscMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            gui.widocznoscMenu();
        }
    }
    
    class OkresWPrzod implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ustawienia.setData(ustawienia.getMnoznik() * 86400000);  //24 * 60 * 60 * 100;
            gui.odswiezDaty();
            gui.odswiez();
        }
    }
    
    class OkresWTyl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ustawienia.setData(ustawienia.getMnoznik() * -86400000);  //24 * 60 * 60 * 100;
            gui.odswiezDaty();
            gui.odswiez();
        }
    }
}
