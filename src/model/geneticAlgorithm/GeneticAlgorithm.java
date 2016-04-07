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
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
        isStarted = false;
    }
    
    public void start(int _size, float _mutationRate) {
        isStarted = true;
        size = _size;
        mutationRate = _mutationRate;
        population = new Population(size, map);
        bestSolution = population.getBestSolution().clone();
        
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
