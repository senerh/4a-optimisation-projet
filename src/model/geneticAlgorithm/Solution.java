package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import model.Agency;
import model.Map;
import model.Model;
import model.Place;

/**
 * represents a solution (boolean list and hashmap)
 */
public class Solution implements Comparable<Solution> {
    private Map map;
    private List<Boolean> listPlaces;
    private java.util.Map<Place, List<Agency>> solution;
    private Random random;
    private float fitness;
    private float totalDistance;

    private Solution(
            Map map,
            List<Boolean> listPlaces,
            java.util.Map<Place, List<Agency>> solution,
            Random random,
            float fitness,
            float totalDistance) {
        this.map = map;
        this.listPlaces = listPlaces;
        this.solution = solution;
        this.random = random;
        this.fitness = fitness;
        this.totalDistance = totalDistance;
    }

    public Solution(Map map) {
        this.map = map;
        random = new Random();
        solution = new HashMap<Place, List<Agency>>();
        generateRandomSolution();
    }

    /**
     * generate random solution with a minimum number of centers
     */
    private void generateRandomSolution() {
        int nbPlaces = map.getListPlaces().size();

        listPlaces = new ArrayList<Boolean>();
        for (int i=0; i<nbPlaces; i++) {
            listPlaces.add(false);
        }
        
        associateAgencies();
    }

    /**
     * Associate all agencies with the nearest center
     * Agencies are browsed randomly
     */
    public void associateAgencies() {
        boolean isCorrect;
        do {
            isCorrect = true;
            solution.clear();
            List<Agency> listAgencies = new ArrayList<Agency>(map.getListAgencies());
            while (!listAgencies.isEmpty()) {
                int index = random.nextInt(listAgencies.size());
                isCorrect = associateNearestCenter(listAgencies.get(index)) && isCorrect;
                listAgencies.remove(index);
            }
        } while (!isCorrect);
        removeUselessCenters();
        calculateFitness();
    }

    /**
     * Associate an agency with the nearest agency
     * @param agency
     * @return
     */
    private boolean associateNearestCenter(Agency agency) {
        Place nearestCenter = null;
        float nearestDistance = Float.MAX_VALUE;
        int index = 0;
        Place center;
        float distance;
        for (Boolean isCenter : listPlaces) {
            center = map.getListPlaces().get(index);
            if (isCenter && isAvailable(center, agency)) {
                distance = distFrom(agency.getLatitude(), agency.getLongitude(), center.getLatitude(), center.getLongitude());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestCenter = center;
                }
            }
            index++;
        }
        if (nearestCenter == null) {
            addCenter(agency);
            return false;
        } else if (solution.containsKey(nearestCenter)) {
            solution.get(nearestCenter).add(agency);
        } else {
            List<Agency> l = new ArrayList<Agency>();
            l.add(agency);
            solution.put(nearestCenter, l);
        }
        return true;
    }

    /**
     * Correct a solution when there is a lack of agencies
     * @param agency agency which don't own center
     */
    private void addCenter(Agency agency) {
        int index = 0;
        float nearestDist = Float.MAX_VALUE;
        int nearestPlaceIndex = 0;
        Place nearestPlace = null;
        Place currentPlace = null;
        float currentDist;
        for (Boolean isCenter : listPlaces) {
            if (!isCenter) {
                currentPlace = map.getListPlaces().get(index);
                currentDist = distFrom(agency.getLatitude(), agency.getLongitude(), currentPlace.getLatitude(), currentPlace.getLongitude());
                if (currentDist < nearestDist) {
                    nearestDist = currentDist;
                    nearestPlaceIndex = index;
                    nearestPlace = currentPlace;
                }
            }
            index++;
        }
        //Si nearestCenter est null, cela voudrait dire qu'il n'y a pas assez de villes pour accueillir les centres.
        //Le problème ne peut donc pas être résolu.
        listPlaces.set(nearestPlaceIndex, true);
        List<Agency> l = new ArrayList<Agency>();
        l.add(agency);
        solution.put(nearestPlace, l);
    }

    /**
     * remove centers which don't own agencies
     */
    private void removeUselessCenters() {
        int index = 0;
        for (Boolean isCenter : listPlaces) {
            if (isCenter && !solution.containsKey(map.getListPlaces().get(index))) {
                listPlaces.set(index, false);
            }
            index++;
        }
    }

    /**
     * if there is enough available space in a center to put agency
     * @param center
     * @param agency
     * @return
     */
    private boolean isAvailable(Place center, Agency agency) {
        if (solution.containsKey(center)) {
            List<Agency> l = solution.get(center);
            int nbPersons = 0;
            for (Agency a : l) {
                nbPersons += a.getNbPersons();
            }
            return agency.getNbPersons() + nbPersons <= Model.NB_PERSONS_BY_CENTER;
        }
        return true;
    }

    /**
     * calculate the distance between two cities
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
    
    public java.util.Map<Place, List<Agency>> getSolution() {
        return solution;
    }

    /**
     * calculate the fitness of a solution
     */
    private void calculateFitness() {
        totalDistance = 0;
        for (Entry<Place, List<Agency>> entry : solution.entrySet()) {
            for (Agency agency : entry.getValue()) {
                totalDistance += 2 * agency.getNbPersons() * distFrom(entry.getKey().getLatitude(), entry.getKey().getLongitude(), agency.getLatitude(), agency.getLongitude());
            }
        }
        fitness = Model.KM_COST * totalDistance + solution.size() * Model.CENTER_COST;;
    }
    
    public float getFitness() {
        return fitness;
    }
    
    public float getTotalDistance() {
        return totalDistance;
    }

    @Override
    protected Solution clone() {
        List<Boolean> clonedListPlaces = new ArrayList<Boolean>(listPlaces);
        java.util.Map<Place, List<Agency>> clonedSolution = new HashMap<Place, List<Agency>>(solution);
        
        return new Solution(map, clonedListPlaces, clonedSolution, random, fitness, totalDistance);
    }

    /**
     * cross between start and end of the solution
     * @param other
     */
    public void cross(Solution other) {
        int start = random.nextInt(listPlaces.size());
        int end = start + random.nextInt(listPlaces.size() - start);
        cross(other, start);
        cross(other, end);
    }

    /**
     * cross between index and end of the solution
     * @param other
     * @param index
     */
    private void cross(Solution other, int index) {
        List<Boolean> l1 = new ArrayList<Boolean>(listPlaces.subList(0, index));
        l1.addAll(other.listPlaces.subList(index, other.listPlaces.size()));
        
        List<Boolean> l2 = new ArrayList<Boolean>(other.listPlaces.subList(0, index));
        l2.addAll(listPlaces.subList(index, listPlaces.size()));
        
        listPlaces = l1;
        other.listPlaces = l2;
    }

    /**
     * mutate a solution with a mutation rate
     * @param mutationRate
     */
    public void mutate(float mutationRate) {
        int nbMutations = (int) (listPlaces.size() * mutationRate);
        for (int i=0; i<nbMutations; i++) {
            int index = random.nextInt(listPlaces.size());
            listPlaces.set(index, !listPlaces.get(index));
        }
    }

    public int compareTo(Solution other) {
        return (int) (getFitness() - other.getFitness());
    }
    
    @Override
    public String toString() {
        return "[Solution (" + fitness + ")]";
    }

}
