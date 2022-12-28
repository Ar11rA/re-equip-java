package self.study.example.utilities;

public class TimeUtils {
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException exception) {
            System.out.println("Ignoring ex...");
        }
    }
}
