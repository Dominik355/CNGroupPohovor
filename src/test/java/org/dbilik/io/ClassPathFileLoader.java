package org.dbilik.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ClassPathFileLoader implements FileLoader {

    @Override
    public Stream<String> loadFile(String filePath) {
        try {
            return Files.lines(
                    Paths.get(getClass().getClassLoader()
                            .getResource(filePath)
                            .toURI()));
        } catch (IOException e) {
            throw new RuntimeException("An IOException occured while loading file", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException("An URISyntaxException occured while loading file", e);
        }
    }

    public static ClassPathFileLoader getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    private static class LazySingletonHolder {
        static final ClassPathFileLoader INSTANCE = new ClassPathFileLoader();
    }

}
