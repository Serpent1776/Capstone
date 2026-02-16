import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ReferenceGUI {
    JLabel[] Displayedcontent;
    JFrame window;
    GridBagConstraints gbc;

    public ReferenceGUI(JLabel[] contents) {
        this.window = new JFrame("Reference");
        this.window.setSize(300, 300);
        this.Displayedcontent = new JLabel[contents.length];
        this.window.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        for(int i = 0; i < contents.length; i++) {
            this.window.getContentPane().add(contents[i], gbc);
            Displayedcontent[i] = contents[i];
            this.gbc.gridy++;
        }
    }
    public void reset(JLabel[] contents) {
        removeContent();
        this.Displayedcontent = new JLabel[contents.length];
         for(int i = 0; i < contents.length; i++) {
            this.window.getContentPane().add(contents[i], gbc);
            Displayedcontent[i] = contents[i];
            this.gbc.gridy++;
        }
    }
    public void removeContent() {
         for (JLabel jLabel : Displayedcontent) {
            this.window.getContentPane().remove(jLabel);
        }
    }
    public void open() {
        this.window.setVisible(true);
    }
}
