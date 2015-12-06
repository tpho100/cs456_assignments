import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Thanh-Phong on 11/5/2015.
 */
public class RSA
{
    private BigInteger n;
    private BigInteger p;
    private BigInteger q;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public void generateKeys(int bitLength){
        p = BigInteger.probablePrime(bitLength, new Random());
        q = BigInteger.probablePrime(bitLength, new Random());
        n = p.multiply(q);
        BigInteger phi1 = p.subtract(BigInteger.ONE);
        BigInteger phi2 = q.subtract(BigInteger.ONE);
        phi = phi1.multiply(phi2);
        e = BigInteger.probablePrime(bitLength /2, new Random());

        while(phi.gcd(e).compareTo(BigInteger.ONE)> 0 && e.compareTo(phi) < 0){
            e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
        System.out.println("Keys successfully generated.\n");
    }

    public BigInteger encryptChar(char c, BigInteger n, BigInteger e) {
        int ascii = (int) c;
        BigInteger asciii = BigInteger.valueOf(ascii);
        return asciii.modPow(e,n);
    }

    public void decryptWithTable(String inputFile, String outputFile, BigInteger n, BigInteger e){

        BufferedReader reader;

        Character[] asciiArr = new Character[128];

        //System.out.println("Generating ASCII table...\n");
        for(int i = 0; i < 128; i++)
        {
            asciiArr[i] = (char) i;
        } // Generate ascii table as Character (object) array

        List<BigInteger> map1 = new ArrayList<>();
        Map<BigInteger,Character> mapping = new HashMap<>();

        for(int i = 0; i < 128; i++)
        {
            //System.out.println("Mapping..." + i + "\n");
            map1.add(encryptChar(asciiArr[i], n, e));
        }

        Iterator<BigInteger> itr = map1.iterator();
        for(int j = 0; j < 128; j++)
        {
            mapping.put(itr.next(), asciiArr[j]);
            //System.out.println("iteration " + j + "\n");
        }

        /*
        Grab the entire text file  contents line by line
        Dump as big integers
        */

        ArrayList<BigInteger> cipher = new ArrayList<>(); //contains file info
        try
        {
            reader = new BufferedReader(new FileReader(inputFile));

            String inLine;
            while(!((inLine = reader.readLine()) == null))
                cipher.add(new BigInteger(inLine));
            reader.close();
        }
        catch(IOException ex)
        {
            System.err.println("File not found.");
        }

        BufferedWriter writer;
        try
        {
            writer = new BufferedWriter(new FileWriter(outputFile));

            for (BigInteger current : cipher) {
                //System.out.println(mapping.get(current));
                writer.write(mapping.get(current));
                writer.newLine();
                //Uncomment above if you want to see what the character for each line really is.
            }
            writer.close();
        }
        catch(IOException exe)
        {
            exe.printStackTrace();
        }
    }

    public Character decryptChar(BigInteger m, BigInteger d, BigInteger n){
        return (char) m.modPow(d,n).intValue();
    }

    public List<BigInteger> encryptCharacters(List<Character> characters, BigInteger n, BigInteger e){
        List<BigInteger> encryptedCharacters = new ArrayList<>();
        for(char c: characters){
            encryptedCharacters.add( encryptChar(c,n,e) );
        }
        return encryptedCharacters;
    }

    public List<Character> decryptCharacters(List<BigInteger> messages, BigInteger d, BigInteger n){
        List<Character> decryptedCharacters = new ArrayList<>();
        for(BigInteger b: messages){
            decryptedCharacters.add( decryptChar(b,d,n) );
        }
        return decryptedCharacters;
    }

    public void writeBigIntegersToFile(List<BigInteger> content, String outputFileName){
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter(outputFileName));
            for(BigInteger b : content){
                writer.write(b.toString());
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Could not create file.");
        }

    }

    public List<Character> readCharactersFromFile(String inputFileName){
        BufferedReader reader;
        List<Character> lines = new ArrayList<>();

        try
        {
            reader = new BufferedReader((new FileReader( inputFileName )));
            String inLine;

            while((inLine = reader.readLine()) != null)
            {
                if(inLine.length() == 0)
                    lines.add((char) 10);
                else
                    lines.add(inLine.charAt(0));
            }
            reader.close();
        }
        catch(IOException ex)
        {
            System.out.println("File not found.");
        }

        return lines;
    }

    public List<BigInteger> readBigIntegersFromFile(String inputFileName){
        BufferedReader reader;
        List<BigInteger> lines = new ArrayList<>();

        try
        {
            reader = new BufferedReader((new FileReader( inputFileName )));
            String inLine;

            while((inLine = reader.readLine()) != null)
            {
                lines.add(new BigInteger(inLine));
            }
            reader.close();
        }
        catch(IOException ex)
        {
            System.out.println("File not found.");
        }

        return lines;
    }

    public void writeCharactersToFile(List<Character> content, String outputFileName){
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter(outputFileName));
            for(char b : content){
                writer.write(b);
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Could not create file.");
        }

    }


}
