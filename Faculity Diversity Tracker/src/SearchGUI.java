import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SearchGUI extends TemplateGUI {
    String[] searchTypes;
    String[] panelTypes;
    JComboBox<String> searchTypeSelector;
    JComboBox<String> panelSwitcher;
    JButton confirmButton;
    JPanel mainPanel;
    JPanel memberPanel;
    JPanel eventPanel;
    JPanel certPanel;
    JScrollPane searchResults;
    JTextField queryBox;
    JTextArea resultTemplate;
    GridBagConstraints gbc;
    SQLProcessor sqlPro;
    JLabel except;
    public SearchGUI() throws Exception {
        super("Searching");
        this.searchTypes = new String[3];
        this.searchTypes[0] = "Faculty Member";
        this.searchTypes[1] = "Event";
        this.searchTypes[2] = "Certificate";
        this.searchTypeSelector = new JComboBox<String>(searchTypes);
        this.panelTypes = new String[4];
        this.panelTypes[0] = "Main Results";
        this.panelTypes[1] = "Faculty Members of Result";
        this.panelTypes[2] = "Events of Result";
        this.panelTypes[3] = "Certificates of Result";
        this.panelSwitcher = new JComboBox<String>(panelTypes);
        this.sqlPro = new SQLProcessor();
        this.except = new JLabel();
        except.setBackground(new Color(255, 200, 200));
        except.setFont(new Font("Cambria", 2, 24));
        searchTypeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent selection) {
                try {
                removeAll();
                } catch(Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        });
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridBagLayout());
        this.searchResults = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.memberPanel = new JPanel();
        this.certPanel = new JPanel();
        this.eventPanel = new JPanel();
        this.searchResults.setPreferredSize(new Dimension(1000, 850));
        this.searchResults.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
        this.searchResults.setAutoscrolls(true);
        this.panelSwitcher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent panelSelection) {
                panelSwitch();
            }
        });
        this.queryBox = new JTextField();
        this.queryBox.setPreferredSize(new Dimension(1000, 20));
        this.queryBox.setFont(new Font("Cambria", 4, 16));
        //this.queryBox.setLineWrap(true);
        this.confirmButton = new JButton("confirm");
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pressed) {
                removeAll();
                try {
                    confirmSearch();
                } catch(Exception e) {
                    resultNotFound();
                }
            }
        });
        super.window.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.gridx = 0;
        super.window.getContentPane().add(except, gbc);
        gbc.gridx++;
        super.window.getContentPane().add(searchTypeSelector, gbc);
        gbc.gridy++;
        super.window.getContentPane().add(panelSwitcher, gbc);
        gbc.gridx--;
        super.window.getContentPane().add(searchResults, gbc);
        gbc.gridy++;
        super.window.getContentPane().add(queryBox, gbc);
        gbc.gridx++;
        super.window.getContentPane().add(confirmButton, gbc);
        this.resultTemplate = new JTextArea();
        //this.resultTemplate.setPreferredSize(new Dimension(1000, 0));
        //this.resultTemplate.setBounds(1000, 850, 1000, 850);
        this.resultTemplate.setFont(new Font("Cambria", 4, 24));
        this.resultTemplate.setBackground(null);
        this.resultTemplate.setFocusable(false);
        this.resultTemplate.setEditable(false);
        //this.resultTemplate.setLineWrap(true);
    }
    public void setSearchTypeOnSelector(String type) {
        removeAll();
        switch(type) {
            case "Faculty Member":
            case "Event":
            case "Certificate":
                searchTypeSelector.setSelectedItem(type);
            break;
            default:
                searchTypeSelector.setSelectedItem("Faculty Member");
        }
    }
    public void confirmSearch() throws Exception {
        panelSwitcher.setSelectedItem("Main Results");
        this.except.setText("");
        // indexes the query via making queries with SQL to find the element (by the query)
        String searchQuery = queryBox.getText();
        String result = "";
        String type = (String) searchTypeSelector.getSelectedItem();
        /*
        The search query is the exact "name" of an element within the selected type (from the selector) (case-sensitive)
            For Faculty, this "name" is first name and last name seperated by a space. 
                e.g. A working prompt would be "Scott Weiss" not "Weiss, Scott" or "Weiss Scott" 
            For Event and Certificate, this name is Event Name and Certificate Name respectively.
        What Each Element of The Selected Type (If Found) Returns:
            The Found Faculty Member returns: 
                That faculty member's entry via where with the search query
                Their employment via where, grabbing that staff member's employment ID if it exists
                Their certificates via where using that faculty member's ID and a join of facultyCert with Certificate,
                    only grabbing Certificate Names
                All of the events they have attended via in a similar method to the above 
                    but instead with a join of attendid with facultyevent, only grabbing Event Names
            The Found Event returns:
                That event's entry via where with the search query
                The linked Certificates via where using that Event's ID and a join from eventCert with Certificate,
                    only grabbing Certificate Names
                All Faculity members that attended that Event in a similar method to the above 
                    but instead with a join of attendid with Faculty, only grabbing first and last names of Faculty Members
            The Found Certificate returns:
                That Certificate's entry via where with the search query
                All faculity members that have that Certificate via where using the Certificate's ID and
                    a join of faculityCert with Faculty, only grabbing first and last names of Faculty Members
                All events associated with that Certificate in a similar method to the above 
                    but instead with a join of eventcert with Event, only grabbing Event Names
        If nothing is found from the query (of the selected type from the selector), it returns:
            "Not found!" with the selected type. 
                e.g. "Faculty Member not found!", "Event not found!", "Certificate not found!" 
        */
        result = sqlPro.findElement(searchQuery, type, "~");
        String[] resultSplit = result.split("~");
        String[][] resultSplit2D = null;
        if(type.equals("Event") && resultSplit.length > 6) {
            resultSplit = result.split("\n");
            String[][] resultSplitTemp = new String[resultSplit.length][5];
            for (int i = 0; i < resultSplit.length; i++) {
                resultSplitTemp[i] = resultSplit[i].split("~");
            }
            resultSplit2D = resultSplitTemp;
        }
        int id = 0;
        int[] ids = null;
        if(!type.equals("Event") || resultSplit2D == null) {
            id = Integer.parseInt(resultSplit[0]);
        } else {
            ids = new int[resultSplit2D.length];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = Integer.parseInt(resultSplit2D[i][0]);
            }
        }
        int employmentID = 0;
        if(type.equals("Faculty Member") && !(resultSplit[6].equals("null") || resultSplit[6].equals(" ") || resultSplit[6].equals("NULL"))) {
            employmentID = Integer.parseInt(resultSplit[6]);
        }
        String members = "";
        String certs = "";
        String events = "";
        String[] memberz = null;
        String[] certz = null;
        String[] eventz = null;
        if(type.equals("Event") || type.equals("Certificate")) {
            if(id != 0) {
                members = sqlPro.showFaculty(id, type);
            } else {
                memberz = new String[resultSplit2D.length];
                for(int i = 0; i < resultSplit2D.length; i++) {
                    memberz[i] = sqlPro.showFaculty(ids[i], type);
                }
            }
        }
        if(type.equals("Event") || type.equals("Faculty Member")) {
            if(id != 0) {
            certs = sqlPro.showCertificates(id, type);
            } else {
                certz = new String[resultSplit2D.length];
                for(int i = 0; i < resultSplit2D.length; i++) {
                    certz[i] = sqlPro.showCertificates(ids[i], type);
                }
            }
        }
        if(type.equals("Faculty Member") || type.equals("Certificate")) {
        events = sqlPro.showEvents(id, type);
        }
        if(members != "") {
            memberz = members.split(",");
        }
        if(certs != "") {
            certz = certs.split(",");
        }
        if(events != "") {
            eventz = events.split(",");
        } 
        gbc.gridx = 0;
        gbc.gridy = 0;
        switch(type) {
            case "Faculty Member": 
            JLabel fid = new JLabel("Faculty ID");
            fid.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(fid, gbc);
            //illustratedElements[0] = id;
            gbc.gridx++;
            JLabel lN = new JLabel("Last Name");
            lN.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(lN, gbc);
            //illustratedElements[1] = lN;
            gbc.gridx++;
            JLabel fN = new JLabel("First Name");
            fN.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(fN, gbc);
            //illustratedElements[2] = fN;
            gbc.gridx++;
            JLabel email = new JLabel("Email");
            email.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(email, gbc);
            //illustratedElements[3] = email;
            gbc.gridx++;
            JLabel role = new JLabel("Role");
            role.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(role, gbc);
            //illustratedElements[4] = role;
            gbc.gridx++;
            JLabel dept_div = new JLabel("Dept. Division");
            dept_div.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(dept_div, gbc);
            //illustratedElements[5] = dept_div;
            gbc.gridx++;
            JLabel epID = new JLabel("Employment ID");
            epID.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(epID, gbc);
            //illustratedElements[6] = epID;
            gbc.gridx++;
            JLabel bIPOC = new JLabel("BIPOC?");
            bIPOC.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(bIPOC, gbc);
            //illustratedElements[7] = bIPOC;
            gbc.gridx++;
            JLabel gender = new JLabel("Gender");
            gender.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(gender, gbc);
            //illustratedElements[8] = gender;
            break; //7
            case "Event": 
            JLabel eID = new JLabel("Event ID");
            eID.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(eID, gbc);
            //illustratedElements[0] = eID;
            gbc.gridx++;
            JLabel eN = new JLabel("Name of Event");
            eN.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(eN, gbc);
            //illustratedElements[1] = eN;
            gbc.gridx++;
            JLabel eDate = new JLabel("Event Date");
            eDate.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(eDate, gbc);
            //illustratedElements[2] = eDate;
            gbc.gridx++;
            JLabel eventType = new JLabel("Event Type");
            eventType.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(eventType, gbc);
            //illustratedElements[3] = eventType;
            gbc.gridx++;
            JLabel requirement = new JLabel("Requirement");
            requirement.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(requirement, gbc);
            //illustratedElements[4] = requirement;
            gbc.gridx++;
            JLabel notes = new JLabel("notes");
            notes.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(notes, gbc);
            //illustratedElements[5] = notes;
            break; //5
            case "Certificate":
            JLabel certID = new JLabel("Certificate ID");
            certID.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(certID, gbc);
            //illustratedElements[0] = certID;
            gbc.gridx++;
            JLabel noc = new JLabel("Name of Certificate");
            noc.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(noc, gbc);
            //illustratedElements[1] = noc;
            gbc.gridx++;
            JLabel cT = new JLabel("Certificate Type");
            cT.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(cT, gbc);  
            //illustratedElements[2] = cT;
            break; //2
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        if(!type.equals("Event") || id != 0) {
            for (String s : resultSplit) {
                JTextArea facultyArea = new JTextArea(s);
                //facultyArea.setPreferredSize(resultTemplate.getPreferredSize());
                //facultyArea.setBounds(resultTemplate.getBounds());
                facultyArea.setFont(resultTemplate.getFont());
                facultyArea.setBackground(null);
                facultyArea.setFocusable(false);
                facultyArea.setEditable(false);
                mainPanel.add(facultyArea, gbc);
                gbc.gridx++;
        }
     } else {
        for(String[] sArr: resultSplit2D) {
            gbc.gridx = 0;
            for(String s: sArr) {
                JTextArea eventArea = new JTextArea(s);
                eventArea.setFont(resultTemplate.getFont());
                eventArea.setBackground(null);
                eventArea.setFocusable(false);
                eventArea.setEditable(false);
                mainPanel.add(eventArea, gbc);
                gbc.gridx++;
            }
            gbc.gridy++;
        }
    }
        String[] emp = null;
        gbc.gridx = 0;
        gbc.gridy++;
        if(employmentID != 0) {
            JLabel appID = new JLabel("Application ID");
            appID.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(appID, gbc); 
            //illustratedElements[1] = appID;
            gbc.gridx++;
            JLabel desc = new JLabel("Description");
            desc.setFont(new Font("Cambria Bold", 8, 24));
            mainPanel.add(desc, gbc); 
            //illustratedElements[2] = desc;
            gbc.gridy++;
            gbc.gridx = 0;
            emp = sqlPro.showEmployment(employmentID, "~").split("~");
            for(String es: emp) {
                JTextArea employmentArea = new JTextArea(es);
                employmentArea.setFont(resultTemplate.getFont());
                employmentArea.setBackground(null);
                employmentArea.setFocusable(false);
                employmentArea.setEditable(false);
                mainPanel.add(employmentArea, gbc);
                gbc.gridx++;
            }
            gbc.gridy++;
            gbc.gridx = 0;
        }
            String memberR = "";
            String eventR = "";
            String certR = "";
            switch(type) {
                case "Faculty Member": //certs then events
                certR = "Certificates: ";
                if(certz == null) {certR += "None";} else if(certz.length != 0) {
                for(int i = 0; i < certz.length; i++) {
                    String s = certz[i];
                    if(i < certz.length - 1) {s += ", ";}
                    certR += s;
                }
                } else {certR += "None";}
                JTextArea certificateResults = new JTextArea(certR);
                certificateResults.setFont(resultTemplate.getFont());
                //certificateResults.setPreferredSize(new Dimension(1000, 0));
                certificateResults.setBounds(0, 0, 980, 20);
                certificateResults.setBackground(null);
                certificateResults.setFocusable(false);
                certificateResults.setEditable(false);
                certificateResults.setWrapStyleWord(true);
                certPanel.add(certificateResults);
                eventR = "Events: ";
                if(eventz == null) {eventR += "None";} else if(eventz.length != 0) {
                for(int i = 0; i < eventz.length; i++) {
                    String s = eventz[i];
                    if(i < eventz.length - 1) {s += ", ";}
                    eventR += s;
                }
                } else {eventR += "None";}
                JTextArea eventResults = new JTextArea(eventR);
                eventResults.setFont(resultTemplate.getFont());
                //eventResults.setPreferredSize(new Dimension(1000, 0));
                eventResults.setBounds(0, 0, 980, 20);
                eventResults.setBackground(null);
                eventResults.setFocusable(false);
                eventResults.setEditable(false);
                eventResults.setLineWrap(true);
                eventResults.setWrapStyleWord(true);
                eventPanel.add(eventResults);
                break;
                case "Event": // Certificate/s then Faculty members
                certR = "Certificates linked to Event: ";
                if(id != 0) {
                if(certz == null) {certR += "None";} else if(certz.length != 0) {
                for(int i = 0; i < certz.length; i++) {
                    String s = certz[i];
                    if(i < certz.length - 1) {s += ", ";}
                    certR += s;
                }
                } else {certR += "None";}
                } else {
                certR = "";
                for(int i = 0; i < certz.length; i++) {
                    certR += "Certificates linked to Event " + (i + 1) + ": ";
                    if(certz[i] == "") {certR += "No one.";} else if(certz[i].length() != 0) {
                        String[] memberSpl = certz[i].split(",");
                    for(int u = 0; u < memberSpl.length; u++) {
                        String s = memberSpl[u];
                        if(u < memberSpl.length - 1) {s += ", ";}
                        certR += s; 
                    }
                } else {certR += "No one.";}
                    certR += "\n";
                }
            }
                JTextArea certificateResultz = new JTextArea(certR);
                certificateResultz.setFont(resultTemplate.getFont());
                //certificateResults.setPreferredSize(new Dimension(1000, 0));
                certificateResultz.setBounds(0, 0, 980, 20);
                certificateResultz.setBackground(null);
                certificateResultz.setFocusable(false);
                certificateResultz.setEditable(false);
                certificateResultz.setWrapStyleWord(true);
                certPanel.add(certificateResultz);
                memberR = "People who attended the event: ";
                if(id != 0) {
                if(memberz == null) {memberR += "No one.";} else if(memberz.length != 0) {
                    for(int i = 0; i < memberz.length; i++) {
                        String s = memberz[i];
                        if(i < memberz.length - 1) {s += ", ";}
                        memberR += s; 
                    }
                } else {memberR += "No one.";}
                } else {
                    memberR = "";
                    for(int i = 0; i < memberz.length; i++) {
                        memberR += "People who attended Event " + (i + 1) + ": ";
                        if(memberz[i] == "") {memberR += "No one.";} else if(memberz[i].length() != 0) {
                        String[] memberSpl = memberz[i].split(",");
                    for(int u = 0; u < memberSpl.length; u++) {
                        String s = memberSpl[u];
                        if(u < memberSpl.length - 1) {s += ", ";}
                        memberR += s; 
                    }
                } else {memberR += "No one.";}
                    memberR += "\n";
                }
                }
                JTextArea memberResults = new  JTextArea(memberR);
                memberResults.setFont(resultTemplate.getFont());
                memberResults.setBounds(0, 0, 980, 20);
                memberResults.setBackground(null);
                memberResults.setFocusable(false);
                memberResults.setEditable(false);
                memberResults.setWrapStyleWord(true);
                memberPanel.add(memberResults);
                break;  
                case "Certificate": // Faculty members then Events
                memberR = "People who have this certificate: ";
                if(memberz == null) {memberR += "No one.";} else if(memberz.length != 0) {
                    for(int i = 0; i < memberz.length; i++) {
                        String s = memberz[i];
                        if(i < memberz.length - 1) {s += ", ";}
                        memberR += s; 
                    }
                } else {memberR += "No one.";}
                JTextArea memberResultz = new  JTextArea(memberR);
                memberResultz.setFont(resultTemplate.getFont());
                memberResultz.setBounds(0, 0, 980, 20);
                memberResultz.setBackground(null);
                memberResultz.setFocusable(false);
                memberResultz.setEditable(false);
                memberResultz.setWrapStyleWord(true);
                memberPanel.add(memberResultz);
                eventR = "Events linked to this certificate: ";
                if(eventz == null) {eventR += "None";} else if(eventz.length != 0) {
                for(int i = 0; i < eventz.length; i++) {
                    String s = eventz[i];
                    if(i < eventz.length - 1) {s += ", ";}
                    eventR += s;
                }
                } else {eventR += "None";}
                JTextArea eventResultz = new JTextArea(eventR);
                eventResultz.setFont(resultTemplate.getFont());
                //eventResults.setPreferredSize(new Dimension(1000, 0));
                eventResultz.setBounds(0, 0, 980, 20);
                eventResultz.setBackground(null);
                eventResultz.setFocusable(false);
                eventResultz.setEditable(false);
                eventResultz.setLineWrap(true);
                eventResultz.setWrapStyleWord(true);
                eventPanel.add(eventResultz);
                break;
            }
       this.searchResults.setViewportView(mainPanel);
    }
    public void removeAll() {
        this.mainPanel.removeAll();
        this.certPanel.removeAll();
        this.eventPanel.removeAll();
        this.memberPanel.removeAll();
        this.searchResults.setViewportView(mainPanel);
    }
    public JFrame getSearchWindow() {
        return super.window;
    }
    public String getToBeSearchedType() {
        return (String) searchTypeSelector.getSelectedItem();
    }
    public void panelSwitch() {
        String type = (String) this.panelSwitcher.getSelectedItem();
        switch (type) {
            case "Main Results":
                this.searchResults.setViewportView(mainPanel);
                break;
            case "Faculty Members of Result":
                this.searchResults.setViewportView(memberPanel);
                break;
            case "Events of Result":
                this.searchResults.setViewportView(eventPanel);
                break;
            case "Certificates of Result":
                this.searchResults.setViewportView(certPanel);
                break;
            default:
                break;
        }
    }
    public void resultNotFound() {
       this.except.setText(searchTypeSelector.getSelectedItem() + " not found!");
    }
}
