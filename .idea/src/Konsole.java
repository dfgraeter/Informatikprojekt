import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Konsole {
    public static final String ROT = "\u001B[31m";
    public static final String BLAU = "\u001B[34m";
    public static final String STANDARD = "\u001B[0m";
    private static final Date date = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final String datum = formatter.format(date);
    private static File file = new File(".idea/src/Files/Medikamentenliste");


    public static void hilfe() {
        System.out.println("Hilfe: ");
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
        System.out.println("Schreiben Sie die " + ROT + "Zahl" + STANDARD + ", welche " +
                "neben dem Befehl steht um diesen auszuführen.");
        System.out.println("Für genauere Hilfe schreiben Sie " + ROT + "Zahl" + STANDARD);
        System.out.println("Beispiel: 10 für detallierte Informationen über den bestellen-Befehl");
        System.out.println("Wenn Sie fertig sind schreiben Sie: " + ROT + "0" + STANDARD);
        Scanner hilfeSc = new Scanner(System.in);

        int eingabe = hilfeSc.nextInt();
        while(eingabe != 0){
            switch (eingabe) {
                case 1:
                    System.out.println(BLAU + "Mit diesem Befehl können Sie Produkte bestellen. Der Befehl verändert die Anzahl der Medikamente" + STANDARD);
                    break;
                case 2:
                    System.out.println(BLAU + "Mit diesem Befehl können Sie Produkte ausliefern. Der Befehl verändert die Anzahl der Medikamente" + STANDARD);
                    break;
                case 3:
                    System.out.println(BLAU + "Mit diesem Befehl können Sie Informationen zu den Produkten in Ihrem " +
                            "Lager erhalten." + STANDARD);
                    break;
                case 4:
                    System.out.println(BLAU + "Mit diesem Befehl können Sie alle abgelaufenen Produkte löschen." + STANDARD);
                    break;
                case 6:
                    System.out.println(BLAU + "Mit diesem Befehl loggen Sie sich aus, das Programm wird beendet." + STANDARD);
                    break;
            }
            System.out.println();
            System.out.println("Für genauere Hilfe schreiben Sie " + ROT + "Zahl" + STANDARD);
            System.out.println("Beispiel: 10 für detallierte Informationen über den bestellen-Befehl");
            System.out.println("Wenn Sie fertig sind schreiben Sie: " + ROT + "0" + STANDARD);
            eingabe = hilfeSc.nextInt();
        }


    }

    /**
     * Erweitern mit aktiver Suche
     */

    public static void informationen() {

        System.out.println(ROT + "0: " + STANDARD + "Hauptmenü");
        System.out.println(BLAU + "Was wollen Sie wissen?" + STANDARD);
        System.out.println(ROT + "1: " + STANDARD + "Für alle Medikamentennamen, Anzahl, Preise, Mindesthaltbarkeitsdatum und Gesamtpreis(Anzahl*Preis)");
        System.out.println(ROT + "2: " + STANDARD + "Für alle Medikamente mit dem Mindesthaltbarkeitsdatum");
        System.out.println(ROT + "3: " + STANDARD + "Für alle Medikamente mit Anzahl");
        System.out.println(ROT + "4: " + STANDARD + "Für alle Medikamente mit Anzahl und Preis");
        System.out.println(ROT + "5: " + STANDARD + "Für alle Medikamente mit Anzahl,Preis und dem Mindesthaltbarkeitsdatum");


        Scanner infSc = new Scanner(System.in);
        int eingabe = infSc.nextInt();


        String zeile;
        while (eingabe != 0) {


            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while ((zeile = reader.readLine()) != null) {
                    if (zeile.contains("Name")) {
                        continue;
                    } else {
                        String[] strings = zeile.split(";");

                        switch (eingabe) {
                            case 1:
                                if (!ueberpruefenAbgelaufen(strings[3])) {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD + " Einzelpreis: " + BLAU + strings[2] + STANDARD +
                                            " Mindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD + " Gesamtpreis: " + BLAU + strings[4] + STANDARD);
                                } else {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD + " Einzelpreis: " + BLAU + strings[2] + STANDARD +
                                            " Mindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD + " Gesamtpreis: " + BLAU + strings[4] + STANDARD);
                                }
                                break;
                            case 2:
                                if (!ueberpruefenAbgelaufen(strings[3])) {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Mindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD);
                                } else {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Mindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD);
                                }
                                break;
                            case 3:
                                System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD);
                                break;
                            case 4:
                                System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD + " Preis: " + BLAU + strings[2] + STANDARD);
                                break;
                            case 5:
                                if (!ueberpruefenAbgelaufen(strings[3])) {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD + " Preis: " + BLAU + strings[2] + STANDARD +
                                            " Mindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD);
                                } else {
                                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + " Anzahl: " + BLAU + strings[1] + STANDARD + " Preis: " + BLAU + strings[2] + STANDARD +
                                            " Mindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD);
                                }
                                break;

                        }


                    }
                }
                reader.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("Wollen Sie weitere Informationen abrufen?");
            System.out.println(ROT + "0: " + STANDARD + "Nein");
            System.out.println("Sonst eine Zahl von 1-5 für die jeweilige Information.");
            eingabe = infSc.nextInt();
        }

    }

    /**
     *loeschen begrenzen auf alle abgelaufenen sonst nichts machen mit löschen!!!
     * zur Not ArrayList<zeile[]> zeilen = new ArrayList<>(); und dann neu in file schreiben und mit remove
     */

    public static void loeschen() {
        System.out.println("Was wollen Sie löschen?");
        System.out.println(ROT + "1: " + STANDARD + "Alles");
        System.out.println(ROT + "2: " + STANDARD + "Alle abgelaufenen Medikamente");
        System.out.println(ROT + "3: " + STANDARD + "Individuell");

        String zeile;
        Scanner loeSc = new Scanner(System.in);
        int eingabe = loeSc.nextInt();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            while ((zeile = reader.readLine()) != null) {
                if (eingabe == 1) {
                    writer.write("");
                    writer.close();
                    reader.close();
                } else if (zeile.contains("Name")) {
                    continue;
                } else {
                    String[] strings = zeile.split(";");
                    switch (eingabe) {
                        case 2:
                            if (ueberpruefenAbgelaufen(strings[3])) {
                                continue;
                            } else {
                                writer.write("");
                            }

                            break;
                        case 3:
                            break;
                    }
                }


            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }


    /**
     * Wenn false dann ist es abgelaufen, bei true nicht.
     *
     * @param datumMed
     * @return
     */

    private static boolean ueberpruefenAbgelaufen(String datumMed) {
        if (datum.equals(datumMed)) {
            return true;
        } else {
            String[] stringsHeute = datum.split("\\.");
            int jahrHeute = Integer.parseInt(stringsHeute[2]);
            int monatHeute = Integer.parseInt(stringsHeute[1]);
            int tagHeute = Integer.parseInt(stringsHeute[0]);
            String[] stringsMed = datumMed.split("\\.");
            int jahrMed = Integer.parseInt(stringsMed[2]);
            int monatMed = Integer.parseInt(stringsMed[1]);
            int tagMed = Integer.parseInt(stringsMed[0]);

            if (jahrMed < jahrHeute) {
                return false;
            } else if (jahrMed == jahrHeute) {
                if (monatMed < monatHeute) {
                    return false;
                } else if (monatMed == monatHeute) {
                    if (tagMed < tagHeute) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

}
