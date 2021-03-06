package viewController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.geneticAlgorithm.GeneticAlgorithm;

public class Form extends JPanel {

    private static final long serialVersionUID = -1052244012517863332L;

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    private String startString = "Démarrer";
    private String updateString = "Modifier";
    private String titleString = "<html><h3>Algorithme génétique</h3></html>";
    private String populationSizeString = "Taille de la population : ";
    private String mutationRateString = "Taux de mutation : ";
    private String keptPopulationSizeString = "Taille de la population gardée : ";
    private String mutatedPopulationSizeString = "Taille de la population mutée : ";
    private String stopString = "Arreter";

    private JLabel titleLabel;
    private JLabel populationSizeLabel;
    private JLabel mutationRateLabel;
    private JLabel keptPopulationSizeLabel;
    private JLabel mutatedPopulationSizeLabel;

    private JTextField populationSizeField;
    private JTextField mutationRateField;
    private JTextField keptPopulationSizeField;
    private JTextField mutatedPopulationSizeField;

    private JButton startButton;
    private JButton stopButton;

    private GeneticAlgorithm geneticAlgorithm;

    public Form(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
        build();
    }

    private void build() {
        titleLabel = new JLabel(titleString);
        populationSizeLabel = new JLabel(populationSizeString);
        mutationRateLabel = new JLabel(mutationRateString);
        keptPopulationSizeLabel = new JLabel(keptPopulationSizeString);
        mutatedPopulationSizeLabel = new JLabel(mutatedPopulationSizeString);

        populationSizeField = new JTextField("100", 5);
        mutationRateField = new JTextField("0.001", 5);
        keptPopulationSizeField = new JTextField("50", 5);
        mutatedPopulationSizeField = new JTextField("30", 5);

        startButton = new JButton(startString);
        startButton.addActionListener(new StartController());
        
        stopButton = new JButton(stopString);
        stopButton.addActionListener(new StopController());
        stopButton.setEnabled(false);

        JPanel labelsContainer = new JPanel(new GridLayout(0, 1, 0, 10));
        labelsContainer.add(populationSizeLabel);
        labelsContainer.add(mutationRateLabel);
        labelsContainer.add(keptPopulationSizeLabel);
        labelsContainer.add(mutatedPopulationSizeLabel);

        JPanel fieldsContainer = new JPanel(new GridLayout(0, 1, 0, 10));
        fieldsContainer.add(populationSizeField);
        fieldsContainer.add(mutationRateField);
        fieldsContainer.add(keptPopulationSizeField);
        fieldsContainer.add(mutatedPopulationSizeField);

        JPanel formContainer = new JPanel();
        formContainer.add(labelsContainer);
        formContainer.add(fieldsContainer);
        add(titleLabel);
        add(formContainer);
        add(startButton);
        add(stopButton);
    }

    class StartController implements ActionListener {
        
        private int populationSize;
        private float mutationRate;
        private int keptPopulationSize;
        private int mutatedPopulationSize;
        
        private boolean checkAgencies() {
            if (geneticAlgorithm.getMap().getListAgencies().isEmpty()) {
                JOptionPane.showMessageDialog(Form.this,
                        "Veuillez selectionner un jeu de données pour les agences.",
                        "Il n'y a pas d'agence à affecter",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }

        private boolean checkPopulationSize() {
            try {
                populationSize = Integer.parseInt(populationSizeField.getText());
                if (populationSize < 10) {
                    displayError("La taille de la population doit être supérieur ou égale à 10.");
                    return false;
                }
            } catch (NumberFormatException e1) {
                displayError("La taille de la population doit être un entier.");
                return false;
            }
            return true;
        }
        
        private boolean checkMutationRate() {
            try {
                mutationRate = Float.parseFloat(mutationRateField.getText());
                if (mutationRate < 0 || mutationRate > 1) {
                    displayError("Le taux de mutation doit être compris entre 0 et 1.");
                    return false;
                }
            } catch (NumberFormatException e1) {
                displayError("Le taux de mutation doit être un nombre décimale.");
                return false;
            }
            return true;
        }

        private boolean checkKeptPopulationSize() {
            try {
                keptPopulationSize = Integer.parseInt(keptPopulationSizeField.getText());
                if (keptPopulationSize < 1 || keptPopulationSize > populationSize) {
                    displayError("La taille de la population gardée doit être comprise entre 1 et " + populationSize + ".");
                    return false;
                }
            } catch (NumberFormatException e1) {
                displayError("La taille de la population gardée doit être un entier.");
                return false;
            }
            return true;
        }

        private boolean checkMutatedPopulationSize() {
            try {
                mutatedPopulationSize = Integer.parseInt(mutatedPopulationSizeField.getText());
                if (mutatedPopulationSize < 0 || mutatedPopulationSize > populationSize) {
                    displayError("La taille de la population mutée doit être comprise entre 0 et " + populationSize + ".");
                    return false;
                }
            } catch (NumberFormatException e1) {
                displayError("La taille de la population mutée doit être un entier.");
                return false;
            }
            return true;
        }
        
        private void displayError(String msg) {
            JOptionPane.showMessageDialog(
                    Form.this,
                    msg,
                    "Données du formulaires incorrectes.",
                    JOptionPane.ERROR_MESSAGE);
        }
        
        public void actionPerformed(ActionEvent e) {
            if (checkAgencies()) {
                if (geneticAlgorithm.getIsStarted()) {
                    if (checkMutationRate() && checkKeptPopulationSize() && checkMutatedPopulationSize()) {
                        geneticAlgorithm.updateParameters(mutationRate, keptPopulationSize, mutatedPopulationSize);
                    }
                } else {
                    if (checkPopulationSize() && checkMutationRate() && checkKeptPopulationSize() && checkMutatedPopulationSize()) {
                        executor.execute(new Runnable() {
                            public void run() {
                                geneticAlgorithm.start(populationSize, mutationRate, keptPopulationSize, mutatedPopulationSize);
                            }
                        });
                        populationSizeField.setEditable(false);
                        startButton.setText(updateString);
                        stopButton.setEnabled(true);
                    }
                }
            }
        }
    }
    
    class StopController implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            geneticAlgorithm.stop();
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
            
            try {
                geneticAlgorithm.getHistory().save();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        Form.this,
                        "Vérifiez que vous disposez des droits d'écriture dans le dossier de l'application.",
                        "Impossible d'enregistrer l'historique.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

}
