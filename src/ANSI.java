@SuppressWarnings("unused")
public class ANSI {
    // https://en.wikipedia.org/wiki/ANSI_escape_code#Select_Graphic_Rendition_parameters
    // ANSI escape sequences for styling terminal output

    // reset all styling to their default values
    public static String reset() {
        return "\033[0m";
    }

    public static String bold() {
        return "\033[1m";
    }

    public static String italic() {
        return "\033[3m";
    }

    public static String underline() {
        return "\033[4m";
    }

    public static String noBold() {
        return "\033[22m";
    }

    public static String noItalic(){
        return "\033[23m";
    }

    public static String noUnderline(){
        return "\033[24m";
    }

    public static String color(String col){
        switch (col.toLowerCase()){
            case "red":{
                return "\033[31m";
                break;
            }
            case "green":{
                return "\033[32m";
                break;
            }
            case "yellow":{
                return "\033[33m";
                break;
            }
            case "blue":{
                return "\033[34m";
                break;
            }
            case "magenta":{
                return "\033[35m";
                break;
            }
            case "cyan":{
                return "\033[36m";
                break;
            }
            case "white":{
                return "\033[37m";
                break;
            }
            case "orange":{
                return "\033[38m";
                break;
            }
            case "default":{
                return "\033[39m";
                break;
            }
            case "bright_black":{
                return "\033[90m";
                break;
            }
            case "bright_red":{
                return "\033[91m";
                break;
            }
            case "bright_green":{
                return "\033[92m";
                break;
            }
            case "bright_yellow":{
                return "\033[93m";
                break;
            }
            case "bright_blue":{
                return "\033[94m";
                break;
            }
            case "bright_magenta":{
                return "\033[95m";
                break;
            }
            case "bright_cyan":{
                return "\033[96m";
                break;
            }
            case "bright_white":{
                return "\033[97m";
                break;
            }
        }
    }

    public static String clearScreen(){
        return "\033[2J\033[H";
    }

    public static String hideCursor(){
        return "\033[?25l";
    }

    public static String showCursor(){
        return "\033[?25h";
    }

}
