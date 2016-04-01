package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import model.Map;

public class Population {
    
    private List<Solution> listSolutions;
    
    public Population(int size, Map map) {
        
        listSolutions = new ArrayList<Solution>();
        
        for (int i=0; i<size; i++) {
            listSolutions.add(new Solution(map));
        }
    }
}
