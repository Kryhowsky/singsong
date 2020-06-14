import GUIclasses.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        GUI mainGUI = new GUI();

        SwingUtilities.invokeLater(() -> mainGUI.createAndShowGUI());
    }
}
