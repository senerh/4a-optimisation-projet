package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Wheel {
    
    private Population population;
    private List<Float> cumulatedProbs;
    private List<Solution> listBestSolutions;
    private int keptPopulationSize;
    private Random random;

    public Wheel(Population population, int keptPopulationSize) {
        this.population = population;
        this.keptPopulationSize = keptPopulationSize;
        listBestSolutions = new ArrayList<Solution>(population.getListSolutions());
        cumulatedProbs = new ArrayList<Float>();
        random = new Random();
        calculate();
    }
    
    private void calculate() {
        Collections.sort(listBestSolutions);
        listBestSolutions = listBestSolutions.subList(0, keptPopulationSize);
        float totalFitness = 0;
        for (Solution solution : listBestSolutions) {
            totalFitness += 1 / solution.getFitness();
        }

        float cumulatedFitness = 0;
        for (Solution solution : listBestSolutions) {
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
                solution = listBestSolutions.get(index);
                break;
            }
            index++;
        }
        return solution;
    }

}
