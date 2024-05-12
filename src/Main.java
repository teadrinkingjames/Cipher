//#########################################################################################################################################
// Name: James de Groot                                                                                                                   #
// File: Main.java                                                                                                                        #
// Description:                                                                                                                           #
// 2024/05/06 - Created the file                                                                                                          # 
// 2024/04/06 - Started the code                                                                                                          #
//#########################################################################################################################################

import java.util.Scanner;

public class Main {
    private static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    private static final int ASCII_MINIMUM_UPPERCASE = 65;
    private static final int ASCII_MAXIMUM_UPPERCASE = 91;
    private static final int ASCII_MINIMUM_LOWERCASE = 97;
    private static final int ASCII_MAXIMUM_LOWERCASE = 123;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        testCases(); // Test cases of all methods

        String encodeOrDecode = "";
        while (true) {
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
        while (true) {
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
        while (true) {
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
                    System.out.println("The decoded text is: " + ArrayShiftCaesarCipher(encodeOrDecode, text, codeword, true));
                    break;
                
                }
            } else { // encodes and prints the encoded text using all three methods
                System.out.println("Encoded text using regular caesar cipher: " +     CaesarCipher(encodeOrDecode, text, codeword, "length", true));
                System.out.println("Encoded text using mulitShift caesar cipher: " +  MultiShiftCaesarCipher(encodeOrDecode, text, codeword, true));
                System.out.println("Encoded text using array shift caesar cipher: " + ArrayShiftCaesarCipher(encodeOrDecode, text, codeword, true));
                break;
            }
            
        }
    }

    public static String CaesarCipher(String encodeOrDecode, String textToAlter, String codeword, String lengthOrValue, boolean STOPPRINTS) {
        int shift = 0;
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
            if (!STOPPRINTS) {
                System.out.println("Shift value: " + shift);
            }
        }
        if (encodeOrDecode.equalsIgnoreCase("decode")) {
            shift *= -1;
        }
        String alteredText = ""; 
        for (int i = 0; i < textToAlter.length(); i++) {
            char character = textToAlter.charAt(i);
            if (Character.isLetter(character)) {
                if (Character.isLowerCase(character)) {
                    final int shiftedCharacter = (character-ASCII_MINIMUM_LOWERCASE+shift+NUMBER_OF_LETTERS_IN_ALPHABET)%NUMBER_OF_LETTERS_IN_ALPHABET+ASCII_MINIMUM_LOWERCASE;
                    if (!STOPPRINTS) {
                        System.out.println(shiftedCharacter);
                    }
                    if (shiftedCharacter > ASCII_MINIMUM_LOWERCASE - 1 && shiftedCharacter < ASCII_MAXIMUM_LOWERCASE) {
                        alteredText += (char) (shiftedCharacter);
                    }
                    for (int j = shiftedCharacter; j > ASCII_MAXIMUM_LOWERCASE; j =- NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (j < ASCII_MINIMUM_LOWERCASE - 1 && j > ASCII_MAXIMUM_LOWERCASE) {
                            alteredText += (char) j;
                            break;
                        }
                    }
                    for (int j = shiftedCharacter; j < ASCII_MINIMUM_LOWERCASE-1; j=+NUMBER_OF_LETTERS_IN_ALPHABET+1) {
                        if (j < ASCII_MINIMUM_LOWERCASE - 1 && j > ASCII_MAXIMUM_LOWERCASE) {
                            alteredText += (char) j;
                            break;
                        }
                    }
                } else if (Character.isUpperCase(character)) {
                    final int shiftedCharacter = (character - ASCII_MINIMUM_UPPERCASE + shift + NUMBER_OF_LETTERS_IN_ALPHABET) % NUMBER_OF_LETTERS_IN_ALPHABET + ASCII_MINIMUM_UPPERCASE;
                    if (!STOPPRINTS) {
                        System.out.println(shiftedCharacter);
                    }
                    if (shiftedCharacter > ASCII_MINIMUM_UPPERCASE - 1 && shiftedCharacter < ASCII_MAXIMUM_UPPERCASE) {
                        alteredText += (char) shiftedCharacter;
                    }
                    for (int j = shiftedCharacter; j > ASCII_MAXIMUM_UPPERCASE; j =- NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (j < ASCII_MINIMUM_UPPERCASE - 1 && j > ASCII_MAXIMUM_UPPERCASE) {
                            alteredText += (char) j;
                            break;
                        }
                    }
                    for (int j = shiftedCharacter; j < ASCII_MINIMUM_UPPERCASE - 1; j =+ NUMBER_OF_LETTERS_IN_ALPHABET + 1) {
                        if (j < ASCII_MINIMUM_UPPERCASE - 1 && j > ASCII_MAXIMUM_UPPERCASE) {
                            alteredText += (char) j;
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
        for (int i = 0; i < cipheredText.length(); i++) {
            cipheredTextArray[i] = String.valueOf(cipheredText.charAt(i));
        }
        // creates an array of characters of the codeword
        for (int i = 0; i < codeChars.length; i++) {
            codeChars[i] = codeword.charAt(i);
        }
        // TEST prints of the values of the code word
        if (!STOPPRINTS) {  
            for (int i = 0; i < codeChars.length; i++) {
                if (encodeOrDecode.equalsIgnoreCase("decode")) {
                    System.out.println(codeChars[i] +"\t"+ (ASCII_MINIMUM_LOWERCASE - codeword.charAt(i)));
                } else {
                    System.out.println(codeChars[i] +"\t"+ (codeword.charAt(i) - ASCII_MINIMUM_LOWERCASE));
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
            for (int i = 0; i < codeChars.length; i++) {
                try {
                    if (!STOPPRINTS) {
                        System.out.println(CaesarCipher(encodeOrDecode, cipheredTextArray[workingCharacter], String.valueOf(codeChars[i]), "value", STOPPRINTS));
                    }
                    if (!cipheredTextArray[workingCharacter].contains("*")) {
                        alteredText += CaesarCipher(encodeOrDecode, cipheredTextArray[workingCharacter], String.valueOf(codeChars[i]), "value", STOPPRINTS);
                        workingCharacter++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignorException) {} // ignores the exception if the workingCharacter is out of bounds
            }
        }
        return alteredText;
    }

    public static String ArrayShiftCaesarCipher(String encodeOrDecode, String textToAlter, String codeword, boolean STOPPRINTS) {
        int paddingCharacters = 0;
        if (textToAlter.length()%codeword.length() != 0) {
            paddingCharacters = codeword.length() - (textToAlter.length()%codeword.length());
            for (int i = 0; i < paddingCharacters; i++) {
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
            for (int j = 0; j < cipherStrings.length; j++) {
                cipherStrings[j] = "";
                for (int i = j; i < LENGTH_OF_WORD; i += LENGTH_OF_CODEWORD) {
                    cipherStrings[j] += String.valueOf(textToAlter.charAt(i));
                } 
            }
            // test prints for the strings in the cipherStrings array ex. word:"jamjamjam" + code:"jam" -> "jjj" + "aaa" + "mmm"
            if (!STOPPRINTS) {
                for (int i = 0; i < cipherStrings.length; i++) {
                    System.out.println(cipherStrings[i]);
                }
            }
            // ciphering the strings in the cipherStrings array using the MultiShiftCaesarCipher method
            String[] cipheredStrings = new String[LENGTH_OF_CODEWORD];
            for (int i = 0; i < cipherStrings.length; i++) {
                cipheredStrings[i] = MultiShiftCaesarCipher(encodeOrDecode, cipherStrings[i], codeword, STOPPRINTS);
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
            if (paddingCharacters == 0) {
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
                        if (letterIndex == LENGTH_OF_SPLITS-1 && SpiltIndex + paddingCharacters > splitStrings.length-1) {
                            splitStrings[SpiltIndex] += "*";
                        } else {
                            splitStrings[SpiltIndex] += textToAlter.charAt(workingCharacter);
                            workingCharacter++;
                        }
                    }
                }
            }
            if (!STOPPRINTS) {  // TEST prints of the spilts before decoding
                for (int i = 0; i < splitStrings.length; i++) {
                    System.out.println(splitStrings[i]);
                } 
            }
            // use the decode function of the MultiShiftCaesarCipher method to decode the strings in the splitStrings array
            for (int i = 0; i < splitStrings.length; i++) {
                splitStrings[i] = MultiShiftCaesarCipher(encodeOrDecode, splitStrings[i], codeword, STOPPRINTS);
            }
            if (!STOPPRINTS) {  // TEST print of spilts after decoding
                System.out.println("++++++++++++++++++++++++++++++");
                for (int i = 0; i < splitStrings.length; i++) {
                    System.out.println(splitStrings[i].length() + "\t" + splitStrings[i]);
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
        System.out.println("WORD: 'SYZYGYsyzygy',               CODE: 'GGGggg',     ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "SYZYGYsyzygy",                "GGGggg",     true) + "\t\t\t " +"Decoded: " + ArrayShiftCaesarCipher("decode", "YyEeFfEeMmEe",               "GGGggg", true));
        System.out.println("WORD: 'Banana splits are delicious!'CODE: 'abc',        ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Banana splits are delicious!","abc",        true) +  "\t "    +"Decoded: " + ArrayShiftCaesarCipher("decode", "Bb lttdjq!aoui gedwnbrtb lju","abc",   true));
        System.out.println("WORD: 'Alphabet',                   CODE: 'ZwXy',       ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Alphabet",                    "ZwXy",       true) +  "\t\t\t "+"Decoded: " + ArrayShiftCaesarCipher("decode", "Zwkxoagp",                   "ZwXy",   true));
        System.out.println("WORD: 'Glebe Collegiate Institute', CODE: 'Ottawa',     ||\tEncoded: "+ ArrayShiftCaesarCipher("encode", "Glebe Collegiate Institute",  "Ottawa",     true) + "\t " +    "Decoded: " + ArrayShiftCaesarCipher("decode", "UVbnpzhtsasemtpexisx t zBu", "Ottawa", true));
    }
}