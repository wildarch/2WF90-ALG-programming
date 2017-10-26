package group14

import group14.field.AdditionTableTest
import group14.field.FiniteFieldIntegration
import group14.field.FiniteFieldTest
import group14.field.MultiplicationTableTest
import group14.integer.ModularIntegerTest
import group14.parser.LexerTest
import group14.parser.ParserTest
import group14.parser.PolynomialConverterTest
import group14.polynomial.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Ruben Schellekens
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        ConcurrencyTest::class,
        FuzzTester::class,
        TableTest::class,
        UtilKtTest::class,
        AdditionTableTest::class,
        FiniteFieldIntegration::class,
        FiniteFieldTest::class,
        MultiplicationTableTest::class,
        ModularIntegerTest::class,
        LexerTest::class,
        ParserTest::class,
        PolynomialConverterTest::class,
        PolynomialArithmetic::class,
        PolynomialEuclidsTest::class,
        PolynomialIntegration::class,
        PolynomialIrreducible::class,
        PolynomialLongDivision::class,
        PolynomialTest::class
)
object AlgebraTestSuite