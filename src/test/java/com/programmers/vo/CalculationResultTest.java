package com.programmers.vo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationResultTest {

    @Test
    void getFullCalculation() {
        //given
        CalculationResult calculation = new CalculationResult(List.of("1", "+", "2"), 3);

        //when
        String fullCalculation = calculation.getFullCalculation();

        //then
        assertThat(fullCalculation).isEqualTo("1 + 2 = 3");
    }
}