package com.programmers.enumtype;

import java.util.Arrays;
import java.util.Objects;

public enum ServiceSelection {
    LOOKUP_RECORDS("1"),
    CALCULATION("2"),
    EXIT_SERVICE("3");

    private final String number;

    ServiceSelection(String number) {
        this.number = number;
    }

    public static ServiceSelection getValue(String number) {
        return Arrays.stream(values())
                .filter(service -> Objects.equals(service.number, number))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바른 서비스 번호를 입력해주세요. 현재 입력: " + number));
    }
}
