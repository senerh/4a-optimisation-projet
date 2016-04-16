package model.history;

import java.util.ArrayList;
import java.util.List;

import model.geneticAlgorithm.GeneticAlgorithm;
import model.geneticAlgorithm.Solution;


public class Line {
    
    private int populationSize;
    private float mutationRate;
    private int keptPopulationSize;
    private int mutatedPopulationSize;
    private List<Float> listFitness;
    
    public Line(GeneticAlgorithm geneticAlgorithm) {
        populationSize = geneticAlgorithm.getPopulationSize();
        mutationRate = geneticAlgorithm.getMutationRate();
        keptPopulationSize = geneticAlgorithm.getKeptPopulationSize();
        mutatedPopulationSize = geneticAlgorithm.getMutatedPopulationSize();
        
        listFitness = new ArrayList<Float>();
        List<Solution> listSolutions = geneticAlgorithm.getPopulation().getListSolutions();
        for (Solution s : listSolutions) {
            listFitness.add(s.getFitness());
        }
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public float getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(float mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getKeptPopulationSize() {
        return keptPopulationSize;
    }

    public void setKeptPopulationSize(int keptPopulationSize) {
        this.keptPopulationSize = keptPopulationSize;
    }

    public int getMutatedPopulationSize() {
        return mutatedPopulationSize;
    }

    public void setMutatedPopulationSize(int mutatedPopulationSize) {
        this.mutatedPopulationSize = mutatedPopulationSize;
    }

    public List<Float> getListFitness() {
        return listFitness;
    }

    public void setListFitness(List<Float> listFitness) {
        this.listFitness = listFitness;
    }
}
