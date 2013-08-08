package fi.helsinki.cs.okkopa.reference;

public class Warning {
    public static String warning = "";

    public static String getWarning() {
        return warning;
    }

    public static void setWarning(String warning) {
        Warning.warning = warning;
    }
    
    public static void addWarning(String warning) {
        Warning.warning += "<br/>" + warning;
    }
    
}
