package group14

import group14.field.AdditionTableTest
import group14.field.FiniteFieldTest
import group14.field.MultiplicationTable
import group14.field.MultiplicationTableTest
import group14.integer.ModularIntegerTest
import group14.polynomial.*
import group14.parser.LexerTest
import group14.parser.ParserTest
import group14.parser.PolynomialConverterTest
import group14.polynomial.PolynomialArithmetic
import group14.polynomial.PolynomialLongDivision
import group14.polynomial.PolynomialTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Ruben Schellekens
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        PolynomialConverterTest::class,
        UtilKtTest::class,
        FiniteFieldTest::class,
        ModularIntegerTest::class,
        PolynomialArithmetic::class,
        PolynomialTest::class,
        LexerTest::class,
        ParserTest::class,
        ConcurrencyTest::class,
        PolynomialLongDivision::class,
        PolynomialIrreducible::class,
        PolynomialEuclidsTest::class,
        TableTest::class,
        AdditionTableTest::class,
        MultiplicationTableTest::class,
        ConcurrencyTest::class
)
object AlgebraTestSuite