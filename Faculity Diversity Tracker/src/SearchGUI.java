import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SearchGUI extends TemplateGUI {
    String[] searchTypes;
    JComboBox<String> searchTypeSelector;
    JButton confirm;
    JPanel scrollPanel;
    JScrollPane searchResults;
    JTextField queryBox;
    JTextArea resultTemplate;
    GridBagConstraints gbc;
    public SearchGUI() {
        super("Searching");
        this.searchTypes = new String[7];
        this.searchTypes[0] = "Faculty Member";
        this.searchTypes[1] = "Event";
        this.searchTypes[2] = "Certificate";
        this.searchTypeSelector = new JComboBox<String>(searchTypes);
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
        this.scrollPanel = new JPanel();
        this.searchResults = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.searchResults.setPreferredSize(new Dimension(1000, 850));
        this.searchResults.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
        this.searchResults.setAutoscrolls(true);
        this.queryBox = new JTextField();
        this.queryBox.setPreferredSize(new Dimension(1000, 20));
        this.queryBox.setFont(new Font("Cambria", 4, 16));
        //this.queryBox.setLineWrap(true);
        this.confirm = new JButton("confirm");
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pressed) {
                confirmSearch();
            }
        });
        super.window.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.gridx = 1;
        super.window.getContentPane().add(searchTypeSelector, gbc);
        gbc.gridx--;
        super.window.getContentPane().add(searchResults, gbc);
        gbc.gridy++;
        super.window.getContentPane().add(queryBox, gbc);
        gbc.gridx++;
        super.window.getContentPane().add(confirm, gbc);
        this.resultTemplate = new JTextArea();
        this.resultTemplate.setPreferredSize(new Dimension(1000, 0));
        this.resultTemplate.setBounds(1000, 850, 1000, 850);
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
            default:
                searchTypeSelector.setSelectedItem("Faculty Member");
        }
    }
    public String confirmSearch() {
        // indexes the query via making queries with SQL to find the element (by the query)
        @SuppressWarnings("unused")
        String searchQuery = queryBox.getText();
        String result = "";
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
        return result;
    }
    public void removeAll() {
        this.scrollPanel.removeAll();
    }
    public JFrame getSearchWindow() {
        return super.window;
    }
    public String getToBeSearchedType() {
        return (String) searchTypeSelector.getSelectedItem();
    }
}
