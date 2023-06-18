package com.programmers.repository;

import com.programmers.vo.CalculationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorMemoryRepository {
    private final Map<Integer, CalculationResult> calculations = new HashMap<>();

    public void save(CalculationResult result) {
        int nextId = calculations.size() + 1;

        calculations.put(nextId, result);
    }

    public List<CalculationResult> findAll() {
        return calculations.keySet()
                .stream().sorted()
                .map(calculations::get)
                .toList();
    }
}
