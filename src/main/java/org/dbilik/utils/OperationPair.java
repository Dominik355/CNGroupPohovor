package org.dbilik.utils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public class OperationPair implements Iterable<Object>, Serializable {

    final String operation;
    final Double value;

    public OperationPair(String operation, Double value) {
        this.operation = Objects.requireNonNull(operation, "operation can not be null");
        this.value = Objects.requireNonNull(value, "value can not be null");
    }

    public String getOperation() {
        return operation;
    }

    public Double getValue() {
        return value;
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return operation;
            case 1:
                return value;
            default:
                return null;
        }
    }

    public <R> R mapOperation(Function<String, R> mapper) {
        return mapper.apply(operation);
    }

    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    public Object[] toArray() {
        return new Object[]{operation, value};
    }

    @Override
    public int hashCode() {
        int result = size();
        result = 21 * result + operation.hashCode();
        result = 21 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationPair pair2 = (OperationPair) o;

        return operation.equals(pair2.operation) && value.equals(pair2.value);
    }

    @Override
    public String toString() {
        return """
            OperationPair{
                operation='$operation', 
                value=$value
            }
            """;
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(toList()).iterator();
    }

    public int size() {
        return 2;
    }

    public static class OperationPairBuilder {
        private String operation;
        private Double value;

        public OperationPairBuilder withOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public OperationPairBuilder withValue(Double value) {
            this.value = value;
            return this;
        }

        public OperationPair build() {
            return new OperationPair(operation, value);
        }
    }

}