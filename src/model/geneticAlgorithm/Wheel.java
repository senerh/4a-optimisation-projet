package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Wheel {
    
    private Population population;
    private HashMap<Float, Solution> wheel;
    private Random random;

    public Wheel(Population population) {
        this.population = population;
        wheel = new HashMap<Float, Solution>();
        random = new Random();
        calculate();
    }
    
    private void calculate() {
        float totalFitness = 0;
        for (Solution solution : population.getListSolutions()) {
            totalFitness += solution.getFitness();
        }

        float cumulatedFitness = 0;
        for (Solution solution : population.getListSolutions()) {
            cumulatedFitness += solution.getFitness();
            wheel.put(cumulatedFitness / totalFitness, solution);
        }
    }
    
    public List<Solution> turn() {
        List<Solution> listSolutions = new ArrayList<Solution>();
        for (int i=0; i<population.getSize(); i++) {
            float index = random.nextFloat();
            //TODO: continuer
        }
        return listSolutions;
    }
    
    private Solution getSolution(float index) {
        Solution solution = null;
        for (Float cumulatedFitness : wheel.keySet()) {
            if (index <= cumulatedFitness) {
                solution = wheel.get(cumulatedFitness);
                break;
            }
        }
        return solution;
    }

}
