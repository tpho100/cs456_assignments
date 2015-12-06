import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanh-Phong on 11/10/2015.
 */
public class EllipticCurves
{
    public static void main(String args[])
    {
        BigInteger p = new BigInteger("209906856686557297804189542037668914591");
        //Elliptic curve parameters
        BigInteger A = new BigInteger("275060611907024748799559873496763836649");
        BigInteger B = new BigInteger("316383891550644327351453900934778618911");

        //Generator
        Point G = new Point(new BigInteger("142575573608468184147610518192339856885"),new BigInteger("171382264163058547181797964002832042773"),p,A,B);

        //Public point p
        Point P = new Point(new BigInteger("121016959417073126968044060009247001163"),new BigInteger("94164671526931892755413090614070816713"),p,A,B);
        //Point dummy;
        //int N = 2; //start at 2
        /*
        while(true)
        { Finds N by recursively adding G to obtain P.

            dummy = G.recursiveAdd(N,G);

            if(dummy.getX().compareTo(P.getX()) == 0 && dummy.getY().compareTo(P.getY()) == 0)
            {
                System.out.println("FOUND N!!!!!: " + N);
                break;
            }
            else
            {
                N++;
                System.out.println("Current N: " + N);
            }
        }
        */

        BufferedReader reader = null;
        List<String> lines = new ArrayList<String>();

        try
        {
            reader = new BufferedReader((new FileReader( "a4cipher.txt")));
            String inLine;

            while((inLine = reader.readLine()) != null)
            {
                lines.add(inLine);
            }
        }
        catch(IOException ex)
        {
            System.out.println("File not found.");
        }

        for (String c : lines) {
            Point tempHalfMask = getHalfMask(c, p, A, B);
            Point tempCipherMask = getCipher(c, p, A, B);
            System.out.println(getCharacter(tempHalfMask, tempCipherMask, 3));
        }
    }

    public static char getCharacter(Point halfMask, Point cipher, int N)
    {
        Point F = halfMask.recursiveAdd(N, halfMask);
        //System.out.println(F.getX());
        //System.out.println(F.getY());
        Point negF = F.negateY(F);
        Point Message = cipher.addPoint(negF);
        BigInteger c = Message.getX();
        return (char) c.intValue();
    }

    public static Point getHalfMask(String s, BigInteger Q, BigInteger A, BigInteger B)
    {
        String delims = "[ ]+";
        String[] tokens = s.split(delims);
        String x = tokens[2];
        //System.out.println("x value"+ x);
        String y = tokens[3];

        return new Point(new BigInteger(x), new BigInteger(y), Q, A ,B);
    }

    public static Point getCipher(String s, BigInteger Q, BigInteger A, BigInteger B)
    {
        String delims = "[ ]+";
        String[] tokens = s.split(delims);
        String x = tokens[0];
        String y = tokens[1];

        return new Point(new BigInteger(x), new BigInteger(y), Q, A ,B);
    }
}
