package model;

import java.util.Observable;

public class Model extends Observable {

    private Map map;
    
    public Model() {
        map = new Map();
    }
    
    public Map getMap() {
        return map;
    }

}
