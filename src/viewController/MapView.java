package viewController;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Agency;
import model.Map;

public class MapView extends JPanel implements Observer {

    private static final long serialVersionUID = 1681067387421746050L;
    private static final float NORTH = (float) 51.05;
    private static final float SOUTH = (float) 42.4;
    private static final float WEST = (float) -4.7;
    private static final float EAST = (float) 7.95;
    private static final float LAT_DIFF = NORTH - SOUTH;
    private static final float LNG_DIFF = EAST - WEST;
    private static final int MAP_WIDTH = 500;
    private static final int MAP_HEIGHT = 500;

    private Map map;

    public MapView(Map map) {
        super();
        this.map = map;
        map.addObserver(this);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
    }

    public void paint(Graphics g) {
        super.paint(g);

        drawAgencies(g);
    }

    private void drawAgencies(Graphics g) {
        for (Agency agency : map.getListAgencies()) {
            int x = (int) (((EAST-agency.getLongitude())/LNG_DIFF)*MAP_WIDTH);
            int y = (int) (((NORTH-agency.getLatitude())/LAT_DIFF)*MAP_HEIGHT);
            g.drawImage(Ressources.AGENCY, x, y,this);
        }
    }

    public void update(Observable arg0, Object arg1) {
        repaint();
    }

}
