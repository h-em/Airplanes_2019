import java.sql.SQLOutput;
import java.util.Scanner;

public class Avioane {

    public static final char SIMBOL_X = 'X';
    public static final char SIMBOL_O = 'O';
    public static final char SIMBOL_AVARIAT = '?';

    public static final int GAME_SIZE = 10;

    char[][] game = new char[GAME_SIZE][GAME_SIZE];

    Player player1;

    Avioane(Player player1) {
        this.player1 = player1;
    }


    public void initBoard() {
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                game[i][j] = '*';
            }
        }
    }

    public void showGame() {
        int index = 0;
        System.out.print("  ");
        for (int k = 0; k < GAME_SIZE - 1; k++) {
            System.out.print(index + " ");
            index++;
        }

        System.out.print(index + "\n");
        for (int i = 0; i < GAME_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < GAME_SIZE; j++) {
                System.out.print(game[i][j] + " ");
            }
            System.out.println();
        }
    }


    public Move readMove() {
        System.out.println(" Mutarea ta este: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] arrayOfString = s.split("-");
        Move mutare = new Move(Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]));

        return mutare;
    }


    public Plane locatieAvion() {
        // switch (x) {
        //  case 0:
            /*    Move move1 = new Move(1, 0);
                Move move2 = new Move(2, 0);
                Move move3 = new Move(3, 0);
                Move move4 = new Move(2, 1);
                Move move5 = new Move(0, 2);
                Move move6 = new Move(1, 2);
                Move move7 = new Move(2, 2);
                Move move8 = new Move(3, 2);
                Move move9 = new Move(4, 2);
                Move move10 = new Move(2, 3);*/

        Move[] move = {
                //coada
                new Move(1, 0),
                new Move(2, 0),
                new Move(3, 0),
                //legatura intre coada si aripile din fata
                new Move(2, 1),
                //aripi fata
                new Move(0, 2),
                new Move(1, 2),
                new Move(2, 2),
                new Move(3, 2),
                new Move(4, 2),
                //cap
                new Move(2, 3)
        };
        Move head = new Move(2, 3);

        return new Plane(move, head);
    }
    // }

/*
    public Move setPlane() {
        int linia = (int) Math.round(Math.random() * 2);
        int coloana = (int) Math.round(Math.random() * 2);

        System.out.println(linia + "-" + coloana);
        Move pozitieAvion = new Move(linia, coloana);
        return pozitieAvion;
    }*/

    public void makeMove(Move move, char symbol) {
        game[move.getLine()][move.getColumn()] = symbol;
    }

    public void arataAvionComplet(Plane plane, char simbol) {
        for (int i = 0; i < plane.avion.length; i++) {
            makeMove(plane.avion[i], simbol);
        }
        showGame();
    }

    public void arataAvion_oMutare(Move move, char simbol) {
        makeMove(move, simbol);
        showGame();
    }


    public void playGame() {
        initBoard();
        System.out.println("Incepe jocul.");
        showGame();
        Plane plane = locatieAvion();
        boolean esteAvariat = false;
        //Player currentPlayer = player1;
        //char currentSymbol = SIMBOL_X;
        int nrAtacuri = 0;
        boolean isWin = false;
        while (!isWin) {

            //citesc mutare
            System.out.print("Player " + player1.getName() + "(" + SIMBOL_X + ")");
            Move move = readMove();
            //validez mutarea
            if (move.getLine() >= 0 && move.getLine() < GAME_SIZE
                    && move.getColumn() >= 0 && move.getColumn() < GAME_SIZE
                    && game[move.getLine()][move.getColumn()] == '*') {

                //verific daca am nimerit avionul

                for (int i = 0; i < plane.avion.length; i++) {

                    //daca nimeresc o parte din avion
                    if (plane.avion[i].getLine() == move.getLine() && plane.avion[i].getColumn() == move.getColumn()) {

                        if ((move.getLine() == plane.head.getLine() && move.getColumn() == plane.head.getColumn())) {
                            System.out.println("Avionul a fost distrus!");
                            //modific matricea si arat avionul complet
                            arataAvionComplet(plane, SIMBOL_O); //arataAvion(plane.avion[i], SIMBOL_A);
                            esteAvariat = true;
                            isWin = true;
                            break;
                        } else {
                            //cresc nr atacuri
                            esteAvariat = true;
                            nrAtacuri++;
                            System.out.println("Avionul a fost Avariat!");
                            //modific maricea
                            arataAvion_oMutare(plane.avion[i], SIMBOL_AVARIAT);
                        }
                    }

                }
                    //afisez doar cand am terminat de iterat prin array-ul meu cu mutari
                    if (esteAvariat==false) {
                        System.out.println("Soo close! Try again!");
                        //inregisterz mutarea in marice
                        makeMove(move, SIMBOL_X);
                        showGame();
                    }

                esteAvariat = false;



        } else{
            System.out.println("Ati introdus o locatie invalida!");
        }
    }
}


}
