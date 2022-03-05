package expression.exceptions;

import expression.exceptions.exception.ExpressionException;
import expression.exceptions.exception.ParseException;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ExpressionParser();
        try {
            var exp = parser.parse("1000000*x*x*x*x*x/(x-1)");
            System.out.printf("%-6s %s%n", "x", "f");
            for (int x = 0; x <= 10; x++) {
                System.out.printf("%-6d ", x);
                try {
                    System.out.println(exp.evaluate(x, 0, 0));
                } catch (ExpressionException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (ParseException e) {
            System.out.println("An error occurred while parsing expression: " + e);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
