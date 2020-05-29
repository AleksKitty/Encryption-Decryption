package encryptdecrypt;


import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String mode = "enc";
        int key = 0;
        String data = "";
        String inFile = "";
        String outFile = "";
        String alg = "shift";


        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode" : {
                    mode = args[i + 1];
                    break;
                }
                case "-key" : {
                    try {
                        key = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Plak-plak");
                    }
                    break;
                }
                case "-data" : {
                    data = args[i + 1];
                    break;
                }
                case "-in" : {
                    inFile = args[i + 1];
                    break;
                }
                case "-out" : {
                    outFile = args[i + 1];
                    break;
                }
                case "-alg" : {
                    if ("unicode".equals(args[i + 1])) {
                        alg = "unicode";
                    }
                }
            }

        }


        // take data from File
        if ("".equals(data)) {
            data = inFile(inFile);
        }

        // do algorithm
        String cypherText = "";
        if ("unicode".equals(alg)) {
            cypherText = unicode(data, key, mode);
        } else if ("enc".equals(mode)){
            cypherText = shiftRight(data, key);
        } else {
            cypherText = shiftLeft(data, key);
        }


        // output
        if ("".equals(outFile)) {
            System.out.println(cypherText);
        } else {
            outFile(outFile, cypherText);
        }
    }

    public static String inFile (String in) {
        File file = new File(in);
        String data = "";

        try (Scanner scanner = new Scanner(file)) {
            data = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + in);
        }

        return data;
    }

    public static void outFile (String outFile, String cypherText) {
        File file = new File(outFile);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(cypherText);
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }


    }

    public static String unicode (String data, int key, String mode){

        // select mode
        if ("dec".equals(mode)) {
            key *= -1;
        }

        StringBuilder cypherText = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {

            cypherText.append((char) (data.charAt(i) + key));
        }

        return String.valueOf(cypherText);
    }


    public static String shiftRight(String data, int key) {
        StringBuilder cypherText = new StringBuilder();
        int letter;

        for (int i = 0; i < data.length(); i++) {
            letter = data.charAt(i);

            if (letter >= 97 && letter <= 122) {
                if (letter + key <= 122) {
                    cypherText.append((char) (letter + key));
                } else {
                    cypherText.append((char) (96 + key - (122 - letter)));
                }
            } else if (letter >= 65 && letter <= 90) {
                if (letter + key <= 90) {
                    cypherText.append((char) (letter + key));
                } else {
                    cypherText.append((char) (64 + key - (90 - letter)));
                }
            } else {
                cypherText.append(data.charAt(i));
            }
        }

        return String.valueOf(cypherText);
    }


    public static String shiftLeft(String data, int key) {

        StringBuilder cypherText = new StringBuilder();
        int letter;

        for (int i = 0; i < data.length(); i++) {
            letter = data.charAt(i);

            if (letter >= 97 && letter <= 122) {
                if (letter - key >= 97) {
                    cypherText.append((char) (letter - key));
                } else {
                    cypherText.append((char) (123 - key + (letter - 97)));
                }
            } else if (letter >= 65 && letter <= 90) {
                if (letter - key >= 65) {
                    cypherText.append((char) (letter + key));
                } else {
                    cypherText.append((char) (91 - key + (letter - 65)));
                }
            } else {
                cypherText.append(data.charAt(i));
            }
        }

        return String.valueOf(cypherText);
    }
}
