package model;

import java.io.IOException;
import java.util.Observable;

public class Model extends Observable {
    
    public static final int NB_PERSONS_BY_CENTER = 60;
    
    public static final int TRAINER_COST = 2000;
    
    public static final int RENTING_COST = 1000;
    
    public static final int CENTER_COST = TRAINER_COST + RENTING_COST;
    
    public static final double KM_COST = 0.4;

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
