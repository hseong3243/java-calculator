package com.programmers.service;

import com.programmers.domain.model.Calculation;
import com.programmers.io.Console;
import com.programmers.repository.CalculationMemoryRepository;
import com.programmers.repository.CalculationRepository;
import com.programmers.util.CalculatiorTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.programmers.util.CalculatiorTestUtil.createCalculation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CalculationServiceTest {
    private final CalculationService calculatorService;
    private final CalculationRepository calculationRepository;

    public CalculationServiceTest() {
        calculationRepository = new CalculationMemoryRepository();
        Console console = new Console();

        calculatorService = new CalculationService(calculationRepository, console, console);
    }

    private static Stream<Arguments> inputData() {
        return Stream.of(
                Arguments.of("1   +   2 +  3", 6),
                Arguments.of("5 * 2 + 3  +  4 - 5 * 6", -13),
                Arguments.of("5 * 2 * 3 - 4 / 2 * 4", 22),
                Arguments.of("3 * 3 3", 99)
        );
    }

    @ParameterizedTest
    @MethodSource
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
        List<Calculation> result = calculationRepository.findAll();

        Calculation expected = createCalculation("1 + 2 + 3", 6);
        assertThat(result).containsExactly(expected);
    }

    @Test
    void findCalculations() {
        //given
        Calculation calculationA = createCalculation("1 + 2 + 3", 6);
        Calculation calculationB = createCalculation("1 * 2 - 3", -1);

        calculationRepository.save(calculationA);
        calculationRepository.save(calculationB);

        //when
        List<Calculation> resultA = calculatorService.findCalculations();

        //then
        assertThat(resultA).containsExactly(calculationA, calculationB);
    }
}
