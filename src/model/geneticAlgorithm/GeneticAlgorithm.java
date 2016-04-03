package model.geneticAlgorithm;

import java.util.Observable;

import model.Map;

public class GeneticAlgorithm extends Observable {
    
    private Map map;
    private Population population;
    private Solution bestSolution;
    
    public GeneticAlgorithm(Map map) {
        this.map = map;
    }
    
    public void start() {
        population = new Population(10, map);
        bestSolution = population.getBestSolution();
        
        setChanged();
        notifyObservers();
    }
    
    public Solution getBestSolution() {
        return bestSolution;
    }
    
    public boolean isStarted() {
        return population != null;
    }

}
