package programs;

import commons.Printer;

import javax.swing.text.Utilities;
import java.util.Arrays;
import java.util.List;

public class MaxProduct {

    private static int maxProduct(String[] words) {
        List<Integer> bitMasks = Arrays
          .stream(words)
          .map(word -> word.chars().reduce(0, (acc, curr) -> acc | (1 << curr - 'a')))
          .toList();

        int max = 0;
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((bitMasks.get(i) & bitMasks.get(j)) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }

        return max;
    }

    private static char findTheDifference(String s, String t) {
        int[] alphabets = new int[26];
        char result = 0;
        for (char c : s.toCharArray()) {
            alphabets[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            alphabets[c - 'a']--;
        }
        Printer.print1DArray(alphabets);
        for (int i = 0; i < alphabets.length; i++) {
            if (alphabets[i] == -1) {
                result = (char) (i + 97);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(maxProduct(new String[] { "abcw", "baz", "foo", "bar", "xtfn", "abcdef" }));
        System.out.println(findTheDifference("abcd", "abcde"));
    }
}
