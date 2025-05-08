package Java;

import java.util.*;

public class StringUtilities {
    public static void main(String[] args) {
        // 1. Pelindrome check
        System.out.println("1. is 'Madam' a pelindrome? " + isPalindrome("Madam"));

        // 2. Count vowels
        System.out.println("2. Vowels in 'Education': " + countVowels("Education"));

        // 3. Are anagrams using toCharArray
        System.out.println("3. Are 'Listen' and 'Silent' anagrams? " + areAnagrams("Listen", "Silent"));

        // 4. Count character using HashMap
        System.out.println("4. Character count 'Banana': "+ countCharacters("Banana"));

        // 5. Remove duplicate characters
        System.out.println("5. remove duplicates from 'Programming': " + removeDuplicates("Programming"));

        //6. Most frequent character
        System.out.println("6. Most frequent character in 'Success': " + mostFrequentChar("Success"));

        //7. Are anagrams without using toCharArray
        System.out.println("7. Are 'Triangle' and 'Integral' anagrams? " + areAnagramsManual("Triangle", "Integral"));

        //8. Format Names
        System.out.println("8. Formatted Name:");
        formatStudentNames();
    }

    //1. Check if String is Palindrome
    public static boolean isPalindrome(String str) {
        String cleaned = str.replaceAll("[^a-zA-Z]","").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    //2. Count number of vowels
    public static int countVowels(String str) {
        int count = 0 ;
        String lower = str.toLowerCase();
        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if ("aiueo".indexOf(c) != -1) {
                    count++;
            }
        }
        return count;
    }

    //3. Anagram check using toCharArray
    public static boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    //4. Count character frequency using HashMap
    public static Map<Character, Integer> countCharacters(String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
    
    //5. Remove duplicate characters from string
    public static String removeDuplicates(String str) {
        StringBuilder sb = new StringBuilder();
        Set<Character> seen = new LinkedHashSet<>();
        for (char c : str.toCharArray()) {
            if (!seen.contains(c)) {
                seen.add(c);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //6. Find most frequent character
    public static char mostFrequentChar(String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        char maxChar = ' ';
        int max = 0;
        for (char c : map.keySet()) {
            if (map.get(c) > max) {
                max = map.get(c);
                maxChar = c;
            }
        }
        return maxChar;
    }

    //7. Anagram check whitout toCharArray
    public static boolean areAnagramsManual(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            map1.put(c1, map1.getOrDefault(c1, 0)+ 1);
            map2.put(c2, map2.getOrDefault(c2, 0)+ 1);
        }
        return map1.equals(map2);
    }

    //8. Format students names: lastName, firstName
    public static void formatStudentNames() {
        String[] students = {
            "Mohamed Ahmed",
            "Mohamed Ali",
            "Mohamed Salah",
            "Mohamed Hassan",
            "Mohamed Ibrahim",
            "Mohamed Noor",
            "Mohamed Farah",
            "Mohamed Yusuf",
            "Mohamed Ismail",
            "Mohamed Kamal"
        };

        for (String fullName : students) {
            String[] parts = fullName.split(" ");
            if (parts.length == 2) {
                String formatted = parts[1] + ", " + parts[0];
                System.out.println(formatted);
            } else {
                System.out.print("Unknown name format :" + fullName);
            }
        }

    }

}
