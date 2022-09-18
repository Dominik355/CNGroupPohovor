package org.dbilik;

import org.dbilik.io.ClassPathFileLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExecutorTest {

    private Executor executor = new Executor(ClassPathFileLoader.getInstance());

    @ParameterizedTest
    @MethodSource("executeFromFileArgumentsProvider")
    public void test (String path, List<BigDecimal> expectedResults, boolean expectedException) {
        if (expectedException) {
            assertThrows(Exception.class, () -> executor.executeFromFile(path));
        } else {
            List<BigDecimal> results =  executor.executeFromFile(path);
            IntStream.range(0, results.size())
                    .forEachOrdered(i -> assertEquals(results.get(i).compareTo(expectedResults.get(i)), 0));
        }
    }

    static Stream<Arguments> executeFromFileArgumentsProvider() {
        return Stream.of(
                arguments(null, Collections.emptyList(), true),
                arguments("test0.txt", Collections.emptyList(), true),
                arguments("test1.txt", toBigDecimalList(20d), false),
                arguments("test2.txt", toBigDecimalList(20d, 20d), false),
                arguments("test3.txt", toBigDecimalList(4d, 20d, 20d), false),
                arguments("test4.txt", toBigDecimalList(5d), false),
                arguments("test5.txt", toBigDecimalList(8d, 0d, 2d), false),
                arguments("test6.txt", toBigDecimalList(8d, 0d, 2d, 4d, 20d), false),
                arguments("test7.txt", toBigDecimalList(5625.5), false),
                arguments("test8.txt", Collections.emptyList(), true),
                arguments("test9.txt", Collections.emptyList(), true),
                arguments("test10.txt", toBigDecimalList(10d), false),
                arguments("test11.txt", Collections.emptyList(), true)
        );
    }
    
    static List<BigDecimal> toBigDecimalList(Double... values) {
        return Stream.of(values)
                        .map(BigDecimal::new)
                        .collect(Collectors.toList());

    }

}
