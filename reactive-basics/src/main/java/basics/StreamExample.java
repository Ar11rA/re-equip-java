package basics;

import java.util.stream.Stream;

public class StreamExample {
    public static void main(String[] args) {
        Stream.of(1, 2, 3)
          .map(curr -> {
              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              return curr * 2;
          })
          .forEach(System.out::println);
    }
}
