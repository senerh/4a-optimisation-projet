package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import model.Map;

public class Population {
    
    private List<Solution> listSolutions;
    private int size;
    
    public Population(int size, Map map) {
        this.size = size;
        
        listSolutions = new ArrayList<Solution>();
        for (int i=0; i<size; i++) {
            listSolutions.add(new Solution(map));
        }
    }

    public Solution getBestSolution() {
        Solution bestSolution = listSolutions.get(0);
        for (Solution solution : listSolutions) {
            if (solution.getFitness() < bestSolution.getFitness()) {
                bestSolution = solution;
            }
        }
        return bestSolution;
    }
    
    public void reproduce() {
        //TODO: coder
    }
    
    public List<Solution> getListSolutions() {
        return listSolutions;
    }
    
    public int getSize() {
        return size;
    }
}
