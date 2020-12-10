import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long k = scanner.nextLong();
        int n = scanner.nextInt();
        double m = scanner.nextDouble();
        boolean isLess;

        do {
            Random random = new Random(k);
            isLess = true;
            for (int i = 0; i < n; i++) {
                double x = random.nextGaussian();
                if (x > m) {
                    isLess = false;
                    break;
                }
            }
            k++;
        } while (!isLess);

        System.out.println(--k);
    }
}