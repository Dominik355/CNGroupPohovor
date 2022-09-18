package org.dbilik;

import org.dbilik.io.SystemFileLoader;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new RuntimeException("There is 1 expected argument with Absolute Path of file. But.. it's missing");
        }
        nicePrint(new Executor(SystemFileLoader.getInstance()).executeFromFile(args[0]));
    }

    static void nicePrint(List<BigDecimal> list) {
        IntStream.range(0, list.size())
                .mapToObj(i -> "Equation " + (i + 1) + ": " + list.get(i))
                .forEachOrdered(System.out::println);
    }
}