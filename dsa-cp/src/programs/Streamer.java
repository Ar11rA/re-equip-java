package programs;

import commons.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streamer {

    private static int reduceExampleSum(List<User> users) {
        return users
          .stream()
          .reduce(0,
            (acc, curr) -> acc + curr.getLevel(),
            Integer::sum
          );
    }

    private static HashMap<Integer, Integer> reduceExampleCountByLevel(List<User> users) {
        return users
          .stream()
          .reduce(new HashMap<>(),
            (acc, curr) -> {
                acc.put(curr.getLevel(), acc.getOrDefault(curr.getLevel(), 0) + 1);
                return acc;
            }, (a, b) -> b); // cannot be parallelized
    }

    private static Map<Integer, Long> collectExampleCountByLevel(List<User> users) {
        return users
          .stream()
          .collect(Collectors.groupingBy(
            User::getLevel,
            Collectors.counting()
          ));
    }

    public static void main(String[] args) {
        System.out.println(reduceExampleSum(Arrays.asList(
          new User("a", "b", 1),
          new User("c", "d", 2),
          new User("e", "f", 2)
        )));
        System.out.println(reduceExampleCountByLevel(Arrays.asList(
          new User("a", "b", 1),
          new User("c", "d", 2),
          new User("e", "f", 2),
          new User("e", "f", 2),
          new User("e", "f", 3)
        )));
        System.out.println(collectExampleCountByLevel(Arrays.asList(
          new User("a", "b", 1),
          new User("c", "d", 2),
          new User("e", "f", 2),
          new User("e", "f", 2),
          new User("e", "f", 3)
        )));
    }
}
