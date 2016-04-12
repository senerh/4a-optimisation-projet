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
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
        isStarted = false;
    }
    
    public void start(int _size, float _mutationRate, int _keptPopulationSize, int _mutatedPopulationSize) {
        isStarted = true;
        size = _size;
        mutationRate = _mutationRate;
        keptPopulationSize = _keptPopulationSize;
        mutatedPopulationSize = _mutatedPopulationSize;
        population = new Population(size, map);
        bestSolution = population.getBestSolution().clone();
        
        display();
        
        Solution currentBestSolution;
        int i = 0;
        System.out.println("generation " + i + " : " + bestSolution.getFitness());
        while (true) {
            i++;
            population.reproduce(keptPopulationSize);
            population.cross();
            population.mutate(mutationRate, mutatedPopulationSize);
            population.calculateFitness();
            currentBestSolution = population.getBestSolution();
            System.out.println("generation " + i + " : " + currentBestSolution.getFitness());
            if (currentBestSolution.getFitness() < bestSolution.getFitness()) {
                bestSolution = currentBestSolution.clone();
                display();
            }
        }
    }
    
    private void display() {
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

}
