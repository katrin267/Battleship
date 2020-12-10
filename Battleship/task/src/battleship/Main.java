package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BattleField player1 = new BattleField("Player 1");
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        BattleField player2 = new BattleField("Player 2");
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        player1.startGame(player2);
        player2.startGame(player1);

        do {
            if (player1.makeShot()) {
                return;
            } else {
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
            }
            if (player2.makeShot()) {
                return;
            } else {
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
            }
        } while (true);
    }
}
