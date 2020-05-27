package com.pepper.symbol;

import java.io.IOException;
import java.nio.file.Path;

public interface ISymbolFileHandler {
    // 提取标识符
    String extractIdentifier() throws IOException;

    // 解析文件
    Path process() throws IOException, InterruptedException;
}
