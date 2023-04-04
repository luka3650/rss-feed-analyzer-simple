package com.luka.hottopics;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class InputHandler {
    public static final String SPLIT_REGEX = "\\s+";
    public static final String INPUT_EXCEPTION_MESSAGE = "At least two RSS URL's should be given!";
    public static final Integer URL_MIN_NUM = 2;


    // Get input from terminal and store it
    public String[] processInput() throws InputException {

        //System.out.print("Enter RSS URL's separated with spaces: ");


        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        PrintStream ps = new PrintStream(fos);
        ps.println("Enter RSS URL's separated with spaces: ");
        ps.close();
        
        // Read array of URL strings input
        Scanner scanner = new Scanner(System.in);
        String[] urlArray = scanner.nextLine().split(SPLIT_REGEX);

        // Check the number of inputs
        int size = urlArray.length;
        if (size < URL_MIN_NUM) {
            throw new InputException(INPUT_EXCEPTION_MESSAGE);
        }

        return urlArray;
    }


}