package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Generate {

    private String fileName = "src/main/resources/enable1.txt";
    private String letters = "eeeeddoonnnsssrv";
    private int length = 4;
    private boolean shuffle = true;
    private int attempts = 1;

    public List<String> generate(String letters, Integer length, Integer maxAttempts, Boolean shuffle) throws Exception {
        List<String> words = new ArrayList<>();
        this.letters = letters;
        this.length = length;

        String strSortedOriginal = "";
        try {
            // Read words from file
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);

            //Get sorted sequence of letters to use
            String strSorted = getLetters(letters);
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

        // Randomizes the order to allow a different possible traversal of the words.
        if(tempStack.size() != length && attempts <= maxAttempts) {
            shuffle = true;
            generate(letters, length, maxAttempts, shuffle);
        } else {
            System.out.println("-------------");
            for(String entry : tempStack) {
                System.out.println(entry);
            }
            System.out.println("-------------");
            System.out.println("Attempts: " + attempts);
        }
        return tempStack;
    }

    /**
     * Finds a word in an array of words.
     *
     * @param tempStack
     * @param words
     * @param letters
     * @param count
     * @return
     * @throws Exception
     */
    public Map<String, String> processWords(List<String> tempStack, List<String> words, String letters, int count) throws Exception {
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

    /**
     * Finds a word that starts with a prefix and ensures it isnt already in the wordsquare.
     * @param tempStack
     * @param words
     * @param searchStr
     * @return
     * @throws Exception
     */
    public List<String> findWord(List<String> tempStack, List<String> words, String searchStr) throws Exception {
        List<String> possibleWords = new ArrayList<>();
        for(String word : words) {
            if(word.startsWith(searchStr) && !tempStack.contains(word)) {
                possibleWords.add(word);
            }
        }
        return possibleWords;
    }

    /**
     * Removes used letters from anagram string.
     * @param letters
     * @param chars
     * @return
     */
    public String removeCharsFromString(String letters, char[] chars) {
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

    /**
     * Retrieves a sequence of letters in alphabetical order.
     * @param letters
     * @return
     * @throws FileNotFoundException
     */
    public String getLetters(String letters) throws FileNotFoundException {
        char[] chars = letters.toCharArray();
        Arrays.sort(letters.toCharArray());
        return new String(chars);
    }
}
