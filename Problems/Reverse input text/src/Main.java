import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String str = reader.readLine();
            StringBuilder stringBuilder = new StringBuilder(str);

            System.out.println(stringBuilder.reverse().toString());
        }
    }
}