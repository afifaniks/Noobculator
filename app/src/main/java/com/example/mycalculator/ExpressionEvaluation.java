package com.example.mycalculator;

import java.util.ArrayList;
import java.util.Stack;
/**
 * @author: Afif Al Mamun
 * @created_in: 2/16/19
 * @project_name: MyCalculator
 **/
public class ExpressionEvaluation {
    /**
     * This field will contain operators '+', '-', '*', '/', '%'.
     */
    private static Stack<Character> operator = new Stack<>();
    /**
     * expressionToken field is going to contain the whole expression
     * as different tokens of string. For example: for a-b*c
     * the list will contain "a", "-", "b", "*", "c".
     * Upon inputting values for different variables they are going to be
     * replaced by the actual values. Assume a = 3, b = 4, c = 4;
     * Then the list will be: "3", "-", "4", "*", "4".
     */
    private static ArrayList<String> expressionToken = new ArrayList<>();
    /**
     * This field will contain operands or the values of variables.
     */
    private static Stack<String> operand = new Stack<>();

    /**
     * Checks if a character is operator or not.
     * @param c: A character.
     * @return: An integer containing symbolizing the precedence of an operator
     * and -1 if not a character.
     */
    private static int isOperator(char c) {
        if (c == '+')
            return 1;
        else if (c == '-')
            return 2;
        else if (c == '\u00d7')
            return 3;
        else if (c == '\u00f7')
            return 4;
        else if (c == '%')
            return 5;
        else
            return -1;
    }

    /**
     * Performs mathematical operation.
     * @param x: Operand 1
     * @param y: Operand 2
     * @param operator: Operator
     * @return: The result of the operation
     */

    private static double calculate(double x, double y, char operator) {
        if (operator == '+')
            return x + y;
        else if (operator == '-')
            return x - y;
        else if (operator == '\u00d7')
            return x * y;
        else if (operator == '\u00f7')
            return x / y;
        else if (operator == '%')
            return x % y;

        return 0.0; // Should never reach
    }

    public static Double expressionEvaluation(String expression) {

        //Clearing previous data
        expressionToken.clear();
        operand.clear();
        operator.clear();

        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }

        char[] expressionArr = expression.toCharArray();
        String oper = "";

        try {
            // Parsing expression into tokens
            for (int i = 0; i < expressionArr.length; i++) {
                if (isOperator(expressionArr[i]) != -1) {
                    expressionToken.add(oper);
                    oper = "";
                    expressionToken.add(String.valueOf(expressionArr[i]));
                } else
                    oper += expressionArr[i];
            }

            expressionToken.add(oper);

            // Infix evaluation for every token in the expressionToken list
            for (String k : expressionToken) {
                if (isOperator(k.charAt(0)) == -1) {
                    operand.push(k);

                } else {
                    if (operator.isEmpty()) {
                        operator.push(k.charAt(0));
                    } else {
                        int precedenceOfStackTop = isOperator(operator.peek());
                        int precedenceOfOperator = isOperator(k.charAt(0));

                        if (precedenceOfStackTop < precedenceOfOperator) {
                            operator.push(k.charAt(0));
                        } else {
                            while ((!operator.isEmpty()) && (precedenceOfStackTop >= precedenceOfOperator)) {
                                double val1 = Double.valueOf(operand.pop());
                                double val2 = Double.valueOf(operand.pop());

                                double res = calculate(val2, val1, operator.pop());
                                operand.push(String.valueOf(res));

                                if (!operator.isEmpty())
                                    precedenceOfStackTop = isOperator(operator.peek());
                            }

                            operator.push(k.charAt(0));
                        }
                    }
                }
            }

            while (!operator.empty()) {
                double val1 = Double.valueOf(operand.pop());
                double val2 = Double.valueOf(operand.pop());
                double res = calculate(val2, val1, operator.pop());

                operand.push(String.valueOf(res));
            }

            Double result = Double.valueOf(operand.peek());
            return result;

        } catch (Exception e) {
            return Double.NaN;
        }

    }

}
