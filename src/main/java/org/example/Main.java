package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static String letters = "eeeeddoonnnsssrv";
    private static int length = 4;
    private static boolean shuffle = false;
    private static int attempts = 1;

    public static void main(String[] args) throws Exception {
        System.out.println("Start Solving Word Square...");
        letters = String.valueOf(args[0]);
        length = Integer.valueOf(args[1]);
        shuffle = Boolean.valueOf(args[2]);
        detect(letters, length);
    }

    public static ArrayList<String> detect(String strSequence, Integer length) throws Exception {
        List<String> words = new ArrayList<>();
        String strSortedOriginal = "";
        try {
            // Read words from file
            File myObj = new File("src/main/resources/enable1.txt");
            Scanner myReader = new Scanner(myObj);

            //Get sorted sequence of letters to use
            String strSorted = getLetters(strSequence);
            strSortedOriginal = strSorted;
            //System.out.println("Letters to use: " + strSorted);
            int charPos = 0;
            while (myReader.hasNextLine()) {
                boolean found = false;
                char x = strSorted.charAt(charPos);
                String data = myReader.nextLine();
                if (data.length() == length) {
                    char[] array = data.toCharArray();
                    for(char b : array) {
                        if(strSorted.contains(String.valueOf(b))) {
                            StringBuilder build = new StringBuilder(strSorted);
                            build.deleteCharAt(strSorted.indexOf(b));
                            strSorted = build.toString();
                        } else {
                            found = true;
                        }
                    }
                    if(!found) {
                        words.add(data);
                        found = false;
                    }
                    strSorted = strSortedOriginal;
                    charPos = 0;
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        List<String> tempStack = new ArrayList<>();
        int x = 0;
        int y = 0;
        String strSeq = strSortedOriginal;
        Map<String, String> result = new HashMap<>();
        result.put("result", "NotFound");
        result.put("letters", strSeq);
        if(shuffle) {
            Collections.shuffle(words);
            attempts++;
        }
        System.out.println(words);
        for(String word : words) {
            if(tempStack.size() == length) {
                break;
            }
            tempStack.clear();
            tempStack.add(word);
            String lets = removeCharsFromString(strSeq, word.toCharArray());
            result.put("letters", lets);
            int count = 0;
            while(count < (length - 1)) {
                if(tempStack.size() == length) {
                    break;
                }
                //System.out.println("Stats: " + result.get("letters") + " " + tempStack.size());
                result = processWords(tempStack, words, result.get("letters"), length);
                if(result.get("result").equals("NotFound")) {
                    strSeq = strSortedOriginal;
                    break;
                } else {
                    count++;
                }
            }
            if(tempStack.size() == length) {
                break;
            }
        }

        if(tempStack.size() != length) {
            shuffle = true;
            detect(letters, length);
        } else {
            System.out.println("-------------");
            for(String entry : tempStack) {
                System.out.println(entry);
            }
            System.out.println("-------------");
            System.out.println("Attempts: " + attempts);
        }
        return null;
    }

    public static Map<String, String> processWords(List<String> tempStack, List<String> words, String letters, int count) throws Exception {
        int tempCount = 0;
        int stackSize = tempStack.size();
        String orgLetters = letters;
        String c = "";
        Map<String, String> result = new HashMap<>();
        while(tempCount < stackSize) {
            try {
                c = c + String.valueOf(tempStack.get(tempCount).charAt(stackSize));
                tempCount++;
            } catch(Exception ex) {
                System.out.println(ex.toString());
            }

        }
        List<String> possibleWords = findWord(tempStack, words, c);
        if(possibleWords.isEmpty()) {
            tempStack.clear();
            result.put("result", "NotFound");
            result.put("letters", letters);
        } else {
            char[] charQueue = possibleWords.get(0).toCharArray();
            try {
                for(char b : charQueue) {
                    StringBuilder build = new StringBuilder(letters);
                    build.deleteCharAt(letters.indexOf(b));
                    letters = build.toString();
                }
                tempStack.add(possibleWords.get(0));
                result.put("result", "Found");
                result.put("letters", letters);
            } catch (Exception ex) {
                result.put("result", "NotFound");
                result.put("letters", orgLetters);
            }
        }
        return result;
    }
    public static List<String> findWord(List<String> tempStack, List<String> words, String searchStr) throws Exception {
        List<String> possibleWords = new ArrayList<>();
        for(String word : words) {
            if(word.startsWith(searchStr) && !tempStack.contains(word)) {
                possibleWords.add(word);
            }
        }
        return possibleWords;
    }

    public static String removeCharsFromString(String letters, char[] chars) {
        try {
            for(char b : chars) {
                StringBuilder build = new StringBuilder(letters);
                build.deleteCharAt(letters.indexOf(b));
                letters = build.toString();
            }
        } catch (Exception ex) {
            return "OutOfBounds";
        }
        return letters;

    }

    public static String getLetters(String letters) throws FileNotFoundException {
        char[] chars = letters.toCharArray();
        Arrays.sort(letters.toCharArray());
        return new String(chars);
    }
}

