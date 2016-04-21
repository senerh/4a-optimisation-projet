package model.history;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.geneticAlgorithm.GeneticAlgorithm;

public class History {

    private static final String FILENAME = "history.csv";
    private static final String DELIMITER = ";";
    private GeneticAlgorithm geneticAlgorithm;
    private List<Line> listLines;

    public History(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
        listLines = new ArrayList<Line>();
    }

    public void add() {
        listLines.add(new Line(geneticAlgorithm));
    }

    public void save() throws IOException {
        String path = System.getProperty("user.dir") + "/"+ FILENAME;
        FileWriter fw = new FileWriter(path);

        BufferedWriter output = new BufferedWriter(fw);

        int populationSize = listLines.get(0).getListFitness().size();
        int generation = 0;
        output.write(header(populationSize));
        for (Line line : listLines) {
            output.write(lineToString(line, generation));
            generation++;
        }

        output.flush();
        output.close();
    }
    
    private String header(int popultaionSize) {
        String s = "";
        s += "\"Generation\"";
        s += DELIMITER + "\"Taille de la population\"";
        s += DELIMITER + "\"Taux de mutation\"";
        s += DELIMITER + "\"Taille de la population gardee\"";
        s += DELIMITER + "\"Taille de la population mutee\"";
        for (int i=1; i<=popultaionSize; i++) {
            s += DELIMITER + "\"Solution " + i + "\"";
        }
        s += "\r\n";
        return s;
    }
    
    private String lineToString(Line line, int generation) {
        String s = "";
        s += generation;
        s += DELIMITER + line.getPopulationSize();
        s += DELIMITER + line.getMutationRate();
        s += DELIMITER + line.getKeptPopulationSize();
        s += DELIMITER + line.getMutatedPopulationSize();
        for (Float fitness : line.getListFitness()) {
            s += DELIMITER + fitness;
        }
        s += "\r\n";
        return s;
    }
}
