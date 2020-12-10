package battleship;

import java.util.Scanner;

public class BattleField {
    Scanner scanner = new Scanner(System.in);

    final static int FIELD_SIZE = 10;
    final static char EMPTY = '~';
    final static char SHIP = 'O';
    final static char HIT = 'X';
    final static char MISS = 'M';
    final static String[] SHIPS_NAMES = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    final static int[] CELLS = {5, 4, 3, 3, 2};

    Ship[] ships = new Ship[SHIPS_NAMES.length];
    char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    int numberOfShipParts = 0;
    BattleField foe;
    String playerName;

    public BattleField(String playerName) {
        this.playerName = playerName;
        fillArray();
        createShips();
        arrangeShips();

    }

    private void createShips() {
        for (int i = 0; i < SHIPS_NAMES.length; i++) {
            ships[i] = new Ship(SHIPS_NAMES[i], CELLS[i], FIELD_SIZE);
        }
    }

    private void fillArray() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    public void printField(FieldType fieldType) {
        System.out.print(" ");
        for (int i = 1; i <= FIELD_SIZE; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print((char) (i + 65));
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldType == FieldType.HIDDEN && field[i][j] == SHIP) {
                    System.out.print(" " + EMPTY);
                } else {
                    System.out.print(" " + field[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void startGame(BattleField foe) {
        this.foe = foe;
//        printField(FieldType.HIDDEN);

//        do {
//            makeShot();
//        } while (numberOfShipParts != 0);

    }

    public boolean makeShot() {
        int[] coord;

        foe.printField(FieldType.HIDDEN);
        System.out.println("---------------------");
        printField(FieldType.OPEN);

        System.out.println(playerName + ", it's your turn:");

        do {
            coord = parseCoordinates(scanner.nextLine());
        } while (!chekIfCoordsOk(coord));

        if (foe.field[coord[0]][coord[1]] == HIT) {
            System.out.println("You hit a ship!");
            return false;
        }

        if (foe.field[coord[0]][coord[1]] == SHIP) {
            foe.field[coord[0]][coord[1]] = HIT;
            foe.numberOfShipParts--;
            if (foe.numberOfShipParts == 0) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return true;
            }
            for (Ship ship : foe.ships) {
                if (ship.sankShip(coord[0], coord[1])) {
                    System.out.println("You sank a ship! Specify a new target:");
                    return false;
                }
            }
            System.out.println("You hit a ship!");
        } else {
            foe.field[coord[0]][coord[1]] = MISS;
            System.out.println("You missed!");
        }
        return false;
    }

    private boolean chekIfCoordsOk(int[] coords) {
        for (var coord : coords) {
            if (coord < 0 || coord >= FIELD_SIZE) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                return false;
            }
        }
        return true;
    }


    private void arrangeShips() {
        System.out.println(playerName + ", place your ships on the game field");
        printField(FieldType.OPEN);
        for (Ship ship : ships) {
            int[] coords;
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship.getName(), ship.getCells());
            do {
                System.out.println();
                coords = getIntCoords(scanner.nextLine());
            } while (!isPlaced(ship.getCells(), coords[0], coords[1], coords[2], coords[3]));
            numberOfShipParts += ship.getCells();
            ship.addShipPosition(coords[0], coords[1], coords[2], coords[3]);
            printField(FieldType.OPEN);
        }
    }

    private int[] parseCoordinates(String coord) {
        int[] array = new int[2];
        array[0] = coord.charAt(0) - 65;
        array[1] = Integer.parseInt(coord.substring(1)) - 1;
        return array;
    }

    private int[] getIntCoords(String coord) {
        String[] coords = coord.split(" ");
        int[] array = new int[4];

        for (int i = 0; i < 2; i++) {
            array[i] = coords[i].charAt(0) - 65;
        }

        if (array[0] > array[1]) {
            int a = array[1];
            array[1] = array[0];
            array[0] = a;
        }

        for (int i = 0; i < 2; i++) {
            array[i + 2] = Integer.parseInt(coords[i].substring(1)) - 1;
        }

        if (array[2] > array[3]) {
            int a = array[3];
            array[3] = array[2];
            array[2] = a;
        }

//        startRow = array[0]
//        endRow = array[1]
//        startCol = array[2]
//        endCol = array[3]

        return array;
    }

    private boolean isPlaced(int cells, int startRow, int endRow, int startCol, int endCol) {

        if ((startRow == endRow) == (startCol == endCol)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        if (isWrongLength(startCol, endCol, cells) || isWrongLength(startRow, endRow, cells)) {
            System.out.println("Error! Wrong length of the Submarine! Try again:");
            return false;
        }
        if (!checkIfFree(startRow - 1, endRow + 1, startCol - 1, endCol + 1)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                field[i][j] = SHIP;
            }
        }

        return true;
    }

    private boolean isWrongLength(int start, int end, int cells) {
        if (start == end) {
            return false;
        }
        return Math.abs(start - end) != cells - 1;
    }

    private boolean checkIfFree(int startRow, int endRow, int startCol, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            if (i < 0 || i >= FIELD_SIZE) {
                continue;
            }
            for (int j = startCol; j <= endCol; j++) {
                if (j < 0 || j >= FIELD_SIZE) {
                    continue;
                }
                if (field[i][j] != EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


}
