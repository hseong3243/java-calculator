package com.programmers.io;

import com.programmers.vo.CalculationResult;

import java.util.List;

public interface Output {
    void printResult(List<CalculationResult> result);

    void printResult(int result);

    void exit();

    void inputError();

    void printError(RuntimeException ex);
}
