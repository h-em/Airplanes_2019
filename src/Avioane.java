import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Random;

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

    public boolean mutareValida(Move move) {
        return move.getLine() >= 0 && move.getLine() < GAME_SIZE
                && move.getColumn() >= 0 && move.getColumn() < GAME_SIZE
                && game[move.getLine()][move.getColumn()] == '*';
    }

    public boolean avionDistrus(Plane plane, Move move) {
        return move.getLine() == plane.head.getLine() && move.getColumn() == plane.head.getColumn();
    }

    public boolean avionAvariat(Plane plane, Move move, int i) {
        return plane.avion[i].getLine() == move.getLine() && plane.avion[i].getColumn() == move.getColumn();
    }


    public Plane locatieAvion() {
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

        //in obiectul de tip Move retin linia si colana unde se afla capul avionului
        Move head = new Move(2, 3);
        return new Plane(move, head);
    }


    public Plane generateRandomPlanePositionUpp() {

        //generez random linia si coloana
        //verific ca locatia capului sa permita construirea unui avion intreg
        //random pt linie - valori intre 0 si 6(inclusiv)
        int line = 0 + (int) (Math.random() * ((6 - 0) + 1));
        //random pt coloana - valori intre 2 si 7(inclusiv)
        int column = 2 + (int) (Math.random() * ((7 - 2) + 1));
        //salvez pozitia capului
        Move head = new Move(line, column);
        //construiesc avionul in functie de pozitia capului
        Move[] move = {

                //cap
                new Move(line, column),
                //corp avion
                new Move(line + 1, column),
                new Move(line + 2, column),
                new Move(line + 3, column),
                //aripa stanga
                new Move(line + 1, column - 1),
                new Move(line + 1, column - 2),
                //aripa dreapta
                new Move(line + 1, column + 1),
                new Move(line + 1, column + 2),
                //coada stanga
                new Move(line + 3, column - 1),
                //coada dreapta
                new Move(line + 3, column + 1)
        };

        return new Plane(move, head);
    }


    public Plane generateRandomPlanePositionDown() {

        //generez random linia si coloana
        //verific ca locatia capului sa permita construirea unui avion intreg
        //random pt linie - valori intre 3 si 9(inclusiv)
        int line = 3 + (int) (Math.random() * ((9 - 3) + 1));

        //random pt coloana - valori intre 2 si 7(inclusiv)
        int column = 2 + (int) (Math.random() * ((7 - 2) + 1));
        //salvez pozitia capului
        Move head = new Move(line, column);
        //construiesc avionul in functie de pozitia capului
        Move[] move = {

                //cap
                new Move(line, column),
                //corp avion
                new Move(line - 1, column),
                new Move(line - 2, column),
                new Move(line - 3, column),
                //aripa stanga
                new Move(line - 1, column - 1),
                new Move(line - 1, column - 2),
                //aripa dreapta
                new Move(line - 1, column + 1),
                new Move(line - 1, column + 2),
                //coada stanga
                new Move(line - 3, column - 1),
                //coada dreapta
                new Move(line - 3, column + 1)
        };

        return new Plane(move, head);
    }


    public void playGame() {
        initBoard();
        System.out.println("Incepe jocul.");
        showGame();
        Plane plane = generateRandomPlanePositionDown();
        boolean esteAvariat = false;
        //Player currentPlayer = player1;
        boolean mutareValida;
        boolean avionDistrus;
        boolean avionAvarit;
        boolean isWin = false;
        while (!isWin) {
            System.out.println(plane.head.getLine() + "-" + plane.head.getColumn());
            //citesc mutare
            System.out.print("Player " + player1.getName() + "(" + SIMBOL_X + ")");
            Move move = readMove();

            mutareValida = mutareValida(move);
            //validez mutarea
            if (mutareValida) {
                //iterez prin vectorul de mutari
                for (int i = 0; i < plane.avion.length; i++) {

                    avionAvarit = avionAvariat(plane, move, i);
                    //daca nimeresc o parte din avion
                    if (avionAvarit) {

                        avionDistrus = avionDistrus(plane, move);
                        //daca nimeresc head-ul  avionul este distrus
                        if (avionDistrus) {
                            System.out.println("Avionul a fost distrus!");
                            //modific matricea si arat avionul complet distrus
                            arataAvionComplet(plane, SIMBOL_O); //arataAvion(plane.avion[i], SIMBOL_A);
                            esteAvariat = true;
                            isWin = true;
                            break;
                        } else {
                            //daca nimeresc o parte din avion
                            esteAvariat = true;
                            System.out.println("Avionul a fost Avariat!");
                            //modific maricea
                            arataAvion_oMutare(plane.avion[i], SIMBOL_AVARIAT);
                        }
                    }
                }
                //daca nu am nimerit avionul
                if (esteAvariat == false) {
                    System.out.println("Soo close! Try again!");
                    //inregisterz mutarea in marice
                    makeMove(move, SIMBOL_X);
                    showGame();
                }

                esteAvariat = false;

            } else {
                System.out.println("Ati introdus o locatie invalida!");
            }
        }
    }


}
