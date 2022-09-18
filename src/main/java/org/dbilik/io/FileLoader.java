package org.dbilik.io;

import java.util.stream.Stream;

public interface FileLoader {

    Stream<String> loadFile(String filePath);

}
