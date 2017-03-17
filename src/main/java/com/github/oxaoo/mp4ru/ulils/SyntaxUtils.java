package com.github.oxaoo.mp4ru.ulils;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 17.02.2017
 */
public class SyntaxUtils {
    public String makeParseFilePath(String textFilePath) {
        String parseFilePath = "text.parse";
        if (textFilePath == null || textFilePath.isEmpty() || !textFilePath.contains(".")) return parseFilePath;
        int pointId = textFilePath.lastIndexOf('.');
        if (pointId < 0) return parseFilePath;
        return textFilePath.substring(0, pointId) + ".parse";
    }
}
