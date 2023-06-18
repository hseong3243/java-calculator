package com.programmers.repository;

import com.programmers.vo.CalculationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationMemoryRepositoryTest {
    CalculatorMemoryRepository calculatorRepository = new CalculatorMemoryRepository();

    @Test
    void save() {
        //given
        CalculationResult calculation = new CalculationResult(List.of("1", "+", "2", "+", "3"), 6);

        //when
        calculatorRepository.save(calculation);

        //then
        List<CalculationResult> result = calculatorRepository.findAll();

        assertThat(result).containsExactly(calculation);
    }

    @Test
    void findAll() {
        //given
        CalculationResult calculationA = new CalculationResult(List.of("1", "+", "2", "+", "3"), 6);
        CalculationResult calculationB = new CalculationResult(List.of("1", "+", "3", "+", "5"), 6);

        //when
        calculatorRepository.save(calculationA);
        calculatorRepository.save(calculationB);

        //then
        List<CalculationResult> result = calculatorRepository.findAll();

        assertThat(result).containsExactly(calculationA, calculationB);
    }
}