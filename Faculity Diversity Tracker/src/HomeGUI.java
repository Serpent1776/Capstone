import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class HomeGUI {
    JFrame frame;
    JPanel buttonPanel;
    JTextArea outputTextField;
    JPanel outPanel;
    JLabel jText;
    InputGUI inGUI;
    EditGUI editGUI;
    ReferenceGUI refGUI;
    DeleteGUI deleteGUI;
    JButton inputButton;
    JButton editButton;
    JButton reminderButton; //for notifications
    JButton searchButton;
    JButton deleteButton;
    JButton referenceButton;
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
        this.outputTextField = new JTextArea();
        this.outputTextField.setPreferredSize(new Dimension(1000, 0));
        this.outputTextField.setBounds(1000, 850, 1000, 850);
        this.outputTextField.setFont(new Font("Cambria", 4, 24));
        this.outputTextField.setBackground(null);
        this.outputTextField.setFocusable(false);
        this.outputTextField.setEditable(false);
        this.scroll = new JScrollPane(this.outputTextField, 
           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scroll.setPreferredSize(new Dimension(1000, 850));
        this.scroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
        //this.outputTextField.setCaretPosition(this.outputTextField.getDocument().getLength());
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
        this.referenceButton = new JButton("Reference");
        this.referenceButton.setBackground(new Color(155, 155, 155));
        this.referenceButton.setPreferredSize(new Dimension(150,50));
        this.referenceButton.setFont(new Font("Cambria", 4, 24));
        //this..setText("");
        //this.outputTextField.setText("");
        this.buttonPanel = new JPanel();
        this.outPanel = new JPanel();
        this.outPanel.add(this.scroll);
        this.buttonPanel.add(this.inputButton);
        this.buttonPanel.add(this.editButton);
        this.buttonPanel.add(this.reminderButton);
        this.buttonPanel.add(this.searchButton);
        this.buttonPanel.add(this.deleteButton);
        this.buttonPanel.add(this.referenceButton);
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        this.gbc.gridy = 0;
        this.gbc.gridx = 0;
        this.inGUI = new InputGUI();
        this.editGUI = new EditGUI();
        this.deleteGUI = new DeleteGUI();
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
        this.referenceButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReference();
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
        instantiateIllustratedElements("Faculty Member");
        this.refGUI = new ReferenceGUI(illustratedElements);
        this.showTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent selection) {
                try {
                JComboBox<String> typeBox = (JComboBox<String>)selection.getSource();
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
        this.illustratedElements = new JLabel[7];
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
        this.outputTextField.setText(s);
    }
    public void openInput() {
        this.inGUI.open();
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
    public void showDataType(String selectedType) throws Exception {
        this.outputTextField.setText(SQLpro.showSpecific(selectedType));
        this.outputTextField.setPreferredSize(new Dimension(1000, SQLpro.getIndex()*29));
        this.scroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, SQLpro.getIndex()*29));
        this.outputTextField.setCaretPosition(0);
        if(!firstTime) {
            instantiateIllustratedElements(selectedType);
            this.refGUI.reset(this.illustratedElements);
        } else {
            firstTime = false;
        }
    }
    public void instantiateIllustratedElements(String selectedType) throws Exception {
         switch(selectedType) {
            case "Faculty Member": 
            this.illustratedElements = new JLabel[7];
            JLabel lN = new JLabel("1. Last Name");
            this.illustratedElements[0] = lN;
            gbc.gridy++;
            JLabel fN = new JLabel("2. First Name");
            this.illustratedElements[1] = fN;
            gbc.gridy++;
            JLabel email = new JLabel("3. Email");
            this.illustratedElements[2] = email;
            gbc.gridy++;
            JLabel role = new JLabel("4. Role");
            this.illustratedElements[3] = role;
            gbc.gridy++;
            JLabel dept_div = new JLabel("5. Dept. Division");
            this.illustratedElements[4] = dept_div;
            gbc.gridy++;
            JLabel bIPOC = new JLabel("6. BIPOC?");
            this.illustratedElements[5] = bIPOC;
            gbc.gridy++;
            JLabel gender = new JLabel("7. Gender");
            this.illustratedElements[6] = gender;
            break; //7
            case "Event": 
            this.illustratedElements = new JLabel[5];
            JLabel eN = new JLabel("1. Name of Event");
            this.illustratedElements[0] = eN;
            gbc.gridy++;
            JLabel eDate = new JLabel("2. Event Date");
            this.illustratedElements[1] = eDate;
            gbc.gridy++;
            JLabel eventType = new JLabel("3. Event Type");
            this.illustratedElements[2] = eventType;
            gbc.gridy++;
            JLabel requirement = new JLabel("4. Requirement");
            this.illustratedElements[3] = requirement;
            gbc.gridy++;
            JLabel notes = new JLabel("5. notes");
            this.illustratedElements[4] = notes;
            break; //5
            case "Certificate":
            this.illustratedElements = new JLabel[2];
            JLabel noc = new JLabel("1. Name of Certificate");
            this.illustratedElements[0] = noc;
            gbc.gridy++;
            JLabel cT = new JLabel("2. Certificate Type");
            this.illustratedElements[1] = cT;
            break; //2
            case "Employment":
            this.illustratedElements = new JLabel[2];
            JLabel appID = new JLabel("1. Application ID");
            this.illustratedElements[0] = appID;
            gbc.gridy++;
            JLabel desc = new JLabel("2. Description"); 
            this.illustratedElements[1] = desc;
            break; //2
            case "Faculty & Certificate":
            this.illustratedElements = new JLabel[5];
            JLabel lNos = new JLabel("1. Staff ID");
            this.illustratedElements[0] = lNos;
            gbc.gridy++;
            JLabel bDate = new JLabel("2. Bronze Date");
            this.illustratedElements[1] = bDate;
            gbc.gridy++;
            JLabel sDate = new JLabel("3. Silver Date");
            this.illustratedElements[2] = sDate;
            gbc.gridy++;
            JLabel gDate = new JLabel("4. Gold date");
            this.illustratedElements[3] = gDate;
            gbc.gridy++;
            JLabel cName = new JLabel("5. Certificate ID");
            this.illustratedElements[4] = cName;
            break; //5
            case "Event Attendence":
            this.illustratedElements = new JLabel[3];
            JLabel lNs = new JLabel("1. Attendence ID");
            this.illustratedElements[0] = lNs;
            gbc.gridy++;
            JLabel nE = new JLabel("2. Staff ID");
            this.illustratedElements[1] = nE;
             JLabel eI = new JLabel("3. Event ID");
            this.illustratedElements[2] = eI;
            break; //2
            case "Certificate & Event":
            this.illustratedElements = new JLabel[2];
            JLabel cne = new JLabel("1. Certificate ID");
            this.illustratedElements[0] = cne;
            gbc.gridy++;
            JLabel ne = new JLabel("2. Event ID");
            this.illustratedElements[1] = ne;
            break; //2   
        }
    }
    public void openReference() {
        this.refGUI.open();
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
}