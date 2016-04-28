package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Map extends Observable {
    
    private List<Agency> listAgencies;
    private List<Place> listPlaces;
    private int nbPersons;

    /**
     * Represent the HashMap of agencies for a city
     */
    public Map() {
        listAgencies = new ArrayList<Agency>();
        listPlaces = new ArrayList<Place>();
        nbPersons = 0;
    }

    /**
     * Fill in the list of agencies with the given file (jeu de test)
     * @param fileName
     * @throws IOException
     */
    public void loadAgencies(String fileName) throws IOException {
        BufferedReader file;
        String line;

        file = new BufferedReader(new FileReader(fileName));
        
        line = file.readLine();
        
        List<Agency> listAgencies = new ArrayList<Agency>();
        nbPersons = 0;
        while ((line = file.readLine()) != null) {
            Agency agency = lineToAgency(line);
            listAgencies.add(agency);
            nbPersons += agency.getNbPersons();
        }
        
        this.listAgencies = listAgencies;
        
        file.close();
        
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @param line
     * @return an agency with all its attributes
     * @throws IOException
     */
    private Agency lineToAgency(String line) throws IOException {
        String[] splitedLine = line.split(";");
        if (splitedLine.length != 6) {
            throw new IOException("Bad line format");
        }
        String id = splitedLine[0].substring(1, splitedLine[0].length() - 1);
        String name = splitedLine[1].substring(1, splitedLine[1].length() - 1);
        String postalCode = splitedLine[2].substring(1, splitedLine[2].length() - 1);
        float longitude = Float.parseFloat(splitedLine[3]);
        float latitude = Float.parseFloat(splitedLine[4]);
        int nbPersons = Integer.parseInt(splitedLine[5]);
        
        return new Agency(id, name, postalCode, longitude, latitude, nbPersons);
    }

    /**
     * Fill in the list of cities with the given file (jeu de test)
     * @param fileName
     * @throws IOException
     */
    public void loadPlaces(URL fileName) throws IOException {
        BufferedReader file;
        String line;

        file = new BufferedReader(new BufferedReader(new InputStreamReader(fileName.openStream())));
        
        line = file.readLine();
        
        List<Place> listPlaces = new ArrayList<Place>();
        
        while ((line = file.readLine()) != null) {
            listPlaces.add(lineToPlace(line));
        }
        
        this.listPlaces = listPlaces;
        
        file.close();
    }

    /**
     *
     * @param line
     * @return a place (city) with all its attributes
     * @throws IOException
     */
    private Place lineToPlace(String line) throws IOException {
        String[] splitedLine = line.split(";");
        if (splitedLine.length != 5) {
            throw new IOException("Bad line format");
        }
        String id = splitedLine[0].substring(1, splitedLine[0].length() - 1);
        String name = splitedLine[1].substring(1, splitedLine[1].length() - 1);
        String postalCode = splitedLine[2].substring(1, splitedLine[2].length() - 1);
        float longitude = Float.parseFloat(splitedLine[3]);
        float latitude = Float.parseFloat(splitedLine[4]);
        
        return new Place(id, name, postalCode, longitude, latitude);
    }

    public List<Agency> getListAgencies() {
        return listAgencies;
    }

    public List<Place> getListPlaces() {
        return listPlaces;
    }
    
    public int getNbPersons() {
        return nbPersons;
    }

}
