package viewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MapLegend extends JPanel {

    private static final long serialVersionUID = -1641193333466746561L;
    public static final int WIDTH = MapView.WIDTH;
    public static final int HEIGHT = Window.HEIGHT - MapView.HEIGHT - 50;
    
    public MapLegend() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        build();
    }
    
    private void build() {
        EmptyBorder margin = new EmptyBorder(10, 10, 10, 10);
        setBorder(margin);
        
        JLabel title = new JLabel("<html><h3>Légende</h3></html>", JLabel.CENTER);
        
        JPanel grid = new JPanel(new GridLayout(2, 1));;
        
        Icon img;
        JLabel lbl;
        
        img = new ImageIcon(Resources.AGENCY);
        lbl = new JLabel("Agences", img, JLabel.LEFT);
        grid.add(lbl);
        
        img = new ImageIcon(Resources.PLACE);
        lbl = new JLabel("Villes", img, JLabel.LEFT);
        grid.add(lbl);
        
        img = new ImageIcon(Resources.CENTER);
        lbl = new JLabel("Centres", img, JLabel.LEFT);
        grid.add(lbl);
        
        setLayout(new BorderLayout());
        add(title, BorderLayout.PAGE_START);
        add(grid, BorderLayout.CENTER);
    }
    
}
