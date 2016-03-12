import javax.swing.SwingUtilities;

import model.Model;
import viewController.Window;


public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Model model = new Model();
                Window window = new Window(model);
                window.setVisible(true);
            }
        });
    }
}
