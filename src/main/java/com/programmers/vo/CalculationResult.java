package com.programmers.vo;

import java.util.List;
import java.util.Objects;

public class CalculationResult {
    private final List<String> infixExpression;
    private final int result;

    public CalculationResult(List<String> infixExpression, int result) {
        this.infixExpression = infixExpression;
        this.result = result;
    }

    public String getFullCalculation() {
        StringBuilder sb = new StringBuilder();
        infixExpression.forEach(element -> {
            sb.append(element);
            sb.append(" ");
        });

        sb.append("= ");
        sb.append(result);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationResult that = (CalculationResult) o;
        return result == that.result && Objects.equals(infixExpression, that.infixExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infixExpression, result);
    }
}
