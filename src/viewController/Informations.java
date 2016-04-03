package viewController;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

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
    
    private String titleString = "<html><h3>Informations sur la solution</h3></html>";
    private String fitnessString = "Cout : ";
    private String nbCentersString = "Nombre de centres : ";
    private String nbSeatsString = "Nombre de places : ";
    private String nbUsedSeatsString = "Nombre de places utilisées : ";
    private String nbUnusedSeatsString = "Nombre de places inutilisées : ";

    private GeneticAlgorithm geneticAlgorithm;
    
    public Informations(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
        geneticAlgorithm.addObserver(this);
        build();
    }
    
    private void build() {
        JPanel component = new JPanel(new GridLayout(6, 1));
        titleLabel = new JLabel(titleString);
        fitnessLabel = new JLabel(fitnessString);
        nbCentersLabel = new JLabel(nbCentersString);
        nbSeatsLabel = new JLabel(nbSeatsString);
        nbUsedSeatsLabel = new JLabel(nbUsedSeatsString);
        nbUnusedSeatsLabel = new JLabel(nbUnusedSeatsString);
        
        component.add(titleLabel);
        component.add(fitnessLabel);
        component.add(nbCentersLabel);
        component.add(nbSeatsLabel);
        component.add(nbUsedSeatsLabel);
        component.add(nbUnusedSeatsLabel);
        
        add(component);
    }

    public void update(Observable arg0, Object arg1) {
        fitnessLabel.setText(fitnessString + geneticAlgorithm.getBestSolution().getFitness() + " €");
        nbCentersLabel.setText(nbCentersString + geneticAlgorithm.getBestSolution().getSolution().size());
        nbSeatsLabel.setText(nbSeatsString + geneticAlgorithm.getBestSolution().getSolution().size() * Model.NB_PERSONS_BY_CENTER);
        nbUsedSeatsLabel.setText(nbUsedSeatsString + geneticAlgorithm.getNbPersons());
        int n = geneticAlgorithm.getBestSolution().getSolution().size() * Model.NB_PERSONS_BY_CENTER - geneticAlgorithm.getNbPersons();
        nbUnusedSeatsLabel.setText(nbUnusedSeatsString + n);
    }

}
