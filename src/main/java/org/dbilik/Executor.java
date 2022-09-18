package org.dbilik;

import org.dbilik.io.FileLoader;
import org.dbilik.io.SystemFileLoader;
import org.dbilik.utils.OperationPair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Executor {

    private final FileLoader fileLoader;

    public Executor(FileLoader fileLoader) {
        Objects.requireNonNull(fileLoader, "FileLoader can not be null");
        this.fileLoader = fileLoader;
    }

    public List<BigDecimal> executeFromFile(String path) {
        if (path.isEmpty()) {
            throw new RuntimeException("Provided path can not be an empty string");
        }

        Stream<String> lines = fileLoader.loadFile(path);

        EquationHolder equationHolder = new EquationHolder();
        lines.forEach(equationHolder::add);

        equationHolder.validate();

        return executeEquationHolder(equationHolder);
    }

    private List<BigDecimal> executeEquationHolder(EquationHolder holder) {
        List<Integer> triggers = IntStream.range(0, holder.operations.size())
                .filter(i -> Operation.isTrigger(holder.operations.get(i).getOperation()))
                .boxed()
                .collect(Collectors.toList());


        List<BigDecimal> results = new ArrayList<>();
        int lastTrigger = 0;
        int i = 0;
        int currentVal;
        while (i < triggers.size()) {
            currentVal = triggers.get(i);
            if ((currentVal - lastTrigger) < 2) {
                results.add(BigDecimal.valueOf(holder.operations.get(currentVal).getValue()));
            } else {
                results.add(executeEquation(
                        holder.operations.subList(i == 0 ? 0 : triggers.get(i - 1) + 1, currentVal),
                        holder.operations.get(currentVal).getValue()
                ));
            }
            lastTrigger = currentVal;
            i++;
        }
        return results;
    }

    private BigDecimal executeEquation(List<OperationPair> operations, Double startingNumber) {
        BigDecimal result = BigDecimal.valueOf(startingNumber);
        for (OperationPair operationPair : operations) {
            result = Operation.getOperation(operationPair.getOperation())
                    .getFunction()
                    .apply(result, BigDecimal.valueOf(operationPair.getValue()));
        }
        return result;
    }

    private class EquationHolder {

        private ArrayList<OperationPair> operations = new ArrayList<>();
        private Double inputNumber;

        private void add(String line) {
            if (line.isBlank()) {
                return;
            }
            String[] splitted = line.split("\\s+", 2);
            add(splitted[0], splitted[1]);
        }

        private void add(String operation, String value) {
            add(new OperationPair(operation, Double.valueOf(value)));
        }

        private void add(OperationPair operationPair) {
            operations.add(operationPair);
        }

        private void setInputNumber(Double number) {
            this.inputNumber = number;
        }

        /**
         * Checks, if last list element is an APPLY operation
         */
        private void validate() {
            if (!Operation.isTrigger(operations.get(operations.size() - 1).getOperation())) {
                throw new RuntimeException("EquationHolder validation failed. Operations are not properly ended. Expected last operation is: APPLY");
            }
        }

    }

}
