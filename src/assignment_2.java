import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;


public class assignment_2 
{
	public static void main(String[] args) throws IOException
	{
		int bitLength;
		String inputFileName;
		String outputFileName;

		RSA encryptor = new RSA();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter bit length for key: ");
		bitLength = Integer.valueOf(reader.readLine()).intValue();

		encryptor.generateKeys(bitLength);
		System.out.println("YOUR KEYS...");
		System.out.println("N: " + encryptor.getN());
		System.out.println("E: " + encryptor.getE());


		try {
			System.out.println("Enter input file name: ");
			inputFileName = reader.readLine();


			System.out.println("Enter output file name: ");
			outputFileName = reader.readLine();

			System.out.println("Type enc to encrypt or dec to decrypt.");
			System.out.println("Type dec2 to decrypt without private keys.");

			switch(reader.readLine()){
				case "enc":
					System.out.println("Enter n: ");
					BigInteger n = new BigInteger(reader.readLine());
					System.out.println("Enter e: ");
					BigInteger e = new BigInteger(reader.readLine());
					List<Character> characters = encryptor.readCharactersFromFile(inputFileName);
					List<BigInteger> ciphers = encryptor.encryptCharacters(characters, n, e);
					encryptor.writeBigIntegersToFile(ciphers,outputFileName);
					break;
				case "dec":
					System.out.println("Enter d: ");
					BigInteger d = new BigInteger(reader.readLine());
					System.out.println("Enter n: ");
					BigInteger n2 = new BigInteger(reader.readLine());

					List<BigInteger> cipherMessages = encryptor.readBigIntegersFromFile(inputFileName);
					List<Character> plainMessages = encryptor.decryptCharacters(cipherMessages, d, n2);
					encryptor.writeCharactersToFile(plainMessages,outputFileName);
					break;
				case "dec2":
					System.out.println("Enter n: ");
					BigInteger n3 = new BigInteger(reader.readLine());
					System.out.println("Enter e: ");
					BigInteger e2 = new BigInteger(reader.readLine());
					encryptor.decryptWithTable(inputFileName,outputFileName,n3,e2);
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
