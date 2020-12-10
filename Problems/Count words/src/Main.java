import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String str = reader.readLine();
        String[] strings = str.split(" ");

        int numberOfWords = 0;

        for (String string : strings) {
            if (!"".equals(string)) {
                numberOfWords++;
            }
        }

        System.out.println(numberOfWords);

        reader.close();
    }
}