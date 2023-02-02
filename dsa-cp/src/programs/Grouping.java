package programs;

import commons.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class Grouping {

    private static final String RECRUIT = "Recruit";
    private static final String SOLDIER = "Soldier";
    private static final String COMMANDER = "Commander";
    private static final String CAPTAIN = "Captain";
    private static final String VETERAN = "Veteran";

    /*
    Levels FYI
    0-999: Recruit
    1000-3000: Soldier
    3001-6000: Commander
    6001-8000: Captain
    8001-10000: Veteran
     */
    private static List<String> printGroupsByFrequency(List<Integer> levels) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        for (Integer level : levels) {
            switch (level) {
                case Integer l when l >= 0 && l <= 999 ->
                  frequencyMap.put(RECRUIT, frequencyMap.getOrDefault(RECRUIT, 0) + 1);
                case Integer l when l >= 1000 && l <= 3000 ->
                  frequencyMap.put(SOLDIER, frequencyMap.getOrDefault(SOLDIER, 0) + 1);
                case Integer l when l >= 3001 && l <= 6000 ->
                  frequencyMap.put(COMMANDER, frequencyMap.getOrDefault(COMMANDER, 0) + 1);
                case Integer l when l >= 6001 && l <= 8000 ->
                  frequencyMap.put(CAPTAIN, frequencyMap.getOrDefault(CAPTAIN, 0) + 1);
                default -> frequencyMap.put(VETERAN, frequencyMap.getOrDefault(VETERAN, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedMap = new ArrayList<>(frequencyMap.entrySet());
        sortedMap.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
        return sortedMap
          .stream()
          .map(sm -> sm.getKey() + "-" + sm.getValue())
          .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Printer.printList(printGroupsByFrequency(Arrays.asList(400, 300, 8888, 2344, 3500, 3600, 10000, 100, 10)));
    }
}
