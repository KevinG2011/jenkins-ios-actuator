package com.pepper.symbol;

import java.io.File;
import java.io.IOException;

public interface ISymbolFileHandler {
    // 提取标识符
    String extractIdentifier() throws IOException;

    // 解析文件
    File process() throws IOException;
}
