package com.github.oxaoo.mp4ru.syntax.utils;

import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 18.03.2017
 */
public class ParserPreprocessor {

    /**
     * Read text from file.
     *
     * @return the text
     */
    @Deprecated
    public static String readText(String textFilePath) throws ReadInputTextException {
        try (BufferedReader br = new BufferedReader(new FileReader(textFilePath))) {
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
