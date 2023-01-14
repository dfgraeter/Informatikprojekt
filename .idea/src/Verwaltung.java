import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Verwaltung extends Konsole {
    private static final Date date = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final String datum = formatter.format(date);
    private static final File file = new File(".idea/src/Files/Medikamentenliste");
    private static ArrayList<String[]> fileListe = new ArrayList<>();


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
     *
     * @param name
     * @param anzahl
     */
    public void bestellenMed(String name, String anzahl) {

        double preis = (Math.round((Math.random() * 21.0 + 5.0) * 100.0) / 100.0);
        String preisMed = preis + "";
        String mindDatum;
        double gesamtpreis = (Math.round(preis * Double.parseDouble(anzahl) * 100.0)/100.0);
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
     *
     * @param name
     * @param anzahl
     * @throws IllegalArgumentException
     */
    public void ausliefernMed(String name, String anzahl) throws IllegalArgumentException {
        int neueAnzahl;
        int auslieferAnzahl = Integer.parseInt(anzahl);

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
                break;
            }
        }


    }


    public void loeschenMed() {
        Iterator<String[]> iterator = fileListe.iterator();
        while (((Iterator<?>) iterator).hasNext()) {
            String[] zeile = iterator.next();
            if (!ueberpruefenAbgelaufen(zeile[3]) || Integer.parseInt(zeile[1]) == 0) {
                iterator.remove();
            }
        }
    }
    public void loeschenNameMed(int antwort,String name) {
        boolean medikamentGefunden = false;
        for (String[] zeile : fileListe) {
            if (zeile[0].equals(name)) {
                fileListe.remove(zeile);
                medikamentGefunden = true;
                break;
            }
        }
        if (!medikamentGefunden) {
            System.out.println("Dieses Medikament existiert nicht");
        }
        }

    /**
     *
     * @param eingabe
     */
    public void ausgabeSortierteInformationen(int eingabe) {
        ArrayList<String[]> sortierteListe = null;

        if (eingabe > 20) {
            sortierteListe = sortierenName(fileListe);
            eingabe -= 20;
        } else if (eingabe > 10) {
            sortierteListe = sortierenDatum(fileListe);
            eingabe -= 10;
        }

        ausgabeInformationen(eingabe, sortierteListe);


    }

    /**
     * Gibt in der Konsole die angeforderten Daten aus
     *
     * @param eingabe gibt an was man wissen möchte
     */
    public void ausgabeInformationen(int eingabe) {

        switch (eingabe) {
            case 1:
                for (String[] strings : fileListe) {
                    if (!ueberpruefenAbgelaufen(strings[3])){
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tEinzelpreis: " + BLAU + strings[2] + STANDARD +
                                "\tMindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD + "\tGesamtpreis: " + BLAU + strings[4] + STANDARD);
                    }else{
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
     * @param eingabe
     * @param liste
     */
    public void ausgabeInformationen(int eingabe, ArrayList<String[]> liste){

        switch (eingabe) {
            case 1:
                for (String[] strings : liste) {
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
                for (String[] strings : liste) {
                    if (!ueberpruefenAbgelaufen(strings[3])) {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tMindesthaltbarkeitsdatum: " + ROT + strings[3] + STANDARD);
                    } else {
                        System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tMindesthaltbarkeitsdatum: " + BLAU + strings[3] + STANDARD);
                    }
                }


                break;
            case 3:
                for (String[] strings : liste) {
                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD);
                }
                break;
            case 4:
                for (String[] strings : liste) {
                    System.out.println("Name: " + BLAU + strings[0] + STANDARD + "\tAnzahl: " + BLAU + strings[1] + STANDARD + "\tPreis: " + BLAU + strings[2] + STANDARD);
                }

                break;
            case 5:
                for (String[] strings : liste) {
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
     *
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
     *
     * @param dateiName
     */
    public void druckenInformationen(String dateiName) {
        BufferedWriter writer = null;
        File file = new File("src/Files");
        if(!file.exists()){
            file.mkdir();
        }
        File neueFile = new File("src/Files/" + dateiName);
        if (fileListe.isEmpty()) {
            System.out.println("Die Liste ist leer, es kann keine Datei erstellt werden.");
            return;
        }
        try {
            int gesamtAnzahl = 0;

            writer = new BufferedWriter(new FileWriter(neueFile));
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
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *
     * @param dateiName
     * @param buchstabe
     * @throws IllegalArgumentException
     */
    public void druckenInformationen(String dateiName, String buchstabe) throws IllegalArgumentException {

        if(buchstabe.length() > 1){
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

            if(anzahlmedikamente > 0){
                writer.write("In Ihrem Lager befinden sich " + anzahlmedikamente + " Medikamente, welche den Anfangsbuchstaben "
                        + buchstabe + " besitzen.");
                writer.newLine();
                writer.write("Insgesamt sind es " + gesamtAnzahl + " von diesen Medikamenten.");
            }else{
                writer.write("Es befinden sich keine Medikamente mit dem Anfangsbuchstaben " + buchstabe + " in Ihrem Besitz");
            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param dateiName
     * @param eingabe
     */
    public void druckenInformationen(String dateiName, int eingabe) {
        ArrayList<String[]> sortierteListe = null;

        if(eingabe != 1){
            sortierteListe = sortierenName(fileListe);
        }else{
            sortierteListe = sortierenDatum(fileListe);
        }
        File neueFile = new File("src/Files/" + dateiName);
        int gesamtAnzahl = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {

            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : sortierteListe) {
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
     *
     * @param dateiName
     * @param buchstabe
     * @param eingabe
     * @throws IllegalArgumentException
     */
    public void druckenInformationen(String dateiName, String buchstabe, int eingabe) throws IllegalArgumentException{
        if(buchstabe.length() > 1){
            throw new IllegalArgumentException();
        }
        ArrayList<String[]> sortierteListe = null;

        if(eingabe == 1){
            sortierteListe = sortierenName(fileListe);
        }else{
            sortierteListe = sortierenDatum(fileListe);
        }
        File neueFile = new File("src/Files/" + dateiName);
        buchstabe = buchstabe.toUpperCase();
        int anzahlmedikamente = 0;
        int gesamtAnzahl = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(neueFile))) {
            writer.write("Heute ist der: " + datum);
            writer.newLine();
            for (String[] zeile : sortierteListe) {
                if (zeile[0].startsWith(buchstabe)) {
                    writer.write("Name: " + zeile[0] + " Anzahl: " + zeile[1] + " Preis: " + zeile[2] +
                            " Mindesthaltbarkeitsdatum: " + zeile[3] + " Gesamtpreis: " + zeile[4]);
                    writer.newLine();
                    anzahlmedikamente++;
                    gesamtAnzahl += Integer.parseInt(zeile[1]);
                }
            }

            if(anzahlmedikamente > 0){
                writer.write("In Ihrem Lager befinden sich " + anzahlmedikamente + " Medikamente, welche den Anfangsbuchstaben "
                        + buchstabe + " besitzen.");
                writer.newLine();
                writer.write("Insgesamt sind es " + gesamtAnzahl + " von diesen Medikamenten.");
            }else{
                writer.write("Es befinden sich keine Medikamente mit dem Anfangsbuchstaben " + buchstabe + " in Ihrem Besitz");
            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> getFileListe() {
        return fileListe;
    }
}