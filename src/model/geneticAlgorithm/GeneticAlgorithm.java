package model.geneticAlgorithm;

import java.util.Observable;

import model.Map;

public class GeneticAlgorithm extends Observable {
    
    private Map map;
    private Population population;
    private Solution bestSolution;
    private boolean isStarted;
    private int size;
    private float mutationRate;
    private int keptPopulationSize;
    private int mutatedPopulationSize;
    private float newMutationRate;
    private int newKeptPopulationSize;
    private int newMutatedPopulationSize;
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
        isStarted = false;
    }
    
    public void start(int size, float mutationRate, int keptPopulationSize, int mutatedPopulationSize) {
        if (!isStarted) {
            isStarted = true;
            this.size = size;
            updateParameters(mutationRate, keptPopulationSize, mutatedPopulationSize);
            population = new Population(size, map);
            bestSolution = population.getBestSolution().clone();
            
            updateView();
            
            loop();
        }
    }
    
    private void loop() {
        int generation = 0;
        System.out.println("generation " + generation + " : " + bestSolution.getFitness());
        while (true) {
            generation++;
            setNewParameters();
            population.reproduce(keptPopulationSize);
            population.cross();
            population.mutate(mutationRate, mutatedPopulationSize);
            population.calculateFitness();
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

    public int getSize() {
        return size;
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
}
