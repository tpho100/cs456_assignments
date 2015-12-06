import java.math.BigInteger;

/**
 * Created by Thanh-Phong on 11/10/2015.
 */
public class Point
{
    private BigInteger x; //X coordinate
    private BigInteger y; //Y coordinate
    private BigInteger q; //modulo
    private BigInteger A; //Elliptic curve parameter
    private BigInteger B; //Elliptic curve parameter

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getQ()
    {
        return q;
    }

    public BigInteger getA() {
        return A;
    }

    public BigInteger getB() {
        return B;
    }

    Point(BigInteger x, BigInteger y, BigInteger q, BigInteger A, BigInteger B)
    {
        this.x = x;
        this.y = y;
        this.q = q;
        this.A = A;
        this.B = B;
    }

    public Point addPoint(Point pointToAdd)
    {
        //Case I: Point addition x1 not equal to x2
        if(this.getX().mod(this.getQ()).compareTo(pointToAdd.getX().mod(pointToAdd.getQ())) != 0)
        {
            //System.out.println("Case I");
            BigInteger yprime = ( pointToAdd.getY().subtract(this.getY()) );
            BigInteger xprime = ( pointToAdd.getX().subtract(this.getX()) );
            xprime = xprime.modInverse(this.q);
            BigInteger m = (yprime.multiply(xprime)).mod(this.q);
            BigInteger c = this.getY().subtract(m.multiply(this.getX()));
            c = c.mod(q);

            BigInteger x3 = (m.multiply(m).subtract(this.getX().add(pointToAdd.getX()))).mod(q);
            BigInteger y3 = (m.multiply(x3).add(c)).mod(q);
            y3 = y3.negate().mod(q);

            return new Point(x3, y3, q, A, B);
        }
        else if( (this.getX().mod(this.getQ()).compareTo(pointToAdd.getX().mod(pointToAdd.getQ())) == 0) && (this.getY().mod(this.getQ()).compareTo(pointToAdd.getY().negate().mod(pointToAdd.getQ())) == 0) )
        { //Case II: x1 = x2 and y1 = -y2
            return new Point(new BigInteger("0"), new BigInteger("0"), pointToAdd.getQ(), A, B );
        }
        else if(this.getX().compareTo(pointToAdd.getX()) == 0)
        { //Case III: Point doubling.m
            BigInteger m = ( this.getX().multiply( this.getX() ) ).multiply(new BigInteger("3")).add(A);
            //System.out.println(m);

            BigInteger yprime = this.getY().multiply( new BigInteger("2") );
            //System.out.println(yprime);

            yprime = yprime.modInverse(q);
            //System.out.println(yprime);

            m = m.multiply(yprime).mod(q);
            //System.out.println("m: "+ m);
            BigInteger x3 = m.multiply(m).subtract( this.getX().multiply(new BigInteger("2")) );
            BigInteger y3 = this.getY().add(m.multiply(x3.subtract(this.getX())));
            y3 = y3.mod(q);
            //System.out.println("y3!: " + y3);
            y3 = y3.negate().mod(q);
            //System.out.println("y3!: " + y3);
            return new Point(x3.mod(q), y3, q, A, B);
        }
        else
        {
            System.out.println("Not one of the point options.");
            return null;
        }


    }

    public Point recursiveAdd(int N, Point secondPoint)
    {
        if(N == 1 || N == 0)
        {
            return secondPoint;
        }
        else
        {
            return recursiveAdd( N-1, this.addPoint(secondPoint) );
        }
    }

    public Point negateY(Point point)
    {
        return new Point(point.getX(),point.getY().negate().mod(q),q,A,B);
    }
}
