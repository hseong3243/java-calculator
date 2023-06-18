package com.programmers.service;

import com.programmers.domain.Calculator;
import com.programmers.domain.Tokenizer;
import com.programmers.vo.CalculationResult;
import com.programmers.enumtype.ServiceSelection;
import com.programmers.io.Input;
import com.programmers.io.Output;
import com.programmers.repository.CalculatorMemoryRepository;

import java.util.List;

public class CalculatorService implements Runnable {
    private final CalculatorMemoryRepository calculatorRepository;
    private final Input input;
    private final Output output;

    public CalculatorService(CalculatorMemoryRepository calculatorRepository, Input input, Output output) {
        this.calculatorRepository = calculatorRepository;
        this.input = input;
        this.output = output;
    }

    public int calculate(String input) {
        List<String> infixExpression = Tokenizer.tokenizeAsExpression(input);

        Calculator calculator = new Calculator(infixExpression);
        int result = calculator.calculate();

        CalculationResult calculationResult = new CalculationResult(infixExpression, result);

        calculatorRepository.save(calculationResult);

        return result;
    }

    public List<CalculationResult> findCalculations() {
        return calculatorRepository.findAll();
    }

    @Override
    public void run() {
        while (true) {
            String serviceNumber = input.selectService();

            boolean exitService = progressService(serviceNumber);
            if (exitService) {
                break;
            }
        }
    }

    private boolean progressService(String serviceNumber) {
        boolean exitService = false;
        try {
            ServiceSelection selected = ServiceSelection.getValue(serviceNumber);
            exitService = selectService(selected);
        } catch (RuntimeException ex) {
            output.printError(ex);
        }

        return exitService;
    }

    private boolean selectService(ServiceSelection selectedService) {
        boolean exitService = false;
        switch (selectedService) {
            case LOOKUP_RECORDS -> {
                List<CalculationResult> findCalculations = findCalculations();
                output.printResult(findCalculations);
            }
            case CALCULATION -> {
                String calculation = input.inputCalculation();
                int result = calculate(calculation);
                output.printResult(result);
            }
            case EXIT_SERVICE -> {
                output.exit();
                exitService = true;
            }
        }

        return exitService;
    }
}
