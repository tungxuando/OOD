public class XMLException extends Exception {
    public static String parseError() {
        return "Parser Configuration Exception";
    }

    public static String unknownTypeError() {
        return "Unknown Element type";
    }

    public static String numberFormatError() {
        return "Number Format Exception";
    }

}
