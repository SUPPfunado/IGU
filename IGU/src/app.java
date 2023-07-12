import javax.swing.SwingUtilities;

import package1.AplicacionCRUD;

public class app {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AplicacionCRUD frame = new AplicacionCRUD();
                frame.setVisible(true);
            }
        });
    }
}


