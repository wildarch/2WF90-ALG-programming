package group14.polynomial;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialEuclidsTest {


    @Test(expected = IllegalStateException.class)
    public void getXTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(5, 1);
        Polynomial b = new Polynomial(5, 1);
        euclid = new PolynomialEuclids(a, b);

        Polynomial x = euclid.getX();
    }

    @Test(expected = IllegalStateException.class)
    public void getYTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(5, 1);
        Polynomial b = new Polynomial(5, 1);
        euclid = new PolynomialEuclids(a, b);

        Polynomial y = euclid.getY();
    }

    @Test(expected = IllegalStateException.class)
    public void getGCDTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(5, 1);
        Polynomial b = new Polynomial(5, 1);
        euclid = new PolynomialEuclids(a, b);

        Polynomial gcd = euclid.getGcd();
    }

    @Test(expected = IllegalArgumentException.class)
    public void ZeroTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(5, 0);
        Polynomial b = new Polynomial(5, 0);
        euclid = new PolynomialEuclids(a, b);
        euclid.execute();
        Polynomial x, y, gcd;
        x = euclid.getX();
        y = euclid.getY();
        gcd = euclid.getGcd();
    }

    @Test
    public void IntegerTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(5, 1, 0, 4);
        Polynomial b = new Polynomial(5, 1, 2, 1);
        euclid = new PolynomialEuclids(a, b);
        euclid.execute();
        Polynomial x, y, gcd;
        x = euclid.getX();
        y = euclid.getY();
        gcd = euclid.getGcd();/*
        System.out.println(a.toPolynomialString());
        System.out.println(b.toPolynomialString());
        System.out.println(x.toPolynomialString());
        System.out.println(y.toPolynomialString());
        System.out.println(gcd.toPolynomialString());*/
    }


    @Test
    public void PolynomialTest() {
        PolynomialEuclids euclid;
        Polynomial a = new Polynomial(2, 1, 1, 0, 1);
        Polynomial b = new Polynomial(2, 0, 1);
        euclid = new PolynomialEuclids(a, b);
        euclid.execute();
        Polynomial x, y, gcd;
        x = euclid.getX();
        y = euclid.getY();
        gcd = euclid.getGcd();

        //System.out.println("A: " + a.toPolynomialString());
        //System.out.println("B: " + b.toPolynomialString());
        //System.out.println("GCD: " + gcd.toPolynomialString());
        //System.out.println("X: " + x.toPolynomialString());
        //System.out.println("Y: " + y.toPolynomialString());
        assertEquals("GCD should equal 1", new Polynomial(2, 1), gcd);
        assertEquals("X should equal 1", new Polynomial(2, 1, 0, 0).toPolynomialString(), x.toPolynomialString());
        assertEquals("Y should equal X^2 + 1", new Polynomial(2, 1, 0, 1), y);
    }

}