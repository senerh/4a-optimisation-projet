package viewController;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import model.geneticAlgorithm.GeneticAlgorithm;

public class Informations extends JPanel implements Observer {

    private static final long serialVersionUID = 5121913232324067217L;
    
    private JLabel titleLabel;
    private JLabel fitnessLabel;
    private JLabel nbCentersLabel;
    private JLabel nbSeatsLabel;
    private JLabel nbUsedSeatsLabel;
    private JLabel nbUnusedSeatsLabel;
    private JLabel totalDistanceLabel;
    
    private String titleString = "<html><h3>Informations sur la solution</h3></html>";
    private String fitnessString = "Cout : ";
    private String nbCentersString = "Nombre de centres : ";
    private String nbSeatsString = "Nombre de places : ";
    private String nbUsedSeatsString = "Nombre de places utilisées : ";
    private String nbUnusedSeatsString = "Nombre de places inutilisées : ";
    private String totalDistanceString = "Distance totale parcourue : ";

    private GeneticAlgorithm geneticAlgorithm;
    
    public Informations(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
        geneticAlgorithm.addObserver(this);
        setLayout(new GridLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        build();
    }
    
    private void build() {
        titleLabel = new JLabel(titleString, JLabel.CENTER);
        fitnessLabel = new JLabel(fitnessString);
        nbCentersLabel = new JLabel(nbCentersString);
        nbSeatsLabel = new JLabel(nbSeatsString);
        nbUsedSeatsLabel = new JLabel(nbUsedSeatsString);
        nbUnusedSeatsLabel = new JLabel(nbUnusedSeatsString);
        totalDistanceLabel = new JLabel(totalDistanceString);

        JPanel component = new JPanel(new GridLayout(0, 1));
        component.add(titleLabel);
        component.add(fitnessLabel);
        component.add(nbCentersLabel);
        component.add(nbSeatsLabel);
        component.add(nbUsedSeatsLabel);
        component.add(nbUnusedSeatsLabel);
        component.add(totalDistanceLabel);
        add(component);
    }

    public void update(Observable arg0, Object arg1) {
        fitnessLabel.setText(fitnessString + geneticAlgorithm.getBestSolution().getFitness() + " €");
        nbCentersLabel.setText(nbCentersString + geneticAlgorithm.getBestSolution().getSolution().size());
        nbSeatsLabel.setText(nbSeatsString + geneticAlgorithm.getBestSolution().getSolution().size() * Model.NB_PERSONS_BY_CENTER);
        nbUsedSeatsLabel.setText(nbUsedSeatsString + geneticAlgorithm.getNbPersons());
        int n = geneticAlgorithm.getBestSolution().getSolution().size() * Model.NB_PERSONS_BY_CENTER - geneticAlgorithm.getNbPersons();
        nbUnusedSeatsLabel.setText(nbUnusedSeatsString + n);
        totalDistanceLabel.setText(totalDistanceString + geneticAlgorithm.getBestSolution().getTotalDistance() + " km");
    }

}
