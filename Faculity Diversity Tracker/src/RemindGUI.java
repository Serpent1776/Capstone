import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
/*
Notification Types: Certificate Event connect, Recommend a Faculty Member for a certificate, Empty column notification
*/
public class RemindGUI extends TemplateGUI {
    JTextArea notificationText;
    ArrayList<String> notifications;
    String[] eventsOfCertificate; //used only as a check for certificate event connect notification 
    String[] certs;
    String[] nonEventCertEvents;
    SQLProcessor sQLPro;
    JButton update;
    JScrollPane notifscroll;
    File report;
    JButton export;
    public RemindGUI() throws Exception {
        super("Notifications");
        super.window.setSize(500, 700);
        this.notificationText = new JTextArea();
        this.notificationText.setFont(new Font("Cambria", 4, 16));
        this.notificationText.setLineWrap(true);
        this.notificationText.setWrapStyleWord(true);
        this.notificationText.setEditable(false);
        this.notificationText.setOpaque(false);
        this.notificationText.setPreferredSize(new Dimension(500, 500));
        this.notifications = new ArrayList<String>();
        this.sQLPro = new SQLProcessor();
        this.eventsOfCertificate = sQLPro.showJoinedEventCertEvent().split("\n");
        this.certs = sQLPro.showSpecific("Certificate", "~").split("\n");
        notifscroll = new JScrollPane(notificationText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notifscroll.setPreferredSize(new Dimension(20, 500));
        super.window.add(notifscroll, BorderLayout.NORTH);
        this.report = new File("Faculity Diversity Tracker\\src\\remind.txt");
        this.export = new JButton("export");
        export.setPreferredSize(new Dimension(120, 50));
        export.setBackground(new Color(0, 200, 200));
        export.setFont(new Font("Cambria", 4, 24));
        export.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                setReportText();     
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        super.window.add(export, BorderLayout.SOUTH);

    }
    public void updateEventList() throws Exception {
        this.eventsOfCertificate = sQLPro.showJoinedEventCertEvent().split("\n");
        String[] events = sQLPro.showSpecific("Event").split("\n");
        if(this.eventsOfCertificate.length < events.length) {
        this.nonEventCertEvents = sQLPro.showNonEventCertEvents().split("\n");
        for(String event: nonEventCertEvents) {
            if(!notificationText.getText().contains(event)) {
                addNotification("Please add an eventCert (event and certifcate connection) for " + event + ".");
            }
        }
        }
    }
    public void removeIrrelevantEventCerts() throws Exception {
        if(nonEventCertEvents == null) {
            return;
        }
        for (String eventName : nonEventCertEvents) {
            for (int i = 0; i < notifications.size(); i++) {
                String notification = notifications.get(i);
                if(notification.equals("Please add an eventCert (event and certifcate connection) for " + eventName + ".")) {
                    notifications.remove(notification);
                    updateNotifications();
                }
            }
        }
        //updateNotifications();
    }
    public void certificateRecommendationCheck() throws Exception {
        String[] events = sQLPro.showSpecific("Event", "~").split("\n");
        int equalscounter = 0;
        for(int i = 0; i < events.length; i++) {
            for (String cert : certs) {      
            if(events[i].split("~")[3].equals(cert.split("~")[2])) {
                equalscounter++;
            }
        }
            if(equalscounter == 0) {
                addNotification("Please add a certificate for " + events[i].split("~")[3] + ".");
            }
            equalscounter = 0;
    }
    }
    public void updateCertList() throws Exception {
        this.certs = sQLPro.showSpecific("Certificate", "~").split("\n");
        ArrayList<Integer> marks = new ArrayList<Integer>();
        for(String cert: this.certs) {
            for (int i = 0; i < notifications.size(); i++) {
                String notification = notifications.get(i);
                if(notification.equals("Please add a certificate for " + cert.split("~")[2] + ".")) {
                    marks.add(i);
                }
        }
            for (Integer i : marks) {
                notifications.remove(i.intValue());
            }
            marks.removeAll(marks);
        }
        updateNotifications();
    }
    public void updateFaculityRecommendations() throws Exception {
        String[] facultyMembers = sQLPro.showSpecific("Faculty Member", "~").split("\n");
        for(int f = 0; f < facultyMembers.length; f++) {
            for(int c = 0; c < certs.length; c++) {
                String[] facultyEvents = sQLPro.showFaculityMemberAttendedEventsforCert(f+1, c+1).split("\n");
                //checks the length for faculity Events
                if(facultyEvents.length >= 6) {
                    String[] thing = sQLPro.showFaculityCert(f+1, c+1, "~").split("\n");
                    if(thing.length > 1) {
                        addNotification("duplicate certificates exist, please have" +
                         "only one faculity-certificate connection per faculty member and per certificate type.");
                        continue;
                    }
                    String[] faculityrow = facultyMembers[f].split("~");
                    if(!thing[0].equals("")) {
                    thing = thing[0].split("~");
                    } else {
                        addNotification("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                         continue;
                    }
                    if(facultyEvents.length >= 18) {
                        if(thing[3].equals("") || thing[3].equals("null") || thing[3].equals(" ")) {
                             addNotification("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Gold " + this.certs[c].split("~")[2] + " certificate.");
                         continue;
                        }
                    } else if (facultyEvents.length >= 12) {
                        if(thing[2].equals("") || thing[2].equals("null") || thing[2].equals(" ")) {
                             addNotification("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Silver " + this.certs[c].split("~")[2] + " certificate.");
                         continue;
                        }
                    } else {
                         if(thing[1].equals("") || thing[1].equals("null") || thing[1].equals(" ")) {
                             addNotification("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                        }
                    }
                }
            }
        }
    }
    public void removeDoneRecommendations() throws Exception {
        String[] facultyMembers = sQLPro.showSpecific("Faculty Member", "~").split("\n");
        for(int f = 0; f < facultyMembers.length; f++) {
            for(int c = 0; c < certs.length; c++) {
                String[] thing = sQLPro.showFaculityCert(f+1, c+1, "~").split("\n");
                if(!thing[0].equals("")) {
                    thing = thing[0].split("~");
                } else {continue;}
                String[] faculityrow = facultyMembers[f].split("~");
                if(!(thing[3].equals("") || thing[3].equals("null"))) {
                    try {
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Gold " + this.certs[c].split("~")[2] + " certificate.");
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Silver " + this.certs[c].split("~")[2] + " certificate.");
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                    } catch(Exception e) {

                    }
                } else if (!(thing[2].equals("") || thing[2].equals("null"))) {
                    try {
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Silver " + this.certs[c].split("~")[2] + " certificate.");
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                    } catch(Exception e) {

                    }
                } else if (!(thing[1].equals("") || thing[1].equals("null"))) {
                    try {
                        notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                    } catch(Exception e) {

                    }
                }
                String[] facultyEvents = sQLPro.showFaculityMemberAttendedEventsforCert(f+1, c+1).split("\n");
                 if(facultyEvents.length >= 18) {
                   
                 } else if(facultyEvents.length >= 12) {
                      notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Gold " + this.certs[c].split("~")[2] + " certificate.");
                 } else if(facultyEvents.length >= 6) {
                    notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Gold " + this.certs[c].split("~")[2] + " certificate.");
                    notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Silver " + this.certs[c].split("~")[2] + " certificate.");
                 } else {
                    notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Gold " + this.certs[c].split("~")[2] + " certificate.");
                    notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Silver " + this.certs[c].split("~")[2] + " certificate.");
                    notifications.remove("Please recommend " + faculityrow[2] + " " + faculityrow[1]
                         + " for a Bronze " + this.certs[c].split("~")[2] + " certificate.");
                 }

            }
        }
        updateNotifications();
    }
    public void notificationCheck() throws Exception {
        if(this.notifications.isEmpty()) {
            certificateRecommendationCheck();
            updateEventList();
            updateFaculityRecommendations();
        } else {
            updateCertList();
            removeIrrelevantEventCerts();
            removeDoneRecommendations();
            certificateRecommendationCheck();
            updateEventList();
            updateFaculityRecommendations();
            //System.out.print(notificationText.getText());
        }
    }
    public void addNotification(String notification) {
        if(!this.notifications.contains(notification)) {
        this.notifications.add(notification);
        }
        updateNotifications();
    }
    public void updateNotifications() {
        String notificationSet = "";
        for (String note : notifications) {
            notificationSet += note + "\n";
        }
        this.notificationText.setText(notificationSet);
        this.notifscroll.setViewportView(notificationText);
    }
    public void setReportText() throws Exception {
        PrintWriter writer = new PrintWriter(report);
        String certificateSet = "";
        for (String string : notifications) {
            if(string.contains("Please recommend")) {
                certificateSet += string + "\n";
            }
        }
        writer.print(certificateSet);
        writer.close();
    }
}
