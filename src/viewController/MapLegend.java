package viewController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapLegend extends JPanel {

    private static final long serialVersionUID = -1641193333466746561L;
    public static final int WIDTH = MapView.WIDTH;
    public static final int HEIGHT = Window.HEIGHT - MapView.HEIGHT - 50;
    
    public MapLegend() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1));
        build();
    }
    
    private void build() {
        Icon img;
        JLabel lbl;
        
        img = new ImageIcon(Resources.AGENCY);
        lbl = new JLabel("Agences", img, JLabel.CENTER);
        add(lbl);
        
        img = new ImageIcon(Resources.PLACE);
        lbl = new JLabel("Places", img, JLabel.CENTER);
        add(lbl);
    }
    
}
