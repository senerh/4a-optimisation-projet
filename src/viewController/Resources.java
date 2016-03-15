package viewController;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Resources {
    
    public static final Image AGENCY;
    static {
        AGENCY = loadImage("images/agency.png");
    }
    
    public static final Image PLACE;
    static {
        PLACE = loadImage("images/place.png");
    }

    public static final Image FRANCE_MAP;
    static {
        FRANCE_MAP = loadImage("images/france_map.png");
    }
    
    private static Image loadImage(String path) {
        ImageIcon m = new ImageIcon(ClassLoader.getSystemResource(path));
        return m.getImage();
    }

}
