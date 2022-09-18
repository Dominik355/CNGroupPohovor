package org.dbilik.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SystemFileLoader implements FileLoader {

    public Stream<String> loadFile(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            throw new RuntimeException(String.format("File [path=%s] could not be found", filePath));
        }

        try {
            return Files.lines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(String.format("An exception occured while reading data from the file [path=%s].", filePath), e);
        }
    }

    public static SystemFileLoader getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    private static class LazySingletonHolder {
        static final SystemFileLoader INSTANCE = new SystemFileLoader();
    }

}
