package group14

import group14.field.AdditionTableTest
import group14.field.FiniteFieldTest
import group14.field.MultiplicationTable
import group14.field.MultiplicationTableTest
import group14.integer.ModularIntegerTest
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
        UtilKtTest::class,
        FiniteFieldTest::class,
        ModularIntegerTest::class,
        PolynomialArithmetic::class,
        PolynomialTest::class,
        PolynomialLongDivision::class,
        TableTest::class,
        AdditionTableTest::class,
        MultiplicationTableTest::class,
        ConcurrencyTest::class
)
object AlgebraTestSuite