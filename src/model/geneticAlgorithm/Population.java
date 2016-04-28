package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.Map;

/**
 * Represents a population
 */
public class Population {
    
    private List<Solution> listSolutions;
    private Solution bestSolution;
    private int size;
    private Random random;
    
    public Population(int size, Map map) {
        this.size = size;
        
        random = new Random();
        
        listSolutions = new ArrayList<Solution>();
        for (int i=0; i<size; i++) {
            listSolutions.add(new Solution(map));
        }
        
        calculateBestSolution();
    }

    public Solution getBestSolution() {
        return bestSolution;
    }

    /**
     * Reproduce keptPopulationSize populations with a biased wheel
     * @param keptPopulationSize
     */
    public void reproduce(int keptPopulationSize) {
        listSolutions = new Wheel(this, keptPopulationSize).getNewSolutions();
    }

    /**
     * Cross the population
     */
    public void cross() {
        int n = listSolutions.size();
        for (int i=0; i+2<=n; i += 2) {
            listSolutions.get(i).cross(listSolutions.get(i + 1));
        }
    }

    /**
     * Mutate mutatedPopulationSize solutions of the population
     * @param mutationRate
     * @param mutatedPopulationSize
     */
    public void mutate(float mutationRate, int mutatedPopulationSize) {
        for (int i=0; i<mutatedPopulationSize; i++) {
            int index = random.nextInt(listSolutions.size());
            listSolutions.get(index).mutate(mutationRate);
        }
    }

    /**
     * calculate the fitness of the whole population
     */
    public void calculateFitness() {
        for (Solution solution : listSolutions) {
            solution.associateAgencies();
        }
        calculateBestSolution();
    }
    
    private void calculateBestSolution() {
        bestSolution = Collections.min(listSolutions);
    }

    public List<Solution> getListSolutions() {
        return listSolutions;
    }
    
    public int getSize() {
        return size;
    }
}
