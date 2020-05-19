package com.pepper.symbol;

import java.io.File;
import java.io.IOException;

public interface ISymbolFileHandler {

    // 是否包含关键信息
    boolean hasValidKeyword() throws IOException;

    // 提取标识符
    String extractIdentifier() throws IOException;

    // 解析文件
    File process();
}
