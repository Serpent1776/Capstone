import java.awt.*;
import javax.swing.*;

public class TemplateGUI { //for edit, search and delete who all get the same size as the default GUI
    JFrame window;

    public TemplateGUI(String title) {
        this.window = new JFrame(title);
        window.setSize(1500, 1000);
    }
    public void add(Component comp) {
        this.window.getContentPane().add(comp);
    }
    public void add(Component comp, Object constraints) {
        this.window.getContentPane().add(comp, constraints);
    }
    public void open() {
        this.window.setVisible(true);
    }
    public void close() {
        this.window.setVisible(false);
    }
}
