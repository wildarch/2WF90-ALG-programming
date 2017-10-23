package group14.polynomial;

import kotlin.Pair;
import kotlin.Triple;

/**
 * Performs Euclid's extended algorithm for two nonzero polynomials
 * <p>
 *
 * Input: Polynomials a and b (nonzero)
 * Output: Polynomials x, y and gcd(a,b) such that gcd(a, b) = x*a + y*b
 * Use getX(), getY() or getGCD to obtain these values
 *
 * @author Erik van Bennekum
 */
public class PolynomialEuclids {
    private Polynomial a, b;
    private Polynomial x, y;
    private Polynomial gcd;
    private Long modulus;

    public PolynomialEuclids(Polynomial a, Polynomial b) {
        if (a == null) {
            throw new IllegalArgumentException("Invalid Polynomials: First polynomial is null");
        }
        if (b == null) {
            throw new IllegalArgumentException("Invalid Polynomials: Second polynomial is null!");
        }
        if (a.getModulus() != b.getModulus()) {
            throw new IllegalArgumentException("Invalid Polynomials: Modulus not equal!");
        }
        else {
            modulus = a.getModulus();
        }
        if (a.equals(Polynomial.zero(modulus)) || b.equals(Polynomial.zero(modulus))) {
            throw new IllegalArgumentException("Invalid Polynomials: Zero polynomials not accepted!");
        }
        this.a = a;
        this.b = b;
    }


    /**
     * Executes the extended euclidean algorithm.
     *
     * @return A triple of form `(x, y, gcd(a,b))`.
     */
    public Triple<Polynomial, Polynomial, Polynomial> execute() {
        Polynomial q, x_old, y_old, u, v;
        x = new Polynomial(a.getModulus(), 1);
        y = Polynomial.zero(a.getModulus());
        u = Polynomial.zero(a.getModulus());
        v = new Polynomial(a.getModulus(), 1);

        while(!b.getZero()) {
            Pair<Polynomial, Polynomial> div = a.div(b);
            q = div.getFirst();
            a = b;
            b = div.getSecond();

            x_old = x;
            y_old = y;
            x = u;
            y = v;
            u = x_old.minus(q.times(u));
            v = y_old.minus(q.times(v));
        }
        gcd = a;

        return new Triple(getX(), getY(), getGcd());
    }

    public Polynomial getX() {
        if (x == null) {
            throw new IllegalStateException("Execute must be called first!");
        }
        return x;
    }

    public Polynomial getY() {
        if (x == null) {
            throw new IllegalStateException("Execute must be called first!");
        }
        return y;
    }

    public Polynomial getGcd() {
        if (x == null) {
            throw new IllegalStateException("Execute must be called first!");
        }
        return gcd;
    }
}
