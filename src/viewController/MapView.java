package viewController;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Agency;
import model.Map;
import model.Place;

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
        
        drawPlaces(g);

        drawAgencies(g);
    }

    private void drawAgencies(Graphics g) {
        for (Agency agency : map.getListAgencies()) {
            int x = longitudeToX(agency.getLongitude());
            int y = latitudeToY(agency.getLatitude());
            g.drawImage(Resources.AGENCY, x, y,this);
        }
    }
    
    private void drawPlaces(Graphics g) {
        for (Place place : map.getListPlaces()) {
            int x = longitudeToX(place.getLongitude());
            int y = latitudeToY(place.getLatitude());
            g.drawImage(Resources.PLACE, x, y,this);
        }
    }

    public void update(Observable arg0, Object arg1) {
        repaint();
    }
    
    private int longitudeToX(float longitude) {
        return (int) (((EAST-longitude)/LNG_DIFF)*MAP_WIDTH);
    }
    
    private int latitudeToY(float latitude) {
        return (int) (((NORTH-latitude)/LAT_DIFF)*MAP_HEIGHT);
    }

}
