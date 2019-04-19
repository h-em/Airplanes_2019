import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int ok;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("introduceti numele: ");
            String numePalayerl1 = scanner.nextLine();

            //jucatorul
            Player player1 = new Player(numePalayerl1);

            //creez jocul
            Avioane avioane = new Avioane(player1);
            avioane.playGame();
            System.out.println("Doresti sa reiei jocul? \nTasteaza: 1->DA   0->NU");
            ok=scanner.nextInt();
        }while(ok==1);
    }
}
