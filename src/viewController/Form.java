package viewController;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.geneticAlgorithm.GeneticAlgorithm;

public class Form extends JPanel {

    private static final long serialVersionUID = -1052244012517863332L;

    private static final String POPULATION_SIZE = "Taille de la population";

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    private JLabel populationSizeLabel;

    private JTextField populationSizeField;

    private GeneticAlgorithm geneticAlgorithm;

    public Form(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        build();
    }

    private void build() {
        populationSizeLabel = new JLabel(POPULATION_SIZE);
        populationSizeField = new JTextField(5);

        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(populationSizeLabel);

        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(populationSizeField);

        add(labelPane, BorderLayout.LINE_START);
        add(fieldPane, BorderLayout.CENTER);

        JButton jButton = new JButton("Commencer");
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                executor.execute(new Runnable() {
                    public void run() {
                        geneticAlgorithm.start();
                    }
                });
            }
        });
        add(jButton, BorderLayout.PAGE_END);
    }

}
