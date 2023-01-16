import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Verwaltung extends Konsole {
    private static final Date date = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final String datum = formatter.format(date);
    private static final File file = new File("src/Files/Medikamentenliste");
    private static final ArrayList<String[]> fileListe = new ArrayList<>();


    public Verwaltung() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                if (zeile.contains("Name")) {
                    continue;
                } else {
                    fileListe.add(zeile.split(";"));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Nachdem die File sortiert wurde, wird sie durch diese Methode zurückgesetzt,
     * um mit der ursprünglichen Datei weiter zuarbeiten.
     */
    public void resetFileListe() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                if (zeile.contains("Name")) {
                    continue;
                } else {
                    fileListe.add(zeile.split(";"));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param name des Medikaments, welches bestellt wird
     * @param anzahl des Medikaments, welches bestellt wird
     */
    public void bestellenMed(String name, String anzahl) {

        double preis = (Math.round((Math.random() * 21.0 + 5.0) * 100.0) / 100.0);
        String preisMed = preis + "";
        String mindDatum;
        double gesamtpreis = (Math.round(preis * Double.parseDouble(anzahl) * 100.0) / 100.0);
        String gesamtpreisMed = "" + gesamtpreis;

        if (date.getDay() < 10) {
            mindDatum = "0" + date.getDay() + "." + date.getMonth() + 1 + "." + (date.getYear() + 1902);
        } else {
            mindDatum = date.getDay() + "." + date.getMonth() + 1 + "." + (date.getYear() + 1902);
        }
        String[] neuesMedikament = {name, anzahl, preisMed, mindDatum, gesamtpreisMed};
        fileListe.add(neuesMedikament);


    }

    /**
     * @param name des Medikaments, welches ausgeliefert werden soll
     * @param anzahl des Medikaments, welches ausgeliefert werden soll
     * @throws IllegalArgumentException, wenn man mehr ausliefern will als vorhanden ist,
     * oder wenn das Medikament nicht gefunden wurde
     */
    public void ausliefernMed(String name, String anzahl) throws IllegalArgumentException {
        int neueAnzahl;
        int auslieferAnzahl = Integer.parseInt(anzahl);
        boolean vorhanden = false;
        for (String[] zeile : fileListe) {
            if (zeile[0].equals(name)) {
                int aktuelleAnzahl = Integer.parseInt(zeile[1]);
                neueAnzahl = aktuelleAnzahl - auslieferAnzahl;
                if (neueAnzahl < 0) {
                    throw new IllegalArgumentException("Du kannst nicht mehr Medikamente ausliefern, als Medikamente im Lager sind. Es sind " + zeile[1] + " im Lager.");
                } else if (neueAnzahl == 0) {
                    fileListe.remove(zeile);
                } else {
                    zeile[1] = neueAnzahl + "";
                }
                vorhanden = true;
                break;
            }
        }
        if (!vorhanden) {
            throw new IllegalArgumentException("Das Medikament konnte nicht gefunden werden");
        }

    }


    /**
     * @param eingabe
     */
    public void ausgabeSortierteInformationen(int eingabe) {
        try {
            if (eingabe > 20) {
                sortierenName(fileListe);
                eingabe -= 20;
            } else if (eingabe > 10) {
                sortierenDatum(fileListe);
                eingabe -= 10;
            }
            ausgabeInformationen(eingabe);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gibt in der Konsole die angeforderten Daten aus
     * @param eingabe gibt an was man wissen möchte
     */
    public void ausgabeInformationen(int eingabe) {

        switch (eingabe) {
            case 1:
                for (String[] strings : fileListe) {
                    if (!ueberpruefenAbgelaufen(strings[3])) {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tEinzelpreis: " + BLAU + strings[2] + STANDARD +
                                "\tMindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD + "\tGesamtpreis: " + BLAU + strings[4] + STANDARD);
                    } else {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tEinzelpreis: " + BLAU + strings[2] + STANDARD +
                                "\tMindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD + "\tGesamtpreis: " + BLAU + strings[4] + STANDARD);
                    }
                }
                break;
            case 2:
                for (String[] strings : fileListe) {
                    if (!ueberpruefenAbgelaufen(strings[3])) {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tMindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD);
                    } else {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tMindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD);
                    }
                }
                break;
            case 3:
                for (String[] strings : fileListe) {
                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD);
                }
                break;
            case 4:
                for (String[] strings : fileListe) {
                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tPreis: " + BLAU + strings[2] + STANDARD);
                }

                break;
            case 5:
                for (String[] strings : fileListe) {
                    if (!ueberpruefenAbgelaufen(strings[3])) {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tPreis: " + BLAU + strings[2] + STANDARD +
                                "\tMindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD);
                    } else {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tPreis: " + BLAU + strings[2] + STANDARD +
                                "\tMindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD);
                    }
                }
                break;

        }

    }


    /**
     *
     */
    public void loeschenMed() {

        Iterator<String[]> iterator = fileListe.iterator();
        while (iterator.hasNext()) {
            String[] zeile = iterator.next();
            if (!ueberpruefenAbgelaufen(zeile[3]) || Integer.parseInt(zeile[1]) == 0) {
                iterator.remove();
            }
        }


    }

    /**
     * @param name
     */
    public void loeschenMed(String name) {
        Iterator<String[]> iterator = fileListe.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            String[] zeile = iterator.next();
            if (zeile[0].equals(name)) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Medikament " + name + " erfolgreich gelöscht.");
        } else {
            System.out.println("Medikament " + name + " konnte nicht gefunden werden.");
        }
    }


    /**
     * @param dateiName
     */
    public void druckenInformationen(String dateiName) {
        File neueFile = new File("src/Files/" + dateiName);
        int gesamtAnzahl = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {

            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : fileListe) {
                writer.write("Name: " + zeile[0] + " Anzahl: " + zeile[1] + " Preis: " + zeile[2] +
                        " Mindesthaltbarkeitsdatum: " + zeile[3] + " Gesamtpreis: " + zeile[4]);
                writer.newLine();
                gesamtAnzahl += Integer.parseInt(zeile[1]);
            }
            writer.write("Insgesamte Anzahl an Medizinischen Produkten im Lager: " + gesamtAnzahl);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param dateiName
     * @param buchstabe
     * @throws IllegalArgumentException
     */
    public void druckenInformationen(String dateiName, String buchstabe) throws IllegalArgumentException {

        if (buchstabe.length() != 1) {
            throw new IllegalArgumentException();
        }

        File neueFile = new File("src/Files/" + dateiName);
        buchstabe = buchstabe.toUpperCase();
        int anzahlmedikamente = 0;
        int gesamtAnzahl = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {
            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : fileListe) {
                if (zeile[0].startsWith(buchstabe)) {
                    writer.write("Name: " + zeile[0] + " Anzahl: " + zeile[1] + " Preis: " + zeile[2] +
                            " Mindesthaltbarkeitsdatum: " + zeile[3] + " Gesamtpreis: " + zeile[4]);
                    writer.newLine();
                    anzahlmedikamente++;
                    gesamtAnzahl += Integer.parseInt(zeile[1]);
                }
            }

            if (anzahlmedikamente > 0) {
                writer.write("In Ihrem Lager befinden sich " + anzahlmedikamente + " Medikamente, welche den Anfangsbuchstaben "
                        + buchstabe + " besitzen.");
                writer.newLine();
                writer.write("Insgesamt sind es " + gesamtAnzahl + " von diesen Medikamenten.");
            } else {
                writer.write("Es befinden sich keine Medikamente mit dem Anfangsbuchstaben " + buchstabe + " in Ihrem Besitz");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param dateiName
     * @param eingabe
     */
    public void druckenInformationen(String dateiName, int eingabe) {


        if (eingabe == 1) {
            sortierenName(fileListe);
        } else {
            sortierenDatum(fileListe);
        }
        File neueFile = new File("src/Files/" + dateiName);
        int gesamtAnzahl = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {

            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : fileListe) {
                writer.write("Name: " + zeile[0] + " Anzahl: " + zeile[1] + " Preis: " + zeile[2] +
                        " Mindesthaltbarkeitsdatum: " + zeile[3] + " Gesamtpreis: " + zeile[4]);
                writer.newLine();
                gesamtAnzahl += Integer.parseInt(zeile[1]);
            }
            writer.write("Insgesamte Anzahl an Medizinischen Produkten im Lager: " + gesamtAnzahl);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param dateiName
     * @param buchstabe
     * @param eingabe
     * @throws IllegalArgumentException wenn der buchstabe ungleich 1
     */
    public void druckenInformationen(String dateiName, String buchstabe, int eingabe) throws IllegalArgumentException {
        if (buchstabe.length() != 1) {
            throw new IllegalArgumentException();
        }

        if (eingabe == 1) {
            sortierenName(fileListe);
        } else {
            sortierenDatum(fileListe);
        }
        File neueFile = new File("src/Files/" + dateiName);
        buchstabe = buchstabe.toUpperCase();
        int anzahlmedikamente = 0;
        int gesamtAnzahl = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {
            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : fileListe) {
                if (zeile[0].startsWith(buchstabe)) {
                    writer.write("Name: " + zeile[0] + " Anzahl: " + zeile[1] + " Preis: " + zeile[2] +
                            " Mindesthaltbarkeitsdatum: " + zeile[3] + " Gesamtpreis: " + zeile[4]);
                    writer.newLine();
                    anzahlmedikamente++;
                    gesamtAnzahl += Integer.parseInt(zeile[1]);
                }
            }

            if (anzahlmedikamente > 0) {
                writer.write("In Ihrem Lager befinden sich " + anzahlmedikamente + " Medikamente, welche den Anfangsbuchstaben "
                        + buchstabe + " besitzen.");
                writer.newLine();
                writer.write("Insgesamt sind es " + gesamtAnzahl + " von diesen Medikamenten.");
            } else {
                writer.write("Es befinden sich keine Medikamente mit dem Anfangsbuchstaben " + buchstabe + " in Ihrem Besitz");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * @param liste
     * @return
     * @throws IllegalArgumentException
     */
    private static ArrayList<String[]> sortierenDatum(ArrayList<String[]> liste) throws IllegalArgumentException {

        if (liste.size() < 1) {
            throw new IllegalArgumentException("Die Liste hat keine Einträge");
        }
        String[] a;

        for (int i = 1; i < liste.size(); i++) {
            for (int j = 0; j < (liste.size() - 1); j++) {
                Date date1 = new Date((Integer.parseInt((liste.get(j))[3].split("\\.")[2]) - 1900),
                        Integer.parseInt((liste.get(j))[3].split("\\.")[1]) - 1, Integer.parseInt((liste.get(j))[3].split("\\.")[0]));
                Date date2 = new Date((Integer.parseInt((liste.get(j + 1))[3].split("\\.")[2]) - 1900),
                        Integer.parseInt((liste.get(j + 1))[3].split("\\.")[1]) - 1, Integer.parseInt((liste.get(j + 1))[3].split("\\.")[0]));
                if (date1.compareTo(date2) > 0) {
                    a = liste.get(j);
                    liste.set(j, liste.get(j + 1));
                    liste.set(j + 1, a);
                }


            }
        }

        return liste;
    }

    /**
     * @param liste
     * @return
     * @throws IllegalArgumentException
     */
    private static ArrayList<String[]> sortierenName(ArrayList<String[]> liste) throws IllegalArgumentException {


        if (liste.size() < 1) {
            throw new IllegalArgumentException("Die Liste hat keine Einträge");
        }


        String[] a;

        for (int i = 1; i < liste.size(); i++) {
            for (int j = 0; j < (liste.size() - 1); j++) {
                if ((liste.get(j))[0].compareTo((liste.get(j + 1))[0]) > 0) {
                    a = liste.get(j);
                    liste.set(j, liste.get(j + 1));
                    liste.set(j + 1, a);
                }


            }
        }
        return liste;
    }

    /**
     * @param datumMed das Datum des Medikaments, welches überprüft werden soll
     * @return ob das Medikament abgelaufen ist, oder nicht
     */
    private static boolean ueberpruefenAbgelaufen(String datumMed) {
        Date dateMedi;
        if (datum.equals(datumMed)) {
            return true;
        } else {
            String[] stringsMed = datumMed.split("\\.");
            int jahrMed = Integer.parseInt(stringsMed[2]);
            int monatMed = Integer.parseInt(stringsMed[1]);
            int tagMed = Integer.parseInt(stringsMed[0]);
            dateMedi = new Date(jahrMed - 1900, monatMed - 1, tagMed);


        }
        return date.before(dateMedi);
    }

    /**
     *
     */
    public void update() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("Name;Anzahl;Preis;Mindesthaltbarkeitsdatum;Gesamtpreis");
            writer.newLine();

            for (String[] zeile : fileListe) {
                writer.write(zeile[0] + ";" + zeile[1] + ";" + zeile[2] + ";" + zeile[3] + ";" + zeile[4]);
                writer.newLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * @return fileListe
     */
    public static ArrayList<String[]> getFileListe() {
        return fileListe;
    }

    /**
     * @param name
     * @return
     */
    public double getPreis(String name) {
        for (String[] zeile : fileListe) {
            if (zeile[0].equals(name)) {
                return Double.parseDouble(zeile[2].replace("€", "").replace(",", "."));
            }


        }
        return 0;
    }
}