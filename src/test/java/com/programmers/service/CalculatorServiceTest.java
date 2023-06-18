package com.programmers.service;

import com.programmers.vo.CalculationResult;
import com.programmers.io.Console;
import com.programmers.repository.CalculatorMemoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService;
    private final CalculatorMemoryRepository calculatorRepository;

    public CalculatorServiceTest() {
        calculatorRepository = new CalculatorMemoryRepository();
        Console console = new Console();

        calculatorService = new CalculatorService(calculatorRepository, console, console);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1   +   2 +  3:6",
            "5 * 2 + 3  +  4 - 5 * 6:-13",
            "5 * 2 * 3 - 4 / 2 * 4:22",
            "3 * 3 3:99"
    }, delimiter = ':')
    void inputData(String input, int result) {
        //when
        int calculated = calculatorService.calculate(input);

        //then
        assertThat(calculated).isEqualTo(result);
    }

    @Test
    void inputDataButInvalidOrder_Then_Exception() {
        //given
        String inputA = "1 + 2 - - 2";

        //when

        //then
        assertThatThrownBy(() -> calculatorService.calculate(inputA))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void inputData_Then_Save() {
        //given
        String inputA = "1 + 2 + 3";

        //when
        calculatorService.calculate(inputA);

        //then
        List<CalculationResult> result = calculatorRepository.findAll();

        CalculationResult expected = new CalculationResult(List.of("1", "+", "2", "+", "3"), 6);
        assertThat(result).containsExactly(expected);
    }

    @Test
    void findCalculations() {
        //given
        CalculationResult calculationA = new CalculationResult(List.of("1", "+", "2", "+", "3"), 6);
        CalculationResult calculationB = new CalculationResult(List.of("1", "*", "2", "-", "3"), -1);

        calculatorRepository.save(calculationA);
        calculatorRepository.save(calculationB);

        //when
        List<CalculationResult> resultA = calculatorService.findCalculations();

        //then
        assertThat(resultA).containsExactly(calculationA, calculationB);
    }
}
