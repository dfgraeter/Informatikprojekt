import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        System.out.println("Wollen Sie das System starten?");
        System.out.println("1: Ja");
        System.out.println("Irgendeine andere Eingabe steht für ein nein");
        Scanner sc = new Scanner(System.in);
        if(sc.nextLine().equals("1")){
            Verwaltung verwaltung = new Verwaltung();
            verwaltung.start();
            sc.close();
        }



    }

}
