package model.geneticAlgorithm;

import java.util.Observable;

import model.Map;
import model.history.History;

/**
 * Represents an iplementation of an genetic algorithm
 */
public class GeneticAlgorithm extends Observable {
    
    private Map map;
    private Population population;
    private Solution bestSolution;
    private boolean isStarted;
    private int populationSize;
    private float mutationRate;
    private int keptPopulationSize;
    private int mutatedPopulationSize;
    private float newMutationRate;
    private int newKeptPopulationSize;
    private int newMutatedPopulationSize;
    private History history;
    private boolean isStopped;
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
        isStarted = false;
    }

    /**
     * Start the genetic algorithm
     * @param size size of the population
     * @param mutationRate
     * @param keptPopulationSize
     * @param mutatedPopulationSize
     */
    public void start(int size, float mutationRate, int keptPopulationSize, int mutatedPopulationSize) {
        if (!isStarted) {
            isStarted = true;
            isStopped = false;
            this.populationSize = size;
            this.mutationRate = mutationRate;
            this.keptPopulationSize = keptPopulationSize;
            this.mutatedPopulationSize = mutatedPopulationSize;
            updateParameters(mutationRate, keptPopulationSize, mutatedPopulationSize);
            population = new Population(size, map);
            bestSolution = population.getBestSolution().clone();
            
            updateView();
            
            history = new History(this);
            history.add();
            
            loop();
        }
    }

    /**
     * main loop of the genetic algorithm (reproduce, cross, mutate, calculate the fitness)
     */
    private void loop() {
        int generation = 0;
        System.out.println("generation " + generation + " : " + bestSolution.getFitness());
        while (!isStopped) {
            generation++;
            setNewParameters();
            population.reproduce(keptPopulationSize);
            population.cross();
            population.mutate(mutationRate, mutatedPopulationSize);
            population.calculateFitness();
            history.add();
            System.out.println("generation " + generation + " : " + population.getBestSolution().getFitness());
            if (population.getBestSolution().getFitness() < bestSolution.getFitness()) {
                bestSolution = population.getBestSolution().clone();
                updateView();
            }
        }
    }

    private void updateView() {
        setChanged();
        notifyObservers();
    }
    
    public Solution getBestSolution() {
        return bestSolution;
    }
    
    public boolean getIsStarted() {
        return isStarted;
    }
    
    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
    
    public int getNbPersons() {
        return map.getNbPersons();
    }
    
    public Map getMap() {
        return map;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void updateParameters(float mutationRate, int keptPopulationSize, int mutatedPopulationSize) {
        newMutationRate = mutationRate;
        newKeptPopulationSize = keptPopulationSize;
        newMutatedPopulationSize = mutatedPopulationSize;
    }
    
    private void setNewParameters() {
        mutationRate = newMutationRate;
        keptPopulationSize = newKeptPopulationSize;
        mutatedPopulationSize = newMutatedPopulationSize;
    }

    public Population getPopulation() {
        return population;
    }

    public float getMutationRate() {
        return mutationRate;
    }

    public int getKeptPopulationSize() {
        return keptPopulationSize;
    }

    public int getMutatedPopulationSize() {
        return mutatedPopulationSize;
    }
    
    public void stop() {
        isStopped = true;
    }
    
    public History getHistory() {
        return history;
    }

}
