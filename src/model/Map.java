package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Map extends Observable {
    
    private List<Agency> listAgencies;

    public Map() {
        this.listAgencies = new ArrayList<Agency>();
    }
    
    public void loadAgencies(String fileName) throws IOException {
        BufferedReader file;
        String line;

        file = new BufferedReader(new FileReader(fileName));
        
        line = file.readLine();
        
        List<Agency> listAgencies = new ArrayList<Agency>();
        
        while ((line = file.readLine()) != null) {
            listAgencies.add(lineToAgency(line));
        }
        
        this.listAgencies = listAgencies;
        
        file.close();
        
        setChanged();
        notifyObservers();
    }
    
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

    public List<Agency> getListAgencies() {
        return listAgencies;
    }

}
