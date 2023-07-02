package org.example;

import java.util.*;

/**
 * WordSquare generator.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Start Solving Word Square...");
        Generate mainClass = new Generate();
        List<String> result =
                mainClass.generate(String.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Boolean.valueOf(args[3]));
    }
}

