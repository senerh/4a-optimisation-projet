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

    public void reproduce(int keptPopulationSize) {
        listSolutions = new Wheel(this, keptPopulationSize).getNewSolutions();
    }
    
    public void cross() {
        int n = listSolutions.size();
        for (int i=0; i+2<=n; i += 2) {
            listSolutions.get(i).cross(listSolutions.get(i + 1));
        }
    }
    
    public void mutate(float mutationRate) {
        int nbMutations = (int) (listSolutions.size() / 10);
        for (int i=0; i<nbMutations; i++) {
            int index = random.nextInt(listSolutions.size());
            listSolutions.get(index).mutate(mutationRate);
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
