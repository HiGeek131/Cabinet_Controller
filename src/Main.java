import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("/sys/class/thermal/thermal_zone0/temp");
            int fileSize = inputStream.available();

            for(int i = 0; i < fileSize; i++) {
                System.out.print((char) inputStream.read());
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
