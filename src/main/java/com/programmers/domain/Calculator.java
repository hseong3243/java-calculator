package com.programmers.domain;

import com.programmers.enumtype.Operator;
import com.programmers.util.Arithmetic;

import java.util.List;
import java.util.Stack;

public class Calculator {
    private final List<String> postfixExpression;

    public Calculator(List<String> infixExpression) {
        validateExpression(infixExpression);

        this.postfixExpression = PostfixConverter.convert(infixExpression);
    }

    private void validateExpression(List<String> infixExpression) {
        boolean numberTurn = true;
        for (String element : infixExpression) {
            if (Arithmetic.isNumber(element) && numberTurn) {
                numberTurn = false;
            } else if (Arithmetic.isOperator(element) && !numberTurn) {
                numberTurn = true;
            } else {
                throw new UnsupportedOperationException(Arithmetic.WRONG_EXPRESSION);
            }
        }

        if (numberTurn) {
            throw new UnsupportedOperationException(Arithmetic.WRONG_EXPRESSION);
        }
    }

    public int calculate() {
        Stack<Integer> numbers = new Stack<>();
        for (String element : postfixExpression) {
            numbers.push(processOperation(numbers, element));
        }

        return numbers.pop();
    }

    private int processOperation(Stack<Integer> numbers, String element) {
        if (Arithmetic.isNumber(element)) {
            return Integer.parseInt(element);
        }
        //token 이 연산자일 경우 스택 상위 2개의 값을 연산한 결과를 반환
        return binaryOperate(numbers.pop(), numbers.pop(), element);
    }

    private int binaryOperate(int a, int b, String operator) {
        return Operator.binaryOperate(b, a, operator);
    }
}
