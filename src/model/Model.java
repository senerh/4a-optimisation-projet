package model;

import java.io.IOException;
import java.util.Observable;

public class Model extends Observable {

    private Map map;
    
    public Model() {
        map = new Map();
        try {
            map.loadPlaces(ClassLoader.getSystemResource("data/LieuxPossibles.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Map getMap() {
        return map;
    }

}
