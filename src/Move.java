public class Move {
    private int line;
    private int column;

    Move(int linie, int coloana) {
        this.line = linie;
        this.column = coloana;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setLine(int linie) {
        this.line = linie;
    }

    public void setColumn(int coloana) {
        this.column= coloana;
    }
}

