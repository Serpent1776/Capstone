import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class EditGUI extends TemplateGUI {
    /*copy text from textlabel when opened. 
    otherwise, it would use the showSpecific function.
    splits the information by space into a string array
    Makes JTextAreas of all the data and mods to put them in rows
    similar to InputGUI in style
    confirm works with SQL processor to update every row with its new varibles
    meant to be a panel that is vertically scrolled by it being wrapped in a scrollPane. 
    */
    JFrame window;
    JComboBox<String> editTypeSelector;
    String[] editTypes;
    JLabel[] onGui;
    ArrayList<JTextArea> textAreas;
    JTextArea template;
    JScrollPane scrollGlass;
    GridBagConstraints gbc;
    SQLProcessor sQLpro;
    JButton confirm;
    boolean first;
    JLabel exceptionMessenger;
    public EditGUI() throws Exception {
        super("Editing");
        //this.window = new JFrame("Editing");
        super.window.setLayout(new GridBagLayout());
        super.window.setSize(1500, 1000);
        this.editTypes = new String[7];
        this.editTypes[0] = "Faculty Member"; //7
        this.editTypes[1] = "Event"; //5
        this.editTypes[2] = "Certificate"; //2
        this.editTypes[3] = "Employment"; //2
        this.editTypes[4] = "Faculty & Certificate"; //5
        this.editTypes[5] = "Event Attendence"; //2
        this.editTypes[6] = "Certificate & Event"; //2
        this.editTypeSelector = new JComboBox<String>(editTypes);
        this.editTypeSelector.setFont(new Font("Cambria", 4, 16));
        this.editTypeSelector.setPreferredSize(new Dimension(175, 20));
        this.onGui = new JLabel[9];
        this.editTypeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try {
                graphToEditInputs();  
              } catch (Exception ex) {
                setExceptionMessenge(ex.getMessage());
              } 
            }
        });
        this.template = new JTextArea();
        this.template.setPreferredSize(new Dimension(600, 29));
        this.template.setFont(new Font("Cambria", 4, 24));
        this.template.setBounds(100, 29, 100, 29);
        this.textAreas = new ArrayList<JTextArea>(1000000);
        this.sQLpro = new SQLProcessor();
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridx = 1;
        super.window.getContentPane().add(editTypeSelector, gbc);
        this.gbc.gridx = 0;
        this.exceptionMessenger = new JLabel();
        super.window.getContentPane().add(this.exceptionMessenger, gbc);
        this.scrollGlass = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollGlass.setPreferredSize(new Dimension(1000, 850));
        this.scrollGlass.getVerticalScrollBar().setPreferredSize(new Dimension(20, 850));
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        super.window.getContentPane().add(scrollGlass, gbc);
        this.confirm = new JButton("Confirm");
        this.confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                confirmEdits();
                } catch(Exception e) {
                    setExceptionMessenge(e.getMessage());
                }
            }
        });
        this.gbc.gridy = 2;
        super.window.getContentPane().add(confirm, gbc);
    }
    public void setTypeonSelector(String type) throws Exception {
        this.editTypeSelector.setSelectedItem(type);
        //graphToEditInputs();
    }
    public void graphToEditInputs() throws Exception {
        if(!this.textAreas.isEmpty()) {
            removeAll();
        }
        String type = (String)this.editTypeSelector.getSelectedItem();
        String toBeSplit = this.sQLpro.showSpecific(type, "~");
        if(toBeSplit.equals("")) {
            JPanel panel = new JPanel();
            scrollGlass.setViewportView(panel);;
            throw new FDTException("Cannot edit an empty table!");
       }
        String[] firstSplit = toBeSplit.split("\n");
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        int div = 0;
        switch(type) {
            case "Faculty Member": div = 8; break;
            case "Event": div = 5; break;
            case "Certificate": div = 2; break;
            case "Employment": div = 2; break;
            case "Faculty & Certificate": div = 5; break;
            case "Event Attendence": div = 2; break;
            //case "Certificate & Event": div = 2; break;
            default:
                throw new FDTException("Certificate & Event is uneditable, use Delete and Add to edit!");
        }
        String[][] secondSplit = new String[firstSplit.length][div];
        for(int i = 0; i < firstSplit.length; i++) {
            secondSplit[i] = firstSplit[i].split("~");
        }
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        this.gbc.gridx = 0;
        switch(type) {
            case "Faculty Member": 
            JLabel id = new JLabel("Faculty ID");
            panel.add(id, gbc);
            this.onGui[0] = id;
            gbc.gridx++;
            JLabel lN = new JLabel("Last Name");
            panel.add(lN, gbc);
            this.onGui[1] = lN;
            gbc.gridx++;
            JLabel fN = new JLabel("First Name");
            panel.add(fN, gbc);
            this.onGui[2] = fN;
            gbc.gridx++;
            JLabel email = new JLabel("Email");
            panel.add(email, gbc);
            this.onGui[3] = email;
            gbc.gridx++;
            JLabel role = new JLabel("Role");
            panel.add(role, gbc);
            this.onGui[4] = role;
            gbc.gridx++;
            JLabel dept_div = new JLabel("Dept. Division");
            panel.add(dept_div, gbc);
            this.onGui[5] = dept_div;
            gbc.gridx++;
            JLabel epID = new JLabel("Employment ID");
            panel.add(epID, gbc);
            this.onGui[6] = epID;
            gbc.gridx++;
            JLabel bIPOC = new JLabel("BIPOC?");
            panel.add(bIPOC, gbc);
            this.onGui[7] = bIPOC;
            gbc.gridx++;
            JLabel gender = new JLabel("Gender");
            panel.add(gender, gbc);
            this.onGui[8] = gender;
            break; //7
            case "Event": 
            JLabel eID = new JLabel("Event ID");
            panel.add(eID, gbc);
            this.onGui[0] = eID;
            gbc.gridx++;
            JLabel eN = new JLabel("Name of Event");
            panel.add(eN, gbc);
            this.onGui[1] = eN;
            gbc.gridx++;
            JLabel eDate = new JLabel("Event Date");
            panel.add(eDate, gbc);
            this.onGui[2] = eDate;
            gbc.gridx++;
            JLabel eventType = new JLabel("Event Type");
            panel.add(eventType, gbc);
            this.onGui[3] = eventType;
            gbc.gridx++;
            JLabel requirement = new JLabel("Requirement");
            panel.add(requirement, gbc);
            this.onGui[4] = requirement;
            gbc.gridx++;
            JLabel notes = new JLabel("notes");
            panel.add(notes, gbc);
            this.onGui[5] = notes;
            break; //5
            case "Certificate":
            JLabel certID = new JLabel("Certificate ID");
            panel.add(certID, gbc);
            this.onGui[0] = certID;
            gbc.gridx++;
            JLabel noc = new JLabel("Name of Certificate");
            panel.add(noc, gbc);
            this.onGui[1] = noc;
            gbc.gridx++;
            JLabel cT = new JLabel("Certificate Type");
            panel.add(cT, gbc);  
            this.onGui[2] = cT;
            break; //2
            case "Employment":
            JLabel EmID = new JLabel("Employmee ID");
            panel.add(EmID, gbc);
            this.onGui[0] = EmID;
            gbc.gridx++;
            JLabel appID = new JLabel("Application ID");
            panel.add(appID, gbc); 
            this.onGui[1] = appID;
            gbc.gridx++;
            JLabel desc = new JLabel("Description");
            panel.add(desc, gbc); 
            this.onGui[2] = desc;
            break; //2
            case "Faculty & Certificate":
            JLabel lNos = new JLabel("Staff ID");
            panel.add(lNos, gbc);
            this.onGui[0] = lNos;
            gbc.gridx++;
            JLabel bDate = new JLabel("Bronze Date");
            panel.add(bDate, gbc);
            this.onGui[1] = bDate;
            gbc.gridx++;
            JLabel sDate = new JLabel("Silver Date");
            panel.add(sDate, gbc);
            this.onGui[2] = sDate;
            gbc.gridx++;
            JLabel gDate = new JLabel("Gold date");
            panel.add(gDate, gbc);
            this.onGui[3] = gDate;
            gbc.gridx++;
            JLabel cName = new JLabel("Certificate ID");
            panel.add(cName, gbc);  
            this.onGui[4] = cName;
            break; //5
            case "Event Attendence":
            JLabel lNs = new JLabel("Staff ID");
            panel.add(lNs, gbc);
            this.onGui[0] = lNs;
            gbc.gridx++;
            JLabel nE = new JLabel("Event ID");
            panel.add(nE, gbc);
            this.onGui[1] = nE;
            break; //2
            case "Certificate & Event":
            JLabel cne = new JLabel("Certificate ID");
            panel.add(cne, gbc);
            this.onGui[0] = cne;
            gbc.gridx++;
            JLabel ne = new JLabel("Event ID");
            panel.add(ne, gbc);
            this.onGui[1] = ne;
            break; //2   
        }
        this.gbc.gridy = 2;
        this.gbc.gridx = 0;
        this.scrollGlass.setViewportView(panel);
        for (int i = 0; i < secondSplit.length; i++) {
            for (int u = 0; u < secondSplit[i].length; u++) {
            JTextArea iJTextArea = new JTextArea(secondSplit[i][u]);
            iJTextArea.setPreferredSize(this.template.getPreferredSize());
            iJTextArea.setFont(this.template.getFont());
            if(((u == 0 || u == 4) && type.equals("Faculty & Certificate"))
            || u == 0) {
                iJTextArea.setEditable(false);
                iJTextArea.setOpaque(false);
                iJTextArea.getCaret().setVisible(false);
            }
            panel.add(iJTextArea, gbc);
            this.textAreas.add(iJTextArea);
            gbc.gridx++;
            }
            gbc.gridx = 0;
            gbc.gridy++; 
        }
        this.scrollGlass.getVerticalScrollBar().setPreferredSize(new Dimension(20, this.sQLpro.getIndex()*29));
        /*this.window.remove(this.scrollGlass);
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.window.add(this.scrollGlass, gbc);*/
        //System.out.println(this.textAreas.size());
        //this.scrollGlass.getVerticalScrollBar().setValue(this.scrollGlass.getVerticalScrollBar().getMaximum());
    }
    public void removeAll() {
        for (JTextArea textArea : this.textAreas) {
            super.window.getContentPane().remove(textArea);
        }
        this.textAreas.removeAll(textAreas);
        for (int i = 0; i < this.onGui.length; i++) {
            super.window.getContentPane().remove(this.onGui[i]);
        }
    }
    public void open() {
        super.window.setVisible(true);
    }
    public void confirmEdits() throws Exception {
        String type = (String)this.editTypeSelector.getSelectedItem();
        int div = 0;
        switch(type) {
            case "Faculty Member": div = 8; break;
            case "Event": div = 5; break;
            case "Certificate": div = 2; break;
            case "Employment": div = 2; break;
            case "Faculty & Certificate": div = 4; break;
            case "Event Attendence": div = 2; break;
            //case "Certificate & Event": div = 2; break;
        }
        String[][] textAreaStrings = new String[this.textAreas.size()/div][div];
        for(int i = 0; i < textAreaStrings.length; i++) {
            for(int u = 0; u < div; u++) {
               textAreaStrings[i][u] = textAreas.get(u + i*div).getText();
            }
        }
        sQLpro.update(textAreaStrings, type);
    }
    public JFrame getEditingWindow() {
        return super.window;
    }
    public String getEditedDataType() {
        return (String)this.editTypeSelector.getSelectedItem();
    }
    public void setExceptionMessenge(String messenge) {
        this.exceptionMessenger.setText(messenge);
    }
}
