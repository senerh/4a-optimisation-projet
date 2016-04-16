package viewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Model;

public class Window extends JFrame {

    private static final long serialVersionUID = -5494106475468177190L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    
    private Model model;

    public Window(Model model) {
        super();
        
        this.model = model;

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0)
            {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public void build() {
        setTitle("Projet - Lieux de formation");
        setSize(WIDTH, HEIGHT);
        
        buildMenu();
        
        buildMap();
        
        buildForm();
        
        setLocationRelativeTo(null);
        
        setResizable(false);
    }
    
    private void buildForm() {
        JPanel container = new JPanel(new GridLayout(0, 1));
        Form form = new Form(model.getGeneticAlgorithm());
        Informations informations = new Informations(model.getGeneticAlgorithm());
        container.add(form);
        container.add(informations);
        add(container);
    }
    
    private void buildMap() {
        MapView mapView = new MapView(model.getMap(), model.getGeneticAlgorithm());
        MapLegend mapLegend = new MapLegend();
        
        JPanel mapContainer = new JPanel(new BorderLayout(0, 0));
        mapContainer.setPreferredSize(new Dimension(MapView.HEIGHT, MapView.WIDTH));
        mapContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        
        mapContainer.add(mapView, BorderLayout.PAGE_START);
        mapContainer.add(mapLegend, BorderLayout.PAGE_END);
        add(mapContainer, BorderLayout.LINE_START);
    }
    
    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Fichier");
        
        JMenuItem menuItemExit = new JMenuItem("Quitter");
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
        JMenuItem menuItemOpen = new JMenuItem("Ouvrir");
        menuItemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (model.getGeneticAlgorithm().getIsStarted()) {
                    JOptionPane.showMessageDialog(Window.this,
                            "Vous ne pouvez pas charger un jeu de données pendant l'exécution de l'algorithme.",
                            "Algorithme déjà démarré.",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JFileChooser fc = new JFileChooser(".");
                    int returnVal = fc.showOpenDialog(Window.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        try {
                            model.getMap().loadAgencies(file.getAbsolutePath());
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(Window.this,
                                    "Vérifiez que le format du fichier est correcte et que vous disposez des droits de lecture.",
                                    "Erreur lors du la lecture du fichier",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        menuFile.add(menuItemOpen);
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }
}
