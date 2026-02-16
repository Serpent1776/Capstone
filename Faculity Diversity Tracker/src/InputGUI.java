import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class InputGUI {
    JFrame inputWindow;
    String[] inputSizes;
    String[] inputTypes;
    String ddSize;
    JComboBox<String> dropDownSize;
    int ddTypeSize;
    String inputType;
    JComboBox<String> dropDownType;
    GridBagConstraints gbc;
    JTextArea inputText;
    JTextArea[][] inputTexts;
    JButton addButton;
    SQLProcessor SQLpro;
    JLabel[] onGui;
    JScrollPane scrollWindow;
    public InputGUI() throws Exception {
        this.inputType = "Faculty Member";
        this.onGui = new JLabel[7];
        this.SQLpro = new SQLProcessor();
        this.inputWindow = new JFrame("Input");
        this.inputWindow.setSize(1500, 1000);
        this.inputWindow.setLayout(new GridBagLayout());
        this.inputSizes = new String[3];
        this.inputSizes[0] = "1";
        this.inputSizes[1] = "5";
        this.inputSizes[2] = "10";
        this.dropDownSize = new JComboBox<String>(this.inputSizes);
        this.dropDownSize.setPreferredSize(new Dimension(50, 20));
        this.dropDownSize.setFont(new Font("Cambria", 4, 16));
        this.dropDownSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> dropDown = (JComboBox<String>)e.getSource();
                String num = (String)dropDown.getSelectedItem();
                listDemolish();
                setDdSize(num);
                listSummon();
            }
        });
        this.inputTypes = new String[7]; //widths below:
        this.inputTypes[0] = "Faculty Member"; //7
        this.inputTypes[1] = "Event"; //5
        this.inputTypes[2] = "Certificate"; //2
        this.inputTypes[3] = "Employment"; //2
        this.inputTypes[4] = "Faculty & Certificate"; //5
        this.inputTypes[5] = "Event Attendence"; //2
        this.inputTypes[6] = "Certificate & Event"; //2
        this.dropDownType = new JComboBox<String>(this.inputTypes);
        this.dropDownType.setPreferredSize(new Dimension(175, 20));
        this.dropDownType.setFont(new Font("Cambria", 4, 16));
        this.dropDownType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> dropDown = (JComboBox<String>)e.getSource();
                int num = dropDown.getSelectedIndex();
                String ddString = inputTypes[num];
                switch (num) {
                    case 0: num = 7; break;
                    case 1: num = 5; break;
                    case 4: num = 5; break;
                    default: num = 2; break;
                }
                listDemolish();
                setDdTypeSize(num);
                setInputType(ddString);
                listSummon();
            }
        });
        this.inputText = new JTextArea(" ");
        this.inputText.setPreferredSize(new Dimension(300, 20));
        this.inputText.setFont(new Font("Cambria", 4, 16));
        //this.inputText.setLineWrap(true);
        this.addButton = new JButton("Add");
        this.addButton.setPreferredSize(new Dimension(60, 36));
        this.addButton.setFont(new Font("Cambria", 4, 12));
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                SQLpro.add(inputType, listgetString());
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        });
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5); // spacing
        this.gbc.gridx = 1;
        this.gbc.anchor = GridBagConstraints.NORTH;
        this.inputWindow.add(dropDownSize, this.gbc);
        this.gbc.gridx = 2;
        this.inputWindow.add(dropDownType, this.gbc);
        this.gbc.gridy = 1500;
        this.gbc.gridx = 1000;
        this.inputWindow.add(addButton, this.gbc);
        this.inputTexts = new JTextArea[10][7];
        this.ddSize = "1";
        this.ddTypeSize = 7;
        this.scrollWindow = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.scrollWindow.setPreferredSize(new Dimension(1000, 850));
        this.inputWindow.add(scrollWindow, this.gbc);
        listSummon();
        //this.inputWindow.getContentPane().add(this.dropDownSize, BorderLayout.NORTH);
        //this.inputWindow.getContentPane().add(this.dropDownType, BorderLayout.NORTH);
    }
    public void changeSize(int inputs) {

    }
    public void open() {
        this.inputWindow.setVisible(true);
    }
    public JTextArea dupe() {
        JTextArea dupeArea = new JTextArea();
        dupeArea.setFont(this.inputText.getFont());
        dupeArea.setPreferredSize(inputText.getPreferredSize());
        return dupeArea;
    }
    public void setDdSize(String ddSize) {
        this.ddSize = ddSize;
    }
    public void setDdTypeSize(int ddTypeSize) {
        this.ddTypeSize = ddTypeSize;
    }
    public void listSummon() {
        gbc.gridy = 1;
        gbc.gridx = 0;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        switch(this.inputType) {
            case "Faculty Member": 
            JLabel lN = new JLabel("Last Name");
            panel.add(lN, gbc);
            this.onGui[0] = lN;
            gbc.gridx++;
            JLabel fN = new JLabel("First Name");
            panel.add(fN, gbc);
            this.onGui[1] = fN;
            gbc.gridx++;
            JLabel email = new JLabel("Email");
            panel.add(email, gbc);
            this.onGui[2] = email;
            gbc.gridx++;
            JLabel role = new JLabel("Role");
            panel.add(role, gbc);
            this.onGui[3] = role;
            gbc.gridx++;
            JLabel dept_div = new JLabel("Dept. Division");
            panel.add(dept_div, gbc);
            this.onGui[4] = dept_div;
            gbc.gridx++;
            JLabel bIPOC = new JLabel("BIPOC?");
            panel.add(bIPOC, gbc);
            this.onGui[5] = bIPOC;
            gbc.gridx++;
            JLabel gender = new JLabel("Gender");
            panel.add(gender, gbc);
            this.onGui[6] = gender;
            break; //7
            case "Event": 
            JLabel eN = new JLabel("Name of Event");
            panel.add(eN, gbc);
            this.onGui[0] = eN;
            gbc.gridx++;
            JLabel eDate = new JLabel("Event Date");
            panel.add(eDate, gbc);
            this.onGui[1] = eDate;
            gbc.gridx++;
            JLabel eventType = new JLabel("Event Type");
            panel.add(eventType, gbc);
            this.onGui[2] = eventType;
            gbc.gridx++;
            JLabel requirement = new JLabel("Requirement");
            panel.add(requirement, gbc);
            this.onGui[3] = requirement;
            gbc.gridx++;
            JLabel notes = new JLabel("notes");
            panel.add(notes, gbc);
            this.onGui[4] = notes;
            break; //5
            case "Certificate":
            JLabel noc = new JLabel("Name of Certificate");
            panel.add(noc, gbc);
            this.onGui[0] = noc;
            gbc.gridx++;
            JLabel cT = new JLabel("Certificate Type");
            panel.add(cT, gbc);  
            this.onGui[1] = cT;
            break; //2
            case "Employment":
            JLabel appID = new JLabel("Application ID");
            panel.add(appID, gbc); 
            this.onGui[0] = appID;
            gbc.gridx++;
            JLabel desc = new JLabel("Description");
            panel.add(desc, gbc); 
            this.onGui[1] = desc;
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
        //this.gbc.gridy = 2;
        for (int i = 0; i < this.inputTexts.length && i < Integer.parseInt(this.ddSize); i++) {
            for(int u = 0; u < this.inputTexts[i].length && u < this.ddTypeSize; u++) {
                this.inputTexts[i][u] = dupe();
                this.gbc.gridx = u;
                this.gbc.gridy = i+2;
                panel.add(this.inputTexts[i][u], gbc);
                //this.inputWindow.getContentPane().add(this.inputTexts[i][u], gbc);
            }
        }
        this.scrollWindow.setViewportView(panel);
    }
    public void listDemolish() {
        for (int i = 0; i < this.onGui.length; i++) {
            this.inputWindow.getContentPane().remove(this.onGui[i]);
        }
        for (int i = 0; i < this.inputTexts.length && i < Integer.parseInt(this.ddSize); i++) {
            for(int u = 0; u < this.inputTexts[i].length && u < this.ddTypeSize; u++) {
                this.inputWindow.getContentPane().remove(this.inputTexts[i][u]);
            }
        }
    }
    public String[][] listgetString() {
        String[][] table = new String[Integer.parseInt(ddSize)][ddTypeSize];
        for (int i = 0; i < this.inputTexts.length && i < Integer.parseInt(this.ddSize); i++) {
            for(int u = 0; u < this.inputTexts[i].length && u < this.ddTypeSize; u++) {
                table[i][u] = inputTexts[i][u].getText();
            }
        }
        return table;
    }
    public void setInputType(String input) {
        this.inputType = input;
    }
    public JFrame getInputWindow() {
        return inputWindow;
    }
    public String getInputType() {
        return inputType;
    }
    
}
