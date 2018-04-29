package com.github.oxaoo.mp4ru.syntax.utils;

import com.github.oxaoo.mp4ru.common.ResourceResolver;
import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;
import com.github.oxaoo.mp4ru.exceptions.WriteToFileException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 17.02.2017
 */
public class ParserUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ParserUtils.class);

    public static String makeParseFilePath(String textFilePath) {
        try {
            String absFilePath = new File(textFilePath).getCanonicalPath();
            String fullPath = FilenameUtils.getFullPath(absFilePath);
            String baseName = FilenameUtils.getBaseName(absFilePath);
            return fullPath + baseName + ".parse";
        } catch (IOException e) {
            LOG.warn("Could not get the resulting path from the source path [{}].", textFilePath);
        }
        return "text.parse";
    }

    public static void writeParsedText(String fileName, List<String> strings) throws WriteToFileException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String string : strings) {
                bw.write(string + "\n");
            }
        } catch (IOException e) {
            throw new WriteToFileException("Failed to write the parsed text into a file.", e);
        }
    }

    /**
     * Read text from file.
     *
     * @return the text
     */
    public static String readText(String textFilePath) throws ReadInputTextException {
        try (BufferedReader br = new BufferedReader(ResourceResolver.getResourceAsStreamReader(textFilePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException e) {
            throw new ReadInputTextException("Failed to read the text file \'" + textFilePath + "\'.", e);
        }
    }
}
