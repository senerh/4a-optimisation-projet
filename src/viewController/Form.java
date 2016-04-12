package viewController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private String titleString = "<html><h3>Algorithme génétique</h3></html>";
    private String populationSizeString = "Taille de la population : ";
    private String mutationRateString = "Taux de mutation : ";
    private String keptPopulationSizeString = "Taille de la population gardée : ";

    private JLabel titleLabel;
    private JLabel populationSizeLabel;
    private JLabel mutationRateLabel;
    private JLabel keptPopulationSizeLabel;

    private JTextField populationSizeField;
    private JTextField mutationRateField;
    private JTextField keptPopulationSizeField;

    private JButton startButton;

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

        populationSizeField = new JTextField("100", 5);
        mutationRateField = new JTextField("0.01", 5);
        keptPopulationSizeField = new JTextField("10", 5);

        startButton = new JButton(startString);
        startButton.addActionListener(new Controller());

        JPanel labelsContainer = new JPanel(new GridLayout(0, 1, 0, 10));
        labelsContainer.add(populationSizeLabel);
        labelsContainer.add(mutationRateLabel);
        labelsContainer.add(keptPopulationSizeLabel);

        JPanel fieldsContainer = new JPanel(new GridLayout(0, 1, 0, 10));
        fieldsContainer.add(populationSizeField);
        fieldsContainer.add(mutationRateField);
        fieldsContainer.add(keptPopulationSizeField);

        JPanel formContainer = new JPanel();
        formContainer.add(labelsContainer);
        formContainer.add(fieldsContainer);
        add(titleLabel);
        add(formContainer);
        add(startButton);
    }

    class Controller implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                final int populationSize = Integer.parseInt(populationSizeField.getText());
                final float mutationRate = Float.parseFloat(mutationRateField.getText());
                final int keptPopulationSize = Integer.parseInt(keptPopulationSizeField.getText());
                executor.execute(new Runnable() {
                    public void run() {
                        geneticAlgorithm.start(populationSize, mutationRate, keptPopulationSize);
                    }
                });
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(Form.this,
                        "Données saisies incorrectes",
                        "Mais respecte toi....",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
