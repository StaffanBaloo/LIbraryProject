import java.util.Scanner;

public class IO {
    Scanner scanner = new Scanner(System.in);
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    // Läs in en rad och konvertera den till ett heltal om det går, annars klaga.
    public static int inputNumber() {
        boolean active=true;
        while (active){
            String input = scanner.nextLine();
            if(input==""){
                active=false;
                return null;
            }
            else if(isNumeric(input)){
                active=false;
                return Integer.parseInt(input);
            } else{
                System.out.println("Please enter a number.");
            }
        }

    }
}
