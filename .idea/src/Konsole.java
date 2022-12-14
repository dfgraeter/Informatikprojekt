import java.util.Scanner;

public class Konsole {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";



    public static void hilfe(){
        System.out.println("|--------------------|");
        System.out.println("|  Mögliche Befehle  |");
        System.out.println("|                    |");
        System.out.println("|   (1)bestellen     |");
        System.out.println("|   (2)ausliefern    |");
        System.out.println("|   (3)informationen |");
        System.out.println("|   (4)loeschen      |");
        System.out.println("|   (6)ausloggen     |");
        System.out.println("|                    |");
        System.out.println("|--------------------|");
        System.out.println();
        System.out.println("Schreiben Sie die " + ANSI_RED + "Zahl" + ANSI_RESET + ", welche " +
                "neben dem Befehl steht um diesen auszuführen.");
        System.out.println("Für genauere Hilfe schreiben Sie " +ANSI_RED +"Zahl" + ANSI_RESET);
        System.out.println("Beispiel: 10 für detallierte Informationen über den bestellen-Befehl");
        System.out.println("Wenn Sie fertig sind schreiben Sie: " + ANSI_RED + "0"+ANSI_RESET);
        Scanner hilfeSc = new Scanner(System.in);

        int eingabe = hilfeSc.nextInt();
        while(eingabe != 0){
            switch (eingabe){
                case 1:
                    System.out.println(ANSI_BLUE + "Mit diesem Befehl können Sie Produkte bestellen." + ANSI_RESET);
                    break;
                case 2:
                    System.out.println(ANSI_BLUE + "Mit diesem Befehl können Sie Produkte ausliefern." + ANSI_RESET);
                    break;
                case 3:
                    System.out.println(ANSI_BLUE + "Mit diesem Befehl können Sie Informationen zu den Produkten und Ihrem " +
                            "Lager erhalten." + ANSI_RESET);
                    break;
                case 4:
                    System.out.println(ANSI_BLUE + "Mit diesem Befehl können Sie Produkte löschen." + ANSI_RESET);
                    break;
                case 6:
                    System.out.println(ANSI_BLUE + "Mit diesem Befehl loggen Sie sich aus, das Programm wird beendet." + ANSI_RESET);
                    break;
            }
            System.out.println();
            System.out.println("Für genauere Hilfe schreiben Sie " +ANSI_RED +"Zahl" + ANSI_RESET);
            System.out.println("Beispiel: 10 für detallierte Informationen über den bestellen-Befehl");
            System.out.println("Wenn Sie fertig sind schreiben Sie: " + ANSI_RED + "0"+ANSI_RESET);
            eingabe = hilfeSc.nextInt();
        }
        hilfeSc.close();

    }

}
