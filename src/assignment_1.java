import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Thanh-Phong on 11/23/2015.
 */
public class assignment_1 {

    public static void main(String args[]){

        VigenereCipher encryptor = new VigenereCipher();
        String inputFileName;
        String outputFileName;
        String keyword;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter input file name: ");
        try {
            inputFileName = reader.readLine();
            System.out.println("Enter output file name: ");
            outputFileName = reader.readLine();
            System.out.println("Enter keyword: ");
            keyword = reader.readLine();
            System.out.println("Type enc to encrypt or dec to decrypt.");

            switch(reader.readLine()){
                case "enc":
                    encryptor.encryptFile(inputFileName,outputFileName,keyword);
                    break;
                case "dec":
                    encryptor.decryptFile(inputFileName,outputFileName,keyword);
                    break;
                default:
                    System.out.println("You didn't enter a valid option. Quitting.");
                    break;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
