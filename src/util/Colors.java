package util;
/**
 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 */

public class Colors {
    public static final String RESET = "\u001B[0m";

    public enum Color {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static String print(String message, Color color) {
        return color.getCode() + message + RESET;
        //System.out.print(color.getCode() + message + RESET);
    }

}
