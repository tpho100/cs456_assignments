import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanh-Phong on 11/23/2015.
 */
public class VigenereCipher
{
    /*
        VigenereCipher only works on capital letters and
        messages without spaces.
     */
    public int[] generateVector(String keyWord){

        /*
            Generates vector of integers 0-25 given a keyword
         */

        char[] keyArr = keyWord.toCharArray();
        int[] keyArrInt = new int[keyArr.length];

        for(int i = 0; i < keyArr.length; i++){
            keyArrInt[i] = ((int) keyArr[i]) - 65;
        }
        return keyArrInt;
    }

    public void encryptFile(String infilename, String outfilename, String keyWord){
        List<String> text = new ArrayList<>(); //contains file info
        readSafely(text, infilename);

        List<String> cipherText = new ArrayList<>();

        for(String s : text){
            cipherText.add(encryptText(s,keyWord));
        }

        writeSafely(cipherText,outfilename);

    }

    public void decryptFile(String infilename, String outfilename, String keyWord){
        List<String> text = new ArrayList<>(); //contains file info
        readSafely(text, infilename);

        List<String> cipherText = new ArrayList<>();

        for(String s : text){
            cipherText.add(decryptText(s,keyWord));
        }

        writeSafely(cipherText,outfilename);

    }

    public void readSafely(List<String> text, String infilename){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(infilename));
            String inLine;
            while(!((inLine = reader.readLine()) == null)){
                inLine = inLine.replaceAll("\\s",""); //Knock off all spaces
                inLine = inLine.toUpperCase(); //Capitalize everything
                text.add(inLine);
            }
            reader.close();

        }
        catch(Exception e)
        {
            System.err.println("File not found.");
        }
    }

    public void writeSafely(List<String> cipherText, String outfilename){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outfilename));
            for (String s : cipherText) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e){
            System.err.println("File does not exist.");
        }

    }

    public String encryptText(String text, String keyWord){

        /*
            Encrypts text using VigenereCipher based on the given keyword
         */

        char[] encryptedText = text.toCharArray();
        int[] key = generateVector(keyWord.toUpperCase());

        for(int i = 1; i <= encryptedText.length; i++){
            int j = i % key.length;
            if(j == 0) {
                j = key.length;
            }
            encryptedText[i-1] = shift( encryptedText[i-1],key[j-1] );
        }

        return String.valueOf(encryptedText);
    }

    public String decryptText(String text, String unknownKeyWord)
    {
        /*
            Decrypts text using VigenereCipher based on the given keyword.
            Uses same algorithm as encryption but instead of shifting forwards,
            it shifts backwards.
         */

        int[] unknownKey = generateVector(unknownKeyWord);
        char[] encryptedText = text.toCharArray();
        for(int i = 1; i <= encryptedText.length; i++){
            int j = i % unknownKey.length;
            if(j == 0) {
                j = unknownKey.length;
            }
            encryptedText[i-1] = shiftBack( encryptedText[i-1],unknownKey[j-1] );
        }

        return String.valueOf(encryptedText);
    }

    public char shift(char letter, int shiftCount){
        return (char) ( ((int) letter - 65 + shiftCount) % 26 + 65 );
    }

    public char shiftBack(char letter, int shiftCount){
        int c = ((int) letter) - shiftCount;
        if(c < 65){
            c += 26;
        }
        return (char) c;
    }


}
