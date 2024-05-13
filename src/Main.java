//#########################################################################################################################################
// Name: James de Groot                                                                                                                   #
// File: Main.java                                                                                                                        #
// Description:                                                                                                                           #
// 2024/05/06 - Created the file                                                                                                          # 
// 2024/05/06 - Started the code                                                                                                          #
// 2024/05/07 - Finished method 1 code                                                                                                    #
// 2024/05/09 - Finished method 2 code                                                                                                    #
// 2024/05/12 - finished method 3 code                                                                                                    #
// 2024/05/13 - cleaned up code, finished comments                                                                                        #
//#########################################################################################################################################

import java.util.Scanner;

public class Main {
    private static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    private static final int ASCII_MINIMUM_UPPERCASE = 65;
    private static final int ASCII_MAXIMUM_UPPERCASE = 91;
    private static final int ASCII_MINIMUM_LOWERCASE = 97;
    private static final int ASCII_MAXIMUM_LOWERCASE = 123;
    private static final boolean TESTPRINTS = true; // if false, the test cases will print the values of the code words and the shifted characters

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        
        testCases(); // Test cases of all methods

        String encodeOrDecode = "";
        while (true) { // asks the user if they want to encode or decode
            System.out.print("Do you want to encode or decode? ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("encode") || userInput.equalsIgnoreCase("decode")) {
                encodeOrDecode = userInput;
                break;
            }
        }
        String text = "";
        System.out.print("Enter the text you want to encode/decode: ");
        String userInput = scanner.nextLine();
        text = userInput;
    
        String codeword = "";
        while (true) { // gets the codeword from the user
            System.out.print("Enter the codeword: ");
            userInput = scanner.nextLine(); 
            if (userInput.length() > NUMBER_OF_LETTERS_IN_ALPHABET-1) {
                System.out.println("The codeword is too long, please enter a codeword shorter than 26 characters");
                continue;
            }
            if (userInput.matches("[a-z,A-Z]*")) {
                codeword = userInput.toLowerCase();
                break;
            }
        }
        while (true) { // asks the user which method they want to use
            if (encodeOrDecode.equalsIgnoreCase("decode")) {
                System.out.println("which kind of cipher do you want to use?" + "\n" + "1. Regular Caesar Cipher" + "\n" + "2. Caesar Cipher with codeword" + "\n" + "3. Caesar Cipher with array codeword" + "\n");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    System.out.println("The decoded text is: " + CaesarCipher(encodeOrDecode, text, codeword, "length", true));
                    break;
                } else if (userInput.equals("2")) {
                    System.out.println("The decoded text is: " + MultiShiftCaesarCipher(encodeOrDecode, text, codeword, true));
                    break;
                } else if (userInput.equals("3")) {
                    System.out.println("The decoded text is: " + ArrayShiftCaesarCipher(encodeOrDecode, text, codeword, TESTPRINTS));
                    break;
                
                }
            } else { // encodes and prints the encoded text using all three methods
                System.out.println("Encoded text using regular caesar cipher: " +     CaesarCipher(encodeOrDecode, text, codeword, "length", true));
                System.out.println("Encoded text using mulitShift caesar cipher: " +  MultiShiftCaesarCipher(encodeOrDecode, text, codeword, true));
                System.out.println("Encoded text using array shift caesar cipher: " + ArrayShiftCaesarCipher(encodeOrDecode, text, codeword, TESTPRINTS));
                break;
            }
            
        }
    }
    // This method encodes or decodes a text using a regular Caesar cipher
    public static String CaesarCipher(String encodeOrDecode, String textToAlter, String codeword, String lengthOrValue, boolean STOPPRINTS) {
        int shift = 0;
        // either gets the shift value from the length of the codeword or the value of the first letter of the codeword
        if (lengthOrValue == "length") {
            shift = codeword.length();
            if (!STOPPRINTS) {
                System.out.println("Shift value: " + shift);
            }
        } else if (lengthOrValue == "value") {
            shift = (int) codeword.charAt(0);
            if (Character.isLetter(codeword.charAt(0))) {
                if (Character.isUpperCase(codeword.charAt(0))) {
                    shift -= ASCII_MINIMUM_UPPERCASE;
                } else if (Character.isLowerCase(codeword.charAt(0))) {
                    shift -= ASCII_MINIMUM_LOWERCASE;
                }
            }
            if (!STOPPRINTS) { // TEST print of the shift value
                System.out.println("Shift value: " + shift);
            }
        }
        // if the user wants to decode the text, the shift value is reversed
        if (encodeOrDecode.equalsIgnoreCase("decode")) {
            shift *= -1;
        }
        String alteredText = ""; 
        for (int textToAlterIndex = 0; textToAlterIndex < textToAlter.length(); textToAlterIndex++) {
            char character = textToAlter.charAt(textToAlterIndex);
            if (Character.isLetter(character)) { // if the character is a letter, it is shifted by the shift value, else its added to the alteredText
                if (Character.isLowerCase(character)) { 
                    final int SHIFTED_CHARACTER = (character-ASCII_MINIMUM_LOWERCASE+shift+NUMBER_OF_LETTERS_IN_ALPHABET)%NUMBER_OF_LETTERS_IN_ALPHABET+ASCII_MINIMUM_LOWERCASE;
                    if (!STOPPRINTS) { // TEST print of the shifted character
                        System.out.println(SHIFTED_CHARACTER);
                    }
                    if (SHIFTED_CHARACTER > ASCII_MINIMUM_LOWERCASE - 1 && SHIFTED_CHARACTER < ASCII_MAXIMUM_LOWERCASE) {
                        alteredText += (char) (SHIFTED_CHARACTER); // adds the shifted character if it is a letter within the ASCII range
                    }
                    // if it is above the ASCII range, it subtracts the NUMBER_OF_LETTERS_IN_ALPHABET to get the correct letter (cycles around the alphabet)
                    for (int alteredShiftCharacter = SHIFTED_CHARACTER; alteredShiftCharacter > ASCII_MAXIMUM_LOWERCASE; alteredShiftCharacter =- NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (alteredShiftCharacter < ASCII_MINIMUM_LOWERCASE - 1 && alteredShiftCharacter > ASCII_MAXIMUM_LOWERCASE) {
                            alteredText += (char) alteredShiftCharacter; 
                            break;
                        }
                    }
                    // if it is below the ASCII range, it adds the NUMBER_OF_LETTERS_IN_ALPHABET to get the correct letter (cycles around the alphabet)
                    for (int alteredShiftCharacter = SHIFTED_CHARACTER; alteredShiftCharacter < ASCII_MINIMUM_LOWERCASE - 1; alteredShiftCharacter =+ NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (alteredShiftCharacter < ASCII_MINIMUM_LOWERCASE - 1 && alteredShiftCharacter > ASCII_MAXIMUM_LOWERCASE) {
                            alteredText += (char) alteredShiftCharacter; 
                            break;
                        }
                    }
                } else if (Character.isUpperCase(character)) {
                    final int SHIFTED_CHARACTER = (character - ASCII_MINIMUM_UPPERCASE + shift + NUMBER_OF_LETTERS_IN_ALPHABET) % NUMBER_OF_LETTERS_IN_ALPHABET + ASCII_MINIMUM_UPPERCASE;
                    if (!STOPPRINTS) { // TEST print of the shifted character
                        System.out.println(SHIFTED_CHARACTER);
                    }
                    if (SHIFTED_CHARACTER > ASCII_MINIMUM_UPPERCASE - 1 && SHIFTED_CHARACTER < ASCII_MAXIMUM_UPPERCASE) {
                        alteredText += (char) SHIFTED_CHARACTER;
                    }
                    // if it is above the ASCII range, it subtracts the NUMBER_OF_LETTERS_IN_ALPHABET to get the correct letter (cycles around the alphabet)
                    for (int alteredShiftCharacter = SHIFTED_CHARACTER; alteredShiftCharacter > ASCII_MAXIMUM_UPPERCASE; alteredShiftCharacter =- NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (alteredShiftCharacter < ASCII_MINIMUM_UPPERCASE - 1 && alteredShiftCharacter > ASCII_MAXIMUM_UPPERCASE) {
                            alteredText += (char) alteredShiftCharacter; 
                            break;
                        }
                    }
                    // if it is below the ASCII range, it adds the NUMBER_OF_LETTERS_IN_ALPHABET to get the correct letter (cycles around the alphabet)
                    for (int alteredShiftCharacter = SHIFTED_CHARACTER; alteredShiftCharacter < ASCII_MINIMUM_UPPERCASE - 1; alteredShiftCharacter =+ NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (alteredShiftCharacter < ASCII_MINIMUM_UPPERCASE - 1 && alteredShiftCharacter > ASCII_MAXIMUM_UPPERCASE) {
                            alteredText += (char) alteredShiftCharacter; 
                            break;
                        }
                    }
                }
            } else {
                alteredText += character;
            }
        }
        return alteredText;
    }

    // This is the same as the above method, but the shift value is the codeword's seperate letter's values repeated
    public static String MultiShiftCaesarCipher(String encodeOrDecode, String cipheredText, String codeword, boolean STOPPRINTS) {
        String alteredText = "";
        char[] codeChars = new char[codeword.length()];
        String[] cipheredTextArray = new String[cipheredText.length()];

        // creates an array of strings of each letter of the textToAlter
        for (int cipheredTextIndex = 0; cipheredTextIndex < cipheredText.length(); cipheredTextIndex++) {
            cipheredTextArray[cipheredTextIndex] = String.valueOf(cipheredText.charAt(cipheredTextIndex));
        }
        // creates an array of characters of the codeword
        for (int codewordIndex = 0; codewordIndex < codeChars.length; codewordIndex++) {
            codeChars[codewordIndex] = codeword.charAt(codewordIndex);
        }
        // TEST prints of the values of the code word
        if (!STOPPRINTS) {  
            for (int codewordIndex = 0; codewordIndex < codeChars.length; codewordIndex++) {
                if (encodeOrDecode.equalsIgnoreCase("decode")) {
                    System.out.println(codeChars[codewordIndex] +"\t"+ (ASCII_MINIMUM_LOWERCASE - codeword.charAt(codewordIndex)));
                } else {
                    System.out.println(codeChars[codewordIndex] +"\t"+ (codeword.charAt(codewordIndex) - ASCII_MINIMUM_LOWERCASE));
                }
            } 
        }
        int numberOfIterations = 0;
        // creates the altered string using the orginial CaesarCipher method
        if (cipheredText.length()%codeword.length() == 0) {
            numberOfIterations = (cipheredText.length()/codeword.length());
        } else {
            numberOfIterations = (cipheredText.length()/codeword.length())+1;
        }
        int workingCharacter = 0;
        for (int iterationsComplete = 0; iterationsComplete < numberOfIterations; iterationsComplete++) {
            for (int codewordIndex = 0; codewordIndex < codeChars.length; codewordIndex++) {
                try {
                    if (!STOPPRINTS) {
                        System.out.println(CaesarCipher(encodeOrDecode, cipheredTextArray[workingCharacter], String.valueOf(codeChars[codewordIndex]), "value", STOPPRINTS));
                    }
                    if (!cipheredTextArray[workingCharacter].contains("*")) {
                        alteredText += CaesarCipher(encodeOrDecode, cipheredTextArray[workingCharacter], String.valueOf(codeChars[codewordIndex]), "value", STOPPRINTS);
                        workingCharacter++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignorException) {} // ignores the exception if the workingCharacter is out of bounds
            }
        }
        return alteredText;
    }
    // This method is the same as the above method, but the textToAlter is split into the same amount of strings as the codeword which are then encoded/decoded seperately using the MultiShiftCaesarCipher method
    public static String ArrayShiftCaesarCipher(String encodeOrDecode, String textToAlter, String codeword, boolean STOPPRINTS) {
        int neededPaddingCharacter = 0;
        if (textToAlter.length()%codeword.length() != 0) {
            neededPaddingCharacter = codeword.length() - (textToAlter.length()%codeword.length());
            for (int numberOfPaddedCharactersAdded = 0; numberOfPaddedCharactersAdded < neededPaddingCharacter; numberOfPaddedCharactersAdded++) {
                textToAlter += "*";
            }
        }
        if (!STOPPRINTS) {
            System.out.println(textToAlter + "|end");
            System.out.println("++++++++++++++++++++++++++++++");
        }
        // following vars MUST be below the padding code above to insure the proper length of the textToAlter is used
        final int LENGTH_OF_CODEWORD = codeword.length();
        final int LENGTH_OF_WORD = textToAlter.length();
        final int LENGTH_OF_SPLITS = LENGTH_OF_WORD/LENGTH_OF_CODEWORD;
        String[] cipherStrings = new String[LENGTH_OF_CODEWORD];

        if (encodeOrDecode.equalsIgnoreCase("encode")) {
            // constructing the cipherStrings array using the textToAlter characters
            for (int cipherStringIndex = 0; cipherStringIndex < cipherStrings.length; cipherStringIndex++) {
                cipherStrings[cipherStringIndex] = "";
                for (int textToAlterIndex = cipherStringIndex; textToAlterIndex < LENGTH_OF_WORD; textToAlterIndex += LENGTH_OF_CODEWORD) {
                    cipherStrings[cipherStringIndex] += String.valueOf(textToAlter.charAt(textToAlterIndex));
                } 
            }
            // test prints for the strings in the cipherStrings array ex. word:"jamjamjam" + code:"jam" -> "jjj" + "aaa" + "mmm"
            if (!STOPPRINTS) {
                for (int cipherStringIndex = 0; cipherStringIndex < cipherStrings.length; cipherStringIndex++) {
                    System.out.println(cipherStrings[cipherStringIndex]);
                }
            }
            // ciphering the strings in the cipherStrings array using the MultiShiftCaesarCipher method
            String[] cipheredStrings = new String[LENGTH_OF_CODEWORD];
            for (int cipherStringIndex = 0; cipherStringIndex < cipherStrings.length; cipherStringIndex++) {
                cipheredStrings[cipherStringIndex] = MultiShiftCaesarCipher(encodeOrDecode, cipherStrings[cipherStringIndex], codeword, STOPPRINTS);
            }
            // rebuilding a string using the cipheredStrings array
            String cipheredText = ""; 
            for (String cipheredString : cipheredStrings) {
                cipheredText += cipheredString;
            }
            return cipheredText; 
        } else { // +++++++++++++++++ decoding cipher code +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // spilts the textToAlter into the same amount of strings as the codeword
            String[] splitStrings = new String[LENGTH_OF_CODEWORD];
            int workingCharacter = 0;
            if (neededPaddingCharacter == 0) {
                for (int SpiltIndex = 0; SpiltIndex < splitStrings.length; SpiltIndex++) {
                    splitStrings[SpiltIndex] = "";
                    for (int letterIndex = 0; letterIndex < LENGTH_OF_SPLITS; letterIndex++) {
                        workingCharacter++;
                        splitStrings[SpiltIndex] += textToAlter.charAt(workingCharacter-1);
                    }
                }
            } else { // if there are padding characters, the padding characters are added to the end of the strings in the splitStrings array
                for (int SpiltIndex = 0; SpiltIndex < splitStrings.length; SpiltIndex++) {
                    splitStrings[SpiltIndex] = "";
                    for (int letterIndex = 0; letterIndex < LENGTH_OF_SPLITS; letterIndex++) {
                        if (letterIndex == LENGTH_OF_SPLITS-1 && SpiltIndex + neededPaddingCharacter > splitStrings.length-1) {
                            splitStrings[SpiltIndex] += "*";
                        } else {
                            splitStrings[SpiltIndex] += textToAlter.charAt(workingCharacter);
                            workingCharacter++;
                        }
                    }
                }
            }
            if (!STOPPRINTS) {  // TEST prints of the spilts before decoding
                for (int splitStringsIndex = 0; splitStringsIndex < splitStrings.length; splitStringsIndex++) {
                    System.out.println(splitStrings[splitStringsIndex]);
                } 
            }
            // use the decode function of the MultiShiftCaesarCipher method to decode the strings in the splitStrings array
            for (int splitStringsIndex = 0; splitStringsIndex < splitStrings.length; splitStringsIndex++) {
                splitStrings[splitStringsIndex] = MultiShiftCaesarCipher(encodeOrDecode, splitStrings[splitStringsIndex], codeword, STOPPRINTS);
            }
            if (!STOPPRINTS) {  // TEST print of spilts after decoding
                System.out.println("++++++++++++++++++++++++++++++");
                for (int splitStringIndex = 0; splitStringIndex < splitStrings.length; splitStringIndex++) {
                    System.out.println(splitStrings[splitStringIndex].length() + "\t" + splitStrings[splitStringIndex]);
                } 
            }
            // rebuild the uncipheredText using the splitStrings array
            String uncipheredText = "";
            for (int letterIndex = 0; letterIndex < splitStrings[0].length(); letterIndex++) {
                for (int stringIndex = 0; stringIndex < splitStrings.length; stringIndex++) {
                    try {
                        uncipheredText += splitStrings[stringIndex].charAt(letterIndex);
                    } catch (StringIndexOutOfBoundsException e) {}
                }
            }
            return uncipheredText; 
        }
    }
    // Test cases for all three methods
    public static void testCases() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++ TESTS for REGULAR caesar cipher ++++++++++++++++++++++++++++++++++++++++++++++++++" );
        System.out.println("WORD: 'james',                      CODE: 'james',      length ||\tEncoded: "   + CaesarCipher("encode", "james",                      "james",      "length", true) + "   \t\t\t "+"Decoded: " + CaesarCipher("decode", "ofrjx",                      "james",      "length", true));
        System.out.println("WORD: 'James de Groot',             CODE: 'CODE',       value  ||\tEncoded: "   + CaesarCipher("encode", "James de Groot",             "CODE",       "value",  true) + "  \t\t "+"Decoded: " +    CaesarCipher("decode", "Lcogu fg Itqqv",             "CODE",       "value",  true));
        System.out.println("WORD: 'hello',                      CODE: 'a',          value  ||\tEncoded: "   + CaesarCipher("encode", "hello",                      "a",          "value",  true) + "   \t\t\t "+"Decoded: " + CaesarCipher("decode", "hello",                      "a",          "value",  true));
        System.out.println("WORD: 'abcdefghijklmnopqrstuvwxyz', CODE: 'a',          length ||\tEncoded: "   + CaesarCipher("encode", "abcdefghijklmnopqrstuvwxyz", "a",          "length", true) + " \t "+"Decoded: " +       CaesarCipher("decode", "bcdefghijklmnopqrstuvwxyza", "a",          "length", true));
        System.out.println("WORD: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', CODE: 'alpha',      length ||\tEncoded: "   + CaesarCipher("encode", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "alpha",      "length", true) + " \t "+"Decoded: " +       CaesarCipher("decode", "FGHIJKLMNOPQRSTUVWXYZABCDE", "alpha",      "length", true));
        System.out.println("WORD: 'I am 16y/o',                 CODE: 'no I'm not', value  ||\tEncoded: "   + CaesarCipher("encode", "I am 16y/o",                 "no I'm not", "value",  true) + " \t\t\t "+"Decoded: " +   CaesarCipher("decode", "V nz 16l/b",                 "no I'm not", "value",  true) + "\n");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++ TESTS for multi-Shift caesar cipher ++++++++++++++++++++++++++++++++++++++++++++++++++" );
        System.out.println("WORD: 'james',                      CODE: 'james',      ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "james",                      "james",      true) + "  \t\t\t "+"Decoded: " + MultiShiftCaesarCipher("decode", "sayik",                      "james",      true));
        System.out.println("WORD: 'James de Groot',             CODE: 'CODE',       ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "James de Groot",             "CODE",       true) + " \t\t "   +"Decoded: " + MultiShiftCaesarCipher("decode", "Lopiu gi Uusqh",             "CODE",       true));
        System.out.println("WORD: 'hello',                      CODE: 'a',          ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "hello",                      "a",          true) + "  \t\t\t "+"Decoded: " + MultiShiftCaesarCipher("decode", "hello",                      "a",          true));
        System.out.println("WORD: 'abcdefghijklmnopqrstuvwxyz', CODE: 'amongus',    ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "abcdefghijklmnopqrstuvwxyz", "amongus",    true) + " \t "     +"Decoded: " + MultiShiftCaesarCipher("decode", "anqqkzyhuxxrgfobeeynmvillf", "amongus",    true));
        System.out.println("WORD: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', CODE: 'alpha',      ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "alpha",      true) + " \t "     +"Decoded: " + MultiShiftCaesarCipher("decode", "AMRKEFRWPJKWBUOPBGZTUGLEYZ", "alpha",      true));
        System.out.println("WORD: 'I am 16y/o',                 CODE: 'no I'm not', ||\tEncoded: "+ MultiShiftCaesarCipher("encode", "I am 16y/o",                 "no I'm not", true) + " \t\t\t " +"Decoded: " + MultiShiftCaesarCipher("decode", "V gu 16l/h",                 "no I'm not", true) + "\n");    
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++ TESTS for array shift caesar cipher ++++++++++++++++++++++++++++++++++++++++++++++++++" );
        System.out.println("WORD: 'SYZYGYsyzygy',               CODE: 'GGGggg',     ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "SYZYGYsyzygy",                "GGGggg",     TESTPRINTS) + "\t\t\t " +"Decoded: " + ArrayShiftCaesarCipher("decode", "YyEeFfEeMmEe",               "GGGggg", TESTPRINTS));
        System.out.println("WORD: 'Banana splits are delicious!'CODE: 'abc',        ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Banana splits are delicious!","abc",        TESTPRINTS) +  "\t "    +"Decoded: " + ArrayShiftCaesarCipher("decode", "Bb lttdjq!aoui gedwnbrtb lju","abc",   TESTPRINTS));
        System.out.println("WORD: 'Alphabet',                   CODE: 'ZwXy',       ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Alphabet",                    "ZwXy",       TESTPRINTS) +  "\t\t\t "+"Decoded: " + ArrayShiftCaesarCipher("decode", "Zwkxoagp",                   "ZwXy",   TESTPRINTS));
        System.out.println("WORD: 'Glebe Collegiate Institute', CODE: 'Ottawa',     ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Glebe Collegiate Institute",  "Ottawa",     TESTPRINTS) + "\t " +    "Decoded: " + ArrayShiftCaesarCipher("decode", "UVbnpzhtsasemtpexisx t zBu", "Ottawa", TESTPRINTS) + "\n");
    }
}