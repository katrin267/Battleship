package battleship;

public class Ship {
    private final String name;
    private final int cells;
    private boolean[][] shipCells;
    private int hitCells = 0;

    public Ship(String name, int cells, int fieldSize) {
        this.name = name;
        this.cells = cells;
        shipCells = new boolean[fieldSize][fieldSize];
    }

    public void addShipPosition(int startRow, int endRow, int startCol, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                shipCells[i][j] = true;
            }
        }
    }

    public boolean sankShip(int row, int col) {
        if (shipCells[row][col]) {
            hitCells++;
            return hitCells == cells;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getCells() {
        return cells;
    }


}
