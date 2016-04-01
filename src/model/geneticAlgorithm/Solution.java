package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import model.Agency;
import model.Map;
import model.Model;
import model.Place;

public class Solution {
    private Map map;
    private List<Boolean> listPlaces;
    private java.util.Map<Place, List<Agency>> solution;
    private Random random;

    public Solution(Map map, List<Boolean> listPlaces) {
        this.map = map;
        this.listPlaces = listPlaces;
        random = new Random();
        solution = new HashMap<Place, List<Agency>>();
    }

    public Solution(Map map) {
        this.map = map;
        random = new Random();
        solution = new HashMap<Place, List<Agency>>();
        generateRandomSolution();
    }
    
    private void generateRandomSolution() {
        int nbPlaces = map.getListPlaces().size();
        int nbPersons = 0;
        for (Agency agency : map.getListAgencies()) {
            nbPersons += agency.getNbPersons();
        }

        listPlaces = new ArrayList<Boolean>();
        int nbCenters = 0;
        for (int i=0; i<nbPlaces; i++) {
            if (random.nextBoolean()) {
                nbCenters++;
                listPlaces.add(true);
            } else {
                listPlaces.add(false);
            }
        }

        while (nbCenters * Model.NB_PERSONS_BY_CENTER < nbPersons) {
            int index = random.nextInt(nbPlaces);
            if (!listPlaces.get(index)) {
                listPlaces.set(index, true);
                nbCenters++;
            }
        }
        
        associateAgencies();
    }

    private void associateAgencies() {
        solution.clear();
        List<Agency> listAgencies = new ArrayList<Agency>(map.getListAgencies());
        while (!listAgencies.isEmpty()) {
            int index = random.nextInt(listAgencies.size());
            associateNearestCenter(listAgencies.get(index));
            listAgencies.remove(index);
        }
        removeUselessCenters();
    }

    private void associateNearestCenter(Agency agency) {
        Place nearestCenter = null;
        float nearestDistance = Float.MAX_VALUE;
        int index = 0;
        for (Boolean isCenter : listPlaces) {
            Place center = map.getListPlaces().get(index);
            if (isCenter && isAvailable(center, agency)) {
                float distance = distFrom(agency.getLatitude(), agency.getLongitude(), center.getLatitude(), center.getLongitude());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestCenter = center;
                }
            }
            index++;
        }
        //TODO: vérifier si nearestCenter=null ??? Cela voudrait dire qu'il n'y a pas assez de centres pour accueillir toutes les agences
        if (solution.containsKey(nearestCenter)) {
            solution.get(nearestCenter).add(agency);
        } else {
            List<Agency> l = new ArrayList<Agency>();
            l.add(agency);
            solution.put(nearestCenter, l);
        }
    }
    
    private void removeUselessCenters() {
        int index = 0;
        for (Boolean isCenter : listPlaces) {
            if (isCenter && !solution.containsKey(map.getListPlaces().get(index))) {
                listPlaces.set(index, false);
            }
            index++;
        }
    }
    
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
    
    public float fitness() {
        float cost = 0;
        for (Place center : solution.keySet()) {
            cost += Model.CENTER_COST;
            for (Agency agency : solution.get(center)) {
                cost += Model.KM_COST * distFrom(center.getLatitude(), center.getLongitude(), agency.getLatitude(), agency.getLongitude());
            }
        }
        return cost;
    }

}
