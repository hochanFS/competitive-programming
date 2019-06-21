package hackercup.y2019.qualification;

import java.io.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 *
 * The whole expression E is either a single literal (Case 1),
 * or a binary operator wrapped by a parentheses (Case 2)
 *
 * Case 1:
 * If the whole expression E is literal (0, 1, x, X), 1 change is required iff it is X or x.
 *
 * Case 2:
 * Let us assume we have calculated all the expressions of inner parentheses except the very last one.
 * Thus, now only two expressions and operator remain within the parentheses.
 *
 * Each of the two expressions may be:
 * >> 1: always true regardless of x
 * >> 0: always false regardless of x
 * >> x: always equal to the value of x
 * >> X: always the negate of the value of x
 *
 * In order to render the final expression independent of the value of x,
 * we still need at most 1 alterations to make: to the operator that joins the final two expressions!
 *
 * To see this, observe that there are 8 combinations that involve at least one variable.
 * (If it does not involve variable, it is already determined.)
 *
 * x (op) x >> replace the final op as ^ -> always false
 * x (op) X >> replace the final op as ^ -> always true
 * x (op) 1 >> replace the final op as | -> always true
 * x (op) 0 >> replace the final op as & -> always false
 * X (op) x >> replace the final op as ^ -> always true
 * X (op) X >> replace the final op as ^ -> always false
 * X (op) 1 >> replace the final op as | -> always true
 * X (op) 0 >> replace the final op as & -> always false
 *
 * We then just need to be able to check whether the boolean expression value depends on x.
 * Note: if the expression result when x is true == the expression result when x is false,
 * it is independent.
 * If not, it is dependent.
 *
 * To evaluate the boolean expression, I included BooleanEvaluator class, which evaluates
 * the boolean expression by repeatedly calling literal() and expression() functions.
 */

public class SolutionC {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2019\\qualification\\inputC.txt"; // renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2019\\qualification\\solutionC.txt"); // renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                char[] tokens = br.readLine().trim().toCharArray();
                BooleanEvaluator evaluatorIfTrue = new BooleanEvaluator(tokens, true);
                BooleanEvaluator evaluatorIfFalse = new BooleanEvaluator(tokens, false);
                int solution = 0;
                if (evaluatorIfFalse.evaluate() ^ evaluatorIfTrue.evaluate()) {
                    solution = 1;
                }
                printWriter.printf("Case #%d: %d\n", i, solution);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static class BooleanEvaluator {
        private char[] tokens;
        private int index;
        private boolean x;

        BooleanEvaluator(char[] tokens, boolean x) {
            this.tokens = tokens;
            this.x = x;
            this.index = 0;
        }

        boolean evaluate() {
            index = 0;
            return literal();
        }

        private boolean literal() throws RuntimeException {
            switch(tokens[index++]) {
                case '(':
                {
                    boolean b = expression();
                    if (tokens[index++] != ')')
                        throw new RuntimeException("')' expected at position " + index + " got: " + tokens[index - 1]);
                    return b;
                }
                case 'x':
                    return x;
                case 'X':
                    return !x;
                case '1':
                    return true;
                case '0':
                    return false;
                default:
                    throw new RuntimeException("Literal expected at position " + index);
            }
        }

        private boolean expression() throws RuntimeException {
            boolean left = literal();
            char operator = tokens[index++];
            boolean right = literal();
            switch (operator) {
                case '^':
                    return left ^ right;
                case '|':
                    return left || right;
                case '&':
                    return left && right;
                default:
                    throw new RuntimeException("Unknown operator " + tokens[index] + "at position " + index);
            }
        }

    }

}
