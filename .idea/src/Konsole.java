import java.util.Scanner;

public class Konsole {
    public static final String ROT = "\u001B[31m";
    public static final String BLAU = "\u001B[34m";
    public static final String STANDARD = "\u001B[0m";
    public static Scanner scanner = new Scanner(System.in);
    Verwaltung verwaltung;




    /**
     * startet das Programm und dient als Hauptmenü, um zu entscheiden was man machen möchte
     */

    public void start() {
        Verwaltung verwaltung = new Verwaltung();
        int eingabe;
        do {
            System.out.println("|-------------------------|");
            System.out.println("|-------Hauptmenü---------|");
            System.out.println("|                         |");
            System.out.println("| (1)bestellen            |");
            System.out.println("| (2)ausliefern           |");
            System.out.println("| (3)informationen        |");
            System.out.println("| (4)loeschen             |");
            System.out.println("| (5)sortieren & ausgeben |");
            System.out.println("| (6)drucken              |");
            System.out.println("| (7)ausloggen            |");
            System.out.println("|                         |");
            System.out.println("|-------------------------|");
            eingabe = scanner.nextInt();

            switch (eingabe) {
                case 1:
                    bestellen();
                    break;
                case 2:
                    ausliefern();
                    break;
                case 3:
                    informationen();
                    break;
                case 4:
                    loeschen();
                    break;
                case 5:
                    sortierenUndAusgeben();
                    break;
                case 6:
                    drucken();
                    break;

            }
        } while (7 != eingabe);

        System.out.println("Soll die File überschrieben werden?");
        System.out.println("1: Ja");
        System.out.println("Irgendeine andere Zahl steht für ein nein");
        eingabe = scanner.nextInt();
        if(eingabe == 1){
            verwaltung.update();
        }


    }


    /**
     *
     */
    public  void informationen() {
        int eingabe;
        do {

            System.out.println(ROT + "0: " + STANDARD + "Hauptmenü");
            System.out.println(BLAU + "Was wollen Sie wissen?" + STANDARD);
            System.out.println(ROT + "1: " + STANDARD + "Für alle Medikamentennamen, Anzahl, Preise, Mindesthaltbarkeitsdatum und Gesamtpreis(Anzahl*Preis)");
            System.out.println(ROT + "2: " + STANDARD + "Für alle Medikamente mit dem Mindesthaltbarkeitsdatum");
            System.out.println(ROT + "3: " + STANDARD + "Für alle Medikamente mit Anzahl");
            System.out.println(ROT + "4: " + STANDARD + "Für alle Medikamente mit Anzahl und Preis");
            System.out.println(ROT + "5: " + STANDARD + "Für alle Medikamente mit Anzahl,Preis und dem Mindesthaltbarkeitsdatum");
            eingabe = scanner.nextInt();
            verwaltung.ausgabeInformationen(eingabe);

        } while (eingabe != 0);
    }


    /**
     *
     */
    public  void sortierenUndAusgeben(){
        int eingabe;
        do {

            System.out.println(ROT + "0: " + STANDARD + "Hauptmenü");
            System.out.println(BLAU + "Was wollen Sie wissen?" + STANDARD);
            System.out.println(ROT + "1x" + STANDARD + "Sortieren nach Datum und dann eine der Optionen von unten");
            System.out.println(ROT + "2x" + STANDARD + "Sortieren nach Name und dann eine der Optionen von unten");
            System.out.println(ROT + "(1/2)1: " + STANDARD + "Für alle Medikamentennamen, Anzahl, Preise, Mindesthaltbarkeitsdatum und Gesamtpreis(Anzahl*Preis)");
            System.out.println(ROT + "(1/2)2: " + STANDARD + "Für alle Medikamente mit dem Mindesthaltbarkeitsdatum");
            System.out.println(ROT + "(1/2)3: " + STANDARD + "Für alle Medikamente mit Anzahl");
            System.out.println(ROT + "(1/2)4: " + STANDARD + "Für alle Medikamente mit Anzahl und Preis");
            System.out.println(ROT + "(1/2)5: " + STANDARD + "Für alle Medikamente mit Anzahl,Preis und dem Mindesthaltbarkeitsdatum");
            eingabe = scanner.nextInt();
            verwaltung.ausgabeSortierteInformationen(eingabe);

        } while (eingabe != 0);



    }

    /**
     *
     */
    public void ausliefern() {
        int groessevorbestellung = verwaltung.getFileListe().size();

        System.out.println("Wie viele Medikamente wollen Sie ausliefern?");
        System.out.print("Anzahl Medikamente: ");
        int x = scanner.nextInt();
        try{
        for (int i = 0; i < x; i++){

            System.out.println(BLAU+"Was wollen Sie ausliefern?"+STANDARD);
            System.out.print("Name: ");
            String name = scanner.next();
            System.out.print("Anzahl: ");
            String anzahl = scanner.next();
            verwaltung.ausliefernMed(name, anzahl);

        }}catch(IllegalArgumentException e){
            System.out.println(e.getMessage());

        }


        double summe = 0;
        System.out.println("Auslieferübersicht: ");
        for(int i = groessevorbestellung; i < verwaltung.getFileListe().size(); i++){
            System.out.println("Produktname: " + (verwaltung.getFileListe().get(i))[0] + " Anzahl: " + (verwaltung.getFileListe().get(i))[1] +
                    " Preis: " + (verwaltung.getFileListe().get(i))[2] + " Gesamtpreis: " + (verwaltung.getFileListe().get(i))[4]);

            summe += Double.parseDouble((verwaltung.getFileListe().get(i))[4].replace("{","").replace("," , "."));
        }

        System.out.println();
        System.out.println("Sie erhalten: " + summe +"€ von Ihrem Kunden");


    }

    /**
     *
     */
    public void bestellen() {
        System.out.println("Wie viele Medikamente wollen Sie bestellen?");
        System.out.print("Anzahl Medikamente: ");
        int sizevorbestellung = verwaltung.getFileListe().size();
        int x = scanner.nextInt();
        for (int i = 0; i < x; i++){

            System.out.println(BLAU+"Was wollen Sie bestellen?"+STANDARD);
            System.out.print("Name: ");
            String name = scanner.next();
            System.out.print("Anzahl: ");
            String anzahl = scanner.next();
            verwaltung.bestellenMed(name, anzahl);

        }

        double summe = 0;
        System.out.println("Bestellübersicht: ");
        for(int i = sizevorbestellung; i < verwaltung.getFileListe().size(); i++){
            System.out.println("Produktname: " + (verwaltung.getFileListe().get(i))[0] + " Anzahl: " + (verwaltung.getFileListe().get(i))[1] +
                    " Preis: " + (verwaltung.getFileListe().get(i))[2] + " Gesamtpreis: " + (verwaltung.getFileListe().get(i))[4]);

            summe += Double.parseDouble((verwaltung.getFileListe().get(i))[4].replace("{","").replace("," , "."));
        }

        System.out.println();
        System.out.println("Summe: " + summe +"€");



    }

    /**
     *
     */
    public void loeschen() {
        System.out.println(BLAU+"Welche Medikamente wollen Sie löschen"+STANDARD);
        System.out.println(ROT + "1: " + STANDARD + "Lösche alle Medikamente die das Minedesthaltbarkeritsdatum überschritten haben");
        System.out.println(ROT + "2: " + STANDARD + "Lösche ein bestmmtes Medikament");
        int eingabe = scanner.nextInt();
        switch(eingabe) {
            case 1:
                System.out.println("Sind sie sich sicher das sie es Löschen wollen?");
                System.out.println("1: Ja");
                System.out.println("Irgendeine andere Eingabe steht für ein nein");
                int antwort = scanner.nextInt();
                if(antwort == 1){
                    verwaltung.loeschenMed();
                }
                break;
            case 2:
                System.out.println("Nach welchem Medikament suchen Sie?");
                System.out.println("Name: ");
                String name = scanner.next();
                verwaltung.loeschenNameMed(name);
                break;
        }

    }

    /**
     *
     */
    public void drucken(){

        System.out.println(BLAU+"Wie soll Ihre Datei heißen?"+STANDARD);
        System.out.print("Name: ");
        String dateiName = scanner.next();

        System.out.println(BLAU+"Was wollen Sie drucken?"+STANDARD);
        System.out.println(ROT + "1: " + STANDARD + "Einfach die File drucken.");
        System.out.println(ROT + "2: " + STANDARD + "File sortieren und dann drucken.");
        System.out.println(ROT + "3: " + STANDARD + "Ein Anfangsbuchstaben suchen und alle Einträge dazu drucken.");
        System.out.println(ROT + "4: " + STANDARD + "Ein Anfangsbuchstaben suchen und alle Einträge dazu sortiert drucken.");
        System.out.println(BLAU + "Hinweis: " + STANDARD + "Es wird nach Namen oder nach  Mindesthaltbarkeitdatum sortiert. Sie entscheiden.");

        int eingabe = scanner.nextInt();
        String buchstabe;

        switch(eingabe){
            case 1:
                verwaltung.druckenInformationen(dateiName);
                break;
            case 2:
                System.out.println(ROT + "1:" + STANDARD + " Sortieren nach Datum");
                System.out.println(ROT + "2:" + STANDARD + " Sortieren nach Name");
                eingabe = scanner.nextInt();
                verwaltung.druckenInformationen(dateiName, eingabe);
                break;
            case 3:
                System.out.print("Anfangsbuchstabe: ");
                buchstabe = scanner.next();
                verwaltung.druckenInformationen(dateiName, buchstabe);
                break;
            case 4:
                System.out.print("Anfangsbuchstabe: ");
                buchstabe = scanner.next();
                System.out.println(ROT + "1" + STANDARD + "Sortieren nach Datum");
                System.out.println(ROT + "2" + STANDARD + "Sortieren nach Name");
                eingabe = scanner.nextInt();
                verwaltung.druckenInformationen(dateiName,buchstabe,eingabe);
                break;
        }
    }

}
