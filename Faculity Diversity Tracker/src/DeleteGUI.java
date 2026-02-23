import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class DeleteGUI extends TemplateGUI {
    //puts each row as an area with a checkbox, meant to be scrollable. Panel wrapped in a scrollpane
    String[] deletionTypes;
    JCheckBox templateBox;
    JComboBox<String> typeSelectionBox;
    JScrollPane deletionSpot;
    GridBagConstraints gbc;
    ArrayList<JCheckBox> checkBoxesonScreen;
    SQLProcessor sQLpro;
    JButton confirm;
    JButton deleteAll;
    boolean first;
    JLabel exceptionMessenger;
    public DeleteGUI() throws Exception {
        super("Delete");
        super.window.setLayout(new GridBagLayout());
        this.templateBox = new JCheckBox();
        this.templateBox.setPreferredSize(new Dimension(1000000, 29));
        this.templateBox.setFont(new Font("Cambria", 4, 24));
        this.deletionTypes = new String[7];
        this.deletionTypes[0] = "Faculty Member"; //7
        this.deletionTypes[1] = "Event"; //5
        this.deletionTypes[2] = "Certificate"; //2
        this.deletionTypes[3] = "Employment"; //2
        this.deletionTypes[4] = "Faculty & Certificate"; //5
        this.deletionTypes[5] = "Event Attendence"; //2
        this.deletionTypes[6] = "Certificate & Event"; //2
        this.typeSelectionBox = new JComboBox<String>(deletionTypes);
        this.typeSelectionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                setExceptionMessenge("");
                setCheckBoxes();
                } catch (Exception e) {
                    setExceptionMessenge(e.getMessage());
                }
            }
        });
        this.deletionSpot = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         this.deletionSpot.setPreferredSize(new Dimension(1000, 850));
        this.deletionSpot.getVerticalScrollBar().setPreferredSize(new Dimension(20, 850));
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        super.window.getContentPane().add(this.deletionSpot, gbc);
        this.gbc.gridy = 0; 
        this.exceptionMessenger = new JLabel();
        super.window.getContentPane().add(this.exceptionMessenger, gbc);
        this.gbc.gridx = 1;
        super.window.getContentPane().add(this.typeSelectionBox, gbc);
        this.checkBoxesonScreen = new ArrayList<JCheckBox>();
        this.sQLpro = new SQLProcessor();
        this.confirm = new JButton("Confirm");
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                setExceptionMessenge("");
                deleteChecked();
                } catch (Exception ex) {
                    setExceptionMessenge(ex.getMessage());
                }
            }
        });
        super.window.getContentPane().add(confirm, gbc);
        this.deleteAll = new JButton("Delete All");
        this.deleteAll.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                setExceptionMessenge("");
                deleteAllData(); //on error checking stage, apply a pop-up that says "Are you sure about deleting all that data?"
                } catch (Exception e) {
                    //System.err.println(e);
                    setExceptionMessenge(e.getMessage());
                }
        }
        });
        gbc.gridx = 1;
        super.window.getContentPane().add(deleteAll, gbc);
        this.first = true;
    }
    public void selectType(String type) {
        this.typeSelectionBox.setSelectedItem(type);
    }
    public void setCheckBoxes() throws Exception {
        if(!this.checkBoxesonScreen.isEmpty()) {
            removeAll();
        } 
       String tobeSplit = sQLpro.showSpecific((String)this.typeSelectionBox.getSelectedItem());
       if(tobeSplit.equals("")) {
            JPanel panel = new JPanel();
            deletionSpot.setViewportView(panel);;
            throw new FDTException("Cannot show data to select for an empty table!");
       }
       String[] checkBoxText = tobeSplit.split("\n");
       JPanel panel = new JPanel();
       panel.setLayout(new GridBagLayout());
       deletionSpot.setViewportView(panel);
       for(int i = 0; i < checkBoxText.length; i++) {
        JCheckBox checkBoxi = new JCheckBox();
        checkBoxi.setText(checkBoxText[i]);
        checkBoxi.setPreferredSize(templateBox.getPreferredSize());
        checkBoxi.setFont(templateBox.getFont()); 
        gbc.gridy++;
        panel.add(checkBoxi, gbc);
        this.checkBoxesonScreen.add(checkBoxi);
       }
       this.deletionSpot.getVerticalScrollBar().setPreferredSize(new Dimension(20, this.sQLpro.getIndex()*29));
    }
    public void removeAll() {
        for (JCheckBox checkBox: this.checkBoxesonScreen) {
            deletionSpot.remove(checkBox);
        }
        this.checkBoxesonScreen.removeAll(this.checkBoxesonScreen);
    }
    public JFrame getDeleteWindow() {
        return super.window;
    }
    public String getDataDeletedType() {
        return (String)typeSelectionBox.getSelectedItem();
    }
    public void setDeleteType(String type) {
        typeSelectionBox.setSelectedItem(type);
    }
    public void deleteAllData() throws Exception {
        String graph = sQLpro.showSpecific((String)this.typeSelectionBox.getSelectedItem());
       if(graph.equals("")) {
            throw new FDTException("Cannot delete data from an empty table!");
       }
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete all your data for " + getDataDeletedType() + "?", "Confirm deletion?",
             JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sQLpro.deleteAll(getDataDeletedType());
            this.checkBoxesonScreen.removeAll(checkBoxesonScreen);
            setCheckBoxes();
        }
    }
    public void deleteChecked() throws Exception {
        for(int i = 1; i < this.checkBoxesonScreen.size() + 1; i++) {
            if(checkBoxesonScreen.get(i-1).isSelected() && getDataDeletedType().contains("&")) {
                String[] splitted = checkBoxesonScreen.get(i-1).getText().split(" ");
               if(getDataDeletedType().equals("Faculty & Certificate")) {
               sQLpro.delete(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[4]), getDataDeletedType());
               } else {
                sQLpro.delete(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), getDataDeletedType());
               } 
            } else if (checkBoxesonScreen.get(i-1).isSelected()) {
               sQLpro.delete(Integer.parseInt(checkBoxesonScreen.get(i-1).getText().split(" ")[0]), getDataDeletedType());
            }
        }
        setCheckBoxes(); 
    }
    public void setExceptionMessenge(String exception) {
        this.exceptionMessenger.setText(exception);
    }
}
