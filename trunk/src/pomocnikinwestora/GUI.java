package pomocnikinwestora;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

/**
 *
 * @author kasia
 */
public class GUI extends JFrame {
    private Model model;
    private Kontroler kontroler;
    private Ustawienia ustawienia;
    private ExecutorService exec;
    
    private JLabel          wykres      = new JLabel(new ImageIcon("placeholder.png"));
    private JToolBar        toolBar     = new JToolBar();
    private JPanel          menu        = new JPanel(new BorderLayout());
    private JPanel          daty        = new JPanel();
    private JTextField      dataOd      = new JTextField();
    private JTextField      dataDo      = new JTextField();
    //private JSpinner        dataOd      = new JSpinner();
    //private JSpinner        dataDo      = new JSpinner();
    private JLabel          dataOdLabel = new JLabel("Od");
    private JLabel          dataDoLabel = new JLabel("do");
    private JPanel          lmenu       = new JPanel(new BorderLayout());
    private JComboBox       typListy;
    private JList           lista       = new JList();
    private JLabel          komunikaty  = new JLabel("Witaj w programie Pomocnik Inwestora!");
    
    private JToggleButton   ulubioneButton;
    private JScrollPane     scrollpane;
    //private SpinnerDateModel    dataOdModel;
    //private SpinnerDateModel    dataDoModel;
    
    

    void set(Model m, Ustawienia u, Kontroler k, ExecutorService es) {
        model = m;
        ustawienia = u;
        kontroler = k;
        exec = es;
    }
    
    Model       pobierzModel()      { return model; }
    Ustawienia  pobierzUstawienia() { return ustawienia; }
    

    void StworzIPokaz() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("Pomocnik Inwestora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension wymiary = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(wymiary);

        ulubioneButton = makeToggleButton("ulubione", "Zaznacz jako ulubiony", "Ulubiony", false, kontroler.new PrzyciskUlubionych());
        dodajPrzyciski();
        
        toolBar.setFloatable(false);
        
        dataOd.setColumns(6);
        dataDo.setColumns(6);
        
        odswiezDaty();
        
        scrollpane = new JScrollPane(lista);
        
        lista.setListData(Model.pobierzListeInstrumentow(ustawienia, ustawienia.getInstrument()));
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (ustawienia.getNazwa() != null) {
            lista.setSelectedValue(ustawienia.getNazwa(), true);
        }
 
        podepnijAkcje();
        pozycjonujElementy();
        
        pack();
        setVisible(true);
        setSize(1440, 900);
        setLocationRelativeTo(null);
    }
    
    private void pozycjonujElementy() {
        daty.add(dataOdLabel);
        daty.add(dataOd);
        daty.add(dataDoLabel);
        daty.add(dataDo);
        menu.add(daty, BorderLayout.NORTH);
        menu.add(lmenu, BorderLayout.CENTER);
        
        toolBar.add(ulubioneButton, toolBar.getComponentCount());
              
        typListy = makeComboBox(Model.getInstrumenty(), ustawienia.getInstrument(), 
                    kontroler.new WybierzZMenuRozwijanego(lista));
        lmenu.add(typListy, BorderLayout.NORTH);
        lmenu.add(scrollpane, BorderLayout.CENTER);
        
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(menu, BorderLayout.WEST);
        getContentPane().add(wykres, BorderLayout.CENTER);
        getContentPane().add(komunikaty, BorderLayout.SOUTH);
    }
    
    private void podepnijAkcje() {
        dataOd.addActionListener(kontroler.new UstawDateOd());
        dataDo.addActionListener(kontroler.new UstawDateDo());
        lista.addListSelectionListener(kontroler.new WybierzPozycjeListy(lista, ulubioneButton));
    }

    private void dodajPrzyciski() {
        if (toolBar.getComponentCount() == 0) {
            toolBar.add(makeButton("odswiez", "Odśwież wykres", "Odśwież", 
                    kontroler.new OdswiezWykres()));
            toolBar.add(makeButton("wtyl", "Poprzedni okres", "Cofnij", 
                    kontroler.new OkresWTyl() ));
            toolBar.add(makeButton("wprzod", "Następny okres", "Dalej", 
                    kontroler.new OkresWPrzod() ));
            toolBar.addSeparator();

            // Lista rozwijana z wyborem wypu wykresu
            toolBar.add(
                    makeComboBox(new String[]{"Słupkowy", "Świecowy", "Liniowy"}, 
                    null, kontroler.new ZmienTypWykresu() ));
            toolBar.addSeparator();
            
            // przyciski do pokazywania wskaźników
            toolBar.add(makeTextButton("ADX", "Average directional movement index", null)); //TODO AL
            toolBar.add(makeTextButton("CCI", "Commodity channel index", null)); //TODO AL
            
            makePairButton(toolBar, "Momentum", "1", "Momentum", false, null); // TODO AL
            makePairButton(toolBar, "ROC", "1", "Rate of change", false, null); // TODO AL
            
            toolBar.add(makeTextButton("ATR", "Average true range", null)); //TODO AL
            toolBar.add(makeTextButton("SMA", "Simple moving average", null)); //TODO AL
            
            toolBar.addSeparator();
            
            // Lista rozwijana z wyborem okresu (tyg, dz, mies)
            toolBar.add(
                    makeComboBox(new String[]{"Tygodniowe", "Miesięczne", "Dzienne"}, 
                    null, kontroler.new UstawOkres() ));
            toolBar.addSeparator();

            toolBar.add(
                    makeToggleButton("siatka", "Pokaż/ukryj siatkę", "Siatka", 
                    false, kontroler.new PokazSiatke() ));

            toolBar.add(makeToggleButton("menu", "Pokaż/ukryj lewe menu", "LMenu", false, 
                    kontroler.new WidocznoscMenu()));
        }
    }

    protected JComboBox makeComboBox(String[] lista, String selected, ActionListener AL) {
        JComboBox jComboBox = new JComboBox(lista);
        if (selected != null) {
            jComboBox.setSelectedItem(selected);
        }
        jComboBox.addActionListener(AL);
        return jComboBox;
    }

    protected JButton makeButton(String imageName, String toolTipText, String altText, ActionListener AL) {
        String imgLocation = "ikony/button_" + imageName + "_24.png";

        JButton button = new JButton();
        button.setToolTipText(toolTipText);
        if (AL != null) {
            button.addActionListener(AL);
        }
        button.setIcon(new ImageIcon(imgLocation, altText));

        return button;
    }
    
    protected JToggleButton makeTextButton(String text, String toolTipText, ActionListener AL) {
        JToggleButton button = new JToggleButton(text, false);
        button.setToolTipText(toolTipText);
        if (AL != null) {
            button.addActionListener(AL);
        }
        return button;
    }

    protected JToggleButton makeToggleButton(String imageName, String toolTipText, String altText, Boolean selected, ActionListener AL) {
        String imgLocation = "ikony/button_" + imageName + "_24.png";

        JToggleButton button = new JToggleButton(new ImageIcon(imgLocation, altText), selected);
        button.setToolTipText(toolTipText);
        if (AL != null) {
            button.addActionListener(AL);
        }

        return button;
    }

    protected void makePairButton(JToolBar tb, String buttonText, String textFieldText, String toolTipText,
            Boolean selected, ActionListener AL) {
        JToggleButton button = new JToggleButton(buttonText, selected);
        button.setToolTipText(toolTipText);
        tb.add(button);
        tb.add(new JTextField(textFieldText, 1));
        tb.addSeparator();
        if (AL != null) {
            button.addActionListener(AL);
        }
    }
    
    protected void widocznoscMenu() {
        if(menu.isVisible()) {
            menu.setVisible(false);
        }
        else {
            menu.setVisible(true);
        }
    }
    
    protected void odswiezDaty() {
        dataOd.setText(ustawienia.getDataOd(null));
        dataDo.setText(ustawienia.getDataDo(null));
    }
    
    public void odswiezUlubione() {
        if(typListy.getSelectedItem().equals("Ulubione")) {
            lista.setListData(Model.pobierzListeInstrumentow(ustawienia, "Ulubione"));
        }
    }

    void odswiez() {
        // to jest ok
        //Wykres w = new Wykres(ustawienia, model);
        //String nazwa = w.utworz();
        //wykres.setIcon(new ImageIcon(nazwa));
    }
}