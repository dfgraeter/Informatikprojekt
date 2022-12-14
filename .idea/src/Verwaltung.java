import java.util.Scanner;

public class Verwaltung {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void main(String[] args) {

        System.out.println("|-------------------------|");
        System.out.println("|-------Hauptmenü---------|");
        System.out.println("|                         |");
        System.out.println("|-(1)bestellen           -|");
        System.out.println("|-(2)ausliefern          -|");
        System.out.println("|-(3)informationen       -|");
        System.out.println("|-(4)loeschen            -|");
        System.out.println("|-(5)hilfe  (6)ausloggen -|");
        System.out.println("|-------------------------|");

        Scanner sc = new Scanner(System.in);
        int eingabe = sc.nextInt();

        while(eingabe != 6){
            switch (eingabe){
                case 1:
                    System.out.println("Was wollen Sie bestellen?");
                    break;
                case 2:
                    System.out.println("Was wollen Sie ausliefern?");
                    break;
                case 3:
                    System.out.println("Informationen");
                    break;
                case 4:
                    System.out.println("Was wollen Sie löschen?");
                    break;
                case 5:
                    System.out.println("Hilfe");
                    Konsole.hilfe();
                    break;

            }

            eingabe = sc.nextInt();
        }



    }
}
