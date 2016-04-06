package viewController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Agency;
import model.Map;
import model.Place;
import model.geneticAlgorithm.GeneticAlgorithm;

public class MapView extends JPanel implements Observer {

    private static final long serialVersionUID = 1681067387421746050L;
    private static final float NORTH = (float) 51.05;
    private static final float SOUTH = (float) 42.4;
    private static final float WEST = (float) -4.7;
    private static final float EAST = (float) 7.95;
    private static final float LAT_DIFF = NORTH - SOUTH;
    private static final float LNG_DIFF = EAST - WEST;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Map map;
    private GeneticAlgorithm geneticAlgorithm;

    public MapView(Map map, GeneticAlgorithm geneticAlgorithm) {
        super();
        this.map = map;
        this.geneticAlgorithm = geneticAlgorithm;
        map.addObserver(this);
        geneticAlgorithm.addObserver(this);
        setPreferredSize(new Dimension(MapView.WIDTH, MapView.HEIGHT));
    }

    public void paint(Graphics g) {
        super.paint(g);

        drawFranceMap(g);
        
        if (geneticAlgorithm.getIsStarted()) {
            drawPlaces(g);
            drawSolution(g);
        } else {
            drawPlaces(g);
            drawAgencies(g);
        }
    }

    private void drawFranceMap(Graphics g) {
        g.drawImage(Resources.FRANCE_MAP, 0, 0,this);
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
    
    private void drawSolution(Graphics g) {
        g.setColor(Color.GREEN);
        java.util.Map<Place, List<Agency>> solution = geneticAlgorithm.getBestSolution().getSolution();
        for (Entry<Place, List<Agency>> entry : solution.entrySet()) {
            int center_x = longitudeToX(entry.getKey().getLongitude());
            int center_y = latitudeToY(entry.getKey().getLatitude());
            for (Agency agency : entry.getValue()) {
                int agency_x = longitudeToX(agency.getLongitude());
                int agency_y = latitudeToY(agency.getLatitude());
                g.drawImage(Resources.AGENCY, agency_x, agency_y, this);
                g.drawImage(Resources.CENTER, center_x, center_y, this);
                g.drawLine(center_x, center_y, agency_x, agency_y);
            }
        }
    }

    public void update(Observable arg0, Object arg1) {
        repaint();
    }
    
    private int longitudeToX(float longitude) {
        return WIDTH - (int) (((EAST-longitude)/LNG_DIFF)*WIDTH);
    }
    
    private int latitudeToY(float latitude) {
        return (int) (((NORTH-latitude)/LAT_DIFF)*HEIGHT);
    }

}
