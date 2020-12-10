import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {

        try (InputStream inputStream = System.in) {

            byte[] bytes1 = inputStream.readAllBytes();

            for (var bt : bytes1) {
                System.out.print(bt);
            }
        }
    }
}