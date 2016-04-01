package model.geneticAlgorithm;

import java.util.List;

import model.Agency;
import model.Map;

public class GeneticAlgorithm {
    
    private Map map;
    
    private List<Agency> listAgencies;
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
        listAgencies = map.getListAgencies();
    }

}
