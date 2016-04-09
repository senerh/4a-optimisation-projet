package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Map;

public class Population {
    
    private List<Solution> listSolutions;
    private int size;
    private Random random;
    
    public Population(int size, Map map) {
        this.size = size;
        
        random = new Random();
        
        listSolutions = new ArrayList<Solution>();
        for (int i=0; i<size; i++) {
            listSolutions.add(new Solution(map));
        }
    }

    //TODO: chercher à optimiser
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
        listSolutions = new Wheel(this).getNewSolutions();
    }
    
    public void cross() {
        for (Solution solution : listSolutions) {
            solution.cross(listSolutions.get(random.nextInt(listSolutions.size())));
        }
    }
    
    public void calculateFitness() {
        for (Solution solution : listSolutions) {
            solution.associateAgencies();
        }
    }
    
    public List<Solution> getListSolutions() {
        return listSolutions;
    }
    
    public int getSize() {
        return size;
    }
}
