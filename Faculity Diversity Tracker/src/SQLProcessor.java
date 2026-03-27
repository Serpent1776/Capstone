import java.sql.*;
public class SQLProcessor {
    Connection conn;
    PreparedStatement statement;
    ResultSet rSet;
    int index;
    String[] types;
    public SQLProcessor() throws Exception {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone","root", "F**~<|*m0f0RE");
        this.index = 0;
    }
    public String show() throws Exception {
        this.statement = conn.prepareStatement("select * from faculty");
        this.rSet = this.statement.executeQuery();
        String results = "";
        this.index = 0;
        while(this.rSet.next() == true) {
        results += this.rSet.getString(1) + " ";
        results += this.rSet.getString(2) + " ";
        results += this.rSet.getString(3) + " ";
        results += this.rSet.getString(4) + " ";
        results += this.rSet.getString(5) + " ";
        results += this.rSet.getString(6) + " ";
        results += this.rSet.getString(7) + " ";
        results += this.rSet.getString(8) + " ";
        results += this.rSet.getString(9) + " ";
        this.index++;
        results += "\n";
        }
        return results;
        //System.out.println(o); debug
    }
    public String show(String splitter) throws Exception {
        this.statement = conn.prepareStatement("select * from faculty");
        this.rSet = this.statement.executeQuery();
        String results = "";
        this.index = 0;
        while(this.rSet.next() == true) {
        results += this.rSet.getString(1) + splitter;
        results += this.rSet.getString(2) + splitter;
        results += this.rSet.getString(3) + splitter;
        results += this.rSet.getString(4) + splitter;
        results += this.rSet.getString(5) + splitter;
        results += this.rSet.getString(6) + splitter;
        results += this.rSet.getString(7) + splitter;
        results += this.rSet.getString(8) + splitter;
        if(this.rSet.getString(9).equals("")) {
            results += " ";
        } else {
            results += this.rSet.getString(9);
        }
        this.index++;
        results += "\n";
        }
        return results;
        //System.out.println(o); debug
    }
    public String showSpecific(String selection) throws Exception {
        String results = "";
        this.index = 0;
        switch (selection) {
            case "Faculty Member":
            results = show();        
            break;
            case "Event": 
                this.statement = conn.prepareStatement("select * from facultyevent");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    results += this.rSet.getString(3) + " ";
                    results += this.rSet.getString(4) + " ";
                    results += this.rSet.getString(5) + " ";
                    results += this.rSet.getString(6) + " ";
                    this.index++;
                    results += "\n";
                }
            break; //5
            case "Certificate":
                this.statement = conn.prepareStatement("select * from certificate");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    results += this.rSet.getString(3) + " ";
                    this.index++;
                    results += "\n";
                }
            break;//2
            case "Employment":
                this.statement = conn.prepareStatement("select * from employment");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    results += this.rSet.getString(3) + " ";
                    this.index++;
                    results += "\n";
                }
            break; //2
            case "Faculty & Certificate":
                this.statement = conn.prepareStatement("select * from facultycert");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    results += this.rSet.getString(3) + " ";
                    results += this.rSet.getString(4) + " ";
                    results += this.rSet.getString(5) + " ";
                    this.index++;
                    results += "\n";
                }
            break;  //5
            case "Event Attendence":
                this.statement = conn.prepareStatement("select * from attendid");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    results += this.rSet.getString(3) + " ";
                    this.index++;
                    results += "\n";
                }
            break; //2
            case "Certificate & Event":
                this.statement = conn.prepareStatement("select * from eventcert");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + " ";
                    results += this.rSet.getString(2) + " ";
                    this.index++;
                    results += "\n";
                }
            break; //2
            default:
                throw new Exception("Wrong String");
        }
        return results;
    }
    public String showSpecific(String selection, String splitter) throws Exception {
        String results = "";
        this.index = 0;
        switch (selection) {
            case "Faculty Member":
            results = show(splitter);        
            break;
            case "Event": 
                this.statement = conn.prepareStatement("select * from facultyevent");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2) + splitter;
                    results += this.rSet.getString(3) + splitter;
                    results += this.rSet.getString(4) + splitter;
                    results += this.rSet.getString(5) + splitter;
                     if(!this.rSet.getString(6).equals("")) {
                    results += this.rSet.getString(6);
                    } else {
                    results += " ";
                    }
                    //o += resultSet.getString(7) + " ";
                    this.index++;
                    results += "\n";
                }
            break; //5
            case "Certificate":
                this.statement = conn.prepareStatement("select * from certificate");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2) + splitter;
                    results += this.rSet.getString(3);
                    this.index++;
                    results += "\n";
                }
            break;//2
            case "Employment":
                this.statement = conn.prepareStatement("select * from employment");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2) + splitter;
                    if(!this.rSet.getString(3).equals("")) {
                    results += this.rSet.getString(3);
                    } else {
                    results += " ";
                    }
                    this.index++;
                    results += "\n";
                }
            break; //2
            case "Faculty & Certificate":
                this.statement = conn.prepareStatement("select * from facultycert");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2) + splitter;
                    results += this.rSet.getString(3) + splitter;
                    results += this.rSet.getString(4) + splitter;
                    results += this.rSet.getString(5);
                    this.index++;
                    results += "\n";
                }
            break;  //5
            case "Event Attendence":
                this.statement = conn.prepareStatement("select * from attendid");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2) + splitter;
                    results += this.rSet.getString(3);
                    this.index++;
                    results += "\n";
                }
            break; //2
            case "Certificate & Event":
                this.statement = conn.prepareStatement("select * from eventcert");
                this.rSet = this.statement.executeQuery();
                while(this.rSet.next() == true) {
                    results += this.rSet.getString(1) + splitter;
                    results += this.rSet.getString(2);
                    this.index++;
                    results += "\n";
                }
            break; //2
            default:
                throw new Exception("Wrong String");
        }
        return results;
    }
    public void add(String tableName, String[][] table) throws Exception {
        statement = conn.prepareStatement("set foreign_key_checks = 0");
        statement.execute();
        String toExecute = "";
        switch(tableName) {
        case "Faculty Member":
           toExecute = "insert into faculty (lastName, firstName, email, personsRole, dept_div, employmentID, BIPOC, gender) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if(u % 8 == 5) {
                        //try a parseInt, throw when failed for all int values
                        if(table[i][u].equals("null") || table[i][u].equals("0") || table[i][u].equals("")) {
                        toExecute += "NULL, ";
                        } else {
                        toExecute += table[i][u] + ", ";
                        }
                    } else if(u % 8 == 6 && !(table[i][u].equals("1") || table[i][u].equals("0"))) {
                        toExecute += "NULL, ";
                    } else if(u == table[i].length - 1) {
                        toExecute += "\"" + table[i][u] + "\"" + ")";
                    } else {
                        toExecute += "\"" + table[i][u] + "\"" + ", ";
                    }
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //7
        case "Event": 
            toExecute = "insert into facultyEvent (eventName, eventDate, eventType, requirement, notes) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if (u % 5 == 1 && table[i][u].equals("")) {
                       toExecute += "NULL, ";
                    } else if(u == table[i].length - 1) {
                        toExecute += "\"" + table[i][u] + "\"" + ")";
                    } else {
                        toExecute += "\"" + table[i][u] + "\"" + ", ";
                    }
                }
                if(i < table.length - 1) {
                    toExecute += ",  ";
                }
            }
        break; //5
        case "Certificate": 
            toExecute = "insert into certificate (certificateName, certType) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if(u == table[i].length - 1) {
                        toExecute += "\"" + table[i][u] + "\"" + ")";
                    } else {
                        toExecute += "\"" + table[i][u] + "\"" + ", ";
                    }
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //2
        case "Employment": 
        toExecute = "insert into employment (ApplicationID, employmentDesc) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if(u == table[i].length - 1) {
                        toExecute += "\"" + table[i][u] + "\"" + ")";
                    } else {
                        toExecute += "\"" + table[i][u] + "\"" + ", ";
                    }
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //5
        case "Faculty & Certificate": 
        toExecute = "insert into facultyCert (personID, bronzeDate, silverDate, goldDate, certID) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                if (!(table[i][u].equals(""))) {   
                    if(u % 5 == 0) {
                        toExecute += table[i][u] + ", ";
                    } else if (u % 5 == 4) {
                        toExecute += table[i][u] + ")";
                    } else
                        toExecute += "\"" + table[i][u] + "\"" + ", ";
                } else if (u == table[i].length - 1) {
                        toExecute += "NULL)";
                } else {
                        toExecute += "NULL, ";
                } 
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //2
        case "Event Attendence": 
        toExecute = "insert into attendID (facultyID, eventID) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if (!(table[i][u].equals(""))) {
                    if(u % 2 == 0) {
                        toExecute +=  table[i][u] + ", ";
                    } else if(u % 2 == 1) {
                        toExecute +=  table[i][u] + ")";
                    }
                } else if (u == table[i].length - 1) {
                        toExecute += "NULL)";
                } else {
                        toExecute += "NULL, ";
                } 
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //2
        case "Certificate & Event": 
        toExecute = "insert into eventCert (certID, eventID) values ";
            for(int i = 0; i < table.length; i++) {
                toExecute += "(";
                for(int u = 0; u < table[i].length; u++) {
                    if (!(table[i][u].equals(""))) {
                    if(u % 2 == 0) {
                        toExecute += table[i][u] + ", ";
                    } else if(u % 2 == 1) {
                        toExecute += table[i][u] + ")"; 
                    }
                } else if (u == table[i].length - 1) {
                        toExecute += "NULL)";
                } else {
                        toExecute += "NULL, ";
                } 
                }
                if(i < table.length - 1) {
                    toExecute += ", ";
                }
            }
        break; //2 
        }
        //System.out.println(toExecute);
        this.statement = conn.prepareStatement(toExecute);
        this.statement.execute();
        statement = conn.prepareStatement("set foreign_key_checks = 1");
        statement.execute();
    }
    public int getIndex() {
        return index;
    }
    public void update(String[][] string2D, String type) throws Exception {
        switch(type) {
            case "Faculty Member":
                statement = conn.prepareStatement("set foreign_key_checks = 0");
                statement.execute();
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update faculty ";
                    stmt += "set lastName = \"" + string2D[i][1] + "\", ";
                    stmt += "firstName = \"" + string2D[i][2] + "\", ";
                    stmt += "email = \"" + string2D[i][3] + "\", ";
                    stmt += "personsRole = \"" + string2D[i][4] + "\", ";
                    stmt += "dept_div = \"" + string2D[i][5] + "\", ";
                    if(string2D[i][6].equals("0") || string2D[i][6].equals("null")
                         || string2D[i][6].equals(" ") || string2D[i][6].equals("")) {
                        stmt += "employmentID = NULL, ";
                    } else {
                        stmt += "employmentID = " + string2D[i][6] + ", ";
                    }
                     if(string2D[i][7].equals("null") ||
                      string2D[i][7].equals(" ") || string2D[i][7].equals("")) {
                        stmt += "BIPOC = NULL, ";
                    } else {
                        stmt += "BIPOC = " + string2D[i][7] + ", ";
                    }
                    stmt += "gender = \"" + string2D[i][8] + "\"";
                    stmt += " where id = " + string2D[i][0] + ";";
                    //System.out.println(stmt);
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                statement = conn.prepareStatement("set foreign_key_checks = 1");
                statement.execute();
                break;
            case "Event":
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update facultyevent ";
                    stmt += "set eventName = \"" + string2D[i][1] + "\", ";
                    if(!(string2D[i][2].equals("null") || string2D[i][2].equals(""))) {
                    stmt += "eventDate = \"" + string2D[i][2] + "\", ";
                    } else {
                        stmt += "eventDate = NULL, ";
                    }
                    stmt += "eventType = \"" + string2D[i][3] + "\", ";
                    stmt += "requirement = \"" + string2D[i][4] + "\", ";
                     stmt += "notes = \"" + string2D[i][5] + "\"";
                    stmt += " where id = " + string2D[i][0] + ";";
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                break;
            case "Certificate":
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update certificate ";
                    stmt += "set certificateName = \"" + string2D[i][1] + "\", ";
                    stmt += "certType = \"" + string2D[i][2] + "\"";
                    stmt += " where id = " + string2D[i][0] + ";";
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                break;
            case "Employment":
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update employment ";
                    stmt += "set applicationID = " + string2D[i][1] + "\", ";
                    stmt += "employmentDesc = \"" + string2D[i][2] + "\"";
                    stmt += " where id = " + string2D[i][0] + ";";
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                break;
            case "Faculty & Certificate":
                statement = conn.prepareStatement("set foreign_key_checks = 0");
                statement.execute();
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update facultycert set ";
                    //stmt += "set personID =" + string2D[i][0] + "\", ";
                    if(!(string2D[i][1].equals("null") || string2D[i][1].equals(""))) {
                        stmt += "bronzeDate = \"" + string2D[i][1] + "\", ";
                    } else {
                        stmt += "bronzeDate = NULL, ";
                    }
                    if(!(string2D[i][2].equals("null") || string2D[i][2].equals(""))) {
                        stmt += "silverDate = \"" + string2D[i][2] + "\", ";
                    } else {
                        stmt += "silverDate = NULL, ";
                    }
                     if(!(string2D[i][3].equals("null") || string2D[i][3].equals(""))) {
                        stmt += "goldDate = \"" + string2D[i][3] + "\"";
                    } else {
                        stmt += "goldDate = NULL ";
                    }
                    stmt += "where PersonID = " + string2D[i][0] + " and " + "certID = " + string2D[i][4] + ";";
                    //System.out.println(stmt);
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                statement = conn.prepareStatement("set foreign_key_checks = 1");
                statement.execute();
                break;
            case "Event Attendence": 
                statement = conn.prepareStatement("set foreign_key_checks = 0");
                statement.execute();
                for(int i = 0; i < string2D.length; i++) {
                    String stmt = "";
                    stmt += "update attendid ";
                    stmt += "set facultyID = " + string2D[i][1] + ", ";
                    stmt += "eventID = " + string2D[i][2] + " ";
                    stmt += "where attendID = " + string2D[i][0] + ";";
                    //System.out.println(stmt);
                    statement = conn.prepareStatement(stmt);
                    statement.execute();
                }
                statement = conn.prepareStatement("set foreign_key_checks = 1");
                statement.execute();
                break;
        }
    }
    public void delete(int index, String type) throws Exception {
        statement = conn.prepareStatement("set foreign_key_checks = 0");
        statement.execute();
        String stmt = "";
        switch (type) {
            case "Faculty Member":
            stmt = "delete from faculty where id = " + index + ";";
            statement = conn.prepareStatement(stmt);
            statement.execute();
            break; //7
            case "Event":
            stmt = "delete from facultyevent where id = " + index + ";";
            statement = conn.prepareStatement(stmt);
            statement.execute();    
            break; //5
            case "Certificate":
            stmt = "delete from certificate where id = " + index + ";";
            statement = conn.prepareStatement(stmt);
            statement.execute();    
            break;//2
            case "Employment":
            stmt = "delete from employment where id = " + index + ";";
            statement = conn.prepareStatement(stmt);
            statement.execute();   
            break;//2
            case "Event Attendence":
            stmt = "delete from attendid where attendID = " + index + ";";
            statement = conn.prepareStatement(stmt);
            statement.execute();     
            break; //2
        }
        statement = conn.prepareStatement("set foreign_key_checks = 1");
        statement.execute();
    }
    public void delete(int frontIndex, int backIndex, String type) throws Exception {
        statement = conn.prepareStatement("set foreign_key_checks = 0");
        statement.execute();
        String stmt = "";
        switch (type) {
            case "Faculty & Certificate":
            stmt = "delete from facultycert where personID = " + frontIndex + " and certID = " + backIndex  + ";";
            System.out.println(stmt);
            statement = conn.prepareStatement(stmt);
            statement.execute();  
            case "Certificate & Event":
            stmt = "delete from eventcert where certID = " + frontIndex + " and eventID = " + backIndex  + ";";
            System.out.println(stmt);
            statement = conn.prepareStatement(stmt);
            statement.execute();    
            break; //2
        }
        statement = conn.prepareStatement("set foreign_key_checks = 1");
        statement.execute();
    }
    public void deleteAll(String type) throws Exception {
        statement = conn.prepareStatement("set foreign_key_checks = 0");
        statement.execute();
        switch (type) {  
        case "Faculty Member": 
            statement = conn.prepareStatement("truncate table faculty");
            statement.execute();
        break;//7
        case "Event": 
            statement = conn.prepareStatement("truncate table event");
            statement.execute();
        break;//5
        case "Certificate": 
            statement = conn.prepareStatement("truncate table certificate");
            statement.execute();
        break;//2
        case "Employment": 
            statement = conn.prepareStatement("truncate table employment");
            statement.execute();
        break;//2
        case "Faculty & Certificate": 
            statement = conn.prepareStatement("truncate table facultycert");
            statement.execute();
        break;//5
        case "Event Attendence": 
            statement = conn.prepareStatement("truncate table attendid");
            statement.execute();
        break;//2
        case "Certificate & Event": 
            statement = conn.prepareStatement("truncate table eventcert");
            statement.execute();
        break;//2
        }
        statement = conn.prepareStatement("set foreign_key_checks = 1");
        statement.execute();
    }
    public String showJoinedEventCertEvent() throws Exception {
        String results = "";
        statement = conn.prepareStatement("select * from capstone.eventcert" +
         " join capstone.facultyevent on capstone.facultyevent.id = capstone.eventcert.eventID;");
         rSet = statement.executeQuery();
         while(this.rSet.next() == true) {
            results += this.rSet.getString(1) + " ";
            results += this.rSet.getString(2) + " ";
            results += this.rSet.getString(3) + " ";
            results += this.rSet.getString(4) + " ";
            results += this.rSet.getString(5) + " ";
            results += this.rSet.getString(6) + " ";
            results += this.rSet.getString(7) + " ";
            if(!this.rSet.getString(8).equals("")) {
                results += this.rSet.getString(8);
                } else {
                    results += " ";
                }
            results += "\n";
            this.index++;
         }
         return results;
    }
    public String showNonEventCertEvents() throws Exception {
        String results = "";
        statement = conn.prepareStatement("select eventName from eventcert" + 
        " right join facultyevent on facultyevent.id = eventcert.eventID" + 
        " where eventcert.eventID is null;");
        rSet = statement.executeQuery();
        while(this.rSet.next() == true) {
            if(!this.rSet.getString(1).equals("") && !this.rSet.getString(1).equals(" ") && !this.rSet.getString(1).equals("null")) {
            results += this.rSet.getString(1) + "\n";
            }
        }
        return results;
    }
    public String showFaculityMemberAttendedEventsforCert(int facultyID, int certID) throws Exception {
        String results = "";
        statement = conn.prepareStatement("select * from attendid join eventcert " + 
                "on attendid.eventID = eventcert.eventID " +
                 "where attendid.facultyID = " + facultyID + " and eventcert.certID = " + certID + ";");
        rSet = statement.executeQuery();
        while(this.rSet.next() == true) {
            results += this.rSet.getString(1) + " ";
            results += this.rSet.getString(2) + " ";
            results += this.rSet.getString(3) + " ";
            results += this.rSet.getString(4) + " ";
            results += this.rSet.getString(5) + "\n";
        }
        return results;
    }
    public String showFaculityCert(int facultyID, int certID, String splitter) throws Exception {
        String result = "";
        statement = conn.prepareStatement("select * from facultycert where personID = " + facultyID + 
        " and certID =" + certID);
        rSet = statement.executeQuery();
        while(this.rSet.next() == true) {
            result += this.rSet.getString(1) + splitter;
            result += this.rSet.getString(2) + splitter;
            result += this.rSet.getString(3) + splitter;
            result += this.rSet.getString(4) + splitter;
            result += this.rSet.getString(5) + "\n";
        }
        return result;
    }
    public String findElement(String query, String type, String splitter) throws Exception {
        String results = "";
        switch(type) {
        case "Faculty Member": 
            String[] splitted = query.split(" ");
            statement = conn.prepareStatement("select * from faculty where firstName = \"" + splitted[0] + "\" and lastName = \"" + splitted[1] + "\"");
            rSet = statement.executeQuery();
            this.index = 0;
            while(rSet.next()) {
                results += this.rSet.getString(1) + splitter;
                results += this.rSet.getString(2) + splitter;
                results += this.rSet.getString(3) + splitter;
                results += this.rSet.getString(4) + splitter;
                results += this.rSet.getString(5) + splitter;
                results += this.rSet.getString(6) + splitter;
                results += this.rSet.getString(7) + splitter;
                results += this.rSet.getString(8) + splitter;
                if(this.rSet.getString(9).equals("")) {
                    results += " ";
                } else {
                    results += this.rSet.getString(9);
                }
                this.index++;
                //results += "\n";
            }
        break;//7
        case "Event": 
            statement = conn.prepareStatement("");
            statement.execute();
        break;//5
        case "Certificate": 
            statement = conn.prepareStatement("");
            statement.execute();
        }
        return results;
    }
    public String showCertificates(int id, String type) throws Exception {//for faculty and events
        String results = "";
        switch(type) {
            case "Faculty Member":
            statement = conn.prepareStatement("select certificateName from certificate join facultyCert on id = certID where personID = " + id);
            rSet = statement.executeQuery();
            this.index = 0;
            while(rSet.next()) {
                results += this.rSet.getString(1) + ",";
                this.index++;
            } 
        }
        return results;
    }
    public String showEmployment(int id, String splitter) throws Exception {//for faculity
        String results = "";
        statement = conn.prepareStatement("select ApplicationID, employmentDesc from employment where id = " + id);
        this.index = 0; 
        rSet = statement.executeQuery();
        while(rSet.next()) {
                results += this.rSet.getString(1) + "~";
                results += this.rSet.getString(2);
                this.index++;
            } 
        return results;
    }
    public String showEvents(int id, String type) throws Exception {//for faculity and certificates
        String results = "";
         switch(type) {
            case "Faculty Member":
            statement = conn.prepareStatement("select eventName from facultyEvent join attendid on id = eventID where facultyID = " + id);
            rSet = statement.executeQuery();
            this.index = 0;
            while(rSet.next()) {
                results += this.rSet.getString(1) + ",";
                this.index++;
            } 
        }
        return results;
    }
    public String showFaculty(int id, String type) { //for certificates and events
         String results = "";
        return results;
    }
}
