package org.example.util;

import java.util.Random;

public class AccountUtil {
    public static String generateRandomAccountNumber(int length){
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // Generate each digit randomly and append to the code
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a digit between 0-9
            code.append(digit);
        }

        return code.toString();
    }

}
