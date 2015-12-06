import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thanh-Phong on 11/10/2015.
 */
public class ElGamal
{
    public static void main(String args[])
    {
        BigInteger p = new BigInteger("19042470332318432728008651603172496764482871966912234470903805373117374356151578219581287068314735220298290096045434417381846456457521048991135358521918839");
        BigInteger g = new BigInteger("10339323646367392962059504438371609835404003535458973512060207933290859300156257530954992191276395156650882585667266999679093976809553364405486541612529242");
        BigInteger A = new BigInteger("1964286381196281972067517961570042297806613013588351278303608271029649417750562973961097547424318371250720556572964548827920863755897610676680796866840229");
        BigInteger b = new BigInteger("8912940101409044180911382321435640248017029524633727570263440073385268705559048089919732833795398538228560794664979769199044625285022097666687617788765734");
        BigInteger a = new BigInteger("1");
        BigInteger Aprime = new BigInteger("1");
        BigInteger b1 = A;
        /*
        while( true )
        {
            Aprime = g.modPow(b1,p);
            if(Aprime.compareTo(A) == 0)
            {
                System.out.println("Found b1: " + b1);
                break;
            }
            else
            {
                b1 = b1.add(new BigInteger("1"));
                System.out.println("b1: " + b1 );
            }
        }

        //a = g.modPow(b,p);
        //System.out.println("a: "+ a);
        //BigInteger p = new BigInteger("23");
        //BigInteger g = new BigInteger("5");
        //BigInteger b = new BigInteger("3");
        */
        BufferedReader reader;
        List<String> lines = new ArrayList<>();

        try
        {
            reader = new BufferedReader((new FileReader( "a3cipher.txt")));
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


//        for(Iterator<String> itr = lines.iterator(); itr.hasNext();)
//        {
//            System.out.println(itr.next());
//        }

        for (String s : lines) {
            BigInteger hFm = getHalfMask(s);
            BigInteger ciph = getCipher(s);

            //System.out.println("Half-mask: " + hFm);
            //System.out.println("Cipher: " + ciph);
            BigInteger int1;
            int1 = hFm.modPow(b, p); //calculates [half-mask]^b mod po
            BigInteger int2;
            int2 = int1.modInverse(p); //finds inverse
            int2 = int2.mod(p);
            //System.out.println("modded inverse: "+ int2);
            BigInteger msg = ciph.multiply(int2);
            msg = msg.mod(p);
            System.out.println((char) msg.intValue());
        }




    }


    public static BigInteger getHalfMask(String s)
    {

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == ',')
            {
                //System.out.println(i);
                return new BigInteger(s.substring(0,i));
                //cipher = new BigInteger(s.substring(i+1,s.length()-1));
            }
        }
        return null;
    }

    public static BigInteger getCipher(String s)
    {

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == ',')
            {
                //System.out.println(i);
                //return new BigInteger(s.substring(0,i-1));
                return new BigInteger(s.substring(i+1,s.length()));
            }
        }
        return null;
    }
}


