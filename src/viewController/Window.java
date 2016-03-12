package viewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Model;

public class Window extends JFrame implements Observer
{
    private static final long serialVersionUID = -5494106475468177190L;
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
        buildMenu();

        setTitle("Projet - Lieux de formation");
        setSize(700, 700);
        
        setLayout(new BorderLayout());
        
        MapView mapView = new MapView(model.getMap());
        mapView.setPreferredSize(new Dimension(500, 500));
        add(mapView, BorderLayout.LINE_START);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 150));
        add(panel, BorderLayout.PAGE_END);
        
        setResizable(false);
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
        });

        menuFile.add(menuItemOpen);
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }

    public void update(Observable arg0, Object arg1) {
        //TODO
    }
}
