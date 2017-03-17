package com.github.oxaoo.mp4ru.ulils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.oxaoo.mp4ru.syntax.GlobalPropertyKeys.CONLL_TEXT_FILE;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 17.02.2017
 */
public class SyntaxUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SyntaxUtils.class);

    /**
     * Remove temporary files with tagged tokens [*.conll]
     */
    @Deprecated
    public void removeTemporaryFiles() {
        Path path = Paths.get(CONLL_TEXT_FILE);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            LOG.warn("An exception occurred during the removal of the temporary file [{}]. Cause: {}",
                    path.toString(), e.getMessage());
        }
    }

    public String makeParseFilePath(String textFilePath) {
        String parseFilePath = "text.parse";
        if (textFilePath == null || textFilePath.isEmpty() || !textFilePath.contains(".")) return parseFilePath;
//        parseFilePath = textFilePath.replaceAll("\\..*", ".parse");
        int pointId = textFilePath.lastIndexOf('.');
        if (pointId < 0) return parseFilePath;
        return textFilePath.substring(0, pointId) + ".parse";
    }
}
