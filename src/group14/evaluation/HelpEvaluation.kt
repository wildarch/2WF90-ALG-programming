package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * Prints the parse tree.
 *
 * @author Ruben Schellekens
 */
open class HelpEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        output.println()
        output.println("[some expression] (mod p)")
        output.println("- Outputs the result of the expression modulo p.")
        output.println("!NYI! [some expression] (mod p) (field d(X))")
        output.println("- Outputs the result of the expression modulo p in a given field.")
        output.println("!NYI! table <add|multiply> [q(X)] (mod p)")
        output.println("- Creates a addition/multiplication table of field Z/pZ[X]/(q(X)).")
        output.println("!NYI! euclid [p(X)] and [q(X)] (mod p)")
        output.println("- Executes the extended euclidean algorithm to produce gcd(p,q)= a*p(X) + b*q(X).")
        output.println("!NYI! isprimitive [p(X)] (mod p) (field d(X))")
        output.println("- Checks if p(X) is primitive in a given field.")
        output.println("!NYI! findprimitives (mod p) (field d(X))")
        output.println("- Finds all the primitive elements in a given fields.")
        output.println("!NYI! isirreducible [p(X)] (mod p)")
        output.println("- Checks if p(X) is irreducible.")
        output.println("!NYI! findirreducibles n (mod p)")
        output.println("- Find irreducible polynomials of degree n.")
        output.println("!NYI! elements (field d(X))")
        output.println("- Print all the elements in a given field.")
        output.println("parsetree [some input]")
        output.println("- (debug) Prints the parse tree of the given input.")
        output.println("exit")
        output.println("- Exits the program.")
    }
}