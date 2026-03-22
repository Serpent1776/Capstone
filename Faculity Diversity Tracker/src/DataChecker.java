

public class DataChecker {
    public static void checkData(String[][] strTable, String type) throws FDTException {
        String[] checkInfo;
        switch (type) {
            case "Faculty Member": checkInfo = new String[8]; //affected
            checkInfo[0] = "String"; //firstName
            checkInfo[1] = "String"; //lastName
            checkInfo[2] = "String"; //email
            checkInfo[3] = "String"; //Personsrole
            checkInfo[4] = "String"; //dept_div
            checkInfo[5] = "int"; //employmentID
            checkInfo[6] = "boolean"; //BIPOC
            checkInfo[7] = "String"; //gender
            break;
            case "Event": checkInfo = new String[5]; //affected
            checkInfo[0] = "String"; //eventName
            checkInfo[1] = "Date"; //eventDate
            checkInfo[2] = "String"; //eventType
            checkInfo[3] = "String"; //requirement
            checkInfo[4] = "String"; //notes
            break;
            case "Certificate": checkInfo = new String[2]; //affected 
            checkInfo[0] = "String"; //certificateName
            checkInfo[1] = "String"; //certificateType
            break;
            case "Employment": checkInfo = new String[2]; //affected
            checkInfo[0] = "int"; //ApplicationID
            checkInfo[1] = "String"; //employmentDesc
            break;
            case "Faculty & Certificate": checkInfo = new String[5];
            checkInfo[0] = "int"; //personID
            checkInfo[1] = "Date"; //bronzeDate
            checkInfo[2] = "Date"; //silverDate
            checkInfo[3] = "Date"; //goldDate
            checkInfo[4] = "int"; //certID
            break;
            case "Event Attendence": checkInfo = new String[2]; //affected
            checkInfo[0] = "int"; //facultyID
            checkInfo[1] = "int"; //eventID
            break;
            case "Certificate & Event": checkInfo = new String[2];
            checkInfo[0] = "int"; //facultyID
            checkInfo[1] = "int"; //eventID
            break;
            default: throw new FDTException("Type does not exist in Faculty Tracker Database.");
        }
        int editFactor = 0;
        int inputFactor = 1;
        if(strTable[0].length == checkInfo.length + 1) {
            editFactor = 1;
            inputFactor = 0;
        }
        for(int i = 0; i < strTable.length; i++) {
            for(int u = editFactor; u < strTable[i].length; u++) {
                if(!(strTable[i][u].equals("null") || strTable[i][u].equals(""))) {
                    switch(checkInfo[u - editFactor]) {
                        case "String": 
                            DataChecker.CheckString(strTable[i][u]);
                        break;
                        case "int":
                        try {
                            DataChecker.checkInt(strTable[i][u]);
                        } catch(NumberFormatException n) {
                            throw new FDTException("Make sure there is only a number only in column " + (u + inputFactor) + ".");
                        }
                        break;
                        case "Date":
                            DataChecker.checkDate(strTable[i][u]);
                        break;
                        case "boolean":
                            DataChecker.checkBoolean(strTable[i][u]);
                        break;
                }
            }
            }
        }
    }
    public static void CheckString(String str) throws FDTException {
        if(str.contains("*") || str.contains("(") || str.contains(")") || str.contains("~")) {
            throw new FDTException("Don't include asterisks, parentheses, or tildas in your Strings.");
        }
        if(str.contains("\"") && str.split("\"").length % 2 == 1) {
            throw new FDTException("Make sure your quotes are balanced.");
        }
        int balance = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '"') {
                balance++;
            }
        }
        if(balance % 2 == 1) {
            throw new FDTException("Don't have uneven quote marks as a part of a String.");
        }
        try {
            DataChecker.checkInt(str);
            throw new FDTException("Make sure your String isn't just a number.");
        } catch (NumberFormatException f) {
            
        }
    }
    public static void checkInt(String parseable) throws NumberFormatException {
        @SuppressWarnings("unused")
        int test = Integer.parseInt(parseable);
    }
    public static void checkDate(String date) throws FDTException {
        if(date.contains("/")) {
            String[] splitCheck = date.split("/");
            if(splitCheck.length == 3 && splitCheck[0].length() >= 4) {
                return;
            } else {
                throw new FDTException("Dates must be in year month day format.");
            }
        }
        if(date.contains("-")) {
            String[] splitCheck = date.split("-");
            if(splitCheck.length == 3 && splitCheck[0].length() >= 4) {
                return;
            } else {
                throw new FDTException("Dates must be in year month day format. Note: Faculty Diversity Tracker is built for the 21st Centry and above.");
            }
        }
        if(date.contains(".")) {
            String[] splitCheck = date.split(".");
            if(splitCheck.length == 3 && splitCheck[0].length() >= 4) {
                return;
            } else {
                throw new FDTException("Dates must be in year month day format. Note: Faculty Diversity Tracker is built for the 21st Centry and above.");
            }
        }
        if(date.contains(",")) {
            String[] splitCheck = date.split(".");
            if(splitCheck.length == 3 && splitCheck[0].length() >= 4) {
                return;
            } else {
                throw new FDTException("Dates must be in year month day format. Note: Faculty Diversity Tracker is built for the 21st Centry and above.");
            }
        }
        throw new FDTException("Dates are to be formatted with slashes, hyphens, commas, or periods");
    }
    public static void checkBoolean(String parseable) throws FDTException {
        int b;
        try{
            b = Integer.parseInt(parseable);
        } catch(NumberFormatException e) {
            throw new FDTException("Booleans aren't to be written as true or false instead it is to be written as 0 or 1. 0 is false, 1 is true.");
        }
        if(b > 1 || b < 0) {
            throw new FDTException("Booleans are only be either 0 or 1 where 0 is false and 1 is true.");
        }
    }
}
