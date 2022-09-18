package org.dbilik;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public enum Operation {

    ADD(BigDecimal::add),
    MULTIPLY(BigDecimal::multiply),
    SUBTRACT(BigDecimal::subtract),
    DIVIDE(BigDecimal::divide),
    APPLY(null);

    private BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

    Operation(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        this.function = function;
    }

    public static Operation getOperation(String operation) {
        return Operation.valueOf(operation.toUpperCase());
    }

    public static boolean isTrigger(String operation) {
        return APPLY.equals(getOperation(operation));
    }

    public static boolean isTrigger(Operation operation) {
        return APPLY.equals(operation);
    }

    public BiFunction<BigDecimal, BigDecimal, BigDecimal> getFunction() {
        return function;
    }
}
