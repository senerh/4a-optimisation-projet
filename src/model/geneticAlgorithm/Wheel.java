package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wheel {
    
    private Population population;
    private List<Float> cumulatedProbs;
    private Random random;

    public Wheel(Population population) {
        this.population = population;
        cumulatedProbs = new ArrayList<Float>();
        random = new Random();
        calculate();
    }
    
    private void calculate() {
        float totalFitness = 0;
        for (Solution solution : population.getListSolutions()) {
            totalFitness += 1 / solution.getFitness();
        }

        float cumulatedFitness = 0;
        for (Solution solution : population.getListSolutions()) {
            cumulatedFitness += 1 / solution.getFitness();
            cumulatedProbs.add(cumulatedFitness / totalFitness);
        }
    }
    
    public List<Solution> getNewSolutions() {
        List<Solution> listSolutions = new ArrayList<Solution>();
        for (int i=0; i<population.getSize(); i++) {
            Solution solution = turn();
            listSolutions.add(solution.clone());
        }
        return listSolutions;
    }
    
    public Solution turn() {
        float probability = random.nextFloat();
        Solution solution = null;
        int index = 0;
        for (Float cumulatedProb : cumulatedProbs) {
            if (probability <= cumulatedProb) {
                solution = population.getListSolutions().get(index);
                break;
            }
            index++;
        }
        return solution;
    }

}
