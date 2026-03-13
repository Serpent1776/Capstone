import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class HomeGUI {
    JFrame frame;
    JPanel buttonPanel;
    JTextArea template;
    ArrayList<JTextArea> outputTexts;
    JPanel outPanel;
    JLabel jText;
    InputGUI inGUI;
    EditGUI editGUI;
    DeleteGUI deleteGUI;
    RemindGUI remindGUI;
    JButton inputButton;
    JButton editButton;
    JButton reminderButton; //for notifications
    JButton searchButton;
    JButton deleteButton;
    SQLProcessor SQLpro;
    JScrollPane scroll;
    JComboBox<String> showTypes;
    String[] inputTypes;
    GridBagConstraints gbc;
    JLabel[] illustratedElements;
    boolean firstTime;
    public HomeGUI() throws Exception {
        this.firstTime = true;
        this.frame = new JFrame("GUITest");
        this.frame.setLayout(new GridBagLayout());
        frame.setSize(1500, 1000);
        this.template = new JTextArea();
        this.template.setPreferredSize(new Dimension(1000, 0));
        this.template.setBounds(1000, 850, 1000, 850);
        this.template.setFont(new Font("Cambria", 4, 24));
        this.template.setBackground(null);
        this.template.setFocusable(false);
        this.template.setEditable(false);
        this.outputTexts = new ArrayList<JTextArea>();
        this.scroll = new JScrollPane(this.template, 
           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scroll.setPreferredSize(new Dimension(1000, 850));
        this.scroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
        //this.template.setCaretPosition(this.template.getDocument().getLength());
        this.scroll.setAutoscrolls(true);
        this.inputButton = new JButton("Add");
        this.inputButton.setBackground(new Color(200, 255, 200));
        this.inputButton.setPreferredSize(new Dimension(100,50));
        this.inputButton.setFont(new Font("Cambria", 4, 24));
        this.editButton = new JButton("Edit");
        this.editButton.setBackground(new Color(220, 200, 220));
        this.editButton.setPreferredSize(new Dimension(100,50));
        this.editButton.setFont(new Font("Cambria", 4, 24));
        this.reminderButton = new JButton("Remind");
        this.reminderButton.setBackground(new Color(255, 255, 200));
        this.reminderButton.setPreferredSize(new Dimension(120,50));
        this.reminderButton.setFont(new Font("Cambria", 4, 24));
        this.searchButton = new JButton("Search");
        this.searchButton.setBackground(new Color(200, 200, 200));
        this.searchButton.setPreferredSize(new Dimension(120,50));
        this.searchButton.setFont(new Font("Cambria", 4, 24));
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setBackground(new Color(255, 120, 120));
        this.deleteButton.setPreferredSize(new Dimension(120,50));
        this.deleteButton.setFont(new Font("Cambria", 4, 24));
        //this..setText("");
        //this.template.setText("");
        this.buttonPanel = new JPanel();
        this.outPanel = new JPanel();
        this.outPanel.add(this.scroll);
        this.buttonPanel.add(this.inputButton);
        this.buttonPanel.add(this.editButton);
        this.buttonPanel.add(this.reminderButton);
        this.buttonPanel.add(this.searchButton);
        this.buttonPanel.add(this.deleteButton);
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridy = 0;
        this.gbc.gridx = 0;
        this.inGUI = new InputGUI();
        this.editGUI = new EditGUI();
        this.deleteGUI = new DeleteGUI();
        this.remindGUI = new RemindGUI();
        this.SQLpro = new SQLProcessor();
        this.inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInput();
            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                openEdit();
                } catch(Exception ex) {
                    System.err.println(ex);
                }
            }
        });
        this.deleteButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                openDelete();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        this.reminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    openRemind();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        this.inGUI.getInputWindow().addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent windowClosed) {
                try {
                redirectToShowInputedData();
                } catch(Exception e) {
                  
                }
            }
            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
            }

            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
            }
        });
        this.editGUI.getEditingWindow().addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent windowClosed) {
                try {
                redirectToShowEditedData();
                } catch(Exception e) {
                  
                }
            }
            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
            }

            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
            }
        });
        this.deleteGUI.getDeleteWindow().addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent windowClosed) {
                try {
                redirectToShowRemainingData();
                } catch(Exception e) {
                  
                }
            }
            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
            }

            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
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
        this.showTypes = new JComboBox<String>(inputTypes);
        this.showTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent selection) {
                try {
                JComboBox<String> typeBox = (JComboBox<String>)selection.getSource();
                removeAll();
                showDataType((String)typeBox.getSelectedItem());
                } catch(Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        });
        this.gbc.gridy = 1500;
        this.gbc.gridx = 1;
        this.frame.getContentPane().add(this.buttonPanel, gbc);
        this.gbc.gridy = 0;
        this.gbc.gridx = 1;
        this.frame.getContentPane().add(this.outPanel, gbc);
        this.gbc.gridx = 2;
        this.frame.getContentPane().add(this.showTypes, gbc);
        this.illustratedElements = new JLabel[9];
    }
    public void open() {
        this.frame.setVisible(true);
    }
    public void close() {
        this.frame.setVisible(false);
    }
    public boolean isClosed() {
        return !(this.frame.isVisible());
    }
    public void setOut(String s) {
        this.template.setText(s);
    }
    public void openInput() {
        String type = (String) this.showTypes.getSelectedItem();
        this.inGUI.open();
        this.inGUI.setTypeonSelector(type);
    }
    public void openEdit() throws Exception {
        String type = (String) this.showTypes.getSelectedItem();
        this.editGUI.open();
        this.editGUI.setTypeonSelector(type);
    }
    public void openDelete() throws Exception {
        String type = (String) this.showTypes.getSelectedItem();
        this.deleteGUI.open();
        this.deleteGUI.setDeleteType(type);
    }
    public void openRemind() throws Exception {
        this.remindGUI.open();
    }
    public void showDataType(String selectedType) throws Exception {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        String toBeSplit = SQLpro.showSpecific(selectedType, "~");
        String[] firstSplit = toBeSplit.split("\n");
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        int div = 0;
        switch(selectedType) {
            case "Faculty Member": 
            JLabel id = new JLabel("Faculty ID");
            id.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(id, gbc);
            illustratedElements[0] = id;
            gbc.gridx++;
            JLabel lN = new JLabel("Last Name");
            lN.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(lN, gbc);
            illustratedElements[1] = lN;
            gbc.gridx++;
            JLabel fN = new JLabel("First Name");
            fN.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(fN, gbc);
            illustratedElements[2] = fN;
            gbc.gridx++;
            JLabel email = new JLabel("Email");
            email.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(email, gbc);
            illustratedElements[3] = email;
            gbc.gridx++;
            JLabel role = new JLabel("Role");
            role.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(role, gbc);
            illustratedElements[4] = role;
            gbc.gridx++;
            JLabel dept_div = new JLabel("Dept. Division");
            dept_div.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(dept_div, gbc);
            illustratedElements[5] = dept_div;
            gbc.gridx++;
            JLabel epID = new JLabel("Employment ID");
            epID.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(epID, gbc);
            illustratedElements[6] = epID;
            gbc.gridx++;
            JLabel bIPOC = new JLabel("BIPOC?");
            bIPOC.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(bIPOC, gbc);
            illustratedElements[7] = bIPOC;
            gbc.gridx++;
            JLabel gender = new JLabel("Gender");
            gender.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(gender, gbc);
            illustratedElements[8] = gender;
            break; //7
            case "Event": 
            JLabel eID = new JLabel("Event ID");
            eID.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(eID, gbc);
            illustratedElements[0] = eID;
            gbc.gridx++;
            JLabel eN = new JLabel("Name of Event");
            eN.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(eN, gbc);
            illustratedElements[1] = eN;
            gbc.gridx++;
            JLabel eDate = new JLabel("Event Date");
            eDate.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(eDate, gbc);
            illustratedElements[2] = eDate;
            gbc.gridx++;
            JLabel eventType = new JLabel("Event Type");
            eventType.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(eventType, gbc);
            illustratedElements[3] = eventType;
            gbc.gridx++;
            JLabel requirement = new JLabel("Requirement");
            requirement.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(requirement, gbc);
            illustratedElements[4] = requirement;
            gbc.gridx++;
            JLabel notes = new JLabel("notes");
            notes.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(notes, gbc);
            illustratedElements[5] = notes;
            break; //5
            case "Certificate":
            JLabel certID = new JLabel("Certificate ID");
            certID.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(certID, gbc);
            illustratedElements[0] = certID;
            gbc.gridx++;
            JLabel noc = new JLabel("Name of Certificate");
            noc.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(noc, gbc);
            illustratedElements[1] = noc;
            gbc.gridx++;
            JLabel cT = new JLabel("Certificate Type");
            cT.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(cT, gbc);  
            illustratedElements[2] = cT;
            break; //2
            case "Employment":
            JLabel EmID = new JLabel("Employmee ID");
            EmID.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(EmID, gbc);
            illustratedElements[0] = EmID;
            gbc.gridx++;
            JLabel appID = new JLabel("Application ID");
            appID.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(appID, gbc); 
            illustratedElements[1] = appID;
            gbc.gridx++;
            JLabel desc = new JLabel("Description");
            desc.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(desc, gbc); 
            illustratedElements[2] = desc;
            break; //2
            case "Faculty & Certificate":
            JLabel lNos = new JLabel("Staff ID");
            lNos.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(lNos, gbc);
            illustratedElements[0] = lNos;
            gbc.gridx++;
            JLabel bDate = new JLabel("Bronze Date");
            bDate.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(bDate, gbc);
            illustratedElements[1] = bDate;
            gbc.gridx++;
            JLabel sDate = new JLabel("Silver Date");
            sDate.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(sDate, gbc);
            illustratedElements[2] = sDate;
            gbc.gridx++;
            JLabel gDate = new JLabel("Gold date");
            gDate.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(gDate, gbc);
            illustratedElements[3] = gDate;
            gbc.gridx++;
            JLabel cName = new JLabel("Certificate ID");
            cName.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(cName, gbc);  
            illustratedElements[4] = cName;
            break; //5
            case "Event Attendence":
            JLabel ad = new JLabel("Attend ID");
            ad.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(ad, gbc);
            illustratedElements[0] = ad;
            gbc.gridx++;
            JLabel lNs = new JLabel("Staff ID");
            lNs.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(lNs, gbc);
            illustratedElements[1] = lNs;
            gbc.gridx++;
            JLabel nE = new JLabel("Event ID");
            nE.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(nE, gbc);
            illustratedElements[2] = nE;
            break; //2
            case "Certificate & Event":
            JLabel cne = new JLabel("Certificate ID");
            cne.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(cne, gbc);
            illustratedElements[0] = cne;
            gbc.gridx++;
            JLabel ne = new JLabel("Event ID");
            ne.setFont(new Font("Cambria Bold", 8, 24));
            panel.add(ne, gbc);
            illustratedElements[1] = ne;
            break; //2   
        }
        switch(selectedType) {
            case "Faculty Member": div = 9; break;
            case "Event": div = 6; break;
            case "Certificate": div = 3; break;
            case "Employment": div = 3; break;
            case "Faculty & Certificate": div = 5; break;
            case "Event Attendence": div = 3; break;
            case "Certificate & Event": div = 2; break;
        }
        String[][] secondSplit = new String[firstSplit.length][div];
         for(int i = 0; i < firstSplit.length; i++) {
            secondSplit[i] = firstSplit[i].split("~");
        }
        gbc.gridx = 0;
        gbc.gridy++;
         for (int i = 0; i < secondSplit.length; i++) {
            for (int u = 0; u < secondSplit[i].length; u++) {
            JTextArea iJTextArea = new JTextArea(secondSplit[i][u]);
            iJTextArea.setPreferredSize(this.template.getPreferredSize());
            iJTextArea.setFont(this.template.getFont());
            iJTextArea.setEditable(false);
            iJTextArea.setOpaque(false);
            iJTextArea.getCaret().setVisible(false);
            panel.add(iJTextArea, gbc);
            this.outputTexts.add(iJTextArea);
            gbc.gridx++;
            }
            gbc.gridx = 0;
            gbc.gridy++; 
        }
        int highest = 0;
        for(String str: firstSplit) {
            if(str.length() > highest) {
                highest = str.length();
            }
        }
        panel.setPreferredSize(new Dimension(highest*19, SQLpro.getIndex()*29));
        this.scroll.setViewportView(panel);
        this.scroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, SQLpro.getIndex()*29));
    }
    public void redirectToShowInputedData() throws Exception {
        showDataType(this.inGUI.getInputType());
        this.showTypes.setSelectedItem(this.inGUI.getInputType());
    }
    public void redirectToShowEditedData() throws Exception {
        showDataType(this.editGUI.getEditedDataType());
        this.showTypes.setSelectedItem(this.editGUI.getEditedDataType());
    }
    public void redirectToShowRemainingData() throws Exception {
        showDataType(this.deleteGUI.getDataDeletedType());
        this.showTypes.setSelectedItem(this.deleteGUI.getDataDeletedType());
    }
    public void removeAll() throws Exception {
        if(!this.outputTexts.isEmpty()) {
       for (JTextArea textArea : this.outputTexts) {
            this.frame.getContentPane().remove(textArea);
        }
        this.outputTexts.removeAll(this.outputTexts);
        }   
        for (int i = 0; i < this.illustratedElements.length; i++) {
            if(this.illustratedElements[i] != null) {
            this.frame.getContentPane().remove(this.illustratedElements[i]);
            }
        }
        this.illustratedElements = new JLabel[illustratedElements.length];
    }
}