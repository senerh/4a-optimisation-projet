package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Agency;
import model.Map;
import model.Model;
import model.Place;

public class Solution {

    private Map map;

    private List<Boolean> listPlaces;
    //TODO: hashmap<centres, listPlaces>
    private List<Place> listCenters;

    private Random random;

    public Solution(Map map, List<Boolean> listPlaces) {
        this.map = map;
        this.listPlaces = listPlaces;
        random = new Random();
    }

    public Solution(Map map) {
        this.map = map;
        random = new Random();
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
            int r = random.nextInt(nbPlaces);
            if (!listPlaces.get(r)) {
                listPlaces.set(r, true);
                nbCenters++;
            }
        }
    }

    private void associateAgencies() {
        List<Agency> listAgencies = new ArrayList<Agency>(map.getListAgencies());
        while (!listAgencies.isEmpty()) {
            int r = random.nextInt(listAgencies.size());
            //TODO: affecter au centre le plus proche
            listAgencies.remove(r);
        }
    }

    private void associateNearestCenter(Agency agency) {
        Place nearestCenter;
        float nearestDistance = Float.MAX_VALUE;
        int index = 0;
        for (Boolean isCenter : listPlaces) {
            if (isCenter) {
                Place center = map.getListPlaces().get(index);
                
                float distance = distFrom(agency.getLatitude(), agency.getLongitude(), center.getLatitude(), center.getLongitude());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestCenter = center;
                }
            }
            index++;
        }
    }

    private float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

}
